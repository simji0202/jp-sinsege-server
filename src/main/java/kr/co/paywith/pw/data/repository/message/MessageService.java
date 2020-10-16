package kr.co.paywith.pw.data.repository.message;


import com.popbill.api.KakaoService;
import com.popbill.api.PopbillException;

import com.querydsl.core.BooleanBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import kr.co.paywith.pw.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {


  @Autowired
  AppProperties appProperties;

  @Autowired
  com.popbill.api.MessageService popbillMessageService;

  @Autowired
  KakaoService popbillKakaoService;

  @Autowired
  MsgRuleRepository msgRuleRepository;


  /**
   * @param sendDttm yyyyMMddHHmmss: ex)20190717130000 (2019년 7월 17일 13시 0분 0초)
   * @see "https://docs.popbill.com"
   */
  public String sendMsgByRule(String receiverNum, String sendDttm, MsgRuleCd msgRuleCd,
      Map<String, Object> params) {

    // 메시지 룰로 검색
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QMsgRule qmr = QMsgRule.msgRule;
    booleanBuilder.and(qmr.msgRuleCd.eq(msgRuleCd));
    booleanBuilder.and(qmr.activeFl.isTrue());

    // 검색
    MsgRule msgRule = msgRuleRepository.findOne(booleanBuilder).orElse(null);
    // 검색된 룰 없으면 return null
    if (msgRule == null ) {
      return null;
    }

    // 룰에 해당하는 템플릿 없으면 return null
    MsgTemplate msgTemplate = msgRule.getMsgTemplate();
    if (msgTemplate == null) {
      return null;
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    Date date = null;
    String subject = null;
    String content = null;

    try {
      switch (msgTemplate.getMsgTypeCd()) {
        case XMS:
          if (sendDttm != null) {
            date = format.parse(sendDttm);
          }
          subject = replaceMsgParams(msgTemplate.getMsgSj(), params);
          content = replaceMsgParams(msgTemplate.getMsgCn(), params);

          /**
           * 단/장문 문자메시지(메시지 길이에 따라 단문/장문을 선택하여 전송) 1건 전송
           *
           * @param CorpNum      연동회원 사업자번호
           * @param sender       발신자번호
           * @param receiver     수신자번호
           * @param receiverName 수신자명칭
           * @param subject     메시지 제목
           * @param content     메시지 내용
           * @param reserveDT   예약전송시 예약일시.
           * @param adsYN       광고문자 전송여부.
           * @param UserID      연동회원의 회원아이디
           * @param requestNum  전송요청번호
           * @return receiptNum 접수번호.
           * @throws PopbillException
           */
          return popbillMessageService.sendXMS(appProperties.getCorpNum(), appProperties.getSenderNum(), receiverNum, null,
                  subject, content, date, false, appProperties.getUserID(), null);
        case KAKAO_ATS:

          content = replaceMsgParams(msgTemplate.getMsgCn(), params);

          // 알림톡 템플릿 코드
          String templateCode = msgRule.getMsgTemplate().getAltTemplateCode();
          // TODO: 알림톡 템플릿 심사 필요
          /**
           * 알림톡 단건전송
           * @param CorpNum  연동회원 사업자번호
           * @param templateCode  알림톡 템플릿코드
           * @param senderNum     발신번호
           * @param content       알림톡 내용
           * @param altContent    대체문자 내용
           * @param altSendType   대체문자 유형  [ 공백-미전송, C-친구톡내용, A-대체문자내용 중 택1 ]
           * @param receiverNum   수신번호
           * @param receiverName  수신자명
           * @param sndDT         예약일시
           * @param UserID        연동회원 아이디
           * @param requestNum    전송요청번호
           * @return receiptNum   접수번호
           */
          String altContent = null, receiverName = null, requestNum = null;
          return popbillKakaoService.sendATS(
              appProperties.getCorpNum(),
              templateCode,
              appProperties.getSenderNum(),
              content,
              altContent,
              "C",
              receiverNum,
              receiverName,
              sendDttm,
              appProperties.getUserID(),
              requestNum
          );

        case KAKAO_FTS:
          content = replaceMsgParams(msgTemplate.getMsgCn(), params);
          return popbillKakaoService
              .sendFTS(appProperties.getCorpNum(),
                  appProperties.getPlusFriendID(),
                  appProperties.getSenderNum(),
                  content,
                  null,
                  "C",
                  null,
                  receiverNum,
                  null,
                  sendDttm, false, appProperties.getUserID(), null);
        default:
          return null;
      }
    } catch (PopbillException pe) {
      pe.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private String replaceMsgParams(String txt, Map<String, Object> params) {
    if (txt == null) {
      return null;
    }

    String result = txt;

    for (String k : params.keySet()) {
      String v = "";

      if (params.get(k) != null) {
        v = params.get(k).toString();
      }

      String replacer = "${" + k + "}";
      result = result.replace(replacer, v);
    }

    return result;
  }


}
