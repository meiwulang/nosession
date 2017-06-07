package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.vo.AppPagedQueryVO;
import com.hjh.mall.field.RestFulAPI;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSaleNumVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.service.OrderDetailService;
import com.hjh.mall.service.OrderMainService;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.CreateOrderVo;
import com.hjh.mall.vo.QueryOrdersVo;

//保证线程安全
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderDetailController {

	@Resource
	private BusiSessionHelper busiSessionHelper;
	@Resource
	private OrderDetailService orderDetailServiceImpl;
	@Resource
	private OrderMainService orderMainServiceImpl;

	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;

	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	/**
	 * @date 2017年2月20日
	 * @Description: 生成订单
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = { RestFulAPI.OrderCode.CREAT_EORDER })
	@ResponseBody
	@Transactional
	public Map<String, Object> createOrder(@RequestBody CreateOrderVo vo) {

		// 判断是否有库存
		UpdateSaleNumVo updateSaleNumVo = JSONUtil.trans(vo, UpdateSaleNumVo.class);
		Map<String, Object> checkStoreNumResult = bizGoodsService.checkStoreNum(updateSaleNumVo);
		if (checkStoreNumResult.get(BasicFields.ERROR_NO).equals(MallErrorCode.GoodsErrorCode.SOTRE_NOT_ENOUGH)
				|| checkStoreNumResult.get(BasicFields.ERROR_NO).equals(MallErrorCode.GoodsErrorCode.GOODS_HAS_UNDER)) {
			// // 重新生成新会话
			// SessionIdentity sessionIdentity =
			// HJYBizDataContext.getSessionIdentity();
			// String accessToken = refreshSession(vo.getAccess_token(),
			// sessionIdentity);
			// checkStoreNumResult.put(BasicFields.ACCESS_TOKEN, accessToken);
			return checkStoreNumResult;
		}

		Map<String, Object> result = orderMainServiceImpl.createOrder(vo);
		// 更新库存以及solr
		if (result.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS)) {
			String accessToken = (String) result.get(BasicFields.ACCESS_TOKEN);
			// 更新数据库
			result = bizGoodsService.updateSaleNum(updateSaleNumVo);
			if (result.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS)) {
				// 更新solr
				Map<String, Object> carTypeName = bizGoodsCarModelService.queryNamesByGoodsId(vo.getPrdt_id());
				UpdateSolrVo updateSolrVo = JSONUtil.trans(carTypeName.get(MallFields.TYPE_NAMES_IN_SOLR),
						UpdateSolrVo.class);
				if (updateSolrVo == null) {
					updateSolrVo = new UpdateSolrVo();
					updateSolrVo.setGoods_id(vo.getPrdt_id());
				}
				bizGoodsService.updateSolr(updateSolrVo);
			}

			result.put(BasicFields.ACCESS_TOKEN, accessToken);

		}
		return result;
	}

	private String refreshSession(String accessToken, SessionIdentity sessionIdentity) {
		busiSessionHelper.destroySession(accessToken);
		String access_token = busiSessionHelper.renewAccess_token(sessionIdentity);
		String operator_noKey = busiSessionHelper.getClient_idKey(sessionIdentity.getClientId());
		// 根据会话令牌保存信息到缓存
		busiSessionHelper.setInfoForSession(operator_noKey, access_token);
		return access_token;
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 查询订单
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = { RestFulAPI.OrderCode.QUERY_ORDER })
	@ResponseBody
	public Map<String, Object> queryOrder(@RequestBody QueryOrdersVo vo) {
		return orderMainServiceImpl.queryOrder(vo);
	}

	/**
	 * @date 2017年2月20日
	 * @Description:导出订单
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 * @throws IOException
	 */
	@RequestMapping(value = { RestFulAPI.OrderCode.EXPORT_ORDER })
	public void exportOrder(QueryOrdersVo vo, HttpServletResponse response) throws IOException {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "机惠多订单列表" + System.currentTimeMillis();
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		fOut = orderMainServiceImpl.exportOrder(vo, fOut);
		fOut.flush();
		fOut.close();
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 查询用户订单
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = { RestFulAPI.OrderCode.QUERY_CLIENTS_ORDERS })
	@ResponseBody
	public Map<String, Object> queryClientsOrders(@RequestBody AppPagedQueryVO vo) {
		return orderMainServiceImpl.getClientsOrders(vo);
	}
}
