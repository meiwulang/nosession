package com.hjh.mall.common.core.validation;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.hjh.mall.common.core.annotation.Past;

public class PastValidator extends DateCompareValidatorBase implements ConstraintValidator<Past, Object> {
    
    @Override
    public void initialize(Past constraintAnnotation) {
        stringPattern = constraintAnnotation.stringPattern();
        refDate = constraintAnnotation.refDate();
        includeRef = constraintAnnotation.includeRef();
        datePrecision = constraintAnnotation.datePrecision();
        
        // 如果没设置stringPattern，生成默认格式
        genDefaultStringPatternIfNecessary();
    }
    
    @Override
    protected boolean compare(long ref, long test) {
        // 参数时间（test）更早，通过
        return test < ref;
    }
    
    @Override
    protected boolean compare(Calendar ref, Calendar test) {
        return test.before(ref) || includeRef && 0 == test.compareTo(ref);
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return isValidInternal(value, context);
    }
    
}
