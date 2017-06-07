package com.hjh.mall.common.core.message;

import java.util.Locale;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class DefaultMessageSource extends ReloadableResourceBundleMessageSource implements ExtendMessageSource {
    
    @Override
    public String getMessage(String code, Object[] args, String defaultMessage) {
        return getMessage(code, args, defaultMessage, null);
    }
    
    @Override
    public String getMessage(String code, Object[] args) throws NoSuchMessageException {
        return getMessage(code, args, (Locale) null);
    }
    
    @Override
    public String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }
    
    @Override
    public String getMessage(String code) throws NoSuchMessageException {
        return getMessage(code, (Object[]) null);
    }
    
}
