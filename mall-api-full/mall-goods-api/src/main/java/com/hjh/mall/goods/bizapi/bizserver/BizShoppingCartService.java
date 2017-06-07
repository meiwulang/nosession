package com.hjh.mall.goods.bizapi.bizserver;

import java.util.Map;

import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderStandardsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrdersVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetLastInfoVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdatePreOrderStandardsVo;

/**
 * @Project: mall-goods-api-bran
 * @Description 购物车服务
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public interface BizShoppingCartService {

	@BizService(functionId = "901101", name = "加入购物车", desc = "app加入购物车")
	Map<String, Object> addNewPreOrder(AddPreOrderVo vo);

	@BizService(functionId = "901102", name = "删除购物车单个商品", desc = "删除购物车单个商品")
	Map<String, Object> delPreOrder(DelPreOrderVo vo);

	@BizService(functionId = "901103", name = "删除购物车多个商品", desc = "删除购物车多个商品")
	Map<String, Object> delPreOrders(DelPreOrdersVo vo);

	@BizService(functionId = "901104", name = "查询购物车商品", desc = "查询购物车商品")
	Map<String, Object> queryPreOrders(PageQueryVO vo);

	@BizService(functionId = "901105", name = "删除购物车单个商品的多个规格", desc = "删除购物车单个商品的多个规格")
	public Map<String, Object> delPreOrdersStandard(DelPreOrderStandardsVo vo);

	@BizService(functionId = "901106", name = "修改规格", desc = "修改规格")
	public Map<String, Object> updatePreOrdersStandard(UpdatePreOrderStandardsVo vo);

	@BizService(functionId = "901107", name = "查询指定购物车最新信息", desc = "查询指定购物车最新信息")
	public Map<String, Object> getlastInfo(GetLastInfoVo vo);
}
