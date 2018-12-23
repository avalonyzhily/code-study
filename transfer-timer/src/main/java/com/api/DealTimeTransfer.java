package com.api;

import com.transfer.TimeEntity;
import com.transfer.TimeTransfer;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/16.
 */
public class DealTimeTransfer {
    private TimeEntity timerEntity;
    public DealTimeTransfer() {
        this.timerEntity = new TimeEntity();
    }
    public DealTimeTransfer(String yearUnit,String monthUnit,String dayUnit) {
        if(StringUtils.isNotEmpty(yearUnit)&&StringUtils.isNotEmpty(monthUnit)&&StringUtils.isNotEmpty(dayUnit)) {
            this.timerEntity = new TimeEntity(yearUnit, monthUnit, dayUnit);
        }else {
            this.timerEntity = new TimeEntity();
        }
    }

    public String getResult(Date beginDate,Date endDate){
        return TimeTransfer.dealDate(beginDate,endDate,timerEntity);
    }
}
