package kr.co.paywith.pw.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import kr.co.paywith.pw.data.repository.SearchForm;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class CheckUtil {

  static AppProperties appProperties;



  @Autowired
  private CheckUtil(AppProperties appProperties) {
    this.appProperties = appProperties;
  }






  /**
   *
   * @param date
   * @return
   */
  public static String getDateType(String date) {

    if ( date !=null && date.length() > 0 ) {
      SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");

      try {
        Date tempDate =  format.parse(date);
        String str = format.format(tempDate);

        return  str;
      } catch ( Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }


}
