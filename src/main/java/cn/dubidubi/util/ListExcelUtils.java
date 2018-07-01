package cn.dubidubi.util;

import cn.dubidubi.model.ExcelWrong;
import cn.dubidubi.model.StandardExcel;
import cn.dubidubi.model.exception.FourRowsException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    //sl4j日志
    private static final Logger logger = LoggerFactory.getLogger(ListExcelUtils.class);

    /**
     * @Description: 测试入口, 上线时入口为web
     * @author lzj
     * @date 18-7-1 下午4:36
     */
    public static void main(String[] args) throws IOException {
        File File = new File("/home/lzj0726/Desktop/gg.zip");
        parseZip(File, false);
    }

    /**
     * @param []
     * @return 错误信息, 为null代表没有
     * @Description: 解析压缩文件
     * @author lzj
     * @date 18-6-29 下午3:46
     */
    public static List parseZip(final File zipFile, final boolean isPrint) {
        ZipFile ZipFile = null;
        final List<ExcelWrong> wrongExcel = new ArrayList<>();
        try {
            ZipFile = new ZipFile(zipFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        final Enumeration<? extends ZipEntry> entries = ZipFile.entries();
        //遍历所有文件与文件夹
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = entries.nextElement();
            if (!zipEntry.isDirectory()) {
                InputStream inputStream = null;
                try {
                    inputStream = ZipFile.getInputStream(zipEntry);
                } catch (IOException e) {
                    wrong(zipEntry, e, wrongExcel);
                    e.printStackTrace();
                } catch (Exception e) {
                    wrong(zipEntry, e, wrongExcel);
                    e.printStackTrace();
                }
                //解析excel
                try {
                    //输出excel的值,上线时可以注释
                    if (isPrint)
                        System.out.println("------------" + zipEntry.getName() + "----------------");
                    parseOneExcel(inputStream, zipEntry.getName(), isPrint);
                    if (isPrint)
                        System.out.println("-----------------------------------");
                } catch (IOException e) {
                    wrong(zipEntry, e, wrongExcel);
                    e.printStackTrace();
                } catch (FourRowsException e) {
                    wrong(zipEntry, e, wrongExcel);
                    e.printStackTrace();
                } catch (Exception e) {
                    wrong(zipEntry, e, wrongExcel);
                    e.printStackTrace();
                }
            }
        }
        System.out.println(wrongExcel);
        logger.info("{}解析完成!", zipFile.getName());
        return wrongExcel;
    }

    /**
     * @param [InputStream, name]
     * @return void
     * @Description: 解析单个excel
     * @author lzj
     * @date 18-6-29 下午3:39
     */
    private static void parseOneExcel(final InputStream InputStream, final String name, final boolean isPrint) throws IOException, FourRowsException {
        final Workbook Workbook = new XSSFWorkbook(InputStream);
        final Sheet sheetAt = Workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        boolean isFourRowsTerminate = false;
        //数据库每行都需要有standardFourRows
        StandardExcel standardExcel = null;
        int dataStart = 0;
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheetAt.getRow(i);
            //获取标准值
            if (i <= 3) {
                if (!isFourRowsTerminate) {
                    isFourRowsTerminate = true;
                    standardExcel = setStandardFourRows(sheetAt);
                }
                continue;
            } else if (i == 4) {
                dataStart = setYears(row, standardExcel);
            } else {
                //获取值
                StringBuilder value = new StringBuilder();
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        //为国家
                        if (j == 0) {
                            standardExcel.setCountry(getValue(cell));
                        }
                        //为值
                        if (j >= dataStart) {
                            value.append(getValue(cell));
                            if (j != row.getLastCellNum() - 1) {
                                value.append(",");
                            }
                        }
                    }
                }
                standardExcel.setData(value.toString());
                if (isPrint)
                    System.out.println(standardExcel);
            }
        }
    }

    /**
     * @param [row, standardExcel]
     * @return int
     * @Description:设置年份
     * @author lzj
     * @date 18-7-1 下午4:32
     */
    private static int setYears(Row row, StandardExcel standardExcel) {
        int DataStart = 0;
        boolean IsDataStartTerminate = false;
        final StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j);
            //为开始行
            String value = getValue(cell, true);
            if (value.indexOf("YR") > -1 && !IsDataStartTerminate) {
                DataStart = j;
                IsDataStartTerminate = true;
            }
            //开始设置年份
            if (j >= DataStart && IsDataStartTerminate) {
                stringBuilder.append(value);
                if (j != row.getLastCellNum() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        standardExcel.setYears(stringBuilder.toString());
        return DataStart;
    }

    /**
     * @param [Sheet]
     * @return cn.dubidubi.model.StandardExcel
     * @Description:设置标准话的4行
     * @author lzj
     * @date 18-7-1 下午4:33
     */
    private static StandardExcel setStandardFourRows(Sheet Sheet) throws FourRowsException {
        final StandardExcel standardExcel = new StandardExcel();
        //获取row
        for (int i = 0; i < 4; i++) {
            Row Row = Sheet.getRow(i);
            //获取cell
            Cell cell = Row.getCell(0);
            //获取cell的未分隔值
            String originValue = getValue(cell, true).toLowerCase();
            //获取cell的分格值
            String value = getValue(cell, false);
            //依据current存储到对应的值中
            switch (i) {
                case 0:
                    if (originValue.indexOf("data from database") == -1) {
                        throw new FourRowsException("初始化4行错误");
                    }
                    standardExcel.setSource(value);
                    break;
                case 1:
                    if (originValue.indexOf("last updated") == -1) {
                        throw new FourRowsException("初始化4行错误");
                    }
                    standardExcel.setUpdateTime(value);
                    break;
                case 2:
                    if (originValue.indexOf("topic") == -1) {
                        throw new FourRowsException("初始化4行错误");
                    }
                    standardExcel.setTopic(value);
                    break;
                case 3:
                    if (originValue.indexOf("unit") == -1) {
                        throw new FourRowsException("初始化4行错误");
                    }
                    standardExcel.setUnit(value);
                    break;
            }
        }
        return standardExcel;

    }

    /**
     * @param [Cell, isSimple]
     * @return java.lang.String
     * @Description: 获取cell的值, 为simple时，不做分隔
     * @author lzj
     * @date 18-7-1 下午4:33
     */
    private static String getValue(Cell Cell, boolean isSimple) {
        if (isSimple) {
            return getValue(Cell);
        } else {
            String temporary = getValue(Cell);
            String[] value = temporary.split("\\:");
            if (value.length >= 2) {
                return value[1];
            } else {
                String[] split = temporary.split("\\：");
                if (split.length >= 2) {
                    return split[1];
                } else {
                    return "";
                }
            }
        }
    }

    /**
     * @param [Cell]
     * @return java.lang.String
     * @Description:根据映射,得到value
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

    /**
     * @param [ZipEntry, Exception, wrongExcel]
     * @return void
     * @Description: 封装错误信息, 返回给前端
     * @author lzj
     * @date 18-7-1 下午4:34
     */
    private static void wrong(ZipEntry ZipEntry, Exception Exception, List<ExcelWrong> wrongExcel) {
        ExcelWrong ExcelWrong = new ExcelWrong();
        ExcelWrong.setName(ZipEntry.getName());
        ExcelWrong.setMessage(Exception.getMessage());
        logger.warn("{}解析错误！", ZipEntry.getName());
        wrongExcel.add(ExcelWrong);
    }

}
