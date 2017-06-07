package com.hjh.mall.bizapi.biz.goods.middle.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Goods;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ChmodBathVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetJsonGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ModifyGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryJsonGoodsListVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryPageLimitVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QuerySolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UndercarriageGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */
public interface GoodsService extends ServiceBase<Goods, String> {

	/**
	 * @date 2017年2月18日
	 * @Description: 查询商品，web用
	 * @author 杨益桦
	 * @param queryGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo);

	/**
	 * @date 2017年2月18日
	 * @Description: 添加商品，web用
	 * @author 杨益桦
	 * @param queryGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> addGoods(AddGoodsVo addGoodsVo);

	/**
	 * @date 2017年2月20日
	 * @Description: 修改商品,web用
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> modifyGoods(ModifyGoodsVo modifyGoodsVo);

	/**
	 * @date 2017年2月20日
	 * @Description: 下架商品
	 * @author 杨益桦
	 * @param undercarriageGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> undercarriageGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	/**
	 * @date 2017年2月23日
	 * @Description: 删除商品
	 * @author 杨益桦
	 * @param undercarriageGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> delGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	/**
	 * @date 2017年2月23日
	 * @Description:上架商品
	 * @author 杨益桦
	 * @param undercarriageGoodsVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> groundingGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	/**
	 * @date 2017年2月22日
	 * @Description: 商品列表，app端用
	 * @author 杨益桦
	 * @param appPagedQueryVO
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryJsonGoodsList(QueryJsonGoodsListVo queryJsonGoodsListVo);

	/**
	 * @date 2017年2月22日
	 * @Description: 查询单个商品
	 * @author 杨益桦
	 * @param queryJsonGoodsListVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> getJsonGoods(GetJsonGoodsVo getJsonGoodsVo);

	/**
	 * @date 2017年3月6日
	 * @Description: app查询 使用page，limit参数
	 * @author 杨益桦
	 * @param queryPageLimitVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryPageLimit(QueryPageLimitVo queryPageLimitVo);

	/**
	 * @date 2017年3月15日
	 * @Description: 批量上架，下架
	 * @author 杨益桦
	 * @param chmodBathVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> chmodBath(ChmodBathVo chmodBathVo);

	/**
	 * @date 2017年3月17日
	 * @Description: 更新索引
	 * @author 杨益桦
	 * @param updateSolrVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> updateSolr(UpdateSolrVo updateSolrVo);

	/**
	 * @date 2017年3月17日
	 * @Description: 删除索引
	 * @author 杨益桦
	 * @param updateSolrVo
	 *            void
	 */
	void delSolr(UpdateSolrVo updateSolrVo);







	/**
	 * @date 2017年3月18日
	 * @Description: 搜索引擎查询商品列表
	 * @author 杨益桦
	 * @param querySolrVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> querySolrByAll(QuerySolrVo querySolrVo);

	/**
	 * @date 2017年3月28日
	 * @Description: 批量更新搜索引擎
	 * @author 杨益桦
	 * @param listVo
	 * @return
	 * @throws HJHBCSErrInfoException
	 *             Map<String,Object>
	 */
	Map<String, Object> bathUpdateSolr(List<Map<String, Object>> list) throws HJHBCSErrInfoException;

	/**
	 * @date 2017年3月28日
	 * @Description:查询全部符合条件的商品id
	 * @author 杨益桦
	 * @return Map<String,Object>
	 */
	Map<String, Object> queryAllId(String startDate, String endDate);

	/**
	 * @date 2017年4月1日
	 * @Description: 刷新搜索引擎前的，更新数据库
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	void updateGoodsTimer(GoodsTimerVo goodsTimerVo);

	/**
	 * @date 2017年4月10日
	 * @Description: 查询id的列表
	 * @author 杨益桦
	 * @param goods
	 * @return List<String>
	 */
	Map<String, Object> queryIdList(GoodsTimerVo goodsTimerVo);

	/**
	 * @date 2017年4月10日
	 * @Description: 查询需要更新到solr的实体，
	 * @author 杨益桦
	 * @param goods
	 *            ids
	 * @return List<Map<String,Object>>
	 */
	Map<String, Object> querySolrEntityByids(List<String> ids);
	
	/** 
	 * @date 2017年5月16日
	 * @Description: 根据商品id和规格id获得详情,下订单用
	 * @author 杨益桦
	 * @param productId
	 * @param standardId
	 * @return
	 * Product4Order
	 */
	Map<String, Object> getProduct4Order(String productId,String standardId);
	

}
