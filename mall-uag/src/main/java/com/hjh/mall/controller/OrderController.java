package com.hjh.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.ClientAddress;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.order.api.OrderCloudService;
import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.order.dto.common.APIResponse;
import com.hjh.mall.order.vo.CreateOrderAddressVo;
import com.hjh.mall.order.vo.CreateOrderUserVo;
import com.hjh.mall.order.vo.CreateOrderVo;
import com.hjh.mall.order.vo.GetLogisticsVo;
import com.hjh.mall.service.ClientAddressService;
import com.hjh.mall.service.SmsSendMessageService;
import com.hjh.mall.type.OrderSms;
import com.hjh.mall.util.OrderSmsSenderUtil;
import com.hjh.mall.util.StringUtil;
import com.hjh.mall.vo.order.AddOrderPayProofVo;
import com.hjh.mall.vo.order.CancelOrderVo;
import com.hjh.mall.vo.order.CreateSingleOrderVo;
import com.hjh.mall.vo.order.ListOrderVo;
import com.hjh.mall.vo.order.ToUserReceivedVo;
import com.hjh.mall.vo.order.UpdateOrderPayProofVo;
import com.hjh.mall.vo.order.manager.OrderIdVO;

/**
 * Created by qiuxianxiang on 17/5/22.
 */

@Api("订单前端服务接口")
@RestController
@Controller
@RequestMapping("/json/order-service/")
public class OrderController {

	@Value("${operation_mobile}")
	private String operationMobile;

	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;
	
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	@Reference(version = "1.0.0")
	private OrderCloudService orderCloudService;

	@Autowired
	private ClientAddressService clientAddressService;

	@Autowired
	private CacheHelper cacheHelper;
	@Resource
	private KeyGenerate keyGenerate;

	@Resource
	private SmsSendMessageService smsSendMessageService;  // OrderSms

	@ApiOperation(value = "查询订单列表", notes = "条件之间是\"AND\"关系")
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public Map<String, Object> listOrder(@RequestBody ListOrderVo vo) {


		com.hjh.mall.order.vo.QueryOrderVo queryOrderVo = new com.hjh.mall.order.vo.QueryOrderVo();
		queryOrderVo.setUserId(getUserId());
		queryOrderVo.setOrderStatus(vo.getOrderStatusList());
		queryOrderVo.setPostageCalculation(vo.getPostageCalculation());
		queryOrderVo.setHaveFullPayProof(vo.getHaveFullPayProof());
		queryOrderVo.setHaveDepositPayProof(vo.getHaveDepositPayProof());
		queryOrderVo.setPageNum(vo.getPage());
		queryOrderVo.setPageSize(vo.getLimit());

		APIResponse<QueryResult<Order>> apiResponse = orderCloudService.listOrder(queryOrderVo);

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "商品下单接口,返回创建订单ID", notes = "默认商品列表组成一个单,如果商品列表存在分单,则抛出异常"
			+ "resubmitToken是防止用户连续提交的Token,机制是 客户端在生成订单之前,随机生成该订单唯一号,并上传提交")
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public Map<String, Object> createSingleOrder(@RequestBody CreateSingleOrderVo vo) {


		// 2. get user info

		// app用户目前没有getOperatorName，如果没有使用clientid代替
		String userId = getUserId();
//		if (StringUtils.isBlank(userId)) {
//			userId = getUserId();
//		}


		// 2.1. 防止订单重复提交
		String cacheKey = userId + "_ORDER_CREATE_KEY";
		String resubmitTokenCache = cacheHelper.get(cacheKey);
		if (StringUtils.isEmpty(resubmitTokenCache)) {
			cacheHelper.set(cacheKey, vo.getResubmitToken(), 5);
		} else {
			// 订单重复提交
			if (resubmitTokenCache.equals(vo.getResubmitToken())) {
				return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.DUPLICATE);
			}

		}

