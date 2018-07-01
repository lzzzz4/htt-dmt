package cn.dubidubi.util;

import jdk.internal.util.xml.impl.Input;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author lzj
 * @Description: 遍历获得excel
 * @date 18-6-29上午10:54
 */

public class ListExcelUtils {
    public static void main(String[] args) throws IOException {
        File File = new File("/home/lzj0726/Desktop/中文.zip");
        parseZip(File);
    }

    /**
     * @param []
     * @return void
     * @Description: 解析压缩文件
     * @author lzj
     * @date 18-6-29 下午3:46
     */
    public static void parseZip(File zipFile) throws IOException {
        ZipFile ZipFile = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> entries = ZipFile.entries();
        //遍历所有文件与文件夹
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (!zipEntry.isDirectory()) {
                InputStream inputStream = ZipFile.getInputStream(zipEntry);
                //解析excel
                parseOneExcel(inputStream, zipEntry.getName());
            }
        }
        //解析结束
    }

    /**
     * @param [InputStream, name]
     * @return void
     * @Description: 解析单个excel
     * @author lzj
     * @date 18-6-29 下午3:39
     */
    private static void parseOneExcel(InputStream InputStream, String name) throws IOException {
        Workbook Workbook = new XSSFWorkbook(InputStream);
        Sheet sheetAt = Workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheetAt.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                System.out.println(getValue(row.getCell(j)));
            }
        }
    }

    /**
     * @param [Cell]
     * @return java.lang.String
     * @Description:得到value
     * @author lzj
     * @date 18-6-29 下午3:30
     */
    private static String getValue(Cell Cell) {
        if (Cell == null) {
            return "";
        }
        int cellType = Cell.getCellType();
        switch (cellType) {
            //数字
            case 0:
                Double numericCellValue = Cell.getNumericCellValue();
                return numericCellValue.toString();
            case 1:
                return Cell.getStringCellValue();
        }
        return "";
    }
}
