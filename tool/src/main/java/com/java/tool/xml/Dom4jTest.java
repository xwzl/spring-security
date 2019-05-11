package com.java.tool.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dom4jTest {
    // 接收书籍实体的集合
    private static ArrayList<Book> bookList = new ArrayList<Book>();

    public static ArrayList<Book> getBookList() {
        return bookList;
    }

    @SuppressWarnings({"rawtypes"})
    private void dom4jReadXMLFile() {
        // Dom4j解析books.xml
        // 创建的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件，获取document对

            URL url = this.getClass().getClassLoader().getResource("ParseXml.xml");
            Document document = reader.read(url);
            // 通过document对象获取根节点bookstore
            Element bookstore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookstore.elementIterator();
            // 全局变量记录第几本书籍
            int num = 0;
            // 遍历迭代器，获取根节点中的信息(书籍)
            while (it.hasNext()) {
                Book bookEntity = new Book();
                num++;
                System.out.println("====开始遍历" + num + "本书====");
                Element book = (Element) it.next();
                // 获取book的属性名以及属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getStringValue());
                    bookEntity.setId(attr.getStringValue());
                }
                // 解析子节点的信息
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名：" + bookChild.getName() + "--节点值："
                            + bookChild.getStringValue());

                    if (bookChild.getName().equals("name")) {
                        bookEntity.setName(bookChild.getStringValue());
                    }
                    if (bookChild.getName().equals("author")) {
                        bookEntity.setAuthor(bookChild.getStringValue());
                    }
                    if (bookChild.getName().equals("year")) {
                        bookEntity.setYear(bookChild.getStringValue());
                    }
                    if (bookChild.getName().equals("price")) {
                        bookEntity.setPrice(bookChild.getStringValue());
                    }
                }
                // 将书籍存入书籍集合中
                bookList.add(bookEntity);
                // 将书籍实体设置为null，节省资源
                bookEntity = null;
                System.out.println("====结束遍历" + num + "本书====");
                System.out.println();//换行
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        // 验证书籍集合中是否成功存入书籍
        for (Book b : bookList) {
            System.out.println("++++开始++++");
            System.out.println(b.getId());
            System.out.println(b.getName());
            System.out.println(b.getAuthor());
            System.out.println(b.getYear());
            System.out.println(b.getPrice());
            System.out.println("++++结束++++");
            System.out.println();//换行
        }
    }

    void file() throws IOException {
        System.out.println(this.getClass().getResource(""));
        System.out.println(this.getClass().getResource("/"));
        //获取resources 目录下的文件
        System.out.println(this.getClass().getClassLoader().getResource("/"));
    }

    public static void main(String[] args) throws IOException {
        Dom4jTest dom4jTest = new Dom4jTest();
        dom4jTest.dom4jReadXMLFile();
        dom4jTest.file();
    }


}