		// 3. get address info TODO maybe need check NullPointException , but i
		// wan`t do it by yyh
		ClientAddress clientAddress = clientAddressService.get(vo.getDeliveryAddressId());
		if (clientAddress == null) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_DEST_ADDR);
		}
		String deliveryAddress = clientAddress.getAddress_info();
		String consigneeName = clientAddress.getConsignee();
		String consigneeMobile = clientAddress.getConsignee_tel();
		String consigneeProvince = clientAddress.getProvicne();
		String consigneeCity = clientAddress.getCity();
		String consigneeDistrict = clientAddress.getArea();

		// 4. get create order info
		APIResponse<String> apiResponse = APIResponse.success();
		List<Product4Order> product4OrderList = initCreateOrderInfo(vo.getProductCompoundInfo(), apiResponse);
		if (apiResponse != null && !apiResponse.isSuccess()) {
			return VOUtil.genErrorResult(apiResponse.getError_no(), apiResponse.getError_info());
		}

		// 生成订单号逻辑 暂时这样
		String currentTime = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String primaryId = keyGenerate.getKeyGenerate(MallFields.ORDER_MAIN);
		String orderNo = new StringBuffer().append(currentTime.substring(0, 8))
				.append(String.format("%s", primaryId.substring(primaryId.length() - 5))).toString();
		;

		CreateOrderVo createOrderVo = new CreateOrderVo();
		createOrderVo.setOrderNo(orderNo);
		createOrderVo.setBuyerComments(vo.getBuyerComments());
		createOrderVo.setInviteCode(getInvokeCode());
		createOrderVo.setProduct4OrderList(product4OrderList);

		CreateOrderAddressVo createOrderAddressVo = new CreateOrderAddressVo();
		createOrderAddressVo.setDeliveryAddressId(vo.getDeliveryAddressId());
		createOrderAddressVo.setConsigneeAddress(deliveryAddress);
		createOrderAddressVo.setConsigneeName(consigneeName);
		createOrderAddressVo.setConsigneeMobile(consigneeMobile);
		createOrderAddressVo.setConsigneeProvince(consigneeProvince);
		createOrderAddressVo.setConsigneeCity(consigneeCity);
		createOrderAddressVo.setConsigneeDistrict(consigneeDistrict);

		CreateOrderUserVo createOrderUserVo = new CreateOrderUserVo();
		createOrderUserVo.setUserId(userId);
		createOrderUserVo.setUserName(getUserName());
		createOrderUserVo.setMobile(getUserMobile());

		APIResponse<String> orderApiResponse = orderCloudService.createSingleOrder(createOrderVo, createOrderAddressVo,
				createOrderUserVo);

		//   更新销量以及solr 
		Map<String, Object> createOrderResult = translateResultMap(orderApiResponse);
		
		if (createOrderResult.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS)) {
			// 更新数据库销量
			createOrderResult = bizGoodsService.updateSaleNumNewOrder(product4OrderList);
			if (createOrderResult.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS)) {
				String goodsId = product4OrderList.get(0).getProductId();
				// 更新solr销量
				Map<String, Object> carTypeName = bizGoodsCarModelService.queryNamesByGoodsId(goodsId);
				UpdateSolrVo updateSolrVo = JSONUtil.trans(carTypeName.get(MallFields.TYPE_NAMES_IN_SOLR),
						UpdateSolrVo.class);
				if (updateSolrVo == null) {
					updateSolrVo = new UpdateSolrVo();
					updateSolrVo.setGoods_id(goodsId);
				}
				bizGoodsService.updateSolr(updateSolrVo);
			}


		}
		return createOrderResult;

	}

	private List<Product4Order> initCreateOrderInfo(String[] productCompoundInfo, APIResponse apiResponse) {

		List<Product4Order> product4OrderList = new ArrayList<>();

		List<Product4Order> createOrderParamsList = parseProductCompoundInfo(productCompoundInfo);
		for (Product4Order createOrderParams : createOrderParamsList) {

			String productId = createOrderParams.getProductId();
			String standardId = createOrderParams.getStandardId();
			Map<String, Object> product4OrderMap = bizGoodsService.getProduct4Order(productId, standardId);

			if (!BasicFields.SUCCESS.equals(product4OrderMap.get(BasicFields.ERROR_NO))) { // 1.
																							// 失败
				// if get order standard faile , so create order failed
				apiResponse.setCode((String) product4OrderMap.get(BasicFields.ERROR_NO));
				apiResponse.setMessage((String) product4OrderMap.get(BasicFields.ERROR_INFO));

			} else { // 2. success

				if (product4OrderMap.containsKey(MallFields.PRODUCT4ORDER)
						&& product4OrderMap.get(MallFields.PRODUCT4ORDER) != null) {
					Product4Order objOrder = (Product4Order) product4OrderMap.get(MallFields.PRODUCT4ORDER);
					objOrder.setPrdtNum(createOrderParams.getPrdtNum());
					product4OrderList.add(objOrder);
				}

			}

		}

		return product4OrderList;
	}

	private List<Product4Order> parseProductCompoundInfo(String[] productCompoundInfos) {
		List<Product4Order> product4OrderList = new ArrayList<>();

		Product4Order product4Order = null;

		for (String productCompoundInfo : productCompoundInfos) {
			product4Order = new Product4Order();
			String[] infos = productCompoundInfo.split("_");

			product4Order.setProductId(infos[0]);
			product4Order.setStandardId(infos[1]);
			product4Order.setPrdtNum(Integer.parseInt(infos[2]));

			product4OrderList.add(product4Order);
		}

		return product4OrderList;
	}

	@ApiOperation(value = "获取订单详情,包括订单购买商品条目", notes = "")
	@RequestMapping(value = "/orderbyid", method = RequestMethod.POST)
	public Map<String, Object> getOrder(@RequestBody OrderIdVO vo) {

		APIResponse<Order> apiResponse = orderCloudService.getOrder(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "获取订单总额(不包含物流费)", notes = "用户支付费用=订单总额+物流费")
	@RequestMapping(value = "/order/transaction/amount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionAmount(@RequestBody OrderIdVO vo

	) {

		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionAmount(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "获取用户支付总额(订单总额+物流费)", notes = "用户支付费用=订单总额+物流费")
	@RequestMapping(value = "/order/transaction/pay/amount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionPayAmount(@RequestBody OrderIdVO vo) {

		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionPayAmount(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "获取订单交易定金", notes = "")
	@RequestMapping(value = "/order/transaction/deposit", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionDeposit(@RequestBody OrderIdVO vo) {

		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionDeposit(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "获取订单实际支付金额", notes = "")
	@RequestMapping(value = "/order/transaction/pay/actualAmount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionActualPayAmount(@RequestBody OrderIdVO vo) {

		String userId = getUserId();

		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionActualPayAmount(vo.getOrderId(), userId);

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "获取订单邮费", notes = "")
	@RequestMapping(value = "/order/transaction/postage", method = RequestMethod.POST)
	public Map<String, Object> getOrderPostage(@RequestBody OrderIdVO vo) {

		APIResponse<Double> apiResponse = orderCloudService.getOrderPostage(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "新增订单支付凭证", notes = "")
	@RequestMapping(value = "/order/pay/AddProof", method = RequestMethod.POST)
	public Map<String, Object> addOrderPayProof(@RequestBody AddOrderPayProofVo vo) {

		APIResponse apiResponse = orderCloudService.addOrderPayProof(vo.getOrderId(), getUserId(), vo.getPaymentUserName(),
				vo.getBankAccount(), vo.getPaymentAccount(), vo.getAmount(), vo.getPaymentProofSnapshot());

		if (apiResponse.isSuccess()) {  // 发送邮费已经核算的短信提醒

			APIResponse<Order> orderAPIResponse = orderCloudService.getOrder(vo.getOrderId(), getUserId());
			Order order = orderAPIResponse.getData();

			if (vo.isDepositProof()) {

				// 1. 通知管理员去查收
				sendSmsMsg(order.getOrderNo(),operationMobile, OrderSms.ORDER_TO_EXAMINE);


				// 2. 加入缓存,等余款逾期到以后,通知用户支付余款
				Date depositProofDate = order.getDepositProofDate();
				int balanceDateCount = order.getBalanceDateCount();
				long times = (depositProofDate.getTime()+balanceDateCount*1000*60*60*24);


				String v = OrderSmsSenderUtil.generateSmsCompoundInfo(order.getOrderId(),order.getUserMobile(),times);
				cacheHelper.lpush(OrderSmsSenderUtil.getPayBalanceRemindKey(), v);



			} else {

				sendSmsMsg(order.getOrderNo(),operationMobile,OrderSms.ORDER_RECEIVABLES);
			}

		}

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "获取支付凭证列表", notes = "")
	@RequestMapping(value = "/order/pay/getProofs", method = RequestMethod.POST)
	public Map<String, Object> getOrderPayProof(@RequestBody OrderIdVO vo

	) {

		APIResponse apiResponse = orderCloudService.getOrderPayProof(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@ApiOperation(value = "更新用户支付凭证", notes = "")
	@RequestMapping(value = "/order/pay/updateProof", method = RequestMethod.POST)
	public Map<String, Object> updateOrderPayProof(@RequestBody UpdateOrderPayProofVo vo) {

		APIResponse apiResponse = orderCloudService.updateOrderPayProof(vo.getOrderId(), vo.getPayProofId(), getUserId(), vo.getPaymentUserName(), vo.getBankAccount(),
				vo.getPaymentAccount(), vo.getAmount(), vo.getPaymentProofSnapshot());


		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "邮费是否核算", notes = "true[已经核算]  false[未核算]")
	@RequestMapping(value = "/order/transaction/postage/status", method = RequestMethod.POST)
	public Map<String, Object> isPostageCalculation(

			@RequestBody OrderIdVO vo) {

		String userId = getUserId();
		APIResponse<Boolean> apiResponse = orderCloudService.isPostageCalculation(vo.getOrderId(), userId);

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "用户已收货", notes = "买家在  记录订单收获后, 更改订单状态为 [用户已收货]")
	@RequestMapping(value = "/order/logistics/received", method = RequestMethod.POST)
	public Map<String, Object> toUserReceived(@RequestBody ToUserReceivedVo vo) {

		APIResponse apiResponse = orderCloudService.toUserReceived(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "获取订单操作列表", notes = "")
	@RequestMapping(value = "/order/operations", method = RequestMethod.POST)
	public Map<String, Object> getOrderOperations(
			@RequestBody OrderIdVO vo) {

		String userId = getUserId();

		APIResponse<List<OrderOperationLog>> apiResponse = orderCloudService.getOrderOperations(vo.getOrderId(),
				userId);


		return translateResultMap(apiResponse);
	}



	@ApiOperation(value = "获取订单", notes = "")
	@RequestMapping(value = "/order/progress", method = RequestMethod.POST)
	public Map<String, Object> getOrderProgress(
			@RequestBody OrderIdVO vo) {

		String userId = getUserId();

		APIResponse<List<OrderOperationLog>> apiResponse = orderCloudService.getOrderProgress(vo.getOrderId(),
				userId);


		return translateResultMap(apiResponse);
	}




	@ApiOperation(value = "取消订单")
	@RequestMapping(value = "/order/cancelOrder", method = RequestMethod.POST)
	public Map<String, Object> cancelOrder(@RequestBody CancelOrderVo cancelOrderVo) {


		APIResponse apiResponse = orderCloudService.cancelOrder(cancelOrderVo.getOrderId(), getUserId(), true,
				cancelOrderVo.getCancelReason());

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "", notes = "获取每种订单类型的数量")
	@RequestMapping(value = "/order/ordercountlist", method = RequestMethod.POST)
	public Map<String, Object> getOrderCount(@RequestBody HJYVO vo) {


		APIResponse<List<Map<String, Object>>> apiResponse = orderCloudService.getOrderCount(getUserId());
		Map<String, Object> genErrorResult = VOUtil.genErrorResult(apiResponse.getError_no(),
				apiResponse.getError_info());
		Object data = apiResponse.getData();
		if (data != null) {
			genErrorResult.put(BasicFields.RESULT_LIST, data);
		}
		return genErrorResult;
	}

	@ApiOperation(value = "申请退款")
	@RequestMapping(value = "/order/refundOrder", method = RequestMethod.POST)
	public Map<String, Object> refundOrder(@RequestBody CancelOrderVo cancelOrderVo) {


		boolean isCustomer = true;
		APIResponse apiResponse = orderCloudService.refundOrder(cancelOrderVo.getOrderId(), getUserId(), isCustomer,
				cancelOrderVo.getCancelReason());

		if (apiResponse.isSuccess()) {
			APIResponse<Order> order = orderCloudService.getOrder(cancelOrderVo.getOrderId(),null);

			sendSmsMsg(order.getData().getOrderNo(), operationMobile, OrderSms.ORDER_APPLY_REFUND);

		}


		return translateResultMap(apiResponse);
	}
	@ApiOperation(value = "撤销申请退款")
	@RequestMapping(value = "/order/undoRefundOrder", method = RequestMethod.POST)
	public Map<String, Object> undoRefundOrder(@RequestBody OrderIdVO orderIdVO) {

		APIResponse apiResponse = orderCloudService.undoRefundOrder(orderIdVO.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}



	@ApiOperation(value = "获得物流信息", notes = "获得物流信息")
	@RequestMapping(value = "/order/orderId/logistics/getLogistics", method = RequestMethod.POST)
	public Map<String, Object> getLogistics(@RequestBody OrderIdVO vo) {

		GetLogisticsVo getLogisticsVo = new GetLogisticsVo();
		getLogisticsVo.setOrderId(vo.getOrderId());

		APIResponse<Logistics> apiResponse = orderCloudService.getLogistics(getLogisticsVo);

		return translateResultMap(apiResponse);
	}



	private String getUserId() {
		String userId = null;

		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();

		if (sessionIdentity != null) {
			userId = sessionIdentity.getClientId();
		}

		return userId;
	}

	private String getUserMobile() {
		String mobile = null;
		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		if (sessionIdentity != null) {
			mobile = sessionIdentity.getMobile_tel();
		}

		return mobile;
	}

	private String getUserName() {
		// 获取注册 用户名
		String operatorName = null;

		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		if (sessionIdentity != null) {
			operatorName = sessionIdentity.getEnterprise_linkman();
		}

		return operatorName;
	}



	private String getInvokeCode() {

		String inviteCode = null;

		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		if (sessionIdentity != null) {
			inviteCode = sessionIdentity.getInvite_code();
		}

		return inviteCode;

	}


	private Map<String, Object> translateResultMap(APIResponse apiResponse) {

		Map<String, Object> genErrorResult = VOUtil.genErrorResult(apiResponse.getError_no(),
				apiResponse.getError_info());
		Object data = apiResponse.getData();
		if (data != null) {
			genErrorResult.put("data", data);
		}
		return genErrorResult;

	}


	private void sendSmsMsg(String orderId, String mobile, OrderSms orderSms) {


		SendMessageEntity<Map<String,Object>> sendMessageEntity = new SendMessageEntity<>();
		sendMessageEntity.setMobile(mobile);
		sendMessageEntity.setTemp(orderSms.getDescription());

		Map<String, Object> messages = VOUtil.genEmptyResult();
		messages.put(com.hjh.mall.field.MallFields.ORDER_ID,orderId);
		sendMessageEntity.setMessage(messages);

		smsSendMessageService.sendOrderMessage(sendMessageEntity);


	}

}
