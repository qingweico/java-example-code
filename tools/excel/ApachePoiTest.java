package tools.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Apache POI Excel 文件读写测试
 *
 * @see HSSFWorkbook
 * @see XSSFWorkbook
 * @see SXSSFWorkbook
 * @author zqw
 * @date 2023/3/31
 */
public class ApachePoiTest {

    @Test
    public void writeXssf() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");

        Row row1 = sheet.createRow(0);

        Cell cell01 = row1.createCell(0);
        cell01.setCellValue("序号");

        Cell cell02 = row1.createCell(1);
        cell02.setCellValue("姓名");

        Cell cell03 = row1.createCell(2);
        cell03.setCellValue("手机号");

        Row row2 = sheet.createRow(1);

        Cell cell11 = row2.createCell(0);
        cell11.setCellValue("1");

        Cell cell12 = row2.createCell(1);
        cell12.setCellValue("2");

        Cell cell13 = row2.createCell(2);
        cell13.setCellValue("3");

        try {
            FileOutputStream fos = new FileOutputStream("Sheet.xlsx");
            workbook.write(fos);
            fos.close();
        }catch (IOException e) {
           // nothing to do
        }
    }


}
