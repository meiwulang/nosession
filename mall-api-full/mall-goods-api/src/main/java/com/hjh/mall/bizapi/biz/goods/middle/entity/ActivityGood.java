package com.hjh.mall.bizapi.biz.goods.middle.entity;

import java.io.Serializable;

import com.hjh.mall.common.core.entity.Updatable;

public class ActivityGood extends Updatable implements Serializable {
	private static final long serialVersionUID = 89111604749267221L;

	// 主键
	private String actGoodId;

	// 商品编号
	private String goodId;

	// 活动编号
	private String activityId;

	public String getActGoodId() {
		return actGoodId;
	}

	public void setActGoodId(String actGoodId) {
		this.actGoodId = actGoodId;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	/**
	 * 
	 */
	public ActivityGood() {
		super();
	}

	/**
	 * @param actGoodId
	 * @param goodId
	 * @param activityId
	 */
	public ActivityGood(String actGoodId, String goodId, String activityId) {
		super();
		this.actGoodId = actGoodId;
		this.goodId = goodId;
		this.activityId = activityId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityId == null) ? 0 : activityId.hashCode());
		result = prime * result + ((goodId == null) ? 0 : goodId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityGood other = (ActivityGood) obj;
		if (activityId == null) {
			if (other.activityId != null)
				return false;
		} else if (!activityId.equals(other.activityId))
			return false;
		if (goodId == null) {
			if (other.goodId != null)
				return false;
		} else if (!goodId.equals(other.goodId))
			return false;
		return true;
	}

}