package com.hjh.mall.common.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hjh.mall.common.core.annotation.Identity;
import com.hjh.mall.common.core.filed.IdKind;
import com.hjh.mall.common.core.model.NCID;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.NCIDUtil;
import com.hjh.mall.common.core.util.ValueUtil;




public class IdentityValidator implements ConstraintValidator<Identity, Object> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityValidator.class);
    
    private String idKindField;
    
    private String idNoField;
    
    private String messageTemplate;
    
    @Override
    public void initialize(Identity constraintAnnotation) {
        idKindField = constraintAnnotation.idKindField();
        idNoField = constraintAnnotation.idNoField();
        messageTemplate = constraintAnnotation.message();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        boolean valid = true;
        if (value instanceof String) {
            valid = validNCID((String) value);
        } else {
            try {
                String idKind = ValueUtil.getString(BeanUtil.getProperty(value, idKindField));
                String idNo = ValueUtil.getString(BeanUtil.getProperty(value, idNoField));
                if (String.valueOf(IdKind.NCID.getVal()).equals(idKind)) {
                    valid = validNCID(idNo);
                }
            } catch (Exception e) {
                LOGGER.error("validate identity failed", e);
            }
        }
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate).addNode(idNoField)
                    .addConstraintViolation();
        }
        return valid;
    }
    
    private boolean validNCID(String idNo) {
        NCID ncid = NCIDUtil.parseNCID(idNo, true);
        return null != ncid && ncid.isValid();
    }
    
}
