package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.ActivityGood;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface ActivityGoodDao extends DAOBase<ActivityGood, String> {
	List<Map<String, Object>> queryByLike(Map<String, Object> example);

	int countByLike(Map<String, Object> example);

	/**
	 * @date 2017年5月8日
	 * @Description: 批量保存
	 * @author：王斌
	 * @param activityGoods
	 */
	int batchSave(List<ActivityGood> activityGoods);

	/**
	 * @date 2017年5月8日
	 * @Description: 根据主键批量删除
	 * @author：王斌
	 * @param activityGoodIds
	 */
	int batchDelByActivityIdAndGoodID(ActivityGood activityGood);

	/**
	 * @date 2017年5月8日
	 * @Description: 根据商品编号批量删除
	 * @author：王斌
	 * @param activityGoodIds
	 */
	int batchDelBygoodId(List<String> goodIds);

	/**
	 * @date 2017年5月8日
	 * @Description: 根据商品编号批量删除
	 * @author：王斌
	 * @param activityGoodIds
	 */
	List<Map<String, Object>> getPrdtList(String activityId);

	/**
	 * @date 2017年5月8日
	 * @Description: 活动获取商品编号
	 * @author：王斌
	 * @param activityGoodIds
	 */
	List<String> getActivitysGoodsId(String activityId);

	/**
	 * @Description: 根据条件模糊查询,web用
	 * @author 王斌
	 * @param goods
	 * @return List<Goods>
	 */
	List<Map<String, Object>> queryGoods(Map<String, Object> goods);

	/**
	 * @Description:根据条件模糊查询,web用 查询总数
	 * @author 王斌
	 * @param goods
	 * @return List<Goods>
	 */
	int countQueryGoods(Map<String, Object> goods);

}