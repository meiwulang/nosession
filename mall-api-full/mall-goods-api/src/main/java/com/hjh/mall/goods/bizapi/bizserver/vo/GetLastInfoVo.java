package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * 获取购物车最新信息
 *
 * @author 王斌
 *
 */
public class GetLastInfoVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	private List<GetLastInfoParamsVo> shopcartIds;// 购物车集合

	public List<GetLastInfoParamsVo> getShopcartIds() {
		return shopcartIds;
	}

	public void setShopcartIds(List<GetLastInfoParamsVo> shopcartIds) {
		this.shopcartIds = shopcartIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
