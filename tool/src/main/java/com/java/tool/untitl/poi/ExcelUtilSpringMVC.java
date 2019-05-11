package com.java.tool.untitl.poi;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuweizhi
 * @date 2019/03/11 11:21
 */
@Controller
@RequestMapping("/excel")
public class ExcelUtilSpringMVC {

    /**
     * 从数据库获取信息导出Excel
     */
    @RequestMapping("/export")
    @SuppressWarnings({"rawtypes","unchecked"})
    public void exportExcel(HttpServletResponse response) {

        //1. 获取数据
        List<String> items = new ArrayList<>();

        if (items.size() > 0) {

            //2. 设置表头和数据
            List<List<String>> data = new ArrayList<>();
            String[] heads = {"主键", "标题", "操作人", "告示内容", "公示开始时间", "公示结束时间", "创建时间"};
            data.add(Arrays.asList(heads));
            items.forEach(notice -> {

            });
            try {
                //3. 获取Excel工具类
                ExcelUtil excelUtil = new ExcelUtil(new XSSFWorkbook());
                excelUtil.write(0, data, "诚信登记记录报告", "诚信登记报告");

                //4. 返回Excel数据流，fileName自定义 编码的问题
                response.setContentType("application/binary;charset=UTF-8");
                String fileName = new String("测试Excel导出".getBytes(), "UTF-8");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

                excelUtil.getWorkbook().write(response.getOutputStream());
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void readAndAppend() throws Exception {
        File file = new File("d:\\工作簿.xlsx");
        boolean excelFlag = ExcelUtil.isExcel(file.getName());
        InputStream in = new FileInputStream(file);
        OutputStream out = new FileOutputStream(new File("d:\\工作簿1.xlsx"));


        //表格数据
        String[] data1 = {"1", "张三1", "121", "2013-12-11"};

        List<String> data = Stream.of(data1).collect(Collectors.toList());

        List<List<String>> listss = new ArrayList<>();

        listss.add(Arrays.asList(data1));

        if (excelFlag) {
            ExcelUtil excelUtil = new ExcelUtil(in, file.getName());
            excelUtil.setPattern("yyyy-MM-dd");
            int sheetCount = excelUtil.getSheetCount();
            for (int i = 0; i < sheetCount; i++) {
                List<List<String>> listList = excelUtil.read(i, 0, 0);
                List<List<String>> listList1 = excelUtil.read(i, 1, excelUtil.getRowCount(i) - 1);
                listList.forEach(lists -> {
                    lists.forEach(s -> {
                        System.out.print(s + "\t");
                    });
                    System.out.println("");
                });
                listList1.forEach(lists -> {
                    lists.forEach(s -> {
                        System.out.print(s + "\t");
                    });
                    System.out.println("");
                });
            }
            //向原Excel表中sheet1后面追加数据
            excelUtil.write(0, listss, true);
            excelUtil.getWorkbook().write(out);
            out.close();

            excelUtil.close();
        }

        out.close();
        in.close();
    }

    /**
     * 写入文件
     */
    private static void writeExcelFile() {
        //表头
        String[] title = {"序号", "姓名", "年龄", "时间"};

        //表格数据
        String[] data1 = {"1", "张三1", "121", "2013-12-12"};
        String[] data2 = {"2", "张三2", "122", "2013-12-12"};
        String[] data3 = {"3", "张三3", "123", "2013-12-12"};
        String[] data4 = {"4", "张三4", "124", "2013-12-12"};

        //创建一个工作蒲，操作Excel对象
        Workbook workbook = new XSSFWorkbook();

        //使用Excel工具类操作Excel对象
        ExcelUtil excelUtil = new ExcelUtil(workbook);

        List<String> strings = Stream.of(title).collect(Collectors.toList());

        List<List<String>> lists = new ArrayList<>();
        lists.add(strings);
        lists.add(Arrays.asList(data1));
        lists.add(Arrays.asList(data2));
        lists.add(Arrays.asList(data3));
        lists.add(Arrays.asList(data4));
        try {
            //向workbook创建一个新列表，并写入数据
            //excelUtil.write(lists, "mySheet", true);
            excelUtil.write(0, lists, "张三", "sheet1");
            FileOutputStream fileOut = new FileOutputStream("d:\\工作簿.xlsx");

            //向磁盘写入数据
            workbook.write(fileOut);
            System.out.println("写入成功");
            excelUtil.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
