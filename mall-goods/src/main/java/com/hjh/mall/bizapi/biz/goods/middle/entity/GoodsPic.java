package com.hjh.mall.bizapi.biz.goods.middle.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh-mall
 * @Description 商品图片表
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class GoodsPic extends Updatable {
	private static final long serialVersionUID = 1L;
	private String pic_id;// 主键
	private String pic_url;// 图片url
	private String pic_desc;// 图片描述
	private String update_date;//
	private String update_time;//

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getPic_id() {
		return pic_id;
	}

	public void setPic_id(String pic_id) {
		this.pic_id = pic_id;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public String getPic_desc() {
		return pic_desc;
	}

	public void setPic_desc(String pic_desc) {
		this.pic_desc = pic_desc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsPic [pic_id=");
		builder.append(pic_id);
		builder.append(", pic_url=");
		builder.append(pic_url);
		builder.append(", pic_desc=");
		builder.append(pic_desc);
		builder.append(", update_version=");
		builder.append(update_version);
		builder.append(", update_user=");
		builder.append(update_user);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", status=");
		builder.append(status);
		builder.append(", init_date=");
		builder.append(init_date);
		builder.append(", init_time=");
		builder.append(init_time);
		builder.append(", page=");
		builder.append(page);
		builder.append(", sortMarkers=");
		builder.append(sortMarkers);
		builder.append(", ids=");
		builder.append(ids);
		builder.append("]");
		return builder.toString();
	}

}
