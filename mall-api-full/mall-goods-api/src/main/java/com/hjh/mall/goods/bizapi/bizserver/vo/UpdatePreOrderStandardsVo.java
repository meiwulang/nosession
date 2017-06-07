package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.math.BigDecimal;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 修改购物车单个商品的规格vo
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public class UpdatePreOrderStandardsVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 规格编号
	@NotBlank
	private String shoppingCartDetailId;

	// 必填规格
	private String standard_must;

	// 第一个选填规格
	private String optional_first;

	// 第二个选填规格
	private String optional_second;

	// 购买数量
	private Integer prdt_num;

	// 商品单价
	private BigDecimal price;

	// 计量单位名称
	private String metadata_name;
	// 最大采购量，最大999
	private Integer max_sale_num;

	public String getShoppingCartDetailId() {
		return shoppingCartDetailId;
	}

	public void setShoppingCartDetailId(String shoppingCartDetailId) {
		this.shoppingCartDetailId = shoppingCartDetailId;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMetadata_name() {
		return metadata_name;
	}

	public void setMetadata_name(String metadata_name) {
		this.metadata_name = metadata_name;
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
