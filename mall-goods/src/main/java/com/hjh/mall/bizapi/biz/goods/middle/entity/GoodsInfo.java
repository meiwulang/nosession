package com.hjh.mall.bizapi.biz.goods.middle.entity;

import com.hjh.mall.common.core.entity.Updatable;

/**
 * @Project: hjh-mall
 * @Description 商品介绍
 * @author 杨益桦
 * @date 2017年2月18日
 * @version V1.0
 */
public class GoodsInfo extends Updatable {
	private static final long serialVersionUID = 1L;
	private String info_id;// 主键
	private String info_title;// 简介标题
	private String info_content;//
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

	public String getInfo_id() {
		return info_id;
	}

	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}

	public String getInfo_title() {
		return info_title;
	}

	public void setInfo_title(String info_title) {
		this.info_title = info_title;
	}

	public String getInfo_content() {
		return info_content;
	}

	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GoodsInfo [info_id=");
		builder.append(info_id);
		builder.append(", info_title=");
		builder.append(info_title);
		builder.append(", info_content=");
		builder.append(info_content);
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
