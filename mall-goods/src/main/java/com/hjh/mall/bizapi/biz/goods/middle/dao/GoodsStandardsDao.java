package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsStandards;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */
public interface GoodsStandardsDao extends DAOBase<GoodsStandards, String> {

	List<Map<String, Object>> queryByIds(GoodsStandards goodsStandards);
	
	/** 
	 * @date 2017年6月1日
	 * @Description:  批量删除
	 * @author 杨益桦
	 * @param goodsStandards
	 * void
	 */
	void bathDelete(GoodsStandards goodsStandards);

}