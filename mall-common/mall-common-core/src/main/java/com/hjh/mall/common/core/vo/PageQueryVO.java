package com.hjh.mall.common.core.vo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import com.hjh.mall.common.core.util.StringUtil;

/**
 * @author chengjia
 *
 */
public class PageQueryVO extends LGSBCVOBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer limit = 25;// 每页条数

	private Integer page = 0;// 当前页数

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageQueryVO [limit=");
		builder.append(limit);
		builder.append(", page=");
		builder.append(page);
		builder.append("]");
		return builder.toString();
	}

	public void blankStringToNull() {
		// 获取当前类
		Class<?> clazz = getClass();
		try {
			// 获取bean描述
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			// 获取所有属性
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			// 遍历属性
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				Class<?> propertyType = propertyDescriptor.getPropertyType();
				if (String.class.isAssignableFrom(propertyType)
						&& StringUtil.isBlank((String) propertyDescriptor.getReadMethod().invoke(this))) {
					propertyDescriptor.getWriteMethod().invoke(this, new Object[] { null });
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("blankStringToNull failed", e);
		}
	}
}
