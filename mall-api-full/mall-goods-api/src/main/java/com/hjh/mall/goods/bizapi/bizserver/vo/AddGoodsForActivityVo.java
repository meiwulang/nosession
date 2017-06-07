package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 活动添加商品vo
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class AddGoodsForActivityVo extends HJYVO {

	private static final long serialVersionUID = 1L; 
	@NotBlank
	private String activityId;// 活动编号
	@NotNull
	private List<String> goodIds;// 商品编号数组

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public List<String> getGoodIds() {
		return goodIds;
	}

	public void setGoodIds(List<String> goodIds) {
		this.goodIds = goodIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
