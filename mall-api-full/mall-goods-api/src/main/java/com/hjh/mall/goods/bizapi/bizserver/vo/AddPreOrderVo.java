package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;


/**
 * @Project: mall-goods-api-bran
 * @Description 加入购物车vo
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public class AddPreOrderVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	// 商品编号
	@NotBlank
	private String goods_id;

	// 商品名称
	@NotBlank
	private String goods_name;
	// 商品图片
	@NotBlank
	private String show_url;
	// 规格集合
	private List<AddPreOrderDetailVo> addPreOrderDetailVo;
	// 商品品牌
	@NotBlank
	private String brand_name;

	public List<AddPreOrderDetailVo> getAddPreOrderDetailVo() {
		return addPreOrderDetailVo;
	}

	public void setAddPreOrderDetailVo(List<AddPreOrderDetailVo> addPreOrderDetailVo) {
		this.addPreOrderDetailVo = addPreOrderDetailVo;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String prdtId) {
		this.goods_id = prdtId;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String prdtName) {
		this.goods_name = prdtName;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String prdtImg) {
		this.show_url = prdtImg;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
