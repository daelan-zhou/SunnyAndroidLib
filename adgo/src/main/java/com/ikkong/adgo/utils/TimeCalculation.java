package com.ikkong.adgo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/5/5
 * Description:
 */
public class TimeCalculation {
    private TimeDiff timeDiff;

    public TimeDiff dateDiff(String startTime, String endTime, String format) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        try {
            //获得两个时间的毫秒时间差异
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff / nd;//计算差多少天
            long hour = diff % nd / nh;//计算差多少小时
            long min = diff % nd % nh / nm;//计算差多少分钟
            long sec = diff % nd % nh % nm / ns;//计算差多少秒
            //输出结果
            timeDiff = new TimeDiff(day, hour, min, sec);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeDiff;
    }
    
    public static void main(String[] args){
        TimeDiff t = new TimeCalculation().dateDiff(new SimpleDateFormat("HHmmss").format(new Date()),"174000","HHmmss");
        System.out.print(t.getDay()+"天"+t.getHour()+"小时"+t.getMin()+"分钟");
    }
}
