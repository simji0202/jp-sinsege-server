package kr.co.paywith.pw.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.co.paywith.pw.data.repository.enumeration.NotifType;
import kr.co.paywith.pw.data.repository.mbs.brand.Brand;
import kr.co.paywith.pw.data.repository.mbs.brand.BrandRepository;
import kr.co.paywith.pw.data.repository.mbs.brand.QBrand;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.QMrhstTrmnl;
import kr.co.paywith.pw.data.repository.mbs.notif.Notif;
import kr.co.paywith.pw.data.repository.mbs.notif.NotifRepository;
import kr.co.paywith.pw.data.repository.mbs.notif.QNotif;
import kr.co.paywith.pw.data.repository.mbs.notifMrhst.NotifMrhst;
import kr.co.paywith.pw.data.repository.mbs.notifUser.NotifUser;
import kr.co.paywith.pw.data.repository.user.userApp.QUserApp;
import kr.co.paywith.pw.data.repository.user.userApp.UserApp;
import kr.co.paywith.pw.data.repository.user.userApp.UserAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushMsgService {

  private final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

  // Firebase 에 한 번 요청할 때 보낼 메시지(메시지+대상 기기 토큰) 개수
  private final int MAX_PER_SEND = 100;

  @Autowired
  private NotifRepository notifRepository;

  @Autowired
  private UserAppRepository userAppRepository;

  @Autowired
  private MrhstTrmnlRepository mrhstTrmnlRepository;

  @Autowired
  private BrandRepository brandRepository;

  /**
   * 푸시 정보를 입력했을 때 정기적으로 푸시 전송
   *
   * FIXME 개별적으로 바로바로 전송하는게 좋을지 검토 필요. 개별 전송 시 sendNotifUserToFcm / sendNotifMrhstToFcm 을 호출?
   */
//  @Scheduled(cron = "0 * * * * *")
//  @SchedulerLock(name = "sendNotif", lockAtMostFor = "50m", lockAtLeastFor = "3m")
  public void sendNotif() {
    QBrand qBrand = QBrand.brand;
    BooleanBuilder bb = new BooleanBuilder();
    bb.and(qBrand.brandCd.eq(""));

    for (Brand brand : brandRepository.findAll(bb)) {
      if (brand.getBrandSetting() == null) {
        continue;
      }
      if (!StringUtils.isEmpty(brand.getBrandSetting().getFcmKey())) {
        sendNotifUser(brand.getBrandSetting().getFcmKey());
      }
      if (!StringUtils.isEmpty(brand.getBrandSetting().getPosFcmKey())) {
        sendNotifMrhst(brand.getBrandSetting().getPosFcmKey());
      }
    }
  }

  /**
   * 미 전송 NotifMrhst 모두 전송
   */
  public void sendNotifMrhst(String fcmKey) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String nowDttmStr = ZonedDateTime.now().format(format);

    // 매장 푸시 조회 후 전송
    BooleanBuilder bb = new BooleanBuilder();
    QNotif qNotif = QNotif.notif;
    bb.and(qNotif.notifType.eq(NotifType.MRHST_TRMNL));
    bb.and(
        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qNotif.sendReqDttm, ConstantImpl.create("%Y-%m-%d %H:%i:%s"))
        .loe(nowDttmStr)
    );

    for (Notif notif: notifRepository.findAll(bb)) {
      sendNotifMrhstToFcm(notif, fcmKey);
    }
  }

  /**
   * 미 전송 NotifUser 모두 전송
   */
  public void sendNotifUser(String fcmKey) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String nowDttmStr = ZonedDateTime.now().format(format);

    // 매장 푸시 조회 후 전송
    BooleanBuilder bb = new BooleanBuilder();
    QNotif qNotif = QNotif.notif;
    bb.and(qNotif.notifType.eq(NotifType.USER));
    bb.and(
        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qNotif.sendReqDttm, ConstantImpl.create("%Y-%m-%d %H:%i:%s"))
        .loe(nowDttmStr)
    );

    for (Notif notif: notifRepository.findAll(bb)) {
      sendNotifUserToFcm(notif, fcmKey);
    }
  }

  public void sendNotifUserToFcm(Notif notif, String fcmKey) {
    log.debug("푸시 전송 시작 >>> {}", notif);
    List<NotifUser> list = notif.getNotifUserList();

    Set<Integer> UserIdSet = new HashSet<>();
    if (StringUtils.isEmpty(fcmKey)) {
      // 푸시 서버 키가 없다면 종료
      return;
    }

    // 구글에 전송하는 건 비동기 처리
    new Thread(() -> {
      int resultCnt = 0;
      try {
        for (NotifUser nu : list) {
          UserIdSet.add(nu.getId());
          nu.setSendDttm(ZonedDateTime.now());

          BooleanBuilder bb = new BooleanBuilder();
          QUserApp q = QUserApp.userApp;
          bb.and(q.userInfo.id.eq(nu.getUserId()));
          bb.and(q.pushFl.isTrue());
          bb.and(q.pushKey.isNotNull());

          List<UserApp> userAppList = new ArrayList<>();
          userAppRepository.findAll(bb).forEach(userAppList::add);

          List<Header> headers = Lists.newArrayList(
              new BasicHeader(
                  HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8"),
              new BasicHeader(
                  HttpHeaders.AUTHORIZATION, "key=" + fcmKey)
          );

          CloseableHttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();
          HttpPost httpPost = new HttpPost(FCM_URL);

          ObjectMapper mapper = new ObjectMapper();

          Map<String, Object> payload = new HashMap<>();

          Map<String, Object> noti = new HashMap<>();
          noti.put("title", notif.getNotifSj());
          noti.put("body", notif.getNotifCn());
          payload.put("notification", noti);
          payload.put("priority", "high");

          TypeReference<HashMap<String, Object>> typeRef
              = new TypeReference<HashMap<String, Object>>() {
          };

          if (StringUtils.isNotEmpty((notif.getNotifData()))) {
            try {
              payload.put("data", mapper.readValue(notif.getNotifData(), typeRef));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }

          int sendStart = 0;
          int sendEnd = 0;

          List<String> tokenList = new ArrayList<>();
          // 적절한 token 개수 한번에 전송
          do {
            if (sendEnd + MAX_PER_SEND > userAppList.size()) {
              sendEnd = userAppList.size();
            } else {
              sendEnd = sendEnd + MAX_PER_SEND;
            }

            tokenList.clear();
            for (int i = sendStart; i < sendEnd; i++) {
              UserApp userApp = userAppList.get(i);

              if (StringUtils.isNotEmpty(userApp.getPushKey())) {
                tokenList.add(userApp.getPushKey());
              }
            }

            payload.put("registration_ids", tokenList);

            String str = mapper.writeValueAsString(payload);
            httpPost.setEntity(new StringEntity(str, "UTF-8"));

            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() >= 400) {
              StringWriter writer = new StringWriter();
              IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
              String result = writer.toString();
              log.warn("Send Error >>> {} ///  notifId : {}, start : {}, end : {}] ", result,
                  notif.getId(), sendStart, sendEnd);
            } else {
              Map<String, Object> result = mapper
                  .readValue(response.getEntity().getContent(), Map.class);
              // {multicast_id=7058037433648543745, success=0, failure=1, canonical_ids=0, results=[{error=NotRegistered}]}
              log.debug(result.toString());
              List<Map<String, Object>> resultList = (List<Map<String, Object>>) result
                  .get("results");
              int deleteAppCnt = 0;
              for (int i = 0; i < resultList.size(); i++) {
                if ("NotRegistered".equals(resultList.get(i).get("error"))) {
                  deleteAppCnt++;
                  UserApp userApp = userAppList.get(i);
                  userAppRepository.delete(userApp);
                }
              }
              if (deleteAppCnt > 0) {
                log.warn("Delete {} Devices' Info", deleteAppCnt);
              }
              resultCnt += (int) result.get("success");
            }
            sendStart = sendEnd;
            client.close();
          } while (sendStart < userAppList.size());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      notif.setSendCnt(resultCnt);
      notifRepository.save(notif);

    }).start();

  }


  public void sendNotifMrhstToFcm(Notif notif, String fcmKey) {
    log.debug("푸시 전송 시작 >>> {}", notif);
    List<NotifMrhst> list = notif.getNotifMrhstList();

    Set<Integer> mrhstSnSet = new HashSet<>();
    if (StringUtils.isEmpty(fcmKey)) {
      // 푸시 서버 키가 없다면 종료
      return;
    }

    new Thread(() -> {
      int resultCnt = 0;
      try {
        for (NotifMrhst nm : list) {
          mrhstSnSet.add(nm.getId());
          nm.setSendDttm(ZonedDateTime.now());

          BooleanBuilder bb = new BooleanBuilder();
          QMrhstTrmnl q = QMrhstTrmnl.mrhstTrmnl;
          bb.and(q.mrhst.id.eq(nm.getMrhstId()));
          bb.and(q.pushFl.isTrue());
          bb.and(q.pushKey.isNotNull());

          List<MrhstTrmnl> mrhstTrmnlList = new ArrayList<>();
          mrhstTrmnlRepository.findAll(bb).forEach(mrhstTrmnlList::add);

          List<Header> headers = Lists.newArrayList(
              new BasicHeader(
                  HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8"),
              new BasicHeader(
                  HttpHeaders.AUTHORIZATION, "key=" + fcmKey)
          );

          CloseableHttpClient client = HttpClients.custom().setDefaultHeaders(headers).build();
          HttpPost httpPost = new HttpPost(FCM_URL);

          ObjectMapper mapper = new ObjectMapper();

          Map<String, Object> payload = new HashMap<>();

          Map<String, Object> noti = new HashMap<>();
          noti.put("title", notif.getNotifSj());
          noti.put("body", notif.getNotifCn());
          payload.put("notification", noti);
          payload.put("priority", "high");

          TypeReference<HashMap<String, Object>> typeRef
              = new TypeReference<HashMap<String, Object>>() {
          };

          if (StringUtils.isNotEmpty((notif.getNotifData()))) {
            try {
              payload.put("data", mapper.readValue(notif.getNotifData(), typeRef));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }

          int sendStart = 0;
          int sendEnd = 0;

          List<String> tokenList = new ArrayList<>();
          // 적절한 token 개수 한번에 전송
          do {
            if (sendEnd + MAX_PER_SEND > mrhstTrmnlList.size()) {
              sendEnd = mrhstTrmnlList.size();
            } else {
              sendEnd = sendEnd + MAX_PER_SEND;
            }

            tokenList.clear();
            for (int i = sendStart; i < sendEnd; i++) {
              MrhstTrmnl mtl = mrhstTrmnlList.get(i);

              if (StringUtils.isNotEmpty(mtl.getPushKey())) {
                tokenList.add(mtl.getPushKey());
              }
            }

            payload.put("registration_ids", tokenList);

            String str = mapper.writeValueAsString(payload);
            httpPost.setEntity(new StringEntity(str, "UTF-8"));

            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() >= 400) {
              StringWriter writer = new StringWriter();
              IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
              String result = writer.toString();
              log.warn("Send Error >>> {} ///  notifId : {}, start : {}, end : {}] ", result,
                  notif.getId(), sendStart, sendEnd);
            } else {
              Map<String, Object> result = mapper
                  .readValue(response.getEntity().getContent(), Map.class);
              // {multicast_id=7058037433648543745, success=0, failure=1, canonical_ids=0, results=[{error=NotRegistered}]}
              log.debug(result.toString());
              List<Map<String, Object>> resultList = (List<Map<String, Object>>) result
                  .get("results");
              int deleteAppCnt = 0;
              for (int i = 0; i < resultList.size(); i++) {
                if ("NotRegistered".equals(resultList.get(i).get("error"))) {
                  deleteAppCnt++;
                  MrhstTrmnl mtl = mrhstTrmnlList.get(i);
                  mtl.setPushKey(null);
                  mrhstTrmnlRepository.save(mtl);
                }
              }
              if (deleteAppCnt > 0) {
                log.warn("Delete {} Devices' Info", deleteAppCnt);
              }
              resultCnt += (int) result.get("success");
            }
            sendStart = sendEnd;
            client.close();
          } while (sendStart < mrhstTrmnlList.size());
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      notif.setSendCnt(resultCnt);
      notifRepository.save(notif);
    }).start();

  }
}
