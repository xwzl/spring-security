package com.java.boot.base.model;

/**
 * @author xuweizhi
 * @date 2019/04/22 14:12
 */
public class Book {

    private String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                '}';
    }
}
