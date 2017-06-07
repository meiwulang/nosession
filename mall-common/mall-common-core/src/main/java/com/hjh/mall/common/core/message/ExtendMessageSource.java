package com.hjh.mall.common.core.message;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public interface ExtendMessageSource extends MessageSource {
    
    String getMessage(String code, Object[] args, String defaultMessage);
    
    String getMessage(String code, Object[] args) throws NoSuchMessageException;
    
    String getMessage(String code, String defaultMessage);
    
    String getMessage(String code) throws NoSuchMessageException;
    
}
