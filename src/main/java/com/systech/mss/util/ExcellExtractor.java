/** */
package com.systech.mss.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author kodero
 * @date-created May 10, 2011
 */
public class ExcellExtractor implements java.io.Serializable {

  private static int cols = 100;

  public ExcellExtractor() {}

  public static List<Vector<String>> extract(String path, int startRow, Integer... sheetNumber) {
    boolean isXlsx = path.endsWith(".xlsx");
    Integer sheetNo = 0;
    for (Integer sh : sheetNumber) {
      sheetNo = sh;
    }
    try (FileInputStream inputStream = new FileInputStream(path)) {
      Workbook workbook;
      if (isXlsx) {
        workbook = new XSSFWorkbook(inputStream);
      } else {
        workbook = new HSSFWorkbook(inputStream);
      }
      Sheet sheet = workbook.getSheetAt(sheetNo);
      FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
      List<Vector<String>> res = new ArrayList<>();
      DataFormatter fmt = new DataFormatter();
      // Iterate through each rows one by one
      Iterator<Row> rowIterator = sheet.rowIterator();
      while (rowIterator.hasNext()){
        Row row = rowIterator.next();
        if (row.getRowNum() < startRow) continue;

        Vector<String> inner = new Vector<>();
        //int noOfColumns = row.getLastCellNum();
        for (int i=0;i<cols;i++){

          Cell cell = row.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
          // Check the cell type after eveluating formulae
          // If it is formula cell, it will be evaluated otherwise no change will happen
          switch (evaluator.evaluateInCell(cell).getCellType()) {
            case NUMERIC:
              if (DateUtil.isCellDateFormatted(cell)) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                inner.add(df.format(cell.getDateCellValue()));
              } else {
                inner.add(fmt.formatCellValue(cell));
              }
              break;
            case STRING:
              inner.add(cell.getStringCellValue().replace("\r", " ").replace("\n", " "));
              break;
            case FORMULA:
              // Not again
              break;
            default:
              inner.add("");
              break;
          }
        }
        res.add(inner);
      }

      return res;

    } catch (IOException e) {
      System.out.println("File not found in the specified path.");
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}
