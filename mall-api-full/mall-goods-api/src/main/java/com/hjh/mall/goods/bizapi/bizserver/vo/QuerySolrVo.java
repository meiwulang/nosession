package com.hjh.mall.goods.bizapi.bizserver.vo;

import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.enums.SolrFactesFields;
import com.hjh.mall.field.enums.SolrSortFields;

/**
 * @Project: mall-goods-api
 * @Description 根据二级级类目查询搜索引擎的vo
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public class QuerySolrVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "三级类目名称", required = false)
	private String third_category_name; // 三级类目名称
	
	@ApiModelProperty(value = "机型名称",   required = false)
	private String car_models_name; // 机型名称
	
	@ApiModelProperty(value = "品牌名称",   required = false)
	private String brand_name;// 品牌名称
	
	@ApiModelProperty(value = "所有类目",   required = false)
	private String categoryAll;// 所有类目

	@ApiModelProperty(value = "分组字段",   required = false)
	@NotBlank
	@EnumValue(enumClass = SolrFactesFields.class)
	private String solrFactesFields;

	@ApiModelProperty(value = "排序字段",   required = false)
	@NotBlank
	@EnumValue(enumClass = SolrSortFields.class)
	private String solrSortField;

	public String getSolrFactesFields() {
		return solrFactesFields;
	}

	public void setSolrFactesFields(String solrFactesFields) {
		this.solrFactesFields = solrFactesFields;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	public String getCar_models_name() {
		return car_models_name;
	}

	public void setCar_models_name(String car_models_name) {
		this.car_models_name = car_models_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getCategoryAll() {
		return categoryAll;
	}

	public void setCategoryAll(String categoryAll) {
		this.categoryAll = categoryAll;
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
		builder.append("QuerySolrVo [third_category_name=");
		builder.append(third_category_name);
		builder.append(", car_models_name=");
		builder.append(car_models_name);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", categoryAll=");
		builder.append(categoryAll);
		builder.append(", solrFactesFields=");
		builder.append(solrFactesFields);
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
