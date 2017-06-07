package com.hjh.mall.vo.goods;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-mall
 * @Description 添加商品实体
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class AddGoodsVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	private String goods_name;// 商品名称
	private String category_id;// 类目id
	private List<Map<String, Object>> standardList;// 规格
	private String ad_url;// 广告图，占位图一
	private String brand_name;// 品牌名称
	private List<Map<String, Object>> bannerList;// banner
	private List<Map<String, Object>> detailList;// 详情
	private List<Map<String, Object>> infoList;// 简介
	private String tel;// 客服电话
	private String remark;// 备注
	private int sort;// 排序字段,默认100

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public List<Map<String, Object>> getStandardList() {
		return standardList;
	}

	public void setStandardList(List<Map<String, Object>> standardList) {
		this.standardList = standardList;
	}

	public String getAd_url() {
		return ad_url;
	}

	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddGoodsVo [goods_name=");
		builder.append(goods_name);
		builder.append(", category_id=");
		builder.append(category_id);
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
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
