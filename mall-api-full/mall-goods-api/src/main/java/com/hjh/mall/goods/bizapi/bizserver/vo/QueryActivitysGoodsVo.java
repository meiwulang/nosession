package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 查询活动关联商品列表
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class QueryActivitysGoodsVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String activityId;// 活动编号
	private String brand_name;// 商品品牌
	private String update_end_date;// 结束时间
	private String goods_id;// 商品编号
	private String goods_name;// 商品名称
	private String update_start_date;// 开始时间
	private String third_category_id;// 三级类目编号

	public String getUpdate_end_date() {
		return update_end_date;
	}

	public void setUpdate_end_date(String update_end_date) {
		this.update_end_date = update_end_date;
	}

	public String getUpdate_start_date() {
		return update_start_date;
	}

	public void setUpdate_start_date(String update_start_date) {
		this.update_start_date = update_start_date;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}


	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

}
