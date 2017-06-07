package com.hjh.mall.goods.bizapi.bizserver.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.hjh.mall.common.core.vo.OperatorVo;

/**
 * @Project: hjh-mall
 * @Description 批量上架，下架vo
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class ChmodBathVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id列表", required = false)
	private List<String> ids;
	private String goods_status;

	public String getGoods_status() {
		return goods_status;
	}

	public void setGoods_status(String goods_status) {
		this.goods_status = goods_status;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChmodBathVo [ids=");
		builder.append(ids);
		builder.append(", goods_status=");
		builder.append(goods_status);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
