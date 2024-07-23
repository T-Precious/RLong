package com.rongzer.suzhou.scm.util;




import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * @Description 处理时间工具类
 * @Author tiancl
 * @Date 2022/10/24 17:08
 * @Version 1.0
 **/
public class DateUtil {

    public static String differenceTime(String time){
        String timeStr=null;
        try {
            Date date=new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date otherDate = sf.parse(time);
            long diff = date.getTime() - otherDate.getTime();
            long sec = diff / 1000;
            if( sec < 60L ){
                timeStr= sec+"秒";
            }else {
                long min = diff / 60 / 1000;
                if (min < 60L) {
                    timeStr = min + "分钟";
                }else {
                    long hours = diff / 60 / 60 / 1000;
                    if (hours < 24L) {
                        timeStr = hours + "小时";
                    }else {
                        long day = diff / 24 / 60 / 60 / 1000;
                        timeStr = day + "天";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }
    public static String getFirstFewYear(String dateStr,String format,boolean flag, int year){
        Calendar c=Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime (date);
        int year1=c.get(Calendar.YEAR);
        if (flag) {
            c.set(Calendar.YEAR, year1 + year);
        }else {
            c.set(Calendar.YEAR, year1- year);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }
    public static String getFirstFewMonth(String dateStr,String format,boolean flag, int month){
        Calendar c=Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime (date);
        int month1=c.get(Calendar.MONTH);
        if (flag) {
            c.set(Calendar.MONTH, month1 + month);
        }else {
            c.set(Calendar.MONTH, month1- month);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }
    /**
     * @Description:获取指定日期的前几天或后几天
     * @Author: tian.changlong
     * @Date: 2022/11/14 14:40
     * @param dateStr: 指定日期
     * @param format: 日期格式
     * @param flag: true为获取后几天, false:为获取前几天
     * @param day: 指定需要获取的天数
     * @return: java.lang.String
     **/
    public static String getFirstFewDays(String dateStr,String format,boolean flag, int day){
        Calendar c=Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime (date);
        int day1=c.get(Calendar.DATE);
        if (flag) {
            c.set(Calendar.DATE, day1 + day);
        }else {
            c.set(Calendar.DATE, day1- day);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static String getFirstFewHours(String dateStr,String format,boolean flag, int hour){
        Calendar c=Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime (date);
        int nowHour = c.get(Calendar.HOUR_OF_DAY);
        if (flag) {
            c.set(Calendar.HOUR_OF_DAY, nowHour + hour);
        }else {
            c.set(Calendar.HOUR_OF_DAY, nowHour - hour);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }

    public static String getFirstFewMinutes(String dateStr,String format,boolean flag, int minute){
        Calendar c=Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime (date);
        int minute1=c.get(Calendar.MINUTE);
        if (flag) {
            c.set(Calendar.MINUTE, minute1 + minute);
        }else {
            c.set(Calendar.MINUTE, minute1- minute);
        }
        return new SimpleDateFormat(format).format(c.getTime());
    }
    public static Date getDate(String dateValue,String format) throws ParseException {
        if (dateValue == null) {
            return null;
        } else {
            SimpleDateFormat sfdate = new SimpleDateFormat(format);
            return sfdate.parse(dateValue);
        }
    }
    public static Map sortDateDesc(JSONObject jsonObject){
        Map<String,Object> map = new TreeMap();
        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Set<String> stringSet=jsonObject.keySet();
//            Map map = new TreeMap();
            List<String> retStr=new ArrayList();
            for(String item:stringSet){
//                map.put(sdf.parse(item).getTime(), item);
                retStr.add(item);
            }
//            Collection coll=map.values();
//            retStr.addAll(coll);
//            Collections.reverse(retStr);
            Collections.sort(retStr);
            retStr.stream().forEach(str->{
//                map.put(str,jsonObject.getIntValue(str));
                map.put(str,jsonObject.get(str));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取当天的时间
     * @return
     */
    public static String getCurrentDate(String formatterStr){
        SimpleDateFormat formater=new SimpleDateFormat (formatterStr);
        String strCurrentTime=formater.format (new Date ());
        return strCurrentTime;
    }
    public static String getCurrentDate(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(LocalDate.now());
    }
    /**
     * 获取本周内的开始时间和结束时间
     * @return
     */
    public static String[] getDateScopeWeek(){
        return new String[]{getStartWeek(),getEndWeek()};
    }

    /**
     * 获取本周开始时间
     * @return
     */
    public static String getStartWeek(){
        LocalDate today = LocalDate.now();
        LocalDate oneDayOfWeek = getOneDayOfWeek(today, 1);
        return LocalDateTime.of(oneDayOfWeek,LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取本周的结束时间
     * @return
     */
    public static String getEndWeek(){
        LocalDate today = LocalDate.now();
        LocalDate oneDayOfWeek = getOneDayOfWeek(today, 7);
        return LocalDateTime.of(oneDayOfWeek,LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取一周内的某一天
     * @param today 这周内任意一天的日期
     * @param day 想要获取一周中的第几天
     * @return LocalDate
     */
    private static LocalDate getOneDayOfWeek(TemporalAccessor today, int day){
        TemporalField fieldIso = WeekFields.of(DayOfWeek.MONDAY, 1).dayOfWeek();
        LocalDate localDate = LocalDate.from(today);
        return localDate.with(fieldIso, day);
    }

    /**
     * 获取本月内的开始时间和结束时间
     * @return
     */
    public static String[] getDateScopeMonth(){
        return new String[]{getOneDayOfMonth(),getEndDayOfMonth()};
    }

    /**
     * 获取本月的开始日期
     * @return
     */
    public static String getOneDayOfMonth(){
        LocalDate date = LocalDate.now();
        LocalDate with = date.with(TemporalAdjusters.firstDayOfMonth());
        return LocalDateTime.of(with,LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取本月的结束日期
     * @return
     */
    public static String getEndDayOfMonth(){
        LocalDate date = LocalDate.now();
        LocalDate with = date.with(TemporalAdjusters.lastDayOfMonth());
        return LocalDateTime.of(with,LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取本年内的开始时间和结束时间
     * @return
     */
    public static String[] getDateScopeYear(){
        return new String[]{getOneDayOfYear(),getEndDayOfYear()};
    }

    /**
     * 获取本年度开始日期
     * @return
     */
    public static String getOneDayOfYear(){
        LocalDate date = LocalDate.now();
        LocalDate with = date.with(TemporalAdjusters.firstDayOfYear());
        return LocalDateTime.of(with,LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取本年度结束日期
     * @return
     */
    public static String getEndDayOfYear(){
        LocalDate date = LocalDate.now();
        LocalDate with = date.with(TemporalAdjusters.lastDayOfYear());
        return LocalDateTime.of(with,LocalTime.MAX).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

   public static String getTimestampDate(Long timeStamp,String patternStr){
       SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patternStr);
       Timestamp ts =new Timestamp(timeStamp);
       String tsStr = simpleDateFormat.format(ts);
       return tsStr;
   }
    public static boolean belongCalendar(String nowDate, String startDate, String endDate)throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date now = format.parse(nowDate);
        Calendar date = Calendar.getInstance();
        date.setTime(now);

        Date start = format.parse(startDate);
        Calendar after = Calendar.getInstance();
        after.setTime(start);

        Date end = format.parse(endDate);
        Calendar before = Calendar.getInstance();
        before.setTime(end);

        if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
//        long nowTime = now.getTime();
//        long startTime = start.getTime();
//        long endTime = end.getTime();
//
//        return nowTime >= startTime && nowTime <= endTime;
    }
    public static void main(String[] args){
//        System.out.println("2022-11-29 15:38".substring(11));
//        System.out.println(DateUtil.getFirstFewYear(LocalDateUtil.nowDate("yyyy-MM"),"yyyy",false,1));
//        JSONObject jsonObject=new JSONObject();
//        String strJson="{\"2022-11-13\":3,\"2022-11-14\":1,\"2022-11-08\":0,\"2022-11-09\":0,\"2022-11-11\":1,\"2022-11-12\":1,\"2022-11-10\":0}";
//        jsonObject=JSONObject.parseObject(strJson);
//        DateUtil.sortDateDesc(jsonObject);
//        String timestamp="seconds:1676358613 nanos:823000000 ";
//        timestamp=timestamp.substring(timestamp.indexOf(":")+1,timestamp.indexOf("nanos")-1);
//        System.out.println(timestamp);
//        //将时间戳转换为时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long lt =Long.parseLong("1676358613000");
//        Date date1 = new Date(lt);
//        String res = simpleDateFormat.format(date1);
//        System.out.println("时间戳转时间结果:" + res);
//
//        Timestamp ts =new Timestamp(1676358613000L);
//        String tsStr = "";
//        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        tsStr = simpleDateFormat.format(ts);
//        System.out.println(tsStr);
        try {
//            System.out.println(belongCalendar("01:37:00","20:00:00","23:59:59") || belongCalendar("01:37:00","00:00:00","06:00:00"));
            System.out.println(getCurrentDate("HH:mm:ss"));
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
