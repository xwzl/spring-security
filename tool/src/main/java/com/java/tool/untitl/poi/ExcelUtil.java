package com.java.tool.untitl.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 徐伟智
 * @date: 2019/03/11 10:42
 * Excel 工具类 4.0改动
 * https://blog.csdn.net/snake214/article/details/84320109
 */
public class ExcelUtil {

    /**
     * 创建工作蒲对象
     */
    private Workbook workbook;

    /**
     * 字节流返回对象化
     */
    private OutputStream os;

    /**
     * 日期格式
     */
    private String pattern;

    /**
     * Excel版本号
     */
    private final static String excelVersion = ".xls";

    /**
     * 设置时间类型转换格式
     *
     * @param pattern 正则表达式
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * 构造方法区，注入workbook对象
     *
     * @param workbook 操作的workbook对象
     */
    public ExcelUtil(Workbook workbook) {
        this.workbook = workbook;
    }

    /**
     * 根据传入的版本号，判断Excel版本并返回与之对应的Workbook对象
     * HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
     * XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx；
     *
     * @param is      页面传输的Excel二进制流
     * @param version 版本号
     */
    public ExcelUtil(InputStream is, String version) throws FileNotFoundException, IOException {
        if (excelVersion.equals(version)) {
            workbook = new HSSFWorkbook(is);
        } else {
            workbook = new XSSFWorkbook(is);
        }
    }

    /**
     * 根据excelName判断Excel版本，返回与之对应的工作蒲对象
     *
     * @return 工作蒲对象
     */
    //public static Workbook creatWorkbook(String excelName) {
    //    if (".xls".endsWith(excelName)) {
    //        return new HSSFWorkbook();
    //    } else {
    //        return new XSSFWorkbook();
    //    }
    //}
    @Override
    public String toString() {
        return "共有 " + getSheetCount() + "个sheet 页！";
    }

    public String toString(int sheetIx) throws IOException {
        return "第 " + (sheetIx + 1) + "个sheet 页，名称： " + getSheetName(sheetIx) + "，共 " + getRowCount(sheetIx) + "行！";
    }

    /**
     * 根据后缀判断是否为 Excel 文件，后缀匹配xls和xlsx
     *
     * @param pathname 文件名称
     * @return 1. true  不符合Excel文件格式
     * 2. false 符合Excel文件格式
     */
    public static boolean isExcel(String pathname) {
        if (pathname == null) {
            return false;
        }
        return pathname.endsWith(".xls") || pathname.endsWith(".xlsx");
    }

    /**
     * 读取 Excel 第一页所有数据
     *
     * @return Excel 第一页所有数据
     * @throws Exception 异常处理
     */
    public List<List<String>> read() throws Exception {
        return read(0, 0, getRowCount(0) - 1);
    }

    /**
     * 读取指定sheet 页所有数据
     *
     * @param sheetIx 指定 sheet 页，从 0 开始
     * @return 指定sheet页所有数据
     * @throws Exception 异常处理
     */
    public List<List<String>> read(int sheetIx) throws Exception {
        return read(sheetIx, 0, getRowCount(sheetIx) - 1);
    }

    /**
     * 读取指定sheet 页指定行数据
     *
     * @param sheetIx 指定 sheet 页，从 0 开始
     * @param start   指定开始行，从 0 开始
     * @param end     指定结束行，从 0 开始
     * @return 指定sheet 页指定行数据
     * @throws Exception 异常处理
     */
    public List<List<String>> read(int sheetIx, int start, int end) throws Exception {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<List<String>> list = new ArrayList<List<String>>();

        if (end > getRowCount(sheetIx)) {
            end = getRowCount(sheetIx);
        }

        // 第一行总列数
        int cols = sheet.getRow(start).getLastCellNum();

        for (int i = start; i <= end; i++) {
            List<String> rowList = new ArrayList<String>();
            Row row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                if (row == null) {
                    rowList.add(null);
                    continue;
                }
                rowList.add(getCellValueToString(row.getCell(j)));
            }
            list.add(rowList);
        }

