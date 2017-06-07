package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 添加活动vo
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class AddActivityVo extends HJYVO {

	private static final long serialVersionUID = 1L;

	// 活动标题
	@NotBlank
	@Length(min = 2, max = 10)
	private String title;

	// 活动图标
	@NotBlank
	@Length(max = 150)
	private String img;
	@NotNull
	// 排序
	private Integer sort;
	@NotBlank
	// 活动内容地址
	@Length(max = 150)
	private String text;
	@Length(max = 10)
	// 备注信息
	private String remark;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
