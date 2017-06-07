package com.hjh.mall.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.restfulapi.RestFulAPI;
import com.hjh.mall.goods.bizapi.bizserver.BizShoppingCartService;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderStandardsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrderVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelPreOrdersVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetLastInfoVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdatePreOrderStandardsVo;

/**
 * @Project: mall-web-bran
 * @Description 购物车
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
@Controller
public class ShoppingCartController {

	@Reference(version = "1.0.0")
	private BizShoppingCartService bizShoppingCartService;

	@RequestMapping(value = RestFulAPI.ShoppingCart.ADD_PREORDER, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPreorder(@RequestBody AddPreOrderVo vo) {
		return bizShoppingCartService.addNewPreOrder(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.DEL_PREORDER, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delPreorder(@RequestBody DelPreOrderVo vo) {
		return bizShoppingCartService.delPreOrder(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.BATCH_DEL_PREORDER, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchDelPreorder(@RequestBody DelPreOrdersVo vo) {
		return bizShoppingCartService.delPreOrders(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.QUERY_PREORDERS, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPreorders(@RequestBody PageQueryVO vo) {
		return bizShoppingCartService.queryPreOrders(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.DEL_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delPreOrdersStandard(@RequestBody DelPreOrderStandardsVo vo) {
		return bizShoppingCartService.delPreOrdersStandard(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.UPDATE_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePreOrdersStandard(@RequestBody UpdatePreOrderStandardsVo vo) {
		return bizShoppingCartService.updatePreOrdersStandard(vo);
	}

	@RequestMapping(value = RestFulAPI.ShoppingCart.GET_LAST_INFO, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getlastInfo(@RequestBody GetLastInfoVo vo) {
		return bizShoppingCartService.getlastInfo(vo);
	}

}