        return list;
    }

    /**
     * 将数据写入到 Excel 默认第一页中，从第1行开始写入
     *
     * @param rowData 数据
     * @return false 写入失败
     * @throws IOException IO 异常
     */
    public boolean write(List<List<String>> rowData) throws IOException {
        return write(0, rowData, 0);
    }

    /**
     * 将数据写入到 Excel 新创建的 Sheet 页
     *
     * @param rowData   数据
     * @param sheetName 长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     * @return 是否写入成功
     * @throws IOException IO异常
     */
    public boolean write(List<List<String>> rowData, String sheetName, boolean isNewSheet) throws IOException {
        Sheet sheet = null;
        if (isNewSheet) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        int sheetIx = workbook.getSheetIndex(sheet);
        return write(sheetIx, rowData, 0);
    }

    /**
     * 将标题写入到 Excel 新创建的 Sheet 页，并指定表的名称含表名称
     *
     * @param rowData   写入数据
     * @param sheetIx   指定 sheet 页，从 0 开始
     * @param title     指定sheet表第一行标题
     * @param sheetName 指定sheet表名
     * @return 是否写入成功
     */
    public boolean write(int sheetIx, List<List<String>> rowData, String title, String sheetName) throws IOException {
        Sheet sheet = workbook.createSheet(sheetName);
        int length = 0;
        if (rowData.size() > 0) {
            length = rowData.get(0).size();
        }
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellStyle(this.makeStyle(1));
        cell.setCellValue(title);
        this.region(0, 0, 0, 0, length - 1);
        return write(sheetIx, rowData, getRowCount(sheetIx), true, 2);
    }

    /**
     * 将数据追加到sheet页最后
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param isAppend 是否追加,true 追加，false 重置sheet再添加
     * @return 追加是否成功
     * @throws IOException IO 异常
     */
    public boolean write(int sheetIx, List<List<String>> rowData, boolean isAppend) throws IOException {
        if (isAppend) {
            return write(sheetIx, rowData, getRowCount(sheetIx));
        } else {// 清空再添加
            clearSheet(sheetIx);
            return write(sheetIx, rowData, 0);
        }
    }

    /**
     * 将数据写入到 Excel 指定 Sheet 页指定开始行中,指定行后面数据向后移动
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param startRow 指定开始行，从 0 开始
     * @return 写入成功与否标志
     * @throws IOException IO异常
     */
    public boolean write(int sheetIx, List<List<String>> rowData, int startRow) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int dataSize = getDataSize(sheetIx, rowData, startRow, sheet);
        for (int i = 0; i < dataSize; i++) {
            Row row = sheet.createRow(i + startRow);
            for (int j = 0; j < rowData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(rowData.get(i).get(j) + "");
            }
        }
        return true;
    }

    /**
     * 获取数据的长度
     *
     * @param sheetIx  表
     * @param rowData  数据
     * @param startRow 起始行
     * @param sheet    表格
     * @return 返回值
     */
    private int getDataSize(int sheetIx, List<List<String>> rowData, int startRow, Sheet sheet) {
        int dataSize = rowData.size();
        if (getRowCount(sheetIx) > 0) {
            sheet.shiftRows(startRow, getRowCount(sheetIx), dataSize);
        }
        return dataSize;
    }

    /**
     * 将数据写入到 Excel 指定 Sheet 页指定开始行中,指定行后面数据向后移动
     *
     * @param rowData        数据
     * @param sheetIx        指定 Sheet 页，从 0 开始
     * @param startRow       指定开始行，从 0 开始
     * @param isSetCellStyle 是否设置单元格样式
     * @param type           单元格样式类型
     * @return 写入成功与否标志
     * @throws IOException IO异常
     */
    public boolean write(int sheetIx, List<List<String>> rowData, int startRow, boolean isSetCellStyle, int type) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        int dataSize = getDataSize(sheetIx, rowData, startRow, sheet);
        for (int i = 0; i < dataSize; i++) {
            Row row = sheet.createRow(i + startRow);
            for (int j = 0; j < rowData.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                if (isSetCellStyle) {
                    //cell.setCellStyle(makeStyle(type));
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                    cell.setCellStyle(cellStyle);
                }
                cell.setCellValue(rowData.get(i).get(j)==null?"":rowData.get(i).get(j));
            }
            for (int k = 0; k < dataSize; k++) {
                sheet.autoSizeColumn(k);
            }
            // 处理中文不能自动调整列宽的问题
            this.setSizeColumn(sheet, dataSize);
        }
        return true;
    }

    /**
     * 自适应宽度(中文支持)
     */
    private void setSizeColumn(Sheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }


    /**
     * 设置cell 样式
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param colIndex 指定列，从 0 开始
     * @return 指定cell样式
     * @throws IOException IO 异常
     */
    public boolean setStyle(int sheetIx, int rowIndex, int colIndex, CellStyle style) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        // sheet.autoSizeColumn(colIndex, true);// 设置列宽度自适应
        sheet.setColumnWidth(colIndex, 4000);

        Cell cell = sheet.getRow(rowIndex).getCell(colIndex);
        cell.setCellStyle(style);

        return true;
    }

    /**
     * 设置样式
     *
     * @param type 1：标题 2：第一行
     * @return
     */
    public CellStyle makeStyle(int type) {
        CellStyle style = workbook.createCellStyle();

        DataFormat format = workbook.createDataFormat();
        // 内容样式 设置单元格内容格式是文本
        style.setDataFormat(format.getFormat("@"));
        // 内容居中
        style.setAlignment(CellStyle.ALIGN_CENTER);

        //边框样式
        //style.setBorderTop(CellStyle.BORDER_THIN);
        //style.setBorderRight(CellStyle.BORDER_THIN);
        //style.setBorderBottom(CellStyle.BORDER_THIN);
        //style.setBorderLeft(CellStyle.BORDER_THIN);
        //文字样式
        Font font = workbook.createFont();

        if (type == 1) {
            // style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);//颜色样式
            // 前景颜色
            // style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);//背景色
            // style.setFillPattern(CellStyle.ALIGN_FILL);// 填充方式
            font.setBold(true);
            font.setFontHeight((short) 500);
        }

        if (type == 2) {
            font.setBold(true);
            font.setFontHeight((short) 300);
        }

        style.setFont(font);

        return style;
    }

    /**
     * 合并单元格
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param firstRow 开始行
     * @param lastRow  结束行
     * @param firstCol 开始列
     * @param lastCol  结束列
     */
    public void region(int sheetIx, int firstRow, int lastRow, int firstCol, int lastCol) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 判断合并了行
     */
    private static boolean isMergedRow(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row == firstRow && row == lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 指定行是否为空
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定开始行，从 0 开始
     * @return true 不为空，false 不行为空
     * @throws IOException IO 异常
     */
    public boolean isRowNull(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getRow(rowIndex) == null;
    }

    /**
     * 创建行，若行存在，则清空
     *
     * @param sheetIx  指定 sheet 页，从 0 开始
     * @param rowIndex 指定创建行，从 0 开始
     * @return 创建行失败
     * @throws IOException IO异常
     */
    public boolean createRow(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.createRow(rowIndex);
        return true;
    }

    /**
     * 指定单元格是否为空
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定开始行，从 0 开始
     * @param colIndex 指定开始列，从 0 开始
     * @return true 行不为空，false 行为空
     * @throws IOException IO异常
     */
    public boolean isCellNull(int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (!isRowNull(sheetIx, rowIndex)) {
            return false;
        }
        Row row = sheet.getRow(rowIndex);
        return row.getCell(colIndex) == null;
    }

    /**
     * 创建单元格
     *
     * @param sheetIx  指定 sheet 页，从 0 开始
     * @param rowIndex 指定行，从 0 开始
     * @param colIndex 指定创建列，从 0 开始
     * @return true 列为空，false 行不为空
     * @throws IOException IO 异常
     */
    public boolean createCell(int sheetIx, int rowIndex, int colIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        row.createCell(colIndex);
        return true;
    }

    /**
     * 返回sheet 中的行数
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return sheet 中的行数
     */
    public int getRowCount(int sheetIx) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        if (sheet.getPhysicalNumberOfRows() == 0) {
            return 0;
        }
        return sheet.getLastRowNum() + 1;

    }

    /**
     * 返回所在行的列数
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 返回-1 表示所在行为空
     */
    public int getColumnCount(int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        return row == null ? -1 : row.getLastCellNum();

    }

    /**
     * 设置row 和 column 位置的单元格值
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @param value    值
     * @return 返回值
     * @throws IOException IO 异常
     */
    public boolean setValueAt(int sheetIx, int rowIndex, int colIndex, String value) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.getRow(rowIndex).getCell(colIndex).setCellValue(value);
        return true;
    }

    /**
     * 返回 row 和 column 位置的单元格值
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @return row 和 column 位置的单元格值
     */
    public String getValueAt(int sheetIx, int rowIndex, int colIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return getCellValueToString(sheet.getRow(rowIndex).getCell(colIndex));
    }

    /**
     * 重置指定行的值
     *
     * @param rowData  数据
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 重置指定行的值
     * @throws IOException IO 异常
     */
    public boolean setRowValue(int sheetIx, List<String> rowData, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        for (int i = 0; i < rowData.size(); i++) {
            row.getCell(i).setCellValue(rowData.get(i));
        }
        return true;
    }

    /**
     * 返回指定行的值的集合
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 指定行的值的集合
     */
    public List<String> getRowValue(int sheetIx, int rowIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        Row row = sheet.getRow(rowIndex);
        List<String> list = new ArrayList<String>();
        if (row == null) {
            list.add(null);
        } else {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                list.add(getCellValueToString(row.getCell(i)));
            }
        }
        return list;
    }

    /**
     * 返回列的值的集合
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @param colIndex 指定列，从0开始
     * @return 列的值的集合
     */
    public List<String> getColumnValue(int sheetIx, int rowIndex, int colIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        List<String> list = new ArrayList<String>();
        for (int i = rowIndex; i < getRowCount(sheetIx); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                list.add(null);
                continue;
            }
            list.add(getCellValueToString(sheet.getRow(i).getCell(colIndex)));
        }
        return list;
    }

    /**
     * 获取excel 中sheet 总页数
     *
     * @return 获取excel 中sheet 总页数
     */
    public int getSheetCount() {
        return workbook.getNumberOfSheets();
    }

    public void createSheet() {
        workbook.createSheet();
    }

    /**
     * 设置sheet名称，长度为1-31，不能包含后面任一字符: ：\ / ? * [ ]
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始，//
     * @param name    设置sheet名称
     * @return 设置成功还是失败
     * @throws IOException IO异常
     */
    public boolean setSheetName(int sheetIx, String name) throws IOException {
        workbook.setSheetName(sheetIx, name);
        return true;
    }

    /**
     * 获取 sheet名称
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return 获取 sheet名称
     * @throws IOException IO异常
     */
    public String getSheetName(int sheetIx) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        return sheet.getSheetName();
    }

    /**
     * 获取sheet的索引，从0开始
     *
     * @param name sheet 名称
     * @return -1表示该未找到名称对应的sheet
     */
    public int getSheetIndex(String name) {
        return workbook.getSheetIndex(name);
    }

    /**
     * 删除指定sheet
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return 删除指定sheet
     * @throws IOException IO异常
     */
    public boolean removeSheetAt(int sheetIx) throws IOException {
        workbook.removeSheetAt(sheetIx);
        return true;
    }

    /**
     * 删除指定sheet中行，改变该行之后行的索引
     *
     * @param sheetIx  指定 Sheet 页，从 0 开始
     * @param rowIndex 指定行，从0开始
     * @return 删除指定sheet中行，改变该行之后行的索引
     * @throws IOException IO异常
     */
    public boolean removeRow(int sheetIx, int rowIndex) throws IOException {
        Sheet sheet = workbook.getSheetAt(sheetIx);
        sheet.shiftRows(rowIndex + 1, getRowCount(sheetIx), -1);
        Row row = sheet.getRow(getRowCount(sheetIx) - 1);
        sheet.removeRow(row);
        return true;
    }

    /**
     * 设置sheet 页的索引
     *
     * @param sheetName Sheet 名称
     * @param sheetIx   Sheet 索引，从0开始
     */
    public void setSheetOrder(String sheetName, int sheetIx) {
        workbook.setSheetOrder(sheetName, sheetIx);
    }

    /**
     * 清空指定sheet页（先删除后添加并指定sheetIx）
     *
     * @param sheetIx 指定 Sheet 页，从 0 开始
     * @return 清空指定sheet页（先删除后添加并指定sheetIx）
     * @throws IOException IO流
     */
    public boolean clearSheet(int sheetIx) throws IOException {
        String sheetname = getSheetName(sheetIx);
        removeSheetAt(sheetIx);
        workbook.createSheet(sheetname);
        setSheetOrder(sheetname, sheetIx);
        return true;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * 关闭流
     *
     * @throws IOException 关闭流
     */
    public void close() throws IOException {
        if (os != null) {
            os.close();
        }
        workbook.close();
    }

    /**
     * 转换单元格的类型为String 默认的 <br>
     * 默认的数据类型：CELL_TYPE_BLANK(3), CELL_TYPE_BOOLEAN(4),
     * CELL_TYPE_ERROR(5),CELL_TYPE_FORMULA(2), CELL_TYPE_NUMERIC(0),
     * CELL_TYPE_STRING(1)
     *
     * @param cell 单元格
     * @return 转换单元格的类型为String 默认的
     */
    private String getCellValueToString(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        strCell = sdf.format(date);
                    } else {
                        strCell = date.toString();
                    }
                    break;
                }
                // 不是日期格式，则防止当数字过长时以科学计数法显示
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                strCell = cell.toString();
                break;
            case Cell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return strCell;
    }

}
