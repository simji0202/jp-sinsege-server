package kr.co.paywith.pw.common;

import java.awt.Color;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelConfig {

  XSSFWorkbook workbook = new XSSFWorkbook();

  @Bean
  public XSSFWorkbook workbook() {
    return workbook;
  }

  @Bean
  public XSSFCellStyle headerStyle() {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    BorderStyle style = BorderStyle.THIN;
    cellStyle.setBorderTop(style);
    cellStyle.setBorderRight(style);
    cellStyle.setBorderBottom(style);
    cellStyle.setBorderLeft(style);
    cellStyle.setFillForegroundColor(new XSSFColor(Color.ORANGE));
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    return cellStyle;
  }

  @Bean
  public XSSFCellStyle dataStyle() {
    XSSFCellStyle cellStyle = workbook.createCellStyle();
    BorderStyle style = BorderStyle.THIN;
    cellStyle.setBorderTop(style);
    cellStyle.setBorderRight(style);
    cellStyle.setBorderBottom(style);
    cellStyle.setBorderLeft(style);
    return cellStyle;
  }
}
