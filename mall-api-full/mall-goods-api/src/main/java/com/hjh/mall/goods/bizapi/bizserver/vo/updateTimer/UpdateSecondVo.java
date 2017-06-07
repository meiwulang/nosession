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
public class UpdateSecondVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@NotBlank()
	@Length(min = 16, max = 16)
	private String second_category_id;// 第二级类目id

	@NotBlank()
	@Length(max = 50)
	private String second_category_name;// 第二级类目名称

	public String getSecond_category_id() {
		return second_category_id;
	}

	public void setSecond_category_id(String second_category_id) {
		this.second_category_id = second_category_id;
	}

	public String getSecond_category_name() {
		return second_category_name;
	}

	public void setSecond_category_name(String second_category_name) {
		this.second_category_name = second_category_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateSecondVo [second_category_id=");
		builder.append(second_category_id);
		builder.append(", second_category_name=");
		builder.append(second_category_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
