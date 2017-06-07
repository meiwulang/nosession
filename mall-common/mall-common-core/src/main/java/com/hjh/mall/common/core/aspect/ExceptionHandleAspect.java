package com.hjh.mall.common.core.aspect;



import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.util.VOUtil;


/**
 * @author chengjia
 *
 */
public class ExceptionHandleAspect {
    
    private static final String CLASS_NAME = ExceptionHandleAspect.class.getName();
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);
    
    public ExceptionHandleAspect() {
//        PrintUtil.printComponentLoaded(LOGGER, CLASS_NAME);
    }
    
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (HJHBCSErrInfoException e) {
            LOGGER.error("execute failed", e);
            result = VOUtil.genErrorResult(e);
        } catch (Exception e) {
            LOGGER.error("execute failed", e);
            result = VOUtil.genCommonErrorResult();
        }
        return result;
    }

    
}
