package com.hjh.mall.goods.bizapi.bizserver.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.OperatorVo;
import com.hjh.mall.field.type.PayType;

/**
 * @Project: hjh-mall
 * @Description 修改商品实体
 * @author 杨益桦
 * @date 2017年2月20日
 * @version V1.0
 */
public class ModifyGoodsVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id", required = true)
	@NotBlank
	private String goods_id;

	@ApiModelProperty(value = "商品名称", required = true)
	@NotBlank
	@Length(min = 13, max = 38)
	private String goods_name;// 商品名称

	@ApiModelProperty(value = "规格列表", required = false)
	private List<Map<String, Object>> standardList;// 规格

	@ApiModelProperty(value = " 广告图，占位图一", required = false)
	private String ad_url;// 广告图，占位图一

	@ApiModelProperty(value = " 品牌名称", required = true)
	@NotBlank
	private String brand_name;// 品牌名称

	@ApiModelProperty(value = "banner列表", required = false)
	private List<Map<String, Object>> bannerList;// banner

	@ApiModelProperty(value = "详情列表", required = false)
	private List<Map<String, Object>> detailList;// 详情

	@ApiModelProperty(value = "简介列表", required = false)
	private List<Map<String, Object>> infoList;// 简介

	@ApiModelProperty(value = "客服电话", required = true)
	@NotBlank
	private String tel;// 客服电话

	private String remark;// 备注

	@ApiModelProperty(value = "排序字段", notes = "0", required = true)
	@NotNull
	private int sort;// 排序字段,默认100

	@ApiModelProperty(value = "品牌id", required = true)
	@NotBlank
	private String brand_id;// 品牌id

	@ApiModelProperty(value = "操作人姓名", required = false)
	private String update_user_name;// 操作人姓名

	@ApiModelProperty(value = "计量单位id", required = true)
	@NotBlank
	private String unit_id;// 计量单位id

	@ApiModelProperty(value = "计量单位名称", required = true)
	@NotBlank
	private String unit_name;// 计量单位名称

	@ApiModelProperty(value = "value", required = true)
	@NotBlank
	private String show_url;// 首页展示图

	@ApiModelProperty(value = " 第一级类目id", required = true)
	@NotBlank
	private String first_category_id;// 第一级类目id

	@ApiModelProperty(value = "第一级类目名称", required = true)
	@NotBlank
	private String first_category_name;// 第一级类目名称

	@ApiModelProperty(value = "第二级类目id", required = true)
	@NotBlank
	private String second_category_id;// 第二级类目id

	@ApiModelProperty(value = "第二级类目名称", required = true)
	@NotBlank
	private String second_category_name;// 第二级类目名称

	@ApiModelProperty(value = "三级类目id", required = true)
	@NotBlank
	private String third_category_id;// 三级类目id

	@ApiModelProperty(value = "第三级类目名称", required = true)
	@NotBlank
	private String third_category_name;// 第三级类目名称

	@ApiModelProperty(value = "机型列表", required = false)
	private List<String> carModelList;

	@ApiModelProperty(value = "适配全部的机型", required = false)
	private Integer adapt_all_models;// 适配全部的机型

	@ApiModelProperty(value = "支付类型：全款(0)、预付款(1)", required = true)
	@NotNull
	@EnumValue(enumClass = PayType.class)
	private Integer pay_type;// 支付类型：全款(0)、预付款(1)

	@ApiModelProperty(value = "定金比例，与pay_type有关联", required = false)
	@Min(value = 1)
	@Max(value = 100)
	private Integer font_money_rate;// 定金比例，与pay_type有关联
	

	public Integer getPay_type() {
		return pay_type;
	}

	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}

	public Integer getFont_money_rate() {
		return font_money_rate;
	}

	public void setFont_money_rate(Integer font_money_rate) {
		this.font_money_rate = font_money_rate;
	}

	public Integer getAdapt_all_models() {
		return adapt_all_models;
	}

	public void setAdapt_all_models(Integer adapt_all_models) {
		this.adapt_all_models = adapt_all_models;
	}

	public List<String> getCarModelList() {
		return carModelList;
	}

	public void setCarModelList(List<String> carModelList) {
		this.carModelList = carModelList;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	public String getFirst_category_id() {
		return first_category_id;
	}

	public void setFirst_category_id(String first_category_id) {
		this.first_category_id = first_category_id;
	}

	public String getFirst_category_name() {
		return first_category_name;
	}

	public void setFirst_category_name(String first_category_name) {
		this.first_category_name = first_category_name;
	}

	public String getSecond_category_id() {
		return second_category_id;
	}

	public void setSecond_category_id(String second_category_id) {
		this.second_category_id = second_category_id;
	}

	public String getSecond_category_name() {
		return second_category_name;
	}

	public void setSecond_category_name(String second_category_name) {
		this.second_category_name = second_category_name;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public List<Map<String, Object>> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Map<String, Object>> bannerList) {
		this.bannerList = bannerList;
	}

	public List<Map<String, Object>> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<Map<String, Object>> detailList) {
		this.detailList = detailList;
	}

	public List<Map<String, Object>> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Map<String, Object>> infoList) {
		this.infoList = infoList;
	}

	public List<Map<String, Object>> getStandardList() {
		return standardList;
	}

	public void setStandardList(List<Map<String, Object>> standardList) {
		this.standardList = standardList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModifyGoodsVo [goods_id=");
		builder.append(goods_id);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", standardList=");
		builder.append(standardList);
		builder.append(", ad_url=");
		builder.append(ad_url);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", bannerList=");
		builder.append(bannerList);
		builder.append(", detailList=");
		builder.append(detailList);
		builder.append(", infoList=");
		builder.append(infoList);
		builder.append(", tel=");
		builder.append(tel);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", update_user_name=");
		builder.append(update_user_name);
		builder.append(", unit_id=");
		builder.append(unit_id);
		builder.append(", unit_name=");
		builder.append(unit_name);
		builder.append(", show_url=");
		builder.append(show_url);
		builder.append(", first_category_id=");
		builder.append(first_category_id);
		builder.append(", first_category_name=");
		builder.append(first_category_name);
		builder.append(", second_category_id=");
		builder.append(second_category_id);
		builder.append(", second_category_name=");
		builder.append(second_category_name);
		builder.append(", third_category_id=");
		builder.append(third_category_id);
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", carModelList=");
		builder.append(carModelList);
		builder.append(", adapt_all_models=");
		builder.append(adapt_all_models);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
