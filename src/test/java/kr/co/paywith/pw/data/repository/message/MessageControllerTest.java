package kr.co.paywith.pw.data.repository.message;


import java.util.HashMap;
import java.util.Map;
import kr.co.paywith.pw.common.BaseControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageControllerTest extends BaseControllerTest {

  @Autowired
  MessageService messageService;

  @Test
  public void sendMsgByRuleTest1() {
    Map<String, Object> map = new HashMap<>();
    map.put("TITLE", "[인천] 야마나미 골프 투어");
    map.put("NAME", "채원");
    map.put("START_DATE", "2020-08-17");
    map.put("RETURN_DATE", "2020-08-20");
    map.put("TOUR_TERM",  "3박4일" );
    map.put("TOUR_NUMBER", "2");

    String result = messageService.sendMsgByRule("010-4694-0301", null, MsgRuleCd.접수대기_고객, map);
    System.out.println(result);
  }

}
