package com.ikkong.adgo.utils;

import android.text.TextUtils;

import com.ikkong.adgo.entity.IdNameArray;


/**
 * Created by sunny on 2015-09-29.
 */
public class StringArrayUtils {

    /**
     * 在ina前面插入全部
     * @param ina
     * @return
     */
    public static IdNameArray addEmptyFirst(IdNameArray ina){
        String id[] = new String[0],name[] = new String[0];
        if(ina == null){
            ina = new IdNameArray(id,name);
        }
        ina = new IdNameArray(addStrOnStart(ina.getId(),""),addStrOnStart(ina.getName(),"不限"));
        return ina;
    }
    
    /**
     * 增加指定的String 到 某个 String数组的头部
     * @param sa
     * @param s
     * @return
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
     * @param sa
     * @param s
     * @return
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
     * 获取某个字符串属于String数组成员结尾 中的 成员下标，若不在，返回0
     * @param sa
     * @param s
     * @return
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
     * @param sa
     * @param s
     * @return
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
     * @param sa
     * @param prefix
     * @return
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
     * @param sa
     * @return true 全空
     */
    public static boolean isEmpty(String[] sa){
        if(sa == null){
            return true;
        }
        for (int i = 0; i < sa.length; i++) {
            if(sa[i] != null && !sa[i].equals("")){
                return false;
            }
        }
        return true;
    }
}
