package com.jk.common;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 转换为utf时间格式
 * 于洋
 * 2018-3-27 23:00:00
 */
public class UtfDate {

    public static String utfTime(String time) throws ParseException {

        //自动补零
        DecimalFormat df=new DecimalFormat("00");

        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date =sdf.parse(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        UTCTimeBuffer.append(year).append("-").append(df.format(month)).append("-").append(df.format(day)).append("T") ;
        UTCTimeBuffer.append(df.format(hour)).append(":").append(df.format(minute)).append(":").append(df.format(s)).append("Z");
        System.out.println(UTCTimeBuffer.toString());
        return UTCTimeBuffer.toString();
    }

}
