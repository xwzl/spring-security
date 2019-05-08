package com.java.boot.exception;

/**
 * @author xuweizhi
 * @date 2019/04/24 15:47
 */
public class MyException {

    public static void main(String[] args){
         MyException myException = new MyException();
         try {
             myException.test1();
         }catch (ArrayIndexOutOfBoundsException a){
             a.printStackTrace();
             System.out.println(a.getMessage());
             System.out.println(a.fillInStackTrace());
             System.out.println(a.getCause().toString());
         }
    }

    public void test1(){
        test();
    }

    public void test(){
        throw new ArrayIndexOutOfBoundsException("111");
    }
}
