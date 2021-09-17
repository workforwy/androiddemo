package com.ceshi.ha.xml;

//借助domapi构建一个xml文档

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 1.原理(将xml文档理解为由NODE构成)
 * 2.步骤(解析器，解析，处理数据)
 * 3.场合(结合特点)
 * 1)优点：标准，简单，便于反复读取及修改。
 * 2)缺点：占用内存，解析步骤复杂。
 */

public class DomParse {
    static Map<String, String> map = new HashMap<>();

    static {
        map.put("totalPrice", "100");
        map.put("orderDate", "2015-12-24");
    }

    // 从map集合中key/value,然后以key
    // 作为元素名，值作为文本内容，构建一个字符串
    // 要求根元素为order.

    /***
     * <order> <totalPrice>100</totalPrice> <orderDate>2015-12-24</orderDate>
     * </order>
     *
     * @param args
     * @throws ParserConfigurationException
     * @throws TransformerFactoryConfigurationError
     * @throws TransformerException
     */
    public static void main(String[] args) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        // 1.获得Document对象
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        // 2.创建根元素
        Element order = doc.createElement("order");

        Set<Map.Entry<String, String>> set = map.entrySet();

        for (Map.Entry<String, String> e : set) {
            Element en = doc.createElement(e.getKey());
            Text txt = doc.createTextNode(e.getValue());
            en.appendChild(txt);
            order.appendChild(en);
        }
        doc.appendChild(order);

        // 删除节点
        /*
         * System.out.println( doc.getFirstChild()//order .getFirstChild()
         * .getNodeName());
         *
         * Node node=doc.getFirstChild(); node.removeChild( node.getFirstChild());
         */

        // 构建DOMSource对象接收Document节点
        DOMSource ds = new DOMSource(doc);
        // 构建Transformer对象，转换dom节点
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        // 转换dom节点
        /*
         * tf.transform(ds, new StreamResult( new File("a.xml")));
         */

        tf.transform(ds, new StreamResult(System.out));

        /*
         * StringWriter sw= new StringWriter(); tf.transform(ds, new StreamResult(sw));
         * String xml=sw.toString(); System.out.println(xml);
         */

        /*
         * ByteArrayOutputStream bos= new ByteArrayOutputStream(); tf.transform(ds, new
         * StreamResult(bos));
         *
         * System.out.println( bos.toString());
         */
    }
}
