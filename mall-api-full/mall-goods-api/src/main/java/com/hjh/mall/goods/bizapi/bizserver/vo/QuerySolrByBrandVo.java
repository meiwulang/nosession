package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.enums.SolrSortFields;

/**
 * @Project: mall-goods-api
 * @Description 根据机型查找搜索引擎
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public class QuerySolrByBrandVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;

	private String third_category_name; // 三级类目名称
	@NotBlank
	private String brand_name;// 品牌名称

	@NotBlank
	@EnumValue(enumClass = SolrSortFields.class)
	private String solrSortField;

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getSolrSortField() {
		return solrSortField;
	}

	public void setSolrSortField(String solrSortField) {
		this.solrSortField = solrSortField;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuerySolrByBrandVo [third_category_name=");
		builder.append(third_category_name);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", solrSortField=");
		builder.append(solrSortField);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
