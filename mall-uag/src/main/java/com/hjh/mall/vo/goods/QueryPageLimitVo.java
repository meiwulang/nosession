package com.hjh.mall.vo.goods;

import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: hjh-mall
 * @Description 使用page，limit的vo
 * @author 杨益桦
 * @date 2017年3月6日
 * @version V1.0
 */
public class QueryPageLimitVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	private String category_id;// 类目id

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QueryJsonGoods [category_id=");
		builder.append(category_id);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
