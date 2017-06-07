package com.hjh.mall.common.core.entity;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Table;

import com.hjh.mall.common.core.entity.LEGBCSEntity;
import com.hjh.mall.common.core.util.StringUtil;


public class LEGBCSEntityBase implements LEGBCSEntity,Serializable {


	
	 /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    public String tableName() {
        Class<?> clazz = this.getClass();
        Table tableAnn = clazz.getAnnotation(Table.class);
        if (null != tableAnn) {
            return tableAnn.name();
        } else {
            return StringUtil.camelToUnderlineCase(clazz.getSimpleName());
        }
    }
    
    public void initForClearNull() {
        // 获取当前类
        Class<?> clazz = getClass();
        try {
            // 获取bean描述
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            // 获取所有属性
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            // 遍历属性
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                // 如果属性值为null则处理
                if (null == propertyDescriptor.getReadMethod().invoke(this)) {
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    // 如果属性类别为数字，则初始化为0
                    if (Integer.class.isAssignableFrom(propertyType)) {
                        propertyDescriptor.getWriteMethod().invoke(this, 0);
                    } else if (Long.class.isAssignableFrom(propertyType)) {
                        propertyDescriptor.getWriteMethod().invoke(this, 0L);
                    } else if (Float.class.isAssignableFrom(propertyType)) {
                        propertyDescriptor.getWriteMethod().invoke(this, 0f);
                    } else if (Double.class.isAssignableFrom(propertyType)) {
                        propertyDescriptor.getWriteMethod().invoke(this, 0d);
                    } else if (BigDecimal.class.isAssignableFrom(propertyType)) {
                        propertyDescriptor.getWriteMethod().invoke(this, new BigDecimal("0.0"));
                    } else if (String.class.isAssignableFrom(propertyType)) {
                        // 如果属性类别为字符串，则初始化为空格
                        propertyDescriptor.getWriteMethod().invoke(this, " ");
                    }
                    // 其他类型不处理
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("initForClearNull failed", e);
        }
    }
	

}
