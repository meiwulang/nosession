package com.hjh.mall.goods.bizapi.bizserver.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;

/**
 * @Project: mall-goods-api-bran
 * @Description 查询活动关联商品列表
 * @author 王斌
 * @date 2017年5月8日
 * @version V1.0
 */
public class QueryActivityDetailVo extends PageQueryVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String activity_id;// 活动编号

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

}
