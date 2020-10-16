package kr.co.paywith.pw.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

public abstract class ExcelWriterAbstract {

  // 메시지 resource
  @Autowired
  ResourceLoader resourceLoader;

  @Autowired
  XSSFCellStyle headerStyle;

  @Autowired
  XSSFCellStyle dataStyle;

  /**
   * 엑셀파일을 작성한다
   */
  XSSFWorkbook makeWorkbook(String xlsPath) throws IOException, InvalidFormatException {
    if (xlsPath == null) {
      return new XSSFWorkbook();
    } else {
      // 템플릿 파일 가져오기
      InputStream templateFile = resourceLoader.getResource(xlsPath).getInputStream();
      // 워크북 만들기
      return (XSSFWorkbook) WorkbookFactory.create(templateFile);
    }
  }

  /**
   * Workbook을 파일로 저장한다
   */
  public void writeFile(XSSFWorkbook workbook, String fileName, HttpServletResponse res)
      throws IOException {
    res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"%s\"", fileName + ".xlsx");
    res.setHeader(headerKey, headerValue);
    workbook.write(res.getOutputStream());
  }

  /**
   * 셀에 데이터가 있다면 교체, 없다면 생성(XSSFCell.setCellValue 기능 대체)
   */
  XSSFCell setCellValue(XSSFSheet sheet, int rownum, int cellnum, Object value,
      XSSFCellStyle style) {
    XSSFRow row = sheet.getRow(rownum);
    if (row == null) {
      row = sheet.createRow(rownum);
    }
    XSSFCell cell = row.getCell(cellnum);
    if (cell == null) {
      cell = row.createCell(cellnum);
    }
    if (style != null) {
      cell.setCellStyle(style);
    }

    if (value instanceof Double) {
      cell.setCellValue((double) value);
    }
    if (value instanceof Integer) {
      cell.setCellValue((int) value);
    } else {
      cell.setCellValue((String) value);
    }
    return cell;
  }

  /**
   * 셀에 데이터가 있다면 교체, 없다면 생성
   */
  void setCellValue(XSSFSheet sheet, int rownum, int cellnum, Object value) {
    this.setCellValue(sheet, rownum, cellnum, value, null);
  }

  /**
   * 해당 위치에 열 추가
   */
  void expandRow(XSSFSheet sheet, int rowNum, int cnt) {
    if (cnt < 1) {
      return;
    }

    sheet.shiftRows(rowNum + 1, sheet.getLastRowNum(), cnt);
    for (int i = 0; i < cnt; i++) {
      sheet.copyRows(rowNum, rowNum, rowNum + i + 1, new CellCopyPolicy());
    }
  }

  /**
   * A, AA와 같은 행번호를 0을 시작으로 하는 숫자로 변환
   *
   * @param colChr A, AA와 같은 행번호
   * @return 0을 시작으로 하는 index 값 ex> A : 0, AA: 26
   */
  int getCellnum(String colChr) {
    int idx = 0;
    char[] arr = colChr.toUpperCase().toCharArray();
    for (int i = 0; i < arr.length; i++) {
      idx += (int) arr[i] - 65;
      idx *= 26;
    }
    idx /= 26;
    return idx;
  }

  /**
   * 지정한 순서에 해당하는 행번호(A, AA) 찾기
   *
   * @param cellnum 열번호(0부터 시작)
   * @return A를
   */
  String getColChr(int cellnum) {
    String colChr = "";
    while (cellnum >= 26) {
      colChr = "" + (char) (65 + (cellnum % 26)) + colChr;
      cellnum /= 26;
      cellnum--;
    }
    colChr = "" + (char) (65 + (cellnum % 26)) + colChr;
    return colChr;
  }

  /**
   * 셀이 아닌 범위로 테두리 선 만들기
   *
   * @param borderStyle
   * @param cellAddresses
   * @param sheet
   */
  void drawRangeBorder(
      BorderStyle borderStyle, CellRangeAddress cellAddresses, XSSFSheet sheet) {
    RegionUtil.setBorderBottom(borderStyle, cellAddresses, sheet);
    RegionUtil.setBorderTop(borderStyle, cellAddresses, sheet);
    RegionUtil.setBorderLeft(borderStyle, cellAddresses, sheet);
    RegionUtil.setBorderRight(borderStyle, cellAddresses, sheet);
  }
}
