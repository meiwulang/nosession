package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-goods-api-bran
 * @Description 更新活动vo
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class UpdateActivityVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	// 主键
	@NotBlank
	private String activityId;

	// 活动标题
	@Length(min = 2, max = 10)
	private String title;

	// 活动图标
	@Length(max = 150)
	private String img;

	// 排序
	private Integer sort;

	// 活动内容地址
	@Length(max = 150)
	private String text;

	// 备注信息
	@Length(max = 10)
	private String remark;

	// app显示状态（1：显示，0：不显示）
	@EnumValue(enumClass = Status.class)
	private String appDisplay;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppDisplay() {
		return appDisplay;
	}

	public void setAppDisplay(String appDisplay) {
		this.appDisplay = appDisplay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
