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
public class UpdateThird extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@NotBlank()
	@Length(min = 16, max = 16)
	private String third_category_id;// 三级类目id,区分原有categor id

	@NotBlank()
	@Length(max = 50)
	private String third_category_name;// 第三级类目名称

	public String getThird_category_id() {
		return third_category_id;
	}

	public void setThird_category_id(String third_category_id) {
		this.third_category_id = third_category_id;
	}

	public String getThird_category_name() {
		return third_category_name;
	}

	public void setThird_category_name(String third_category_name) {
		this.third_category_name = third_category_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateThird [third_category_id=");
		builder.append(third_category_id);
		builder.append(", third_category_name=");
		builder.append(third_category_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
