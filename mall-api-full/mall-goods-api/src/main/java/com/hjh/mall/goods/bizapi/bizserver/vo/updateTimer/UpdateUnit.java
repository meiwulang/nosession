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
public class UpdateUnit extends OperatorVo {

	private static final long serialVersionUID = 1L;

	@NotBlank()
	@Length(min = 16, max = 16)
	private String unit_id;// 计量单位id

	@NotBlank()
	@Length(max = 50)
	private String unit_name;// 计量单位名称

	public String getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getUnit_name() {
		return unit_name;
	}

	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateUnit [unit_id=");
		builder.append(unit_id);
		builder.append(", unit_name=");
		builder.append(unit_name);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
