package com.hjh.mall.order.aop;


import cn.evun.sweet.framework.core.mvc.BusinessException;
import cn.evun.sweet.framework.core.mvc.ErrorTable;
import com.hjh.mall.order.dto.common.APIResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qiuxianxiang on 17/5/19.
 */

@Aspect
public class BusinessErrorHandlerAspect {

    private static final Logger logger = LoggerFactory.getLogger(BusinessErrorHandlerAspect.class);

    @Pointcut("execution(* com.hjh.mall.order.service.impl.OrderCloudServiceImpl.*(..))")
    private void serviceMethod() {
    }


    @Around("serviceMethod()")
    public Object doTrace(ProceedingJoinPoint pjp) throws Throwable {

        try {
            Object object = pjp.proceed();//执行该方法
            return object;
        } catch (Exception e) {
            logger.error(e.getMessage());

            if (e instanceof BusinessException) {
                BusinessException businessException = (BusinessException) e;

                APIResponse apiResponse = new APIResponse();
                String errorCode = businessException.getErrorCode();

                apiResponse.setCode(errorCode);
                apiResponse.setMessage(ErrorTable.convertCode2LocaleMessage(errorCode));

                return apiResponse;
            }

            // TODO 等环境稳定后,修改为系统未知异常, 不能抛出具体异常信息
            APIResponse apiResponse = APIResponse.fail(e.getMessage());
            return apiResponse;

        }

    }

}