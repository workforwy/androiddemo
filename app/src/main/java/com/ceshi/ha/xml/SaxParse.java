package com.ceshi.ha.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SAX(Simple API for Xml)解析是基于事件对象获取数据的一种方案，它会将xml所有成员理解为事件，在解析时无须将所有xml成员存储在内存，且解析速度会比较快。
 * <p>
 * API：
 * 1)SAXParserFactory(解析器工厂对象)
 * 2)SAXParser(解析器对象)
 * 3)DefaultHandler(事件处理对象)
 * <p>
 * 场合(结合特点)
 * 1)优点：占用内存低，速度快。
 * 2)缺点：重复读取及修改困难。
 */
public class SaxParse {
    static int count;
    static List<Book> list;

    static class MyHandler extends DefaultHandler {
        /**
         * 文档开始事件
         */
        @Override
        public void startDocument() throws SAXException {
            // System.out.println("startDocument");
            list = new ArrayList<Book>();
        }

        Book book = null;
        String currentTag = null;

        /**
         * 元素开始事件
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            // System.out.println("startElement,qName="+qName);
            if (qName.equals("books")) {
                // System.out.println(attributes.getValue("type"));
            } else if (qName.equals("book")) {
                count++;
                book = new Book();
            }
            currentTag = qName;
        }

        /**
         * 元素结束事件
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            // System.out.println("endElement,qName="+qName);
            if ("book".equals(qName)) {
                list.add(book);
            }
            currentTag = null;
        }

        /**
         * 文本事件
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            // System.out.println("characters:"+new String(ch,start,length));
            String content = new String(ch, start, length);
            if ("name".equals(currentTag)) {
                book.setName(content);
            } else if ("price".equals(currentTag)) {
                book.setPrice(Double.parseDouble(content));
            }
        }

        /**
         * 文档结束事件
         */
        @Override
        public void endDocument() throws SAXException {
            // System.out.println("endDocument");
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        // 1.创建解析器
        SAXParser sp = SAXParserFactory.newInstance().newSAXParser();
        // 2.解析文件
        sp.parse("src/day20/books.xml", new MyHandler());// 构建DefaultHandler的子类对象
        // 3.处理数据
        System.out.println(count);
        System.out.println(list);
    }
}
