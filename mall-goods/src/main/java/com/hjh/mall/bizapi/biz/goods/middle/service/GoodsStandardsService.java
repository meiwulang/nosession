package com.hjh.mall.bizapi.biz.goods.middle.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsStandards;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSaleNumVo;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */
public interface GoodsStandardsService extends ServiceBase<GoodsStandards, String> {

	/**
	 * @date 2017年3月21日
	 * @Description: 更新商品库存与销量
	 * @author 杨益桦
	 * @param updateSaleNumVo
	 * @return Map<String,Object>
	 */
	Map<String, Object> updateSaleNum(UpdateSaleNumVo updateSaleNumVo);

	/**
	 * @date 2017年3月27日
	 * @Description: 判断库存
	 * @author 杨益桦
	 * @param updateSaleNumVo
	 * @return
	 * @throws HJHBCSErrInfoException
	 *             Map<String,Object>
	 */
	Map<String, Object> checkStoreNum(UpdateSaleNumVo updateSaleNumVo) throws HJHBCSErrInfoException;
	
	/** 
	 * @date 2017年6月6日
	 * @Description: 更新销量，给新订单使用
	 * @author 杨益桦
	 * @param orderList
	 * @return
	 * Map<String,Object>
	 */
	Map<String, Object> updateSaleNumNewOrder(List<Product4Order> orderList) ;

}
