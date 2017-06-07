package com.hjh.mall.bizapi.biz.goods.middle.bizimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsService;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsStandardsService;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
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
 * @Project: hjh-activity-middle
 * @Description
 * @author 杨益桦
 * @date 2016年10月28日
 * @version V1.0
 */
@Component
public class BizGoodsServiceImpl implements BizGoodsService {

	@Resource
	private GoodsService goodsService;

	@Resource
	private GoodsStandardsService goodsStandardsService;

	//
	@Override
	public Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo) {
		return goodsService.queryGoods(queryGoodsVo);
	}

	@Override
	public Map<String, Object> addGoods(AddGoodsVo addGoodsVo) {
		return goodsService.addGoods(addGoodsVo);
	}

	@Override
	public Map<String, Object> modifyGoods(ModifyGoodsVo modifyGoodsVo) {
		return goodsService.modifyGoods(modifyGoodsVo);
	}

	@Override
	public Map<String, Object> undercarriageGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		return goodsService.undercarriageGoods(undercarriageGoodsVo);
	}

	@Override
	public Map<String, Object> delGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		return goodsService.delGoods(undercarriageGoodsVo);
	}

	@Override
	public Map<String, Object> groundingGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		return goodsService.groundingGoods(undercarriageGoodsVo);
	}

	@Override
	public Map<String, Object> getJsonGoods(GetJsonGoodsVo getJsonGoodsVo) {
		return goodsService.getJsonGoods(getJsonGoodsVo);
	}

	@Override
	public Map<String, Object> chmodBath(ChmodBathVo chmodBathVo) {
		return goodsService.chmodBath(chmodBathVo);
	}

	@Override
	public Map<String, Object> updateSolr(UpdateSolrVo updateSolrVo) {
		return goodsService.updateSolr(updateSolrVo);
	}

	@Override
	public void delSolr(UpdateSolrVo updateSolrVo) {
		goodsService.delSolr(updateSolrVo);
	}



	@Override
	public Map<String, Object> querySolrByAll(QuerySolrVo querySolrVo) {
		return goodsService.querySolrByAll(querySolrVo);
	}

	@Override
	public Map<String, Object> updateSaleNum(UpdateSaleNumVo updateSaleNumVo) {
		return goodsStandardsService.updateSaleNum(updateSaleNumVo);
	}

	@Override
	public Map<String, Object> checkStoreNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException {
		return goodsStandardsService.checkStoreNum(updateSaleNumVo);
	}

	@Override
	public Map<String, Object> bathUpdateSolr(List<Map<String, Object>> list) throws HJHBCSErrInfoException {
		return goodsService.bathUpdateSolr(list);
	}

	@Override
	public Map<String, Object> queryAllId(String startDate, String endDate) throws HJHBCSErrInfoException {
		return goodsService.queryAllId(startDate, endDate);
	}

	@Override
	public void updateGoodsTimer(GoodsTimerVo goodsTimerVo) {
		goodsService.updateGoodsTimer(goodsTimerVo);
	}

	@Override
	public Map<String, Object> queryIdList(GoodsTimerVo goodsTimerVo) {
		return goodsService.queryIdList(goodsTimerVo);
	}

	@Override
	public Map<String, Object> querySolrEntityByids(List<String> ids) {
		return goodsService.querySolrEntityByids(ids);
	}

	@Override
	public Map<String, Object> getProduct4Order(String productId, String standardId) {
		return goodsService.getProduct4Order(productId, standardId);
	}

	@Override
	public Map<String, Object> updateSaleNumNewOrder(List<Product4Order> orderList) {
		return goodsStandardsService.updateSaleNumNewOrder(orderList);
	}

}
