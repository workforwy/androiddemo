package com.ceshi.ha.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/15.
 */
public class HtmlUtils {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String source = "<activity_listview title=中国合伙人 href='#'>中国合伙人</activity_listview><activity_listview title='致青春' href='#'>致青春</activity_listview>";
        List<String> list = match(source, "activity_listview", "title");
        System.out.println(list);
    }

    /**
     * @param urlString
     * @return
     */
    public static String getHtml(String urlString) {
        try {
            StringBuffer html = new StringBuffer();
            URL url = new URL(urlString); //根据 String 表示形式创建 URL 对象。
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
            InputStreamReader isr = new InputStreamReader(conn.getInputStream());//返回从此打开的连接读取的输入流。
            BufferedReader br = new BufferedReader(isr);//创建一个使用默认大小输入缓冲区的缓冲字符输入流。

            String temp;
            while ((temp = br.readLine()) != null) { //按行读取输出流
                if (!temp.trim().equals("")) {
                    html.append(temp).append("\n"); //读完每行后换行
                }
            }
            br.close(); //关闭
            isr.close(); //关闭
            return html.toString(); //返回此序列中数据的字符串表示形式。
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * 获取指定HTML标签的指定属性的值
     *
     * @param source  要匹配的源文本
     * @param element 标签名称
     * @param attr    标签的属性名称
     * @return 属性值列表
     */
    public static List<String> match(String source, String element, String attr) {
        List<String> result = new ArrayList<String>();
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }

    /**
     * @param args
     */
    public static void remove(String[] args) {
        FileReader fr;
        String content = null;
        String regex = "<[Hh]1>.*</[Hh]1>";
        try {
            fr = new FileReader("tag.html");
            BufferedReader br = new BufferedReader(fr);
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\n");
            }
            content = sb.toString();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb1 = new StringBuffer();
        while (matcher.find()) {
            sb1.append(matcher.replaceAll("") + "\n");
        }
        try {
            FileWriter fw = new FileWriter("tag.html");
            BufferedWriter bw = new BufferedWriter(fw);
            fw.write(sb1.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
