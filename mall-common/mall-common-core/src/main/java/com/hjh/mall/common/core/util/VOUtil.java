package com.hjh.mall.common.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.util.ValueUtil;

/**
 * @author chengjia
 *
 */
public class VOUtil {
	public static Map<String, Object> genEmptyResult() {
        return new HashMap<String, Object>();
    }
    
    public static Map<String, Object> setErrorResult(Map<String, Object> result, String errorNo) {
        result.put(BasicFields.ERROR_NO, errorNo);
        return result;
    }
    
    public static Map<String, Object> setErrorResult(Map<String, Object> result, String errorNo, String errorInfo) {
        result.put(BasicFields.ERROR_NO, errorNo);
        result.put(BasicFields.ERROR_INFO, errorInfo);
        return result;
    }
    
    public static Map<String, Object> genErrorResult(String errorNo) {
        Map<String, Object> result = genEmptyResult();
        result.put(BasicFields.ERROR_NO, errorNo);
        return result;
    }
    
    public static Map<String, Object> genErrorResult(String errorNo, String errorInfo) {
        Map<String, Object> result = genEmptyResult();
        result.put(BasicFields.ERROR_NO, errorNo);
        result.put(BasicFields.ERROR_INFO, errorInfo);
        return result;
    }
    
    public static Map<String, Object> setSuccessResult(Map<String, Object> result, String errorInfo) {
        return setErrorResult(result, BasicFields.SUCCESS, errorInfo);
    }
    
    public static Map<String, Object> genSuccessResult() {
        Map<String, Object> result = genEmptyResult();
        return setSuccessResult(result);
    }
    
    public static Map<String, Object> genSuccessExtResult() {
        Map<String, Object> result = genEmptyResult();
        result.put(BasicFields.EXTSUCCESS,BasicFields.EXTRESULT);
        result.put(BasicFields.ERROR_NO, BasicFields.SUCCESS);
        return result;
    }
    
    public static Map<String, Object> genSuccessResult(String errorInfo) {
        return genErrorResult(BasicFields.SUCCESS, errorInfo);
    }
    
    public static Map<String, Object> setSuccessResult(Map<String, Object> result) {
        result.put(BasicFields.ERROR_NO, BasicFields.SUCCESS);
        return result;
    }
    public static Map<String, Object> genErrorResult(HJHBCSErrInfoException exception) {
        return genErrorResult(exception.getErrorNo(), exception.getErrorInfo(), exception.getErrorOn(),
                exception.getErrorValue());
    }
    public static Map<String, Object> genErrorResult(String errorNo, String errorInfo, String errorOn,
            Object errorValue) {
        Map<String, Object> result = genEmptyResult();
        setErrorResult(result, errorNo, errorInfo, errorOn, errorValue);
        return result;
    }
    public static Map<String, Object> setErrorResult(Map<String, Object> result, String errorNo, String errorInfo,
            String errorOn, Object errorValue) {
        result.put(BasicFields.ERROR_NO, errorNo);
        if (null != errorInfo) {
            result.put(BasicFields.ERROR_INFO, errorInfo);
        }
        if (null != errorOn) {
            result.put(BasicFields.ERROR_ON, errorOn);
        }
        if (null != errorValue) {
            result.put(BasicFields.ERROR_VALUE, errorValue);
        }
        return result;
    }
    public static Map<String, Object> genCommonErrorResult() {
        return genErrorResult(BasicErrorCodes.COMMON_ERROR);
    }
    public static boolean isSuccess(Map<String, Object> result) {
        if (null != result) {
            String errorNo = ValueUtil.getString(result.get(BasicFields.ERROR_NO));
            return StringUtils.isEmpty(errorNo) || BasicErrorCodes.SUCCESS.equals(errorNo);
        }
        return false;
    }
    
    public static boolean isErrorOf(Map<String, Object> result, String errorNo) {
        if (null != result && null != errorNo) {
            String errorNoExists = ValueUtil.getString(result.get(BasicFields.ERROR_NO));
            return errorNo.equals(errorNoExists);
        }
        return false;
    }



}
