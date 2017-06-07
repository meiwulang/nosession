package com.hjh.mall.goods.bizapi.bizserver.vo;

import java.util.List;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 删除活动管理商品
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class DelActivitysGoodsVo extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotNull
	private List<String> actGoodIds;// 活动关联商品主键集合
	@NotBlank
	private String activityId;// 活动编号

	public List<String> getActGoodIds() {
		return actGoodIds;
	}

	public void setActGoodIds(List<String> actGoodIds) {
		this.actGoodIds = actGoodIds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

}
