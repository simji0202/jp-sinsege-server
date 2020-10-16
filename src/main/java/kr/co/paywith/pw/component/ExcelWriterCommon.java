package kr.co.paywith.pw.component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExcelWriterCommon extends ExcelWriterAbstract {

  /**
   * 엑셀 파일을 작성한다
   */
  public void makeAndWrite(
      String fileName,
      Map<String, Object> params,
      HttpServletResponse res
  ) throws IOException, InvalidFormatException {
    XSSFWorkbook workbook = makeWorkbook("classpath:xls/" + fileName + ".xlsx");

    // 테이블 sheet 생성
    XSSFSheet sheet = workbook.getSheetAt(0);

    Set set = params.entrySet();
    Iterator iterator = set.iterator();

    while (iterator.hasNext()) {
      Map.Entry entry = (Map.Entry) iterator.next();
      String key = (String) entry.getKey();
      String value = (String) entry.getValue();

      InputStream input = new FileInputStream("classpath:xls/prop/" + fileName + ".properties");

      Properties prop = new Properties();

      // load a properties file
      prop.load(input);

      String[] posArr = prop.getProperty(key).split(",");
      setCellValue(sheet, Integer.parseInt(posArr[0]), Integer.parseInt(posArr[1]), value);

    }

    this.writeFile(workbook, fileName, res);
  }
}
