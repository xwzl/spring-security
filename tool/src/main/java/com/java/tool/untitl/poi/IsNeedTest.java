package com.java.tool.untitl.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xuweizhi
 * @date 2019/03/13 9:27
 * @description
 */

public class IsNeedTest {

    @IsNeed(value = "用户名", type = IsNeedConstant.STRING)
    private String username;

    @IsNeed(value = "密码", type = IsNeedConstant.STRING)
    private String password;

    @IsNeed(value = "年龄", type = IsNeedConstant.NUMBER)
    private Integer age;

    @IsNeed(value = "生日", type = IsNeedConstant.DATE)
    private Date birthday;

    @IsNeed(value = "生日", type = IsNeedConstant.BIGDECIMAL)
    private BigDecimal bigDecimal;

    @IsNeed(value = "标志", type = IsNeedConstant.BOOLEAN)
    private boolean flag;

    private String no;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public static void main(String[] args) throws Exception {
        //writeExcel();
        InputStream out = new FileInputStream("D://women.xls");
        ExcelUtil excelUtil = new ExcelUtil(out, ".xls");
        List<List<String>> read = excelUtil.read(0, 0, 0);
        List<List<String>> read1 = excelUtil.read(0, 1, excelUtil.getRowCount(0) - 2);
        System.out.println(excelUtil.getRowCount(0));
        System.out.println(read);
        System.out.println(read1.size());
        System.out.println(read1);
    }


    private static void writeExcel() throws IOException {
        IsNeedTest test = new IsNeedTest();
        test.setAge(1);
        test.setBigDecimal(new BigDecimal(10));
        test.setBirthday(new Date());
        test.setNo("22");
        test.setPassword("158262751");
        test.setUsername("username");
        List<IsNeedTest> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(test);
        }

        List<List<String>> date = getBodyDate(list, "com.java.common.untitl.poi.IsNeedTest", "yyyy-MM-dd");

        System.out.println(date);

        ExcelUtil excelUtil = new ExcelUtil(new HSSFWorkbook());
        excelUtil.write(0, date, "无语", "我爱我家");

        OutputStream out = new FileOutputStream("D://women.xls");
        excelUtil.getWorkbook().write(out);
        excelUtil.getWorkbook().close();
        out.close();
    }

    public static List<String> getHead(String classPath) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(classPath);
        Field[] fields = clazz.getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            Annotation type = field.getAnnotation(IsNeed.class);
            if (type != null) {
                if (type instanceof IsNeed) {
                    IsNeed isNeed = (IsNeed) type;
                    list.add(isNeed.value());
                }
            }
        }
        return list;
    }


    public static <T> List<List<String>> getBodyDate(List<T> list, String className, String pattern) {
        List<List<String>> data = new ArrayList<>();
        try {
            List<String> head = getHead(className);
            data.add(head);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            for (int i = 0; i < list.size(); i++) {

                T t = list.get(i);
                Class<?> clazz = t.getClass();
                Field[] fields = clazz.getDeclaredFields();
                List<String> template = new ArrayList<>();
                for (Field field : fields) {
                    Annotation type = field.getAnnotation(IsNeed.class);

                    field.setAccessible(true);
                    if (type != null) {
                        if (type instanceof IsNeed) {
                            IsNeed isNeed = (IsNeed) type;
                            switch (isNeed.type()) {
                                case 1:
                                    if (field.get(t) != null) {
                                        template.add((String) field.get(t));
                                    } else {
                                        template.add("");
                                    }

                                    break;
                                case 2:
                                    if (field.get(t) != null) {
                                        template.add(simpleDateFormat.format((Date) field.get(t)));
                                    } else {
                                        template.add("");
                                    }
                                    break;
                                case 3:
                                    if (field.get(t) != null) {
                                        template.add(String.valueOf((Number) field.get(t)));
                                    } else {
                                        template.add("");
                                    }
                                    break;
                                case 4:
                                    if (field.get(t) != null) {
                                        BigDecimal decimal = (BigDecimal) field.get(t);
                                        decimal = decimal.setScale(2, RoundingMode.UP);
                                        template.add(decimal.toString());
                                    } else {
                                        template.add("");
                                    }
                                    break;
                                case 5:
                                    if (field.get(t) != null) {
                                        boolean flag = (boolean) field.get(t);
                                        if (flag) {
                                            template.add("true");
                                        } else {
                                            template.add("false");
                                        }
                                    } else {
                                        template.add("");
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                data.add(template);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
