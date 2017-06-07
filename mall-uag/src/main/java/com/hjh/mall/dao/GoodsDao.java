package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.Goods;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */
public interface GoodsDao extends DAOBase<Goods, String> {

	/**
	 * @date 2017年2月18日
	 * @Description: 根据条件模糊查询,web用
	 * @author 杨益桦
	 * @param goods
	 * @return List<Goods>
	 */
	List<Goods> queryGoods(Map<String, Object> goods);

	/**
	 * @date 2017年2月18日
	 * @Description:根据条件模糊查询,web用 查询总数
	 * @author 杨益桦
	 * @param goods
	 * @return List<Goods>
	 */
	int countQueryGoods(Map<String, Object> goods);

	/**
	 * @date 2017年2月20日
	 * @Description: 修改删除状态
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	void updateStatus(Goods goods);

	/**
	 * @date 2017年2月23日
	 * @Description: 修改上下架状态
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	void updateGoodsStatus(Goods goods);

	/**
	 * @date 2017年2月22日
	 * @Description:查询指定数据，给app端用
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	List<Map<String, Object>> queryJson(Goods goods);

	/**
	 * @date 2017年3月6日
	 * @Description: 查询app，使用page，limit
	 * @author 杨益桦
	 * @param goods
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryPageLimit(Map<String, Object> map);

}