package kr.co.paywith.pw.component;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.paywith.pw.common.AppProperties;
import kr.co.paywith.pw.data.repository.message.MessageService;
import kr.co.paywith.pw.data.repository.message.MsgRuleCd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

  Logger log = LoggerFactory.getLogger(Scheduler.class);

  @Autowired
  MessageService messageService;


  @Autowired
  AppProperties appProperties;

  /**
   *
   */
//  @Scheduled(cron = "0 */1 * * * *")
//  public void cronJobSchRequestCheck() {
//    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    Date now = new Date();
//    String strDate = sdf.format(now);
//
//    // 시간 제한 설정
//    LocalDateTime currentDateTime = LocalDateTime.now();
//    LocalDateTime targetDateTime = currentDateTime.minusMinutes(30);
//
//    String currentDateTimeStr = currentDateTime
//        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//    String targetDateTimeStr = targetDateTime
//        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//
//    System.out.println(currentDateTimeStr);
//    System.out.println(targetDateTimeStr);
//    List<PackagegoodsUse> resultList = this.packageGoodsUseMapper.cancelPackageGoodsUses(targetDateTimeStr);
//
//    for ( PackagegoodsUse packagegoodsUse : resultList ) {
//
//      if (packagegoodsUse != null && packagegoodsUse.getId() > 0 ) {
//        packagegoodsUseRepository.updatePackagegoodsUseCancel(packagegoodsUse.getId());
//
//        // 온라인예약 정보 취득
//        PackagegoodsUse messagePu = packagegoodsUseRepository.findById(packagegoodsUse.getId()).orElse(null);
//        // 취소 메세지 전송
//        this.sendCancelMessage(messagePu);
//      }
//    }
//  }
//
//
//  // 고객 메세지 송신
//  private void sendCancelMessage(PackagegoodsUse packagegoodsUse) {
//
//    Map<String, Object> map = new HashMap<>();
//    map.put("TITLE", packagegoodsUse.getPackagegoods().getTitle());
//    map.put("NAME", packagegoodsUse.getCustomer().getName());
//    map.put("START_DATE", packagegoodsUse.getStartDate());
//    map.put("RETURN_DATE", packagegoodsUse.getReturnDate());
//    map.put("TOUR_TERM", changeString(packagegoodsUse.getTourTerm()));
//    map.put("TOUR_NUMBER", packagegoodsUse.getTourNumber());
//
//    this.messageService.sendMsgByRule(packagegoodsUse.getCustomer().getCellPhone(), null, MsgRuleCd.온라인예약접수_진행취소, map);
//  }
//
//  private String changeString(String str) {
//    if ( str != null ) {
//      str = str.replace("N","박");
//      str = str.replace("D","일");
//
//      return  str;
//    }
//    return "";
//  }



}
