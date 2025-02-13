package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 加入购物车vo
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public class AddPreOrderDetailVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	// 商品名称
	private String goods_name;
	// 规格编号
	@NotBlank
	private String standard_id;
	// 计价单位
	@NotBlank
	private String metadata_name;

	// 必填规格
	@NotBlank
	private String standard_must;

	// 第一个选填规格
	private String optional_first;

	// 第二个选填规格
	private String optional_second;

	// 购买数量
	private Integer prdt_num;

	// 商品单价
	private Double price;
	// 最大采购量，最大999
	private Integer max_sale_num;

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getStandard_id() {
		return standard_id;
	}

	public void setStandard_id(String standard_id) {
		this.standard_id = standard_id;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
	}

	public String getStandard_must() {
		return standard_must;
	}

	public void setStandard_must(String standard_must) {
		this.standard_must = standard_must;
	}

	public String getOptional_first() {
		return optional_first;
	}

	public void setOptional_first(String optional_first) {
		this.optional_first = optional_first;
	}

	public String getOptional_second() {
		return optional_second;
	}

	public void setOptional_second(String optional_second) {
		this.optional_second = optional_second;
	}

	public Integer getPrdt_num() {
		return prdt_num;
	}

	public void setPrdt_num(Integer prdt_num) {
		this.prdt_num = prdt_num;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getMax_sale_num() {
		return max_sale_num;
	}

	public void setMax_sale_num(Integer max_sale_num) {
		this.max_sale_num = max_sale_num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}