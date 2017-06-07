package com.hjh.mall.common.core.aspect;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.util.ValidateUtil;
import com.hjh.mall.common.core.util.ValueUtil;
import com.hjh.mall.common.core.vo.LGSBCVO;


public class ValidateAspect {
    
    private static final String CLASS_NAME = ValidateAspect.class.getName();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);
    
    private boolean exportInner;
    
    public ValidateAspect() {

    }
    
    public Object validateParam(ProceedingJoinPoint pjp) throws Throwable {
        for (Object arg : pjp.getArgs()) {
            if (arg instanceof LGSBCVO) {
                List<Map<String, Object>> violationsStructure = ValidateUtil.validOrReturnViolationsStructure(arg);
                if (null != violationsStructure) {
                    Map<String, Object> result = VOUtil.genEmptyResult();
                    if (exportInner) {
                        Map<String, Object> violation = violationsStructure.get(0);
                        String errorNo = ValueUtil.getString(violation.get(BasicFields.ERROR_NO),
                                BasicErrorCodes.PARAM_ERROR);
                        String errorInfo = ValueUtil.getString(violation.get(BasicFields.ERROR_INFO), null);
                        String errorOn = ValueUtil.getString(violation.get(BasicFields.ERROR_ON), null);
                        String errorValue = ValueUtil.getString(violation.get(BasicFields.ERROR_VALUE), null);
                        return VOUtil.setErrorResult(result, errorNo, errorInfo, errorOn, errorValue);
                    }
                    result.put(BasicFields.ERRORS, violationsStructure);
                    return VOUtil.setErrorResult(result, BasicErrorCodes.PARAM_ERROR);
                }
            } else {
                // ignore;
            }
        }
        
        return pjp.proceed();
    }
    
    public boolean isExportInner() {
        return exportInner;
    }
    
    public void setExportInner(boolean exportInner) {
        this.exportInner = exportInner;
    }
    
}
