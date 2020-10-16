package kr.co.paywith.pw.component;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Slf4j
public abstract class ExcelReaderAbstract {

  public XSSFWorkbook readFile(MultipartFile f) throws IOException {
    return this.readFile(f.getInputStream());
  }

  public XSSFWorkbook readFile(InputStream inputStream) throws IOException {
    XSSFWorkbook workbook;
    try {
      workbook = new XSSFWorkbook(inputStream);
    } catch (IOException e) {
      log.debug("파일 읽기 실패");
      throw e;
    }

    int sheetCnt = workbook.getNumberOfSheets(); // 전체 시트 개수
    if (sheetCnt > 1) {
      log.warn("시트가 여러개 존재");
    }

    return workbook;
  }

  public boolean isEmpty(XSSFCell cell) {
    if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
      return true;
    }

    if (cell.getCellType() == CellType.BLANK) {
      return true;
    }

    if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty()) {
      return true;
    }

    return false;
  }
}
