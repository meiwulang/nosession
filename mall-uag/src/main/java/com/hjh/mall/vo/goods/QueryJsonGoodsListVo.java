package com.hjh.mall.vo.goods;

import com.hjh.mall.common.core.vo.AppPagedQueryVO;

/**
 * @Project: hjh-mall
 * @Description 查询商品列表接口，app用
 * @author 杨益桦
 * @date 2017年2月22日
 * @version V1.0
 */
public class QueryJsonGoodsListVo extends AppPagedQueryVO {

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
