package com.doreamon.treasure.utils;

/**
 * 时间处理工具
 */
public class TimeUtil {

    /**
     * 接收一个时长（单位：秒）转换成格式“xx时xx分xx秒”
     * @param duration 时长（单位：秒）
     * @return xx时xx分xx秒
     */
    public static String durationFormat(long duration) {
        long h = duration / (long)3600;
        long m = duration % (long)3600 / (long)60;
        long s = duration % (long)3600 % (long)60;
        String returnValue = "";
        if (h > 0L) {
            returnValue = returnValue + h + "时";
        }

        if (h > 0L || m > 0L) {
            returnValue = returnValue + m + '分';
        }

        returnValue = returnValue + s + "秒";
        return returnValue;
    }
}
