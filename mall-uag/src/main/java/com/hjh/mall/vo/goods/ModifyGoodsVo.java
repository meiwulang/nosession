package com.hjh.mall.vo.goods;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-mall
 * @Description 修改商品实体
 * @author 杨益桦
 * @date 2017年2月20日
 * @version V1.0
 */
public class ModifyGoodsVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	private String goods_id;
	private String category_id;// 类目id
	private String factory_detail;// 厂家简介
	private String goods_name;// 商品名称
	private String ad_url;// 商品名称
	private String factory_name;// 商品名称
	private String remark;// 备注
	private String tel;
	private String brand_name;
	private List<Map<String, Object>> bannerList;// banner
	private List<Map<String, Object>> detailList;// 详情
	private List<Map<String, Object>> infoList;// 简介
	private List<Map<String, Object>> standardList;// 规格
	private String init_date;//
	private String init_time;//
	private int sort;// 排序字段,默认100

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

	public String getInit_date() {
		return init_date;
	}

	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}

	public String getInit_time() {
		return init_time;
	}

	public void setInit_time(String init_time) {
		this.init_time = init_time;
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

	public String getFactory_name() {
		return factory_name;
	}

	public void setFactory_name(String factory_name) {
		this.factory_name = factory_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getFactory_detail() {
		return factory_detail;
	}

	public void setFactory_detail(String factory_detail) {
		this.factory_detail = factory_detail;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", factory_detail=");
		builder.append(factory_detail);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", ad_url=");
		builder.append(ad_url);
		builder.append(", factory_name=");
		builder.append(factory_name);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", tel=");
		builder.append(tel);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", bannerList=");
		builder.append(bannerList);
		builder.append(", detailList=");
		builder.append(detailList);
		builder.append(", infoList=");
		builder.append(infoList);
		builder.append(", standardList=");
		builder.append(standardList);
		builder.append(", init_date=");
		builder.append(init_date);
		builder.append(", init_time=");
		builder.append(init_time);
		builder.append(", sort=");
		builder.append(sort);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
