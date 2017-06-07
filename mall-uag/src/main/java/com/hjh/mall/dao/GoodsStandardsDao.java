package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.GoodsStandards;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */
public interface GoodsStandardsDao extends DAOBase<GoodsStandards, String> {

	List<Map<String, Object>> queryByIds(GoodsStandards goodsStandards);

}