package com.ikkong.sunnylibrary.utils;

import android.text.TextUtils;

/**
 * Created by sunny on 2015-09-29.
 */
public class StringArrayUtil {

    /**
     * 增加指定的String 到 某个 String数组的头部
     * @param sa 数组
     * @param s 要添加的字符
     * @return 新数组
     */
    public static String[] addStrOnStart(String[] sa,String s){
        String[] sss = new String[sa.length+1];
        sss[0] = s;
        for (int i = 1; i < sss.length; i++) {
            sss[i] = sa[i-1];
        }
        return sss;
    }

    /**
     * 获取某个字符串在String数组中的 下标，若不在，返回0
     * @param sa 数组
     * @param s 要查找的字符
     * @return 首个下标
     */
    public static int getStrIndex(String[] sa,String s){
        for (int i = 0; i < sa.length; i++) {
            if (sa[i].equals(s)){
                return i;
            }
        }
        return 0;
    }

    /**
     * 获取String数组成员 中 特定字符串结尾 的 成员下标，若不在，返回0
     * @param sa 数组
     * @param s  搜索字符
     * @return 首个符合条件的下标
     */
    public static int getStrIndexOnlyEquimentStatus(String[] sa,String s){
        for (int i = 0; i < sa.length; i++) {
            if (sa[i].endsWith(s)){
                return i;
            }
        }
        return 0;
    }

    /**
     * 将sa数组 用s 连接成String，前提条件 sa中字符串不得含有 分隔符s
     * @param sa 数组
     * @param s 连接符
     * @return 连接后的字符串
     */
    public static String buildString(String[] sa , String s){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sa.length; i++) {
            if(!TextUtils.isEmpty(sa[i])) {
                sb.append(sa[i]);
                if (i < sa.length - 1) {
                    sb.append(s);
                }
            }
        }
        String str = sb.toString();
        if(str.endsWith(s)){
            str = StringUtil.trimEnd(str, s);
        }
        return str;
    }

    /**
     * 给 数组 的每个值加一个前缀
     * @param sa 数组
     * @param prefix 前缀
     * @return 新数组
     */
    public static String[] addPrefix(String[] sa , String prefix){
        if(sa == null){
            return null;
        }
        String[] newSa = new String[sa.length];
        for (int i = 0; i < sa.length; i++) {
            newSa[i] = TextUtils.concat(prefix,sa[i]).toString();
        }
        return newSa;
    }

    /**
     * 判断 数组是否所有值均为空，
     * @param sa 数组
     * @return true 全空 false 有不是空的
     */
    public static boolean isEmpty(String[] sa){
        if(sa == null){
            return true;
        }
        for (int i = 0; i < sa.length; i++) {
            if(!TextUtils.isEmpty(sa[i])){
                return false;
            }
        }
        return true;
    }
}
