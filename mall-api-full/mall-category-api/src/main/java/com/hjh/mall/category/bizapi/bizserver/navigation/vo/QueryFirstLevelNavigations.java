package com.hjh.mall.category.bizapi.bizserver.navigation.vo;

import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api-brunch
 * @Description 修改导航vo
 * @author 王斌
 * @date 2017年3月14日
 * @since 1.0
 */
@ApiModel
public class QueryFirstLevelNavigations extends PageQueryVO {
	protected static final long serialVersionUID = 1L;

	// 名称
	@ApiModelProperty(value = "名称", required = true)
	protected String categoryName;
	// 0禁用，1启用
	@ApiModelProperty(value = "0禁用，1启用", required = true)
	protected String status;
	// 创建人
	@ApiModelProperty(value = "创建人", required = true)
	protected String creater;
	// app是否显示
	@ApiModelProperty(value = "app是否显示", required = true)
	protected Boolean appDisplay;
	// 开始时间
	@ApiModelProperty(value = "开始时间 格式：yyyyMMDD", required = true)
	protected String startTime;
	// 结束时间
	@ApiModelProperty(value = "结束时间 格式：yyyyMMDD", required = true)
	protected String endTime;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Boolean getAppDisplay() {
		return appDisplay;
	}

	public void setAppDisplay(Boolean appDisplay) {
		this.appDisplay = appDisplay;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
