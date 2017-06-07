package com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.OperatorVo;

/**
 * @Project: mall-goods-api
 * @Description 刷新搜索引擎前的，更新数据库的vo
 * @author 杨益桦
 * @date 2017年4月1日
 * @version V1.0
 */
public class UpdateFirstVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@NotBlank()
	@Length(min = 16, max = 16)
	private String first_category_id;// 第一级类目id

	@NotBlank()
	@Length(max = 50)
	private String first_category_name;// 第一级类目名称

	public String getFirst_category_id() {
		return first_category_id;
	}

	public void setFirst_category_id(String first_category_id) {
		this.first_category_id = first_category_id;
	}

	public String getFirst_category_name() {
		return first_category_name;
	}

	public void setFirst_category_name(String first_category_name) {
		this.first_category_name = first_category_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateFirstVo [first_category_id=");
		builder.append(first_category_id);
		builder.append(", first_category_name=");
		builder.append(first_category_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
