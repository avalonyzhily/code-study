package com.transfer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/16.
 */
public class TimeTransfer {
    public static String dealDate(Date beginDate,Date endDate,TimeEntity timeEntity){
        //转换为日历类
        Calendar beginDateC = Calendar.getInstance();
        beginDateC.setTime(beginDate);
        Calendar endDateC = Calendar.getInstance();
        endDateC.setTime(endDate);
        //分别获取时间中的年,月,日
        int yearBegin = beginDateC.get(Calendar.YEAR);
        int monthBegin = beginDateC.get(Calendar.MONTH)+1;
        int dayBegin = beginDateC.get(Calendar.DATE);
        int dayMaxBegin = beginDateC.getActualMaximum(Calendar.DAY_OF_MONTH);
        int yearEnd = endDateC.get(Calendar.YEAR);
        int monthEnd = endDateC.get(Calendar.MONTH)+1;
        int dayEnd= endDateC.get(Calendar.DATE);
        int dayMaxEnd = endDateC.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(dayBegin==dayMaxBegin&&dayEnd==dayMaxEnd){
            //开始日期和结束日期都是月底
            //直接计算月份差，推算几年几个月
            int res = (yearEnd-yearBegin)*12+(monthEnd-monthBegin);//月份差
            int difYear = res/12;
            int difMonth = res%12;
            timeEntity.setDifYear(difYear);
            timeEntity.setDifMonth(difMonth);
            return timeEntity.getValueDifference();
        }else if(dayBegin==dayMaxBegin&&dayEnd<dayMaxEnd){
            //开始日期是月底，结束日期不是
            //计算开始日期到结束日期上一个月的月底,再加上结束日期的天数
            int month3 = monthEnd-1;
            int res = (yearEnd-yearBegin)*12+(month3-monthBegin);//月份差
            int difYear = res/12;
            int difMonth = res%12;
            int difDay = dayEnd;
            timeEntity.setDifYear(difYear);
            timeEntity.setDifMonth(difMonth);
            timeEntity.setDifDay(difDay);
            return timeEntity.getValueDifference();
        }else if(dayBegin<dayMaxBegin&&dayEnd<dayMaxEnd){
            //开始日期和结束日期都不是月底
            //情况1,dayBegin<dayEnd
            if(dayBegin<=dayEnd){
                int res = (yearEnd-yearBegin)*12+(monthEnd-monthBegin);//月份差
                int difYear = res/12;
                int difMonth = res%12;
                int difDay = dayEnd-dayBegin;
                timeEntity.setDifYear(difYear);
                timeEntity.setDifMonth(difMonth);
                timeEntity.setDifDay(difDay);
                return timeEntity.getValueDifference();
            }else{
                int monthTemp = monthEnd-1;
                int res = (yearEnd-yearBegin)*12+(monthTemp-monthBegin);//月份差
                int difYear = res/12;
                int difMonth = res%12;
                int difDayTemp = dayBegin-dayEnd;
                Calendar dateTemp = Calendar.getInstance();
                dateTemp.set(yearEnd,monthTemp,0);
                int dayMaxTemp = dateTemp.getActualMaximum(Calendar.DAY_OF_MONTH);
                int difDay = dayMaxTemp-difDayTemp;
                timeEntity.setDifYear(difYear);
                timeEntity.setDifMonth(difMonth);
                timeEntity.setDifDay(difDay);
                return timeEntity.getValueDifference();
            }
        }
        return null;
    }
}
