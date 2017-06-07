package com.hjh.mall.goods.bizapi.bizserver.vo;

import io.swagger.annotations.ApiModelProperty;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: mall-goods-api
 * @Description
 * @author 杨益桦
 * @date 2017年3月17日
 * @version V1.0
 */
public class QueryModelByGoodsVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id", required = true)
	@NotBlank
	private String goods_id;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryModelByGoodsVo [goods_id=");
		builder.append(goods_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

}
