package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuweizhi
 * @date 2019/06/11 23:51
 */
@SpringBootApplication
public class SsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
    }

    //public static void main(String[] args) {
    //    String a = "18.1%";
    //    String b = "11.9%";
    //    String c = "30.6%";
    //    String d = "39.4%";
    //
    //    float result1 = test(a);
    //    float result2 = test(b);
    //    float result3 = test(c);
    //    float result4 = test(d);
    //
    //    System.out.println(result1);
    //    System.out.println(result2);
    //    System.out.println(result3);
    //    System.out.println(result4);
    //    result4 =result1+result2+result3+result4;
    //    System.out.println(result4);
    //}
    //
    //public static Float test(String value) {
    //    return new Float(value.substring(0, value.indexOf("%"))) / 100;
    //}
}
