package com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer;

import java.util.List;

import com.hjh.mall.common.core.vo.OperatorVo;

/**
 * @Project: mall-goods-api
 * @Description 刷新搜索引擎前的，更新数据库的vo
 * @author 杨益桦
 * @date 2017年4月1日
 * @version V1.0
 */
public class UpdateCarModelsVo extends OperatorVo {

	private static final long serialVersionUID = 1L;

	private List<String> ids;// 商品ids,逗号 分隔

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CopyOfGoodsTimerVo [ids=");
		builder.append(ids);
		builder.append(", access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}

}
