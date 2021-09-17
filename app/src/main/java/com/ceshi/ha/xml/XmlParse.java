package com.ceshi.ha.xml;

import static org.xmlpull.v1.XmlPullParser.END_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_DOCUMENT;
import static org.xmlpull.v1.XmlPullParser.START_TAG;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

/**
 * PULL解析是一组开源API,用于解析xml,解析
 * 方式类似SAX,同样是基于事件对象进行数据
 * 处理，但其API的使用要比SAX简单，且高效。在Android中默认集成了PULL解析，并且也推荐使用pull解析.
 */
public class XmlParse {
    //假设此字符串来自网络
    private static String xml =
            "<msg>"
                    + "<title>TA</title>"
                    + "<content>CT</content>"
                    + "</msg>";

    /**
     * 解析xml字符串
     * 1.dom
     * 2.sax
     * 3.pull
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        parseByPULL();
    }

    //DOM解析
    private void parseByDom() throws Exception {
        //创建解析器
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //解析字符串
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        //处理数据
    }

    //SAX解析
    private void parseBySAX() throws Exception {
        SAXParser sp = SAXParserFactory.newInstance().newSAXParser();

        sp.parse(new InputSource(new StringReader(xml)), new DefaultHandler() {
            //重写方法处理事件
        });
    }

    //PULL解析
    private static void parseByPULL() throws Exception {
        //1.创建解析器对象
        XmlPullParser xpp = Xml.newPullParser();
        //2.解析文件
        //xpp.setInput(new StringReader(xml));//系统默认编码
        xpp.setInput(new InputStreamReader(new ByteArrayInputStream(xml.getBytes()), Charset.forName("utf-8")));
        //3.处理数据
        //3.1获得事件类型
        int event = xpp.getEventType();
        Message msg = null;
        //3.2处理事件
        do {
            if (event == START_DOCUMENT) {
                msg = new Message();
            } else if (event == START_TAG) {
                processStartTag(xpp, msg);
            } else if (event == END_TAG) {
                processEndTag(xpp, msg);
            }
            //获得下一个事件
            event = xpp.next();
        } while (event != END_DOCUMENT);

    }

    private static void processEndTag(XmlPullParser xpp, Message msg) {
        if (xpp.getName().equals("msg")) {
            Log.i("TAG", "msg=" + msg.toString());
        }
    }

    private static void processStartTag(XmlPullParser xpp, Message msg)
            throws XmlPullParserException, IOException {
        if (xpp.getName().equals("title")) {
            msg.setTitle(xpp.nextText());
        } else if (xpp.getName().equals("content")) {
            msg.setContent(xpp.nextText());
        }
    }
}
