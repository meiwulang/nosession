package com.hjh.mall.vo;

/**
 * @Project: hjh-businesscard-api
 * @Description 模糊查询标签列表
 * @author 王斌
 * @date 2017年1月3日
 * @version V1.0
 */
public class QueryCategoryByLike extends WebPagedQueryVO {
	private static final long serialVersionUID = 1L;
	private String category_name;
	private String updater;
	private String remark;
	private String start_date;
	private String end_date;

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryCategoryByLike [category_name=");
		builder.append(category_name);
		builder.append(", updater=");
		builder.append(updater);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append("]");
		return builder.toString();
	}

}
