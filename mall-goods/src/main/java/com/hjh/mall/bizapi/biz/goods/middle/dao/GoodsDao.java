package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Goods;
import com.hjh.mall.common.core.dao.base.DAOBase;

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
	List<Map<String, Object>> queryGoods(Map<String, Object> goods);

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

	/**
	 * @date 2017年3月15日
	 * @Description: 批量上架，下架
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	void chmodBatch(Goods goods);

	/**
	 * @date 2017年3月17日
	 * @Description: 获得solr的实体
	 * @author 杨益桦
	 * @param key
	 * @return Map<String,Object>
	 */
	Map<String, Object> getSolrEntity(String key);

	/**
	 * @date 2017年3月28日
	 * @Description: 查询全部启用的商品
	 * @author 杨益桦
	 * @return List<String>
	 */
	List<Map<String, Object>> queryAllId(Map<String, Object> map);

	/**
	 * @date 2017年4月1日
	 * @Description: 刷新搜索引擎前的，更新数据库
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	void updateGoodsTimer(Goods goods);

	/**
	 * @date 2017年4月10日
	 * @Description: 查询id的列表
	 * @author 杨益桦
	 * @param goods
	 * @return List<String>
	 */
	List<String> queryIdList(Goods goods);

	/**
	 * @date 2017年4月10日
	 * @Description: 查询需要更新到solr的实体，
	 * @author 杨益桦
	 * @param goods
	 *            ids
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> querySolrEntityByids(Goods goods);

}