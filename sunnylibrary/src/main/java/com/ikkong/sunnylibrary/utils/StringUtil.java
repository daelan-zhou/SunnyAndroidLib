package com.ikkong.sunnylibrary.utils;

/**
 * Created by ikkong on 2015/12/18.
 */
public class StringUtil {

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
}
