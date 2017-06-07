package com.hjh.mall.goods.bizapi.bizserver.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.hjh.mall.common.core.annotation.Length;
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
	@ApiModelProperty(name = "goods_id", value = "主键", required = false)
	private String goods_id;// 主键
	
	@ApiModelProperty(name = "third_category_name", value = "类目名称", required = false)
	private String third_category_name;// 类目名称
	
	@ApiModelProperty(name = "goods_name", value = "商品名称", required = false)
	private String goods_name;// 商品名称
	
	@ApiModelProperty(name = "brand_id", value = "商品品牌id", required = false)
	private String brand_id;// 商品品牌id
	
	@ApiModelProperty(name = "goods_status", value = "商品状态", required = false)
	private String goods_status;//
	
	@ApiModelProperty(name = "start_date", value = "创建日期，开始", required = false)
	private String start_date;// 创建日期，
	
	@ApiModelProperty(name = "end_date", value = "创建日期，结束", required = false)
	private String end_date;// 创建日期，
	
	@ApiModelProperty(name = "update_start_date", value = "操作日期,开始", required = false)
	private String update_start_date;// 操作日期
	
	@ApiModelProperty(name = "update_end_date", value = "操作日期，结束", required = false)
	private String update_end_date;// 操作日期，
	
	@ApiModelProperty(name = "create_user_name", value = "创建人名称", required = false)
	private String create_user_name;
	
	@ApiModelProperty(name = "brand_name", value = "商品名称", required = false)
	private String brand_name;
	
	private String status = "1";
	@ApiModelProperty(name = "ids", value = "主键id列表", required = false)
	private List<String> ids;

	@Length(max = 16)
	private String third_category_id;// 三级类目id

	@Length(max = 16)
	private String create_user_id;// 创建人id

	public String getUpdate_start_date() {
		return update_start_date;
	}

	public void setUpdate_start_date(String update_start_date) {
		this.update_start_date = update_start_date;
	}

	public String getUpdate_end_date() {
		return update_end_date;
	}

	public void setUpdate_end_date(String update_end_date) {
		this.update_end_date = update_end_date;
	}

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

	public String getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(String create_user_id) {
		this.create_user_id = create_user_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", goods_name=");
		builder.append(goods_name);
		builder.append(", brand_id=");
		builder.append(brand_id);
		builder.append(", goods_status=");
		builder.append(goods_status);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", create_user_name=");
		builder.append(create_user_name);
		builder.append(", brand_name=");
		builder.append(brand_name);
		builder.append(", status=");
		builder.append(status);
		builder.append(", ids=");
		builder.append(ids);
		builder.append(", third_category_id=");
		builder.append(third_category_id);
		builder.append(", create_user_id=");
		builder.append(create_user_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
