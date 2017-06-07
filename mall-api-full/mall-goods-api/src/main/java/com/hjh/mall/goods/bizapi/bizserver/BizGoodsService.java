package com.hjh.mall.goods.bizapi.bizserver;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ChmodBathVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetJsonGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ModifyGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QuerySolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UndercarriageGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSaleNumVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;

/**
 * @Project: mall-commodity-api
 * @Description
 * @author 杨益桦
 * @date 2017年3月14日
 * @version V1.0
 */
public interface BizGoodsService {
	@BizService(functionId = "queryGoods", name = "web页面查询商品列表", desc = "web页面查询商品列表")
	Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo);

	@BizService(functionId = "addGoods", name = "添加商品", desc = "添加商品")
	Map<String, Object> addGoods(AddGoodsVo addGoodsVo);

	@BizService(functionId = "modifyGoods", name = "修改商品", desc = "修改商品")
	Map<String, Object> modifyGoods(ModifyGoodsVo modifyGoodsVo);

	@BizService(functionId = "undercarriageGoods", name = "下架商品", desc = "下架商品")
	Map<String, Object> undercarriageGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	@BizService(functionId = "delGoods", name = "删除商品", desc = "删除商品")
	Map<String, Object> delGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	@BizService(functionId = "groundingGoods", name = "上架商品", desc = "上架商品")
	Map<String, Object> groundingGoods(UndercarriageGoodsVo undercarriageGoodsVo);

	@BizService(functionId = "chmodBath", name = "批量上架，下架", desc = "批量上架，下架")
	Map<String, Object> chmodBath(ChmodBathVo chmodBathVo);

	// @BizService(functionId = "900307", name = "web页面查询商品列表", desc =
	// "web页面查询商品列表")
	// Map<String, Object> queryJsonGoodsList(QueryJsonGoodsListVo
	// queryJsonGoodsListVo);
	//
	@BizService(functionId = "getJsonGoods", name = "app获得商品详情", desc = "app获得商品详情")
	Map<String, Object> getJsonGoods(GetJsonGoodsVo getJsonGoodsVo);

	//
	// @BizService(functionId = "900309", name = "web页面查询商品列表", desc =
	// "web页面查询商品列表")
	// Map<String, Object> queryPageLimit(QueryPageLimitVo queryPageLimitVo);

	@BizService(functionId = "updateSolr", name = "更新solr", desc = "更新solr")
	Map<String, Object> updateSolr(UpdateSolrVo updateSolrVo);

	@BizService(functionId = "delSolr", name = "删除solr", desc = "删除solr")
	void delSolr(UpdateSolrVo updateSolrVo);

	@BizService(functionId = "querySolrByAll", name = "查询搜索引擎", desc = "查询搜索引擎")
	Map<String, Object> querySolrByAll(QuerySolrVo querySolrVo);

	@BizService(functionId = "updateSaleNum", name = "更新销量", desc = "更新销量")
	Map<String, Object> updateSaleNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException;

	@BizService(functionId = "checkStoreNum", name = "更新销量", desc = "更新销量")
	Map<String, Object> checkStoreNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException;

	@BizService(functionId = "bathUpdateSolr", name = "批量更新销量", desc = "批量更新销量")
	Map<String, Object> bathUpdateSolr(List<Map<String, Object>> list) throws HJHBCSErrInfoException;

	@BizService(functionId = "queryAllId", name = "找到所有符合条件的idlist", desc = "找到所有符合条件的idlist")
	Map<String, Object> queryAllId(String startDate, String endDate) throws HJHBCSErrInfoException;

	@BizService(functionId = "updateGoodsTimer", name = "刷新搜索引擎前的，更新数据库", desc = "刷新搜索引擎前的，更新数据库")
	void updateGoodsTimer(GoodsTimerVo goodsTimerVo);

	@BizService(functionId = "queryIdList", name = "查询id的列表", desc = "刷新搜索引擎前的，更新数据库")
	Map<String, Object> queryIdList(GoodsTimerVo goodsTimerVo);

	@BizService(functionId = "querySolrEntityByids", name = "查询需要更新solr的实体", desc = "查询需要更新solr的实体")
	Map<String, Object> querySolrEntityByids(List<String> ids);
	
	@BizService(functionId = "getProduct4Order", name = "查询商品信息，order系统使用", desc = "查询商品信息，order系统使用")
	Map<String, Object> getProduct4Order(String productId,String standardId);
	
	@BizService(functionId = "updateSaleNumNewOrder", name = "更新销量(新订单)", desc = "更新销量(新订单)")
	Map<String, Object> updateSaleNumNewOrder(List<Product4Order> orderList) ;
}
