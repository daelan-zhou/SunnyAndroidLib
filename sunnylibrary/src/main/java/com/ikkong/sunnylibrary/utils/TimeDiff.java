package com.ikkong.sunnylibrary.utils;

/**
 * Author:  ikkong
 * Email:   ikkong@163.com
 * Date:    2016/5/5
 * Description:
 */
public class TimeDiff {
    private long day;//计算差多少天
    private long hour;//计算差多少小时
    private long min;//计算差多少分钟
    private long sec;//计算差多少秒

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getSec() {
        return sec;
    }

    public void setSec(long sec) {
        this.sec = sec;
    }

    public TimeDiff() {

    }

    public TimeDiff(long day, long hour, long min, long sec) {
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }
}
