/**
 * 
 */
package com.hjh.mall.vo;

import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjy-common
 * @Description web分页操作时继承
 * @author 陈士俊
 * @date 2016年7月18日
 * @version V1.0
 */
public class WebPagedQueryVO extends HJYVO {

	private static final long serialVersionUID = 1L;

	private Integer limit;// 每页条数

	private Integer page;// 当前页数

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageQueryVO [limit=");
		builder.append(limit);
		builder.append(", page=");
		builder.append(page);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
