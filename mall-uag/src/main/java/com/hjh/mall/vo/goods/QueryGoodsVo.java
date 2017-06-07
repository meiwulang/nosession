package com.hjh.mall.vo.goods;

import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: hjh-mall
 * @Description 查询商品实体
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class QueryGoodsVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	private String goods_id;// 主键
	private String category_id;// 类目id
	private String goods_name;// 商品名称
	private String goods_status;// 商品名称
	private String start_date;//
	private String end_date;//

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getGoods_status() {
		return goods_status;
	}

	public void setGoods_status(String goods_status) {
		this.goods_status = goods_status;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryGoodsVo [goods_id=");
		builder.append(goods_id);
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", goods_status=");
		builder.append(goods_status);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
