package com.hjh.mall.common.core.validation;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.common.core.constants.DatePrecision;
import com.hjh.mall.common.core.util.DateTimeUtil;

public abstract class DateCompareValidatorBase {
    
    /**
     * 如果值是字符串类型的话，指明格式
     */
    protected String stringPattern;
    
    /**
     * 参考比较时间，{@link java.util.Date#getTime()}
     */
    protected long refDate;
    
    /**
     * 是否包括参考时间
     */
    protected boolean includeRef;
    
    /**
     * 指明比较精度
     */
    protected DatePrecision datePrecision;
    
    protected void genDefaultStringPatternIfNecessary() {
        // 如果没设置stringPattern，默认按数字连续的形式根据datePrecision生成
        if (StringUtils.isBlank(stringPattern)) {
            switch (datePrecision) {
                case YEAR:
                    stringPattern = "yyyy";
                    break;
                case MONTH:
                    stringPattern = "yyyyMM";
                    break;
                case DAY:
                    stringPattern = "yyyyMMdd";
                    break;
                case HOUR:
                    stringPattern = "yyyyMMddHH";
                    break;
                case MINUTE:
                    stringPattern = "yyyyMMddHHmm";
                    break;
                case SECOND:
                    stringPattern = "yyyyMMddHHmmss";
                    break;
                case MILLISECOND:
                    stringPattern = "yyyyMMddHHmmssSSS";
                    break;
            }
        }
    }
    
    protected boolean isValidInternal(Object value, ConstraintValidatorContext context) {
        // 空值直接通过，由@NotNull之类的控制
        if (null == value) {
            return true;
        }
        // 获取传入参数的时间
        long testTime = -1;
        if (value instanceof Date) {
            testTime = ((Date) value).getTime();
        } else if (value instanceof Calendar) {
            testTime = ((Calendar) value).getTimeInMillis();
        } else if (value instanceof String || value instanceof Integer || value instanceof Long) {
            // 字符串或Integer或Long需要解析
            String stringValue = String.valueOf(value).trim();
            // 空值直接通过，由@NotNull之类的控制
            // 0直接通过，按无值处理
            if (stringValue.isEmpty() || "0".equals(stringValue)) {
                return true;
            }
            Date date = DateTimeUtil.toDateOrNull(stringValue, stringPattern);
            if (null == date) {
                // 格式错误返回false
                return false;
            } else {
                testTime = date.getTime();
            }
        } else {
            // 其他类型非法
            return false;
        }
        
        // 如果没有设置参考时间或参考小于等于0，则取当前时间为参考时间
        if (0 >= refDate) {
            refDate = System.currentTimeMillis();
        }
        
        if (compare(refDate, testTime)) {
            // 直接比较毫秒通过
            return true;
        } else {
            // 毫秒比较失败或相等，需要看比较精度
            Calendar refCalendar = Calendar.getInstance();
            refCalendar.setTimeInMillis(refDate);
            
            Calendar testCalendar = Calendar.getInstance();
            testCalendar.setTimeInMillis(testTime);
            
            // 根据需要比较的精度进行穿透case清理字段
            switch (datePrecision) {
                case YEAR:
                    clearField(refCalendar, testCalendar, Calendar.MONTH, 0);
                case MONTH:
                    clearField(refCalendar, testCalendar, Calendar.DAY_OF_MONTH, 1);
                case DAY:
                    clearField(refCalendar, testCalendar, Calendar.HOUR_OF_DAY, 0);
                case HOUR:
                    clearField(refCalendar, testCalendar, Calendar.MINUTE, 0);
                case MINUTE:
                    clearField(refCalendar, testCalendar, Calendar.SECOND, 0);
                case SECOND:
                    clearField(refCalendar, testCalendar, Calendar.MILLISECOND, 0);
                case MILLISECOND:
            }
            // 按设置精度后的日历比较
            return compare(refCalendar, testCalendar);
        }
    }
    
    protected abstract boolean compare(long ref, long test);
    
    protected abstract boolean compare(Calendar ref, Calendar test);
    
    private void clearField(Calendar refCalendar, Calendar testCalendar, int field, int value) {
        refCalendar.set(field, value);
        testCalendar.set(field, value);
    }
    
}
