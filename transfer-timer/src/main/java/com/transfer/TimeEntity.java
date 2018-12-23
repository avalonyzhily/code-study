package com.transfer;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2016/11/16.
 */
public class TimeEntity {
    private int difYear;//年差
    private int difMonth;//月差
    private int difDay;//日差
    private String yearUnit;//年的单位
    private String monthUnit;//月的单位
    private String dayUnit;//日的单位
    private String valueDifference;//根据格式化的表达式输出的结果

    public TimeEntity() {
        this.yearUnit = "年";
        this.monthUnit = "个月";
        this.dayUnit = "天";
    }
    public TimeEntity(String yearUnit,String monthUnit,String dayUnit){
        this.yearUnit = yearUnit;
        this.monthUnit = monthUnit;
        this.dayUnit = dayUnit;
    }
    public int getDifYear() {
        return difYear;
    }

    public void setDifYear(int difYear) {
        this.difYear = difYear;
    }

    public int getDifMonth() {
        return difMonth;
    }

    public void setDifMonth(int difMonth) {
        this.difMonth = difMonth;
    }

    public int getDifDay() {
        return difDay;
    }

    public void setDifDay(int difDay) {
        this.difDay = difDay;
    }

    public String getYearUnit() {
        return yearUnit;
    }

    public void setYearUnit(String yearUnit) {
        this.yearUnit = yearUnit;
    }

    public String getMonthUnit() {
        return monthUnit;
    }

    public void setMonthUnit(String monthUnit) {
        this.monthUnit = monthUnit;
    }

    public String getDayUnit() {
        return dayUnit;
    }

    public void setDayUnit(String dayUnit) {
        this.dayUnit = dayUnit;
    }

    public String getValueDifference(){
        //首选解析表达式
        StringBuilder tempValue = new StringBuilder();
        if(difYear!=0){
            tempValue.append(difYear).append(yearUnit);
        }
        if(difMonth!=0){
            tempValue.append(difMonth).append(monthUnit);
        }
        if(difDay!=0){
            tempValue.append(difDay).append(dayUnit);
        }
        if(StringUtils.isNotEmpty(tempValue)){
            this.valueDifference = tempValue.toString();
        }else{
            this.valueDifference = "";
        }
        return valueDifference;
    }
}
