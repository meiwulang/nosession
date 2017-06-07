package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.Goods;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.vo.goods.AddGoodsVo;
import com.hjh.mall.vo.goods.GetJsonGoodsVo;
import com.hjh.mall.vo.goods.ModifyGoodsVo;
import com.hjh.mall.vo.goods.QueryGoodsVo;
import com.hjh.mall.vo.goods.QueryJsonGoodsListVo;
import com.hjh.mall.vo.goods.QueryPageLimitVo;
import com.hjh.mall.vo.goods.UndercarriageGoodsVo;

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
	@Deprecated
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
	 * @date 2017年4月7日
	 * @Description: 更新商品solr信息
	 * @author：王斌
	 * @param updateModelsVo
	 *            void
	 */
	public void updateGood(GoodsTimerVo goodsTimerVo);

	/**
	 * @date 2017年4月11日
	 * @Description: 更新已上架的商品的搜索引擎
	 * @author 杨益桦 void
	 */
	public void updateGroundSolr();
}
