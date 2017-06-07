package com.hjh.mall.vo.goods;

import org.hibernate.validator.constraints.NotBlank;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-mall
 * @Description 添加商品实体
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class GetJsonGoodsVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String goods_id;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetJsonGoodsVo [goods_id=");
		builder.append(goods_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
