package com.ikkong.adgo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ikkong on 2015/12/18.
 */
public class StringUtil {
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static Pattern IMG_URL = Pattern
            .compile(".*?(gif|jpeg|png|jpg|bmp)");

    private final static Pattern URL = Pattern
            .compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    /**
     * 字符串截取
     * @param str
     * @param length
     * @return
     */
    public static String cutStr(String str,int length){
        return str.length()>=length?str.substring(0,length):str;
    }
    
    /**
     * 判断字符是否中文
     * @param c char
     * @return bool
     */
    public static boolean isChinese(char c) {
        boolean result = false;
        if (c >= 19968 && c <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
            result = true;
        }
        return result;
    }

    /**
     * 去除 str 尾部 的 t
     * @param str 字符串
     * @param t 要去掉的字符
     * @return 去除后的新Str
     */
    public static String trimEnd(String str,String t){
        String rel = str;
        if(rel.endsWith(t)){
            rel = rel.substring(0,str.length()-t.length());
        }
        return rel;
    }

    /**
     * 去除 str 头部 的 t
     * @param str 字符串
     * @param t 要去掉的字符
     * @return 去除后的新Str
     */
    public static String trimStart(String str,String t){
        String rel = str;
        if(rel.startsWith(t)){
            rel = rel.substring(t.length());
        }
        return rel;
    }

    /**
     * 去除 str 头、尾部 的 t
     * @param str 字符串
     * @param t 要去掉的字符
     * @return 去除后的新Str
     */
    public static String trim(String str,String t){
        String rel = trimEnd(str,t);
        rel = trimStart(rel,t);
        return rel;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email 字符
     * @return bool
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断一个url是否为图片url
     *
     * @param url
     * @return
     */
    public static boolean isImgUrl(String url) {
        if (url == null || url.trim().length() == 0)
            return false;
        return IMG_URL.matcher(url).matches();
    }

    /**
     * 判断是否为一个合法的url地址
     *
     * @param str
     * @return
     */
    public static boolean isUrl(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return URL.matcher(str).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str 字符
     * @param defValue 默认值
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static Double toDouble(String obj) {
        if (obj == null)
            return 0.0;
        try {
            return Double.parseDouble(obj);
        } catch (Exception e) {
        }
        return 0.0;
    }

    /**
     * 对象转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static Float toFloat(String obj) {
        if (obj == null)
            return 0.0f;
        try {
            return Float.parseFloat(obj);
        } catch (Exception e) {
        }
        return 0.0f;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj,long defaultVal) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return defaultVal;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /**
     * The string "boo:and:foo", for example, yields the following results with these expressions: 
     Regex Result 
     : { "boo", "and", "foo" }
     o { "b", ":and:f" }
     * @param str 要分割的字符串
     * @param regex 分割参数
     * @return
     */
    public static String[] splitNoEmpty(String str,String regex){
        String A[] = str.split(regex);
        List<String> list = new ArrayList<String>();
        for (String a : A) {
            if(!a.equals(""))
                list.add(a);
        }
        return list.toArray(new String[0]);
    }
}
