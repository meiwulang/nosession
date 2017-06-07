package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsStandardsDao;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Goods;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsStandards;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsStandardsService;
import com.hjh.mall.bizapi.biz.goods.middle.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.goods.bizapi.bizserver.vo.OrderDetailVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSaleNumVo;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */

@Service
public class GoodsStandardsServiceImpl extends HJYServiceImplBase<GoodsStandards, String> implements
		GoodsStandardsService {
	@Resource
	private GoodsStandardsDao goodsStandardsDao;
	@Resource
	private GoodsDao goodsDao;

	@Override
	protected DAOBase<GoodsStandards, String> getDAO() {
		return goodsStandardsDao;
	}

	@Override
	@Transactional
	public Map<String, Object> updateSaleNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException {
		List<OrderDetailVo> list = updateSaleNumVo.getOrderDetailVos();

		Double totalNum = (double) 0;
		for (OrderDetailVo orderDetailVo : list) {
			GoodsStandards db = goodsStandardsDao.get(orderDetailVo.getStandard_id());
			Double storeNum = db.getStore_num();
			Double saleNum = db.getSale_num();
			totalNum += orderDetailVo.getPrdt_num();
			if (storeNum != null) {
				storeNum = storeNum - orderDetailVo.getPrdt_num();
			}
			if (saleNum != null) {
				saleNum = saleNum + orderDetailVo.getPrdt_num();
			}

			if (storeNum < 0) {
				throw new HJHBCSErrInfoException(MallErrorCode.GoodsErrorCode.SOTRE_NOT_ENOUGH);
			}

			GoodsStandards goodsStandards = new GoodsStandards();
			goodsStandards.setStandard_id(db.getStandard_id());
			goodsStandards.setStore_num(storeNum);
			goodsStandards.setSale_num(saleNum);
			goodsStandardsDao.update(goodsStandards);
		}

		Goods goodsDb = goodsDao.get(updateSaleNumVo.getPrdt_id());

		Goods goods = new Goods();
		goods.setGoods_id(updateSaleNumVo.getPrdt_id());
		if (goodsDb.getSale_num() != null) {
			goods.setSale_num(goodsDb.getSale_num() + totalNum);
		} else {
			goods.setSale_num(totalNum);
		}
		goodsDao.update(goods);

		return VOUtil.genSuccessResult();

	}

	@Override
	public Map<String, Object> checkStoreNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException {

		Goods goods = goodsDao.get(updateSaleNumVo.getPrdt_id());
		if (goods.getGoods_status().equals(BasicFields.DISABLE)) {
			return VOUtil.genErrorResult(MallErrorCode.GoodsErrorCode.GOODS_HAS_UNDER);
		}

		List<OrderDetailVo> list = updateSaleNumVo.getOrderDetailVos();

		Double totalNum = (double) 0;
		for (OrderDetailVo orderDetailVo : list) {
			GoodsStandards db = goodsStandardsDao.get(orderDetailVo.getStandard_id());
			Double storeNum = db.getStore_num();
			totalNum += orderDetailVo.getPrdt_num();
			if (storeNum != null) {
				storeNum = storeNum - orderDetailVo.getPrdt_num();
			}
			if (storeNum < 0) {
				throw new HJHBCSErrInfoException(MallErrorCode.GoodsErrorCode.SOTRE_NOT_ENOUGH);
			}
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateSaleNumNewOrder(List<Product4Order> orderList) {

		Double totalNum = (double) 0;
		String goodsId = null;
		for (Product4Order order : orderList) {
			goodsId = order.getProductId();
			GoodsStandards db = goodsStandardsDao.get(order.getStandardId());
			Double saleNum = db.getSale_num();
			totalNum += order.getPrdtNum();
			if (saleNum != null) {
				saleNum = saleNum + order.getPrdtNum();
			}


			GoodsStandards goodsStandards = new GoodsStandards();
			goodsStandards.setStandard_id(db.getStandard_id());
			goodsStandards.setSale_num(saleNum);
			goodsStandardsDao.update(goodsStandards);
		}

		Goods goodsDb = goodsDao.get(goodsId);

		Goods goods = new Goods();
		goods.setGoods_id(goodsId);
		if (goodsDb.getSale_num() != null) {
			goods.setSale_num(goodsDb.getSale_num() + totalNum);
		} else {
			goods.setSale_num(totalNum);
		}
		goodsDao.update(goods);

		return VOUtil.genSuccessResult();

	}
}
