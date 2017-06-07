package com.hjh.mall.controller;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.hjh.mall.field.MallFields;

import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.dto.Order;
import com.hjh.mall.order.dto.OrderOperationLog;
import com.hjh.mall.order.dto.OrderPaymentProof;
import com.hjh.mall.order.dto.QueryResult;
import com.hjh.mall.service.SmsSendMessageService;
import com.hjh.mall.type.OrderSms;
import com.hjh.mall.util.OrderSmsSenderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
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
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.order.api.OrderCloudService;
import com.hjh.mall.order.dto.common.APIResponse;
import com.hjh.mall.order.vo.AddLogisticsVo;
import com.hjh.mall.order.vo.GetLogisticsVo;
import com.hjh.mall.order.vo.QueryOrderVo;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.vo.order.AddOrderPayProofVo;
import com.hjh.mall.vo.order.CancelOrderVo;
import com.hjh.mall.vo.order.manager.DeleteOrderVo;
import com.hjh.mall.vo.order.manager.OrderIdVO;
import com.hjh.mall.vo.order.manager.PayTransactionAmountVo;
import com.hjh.mall.vo.order.manager.PayTransactionBalanceVO;
import com.hjh.mall.vo.order.manager.PayTransactionDepositVO;
import com.hjh.mall.vo.order.manager.RefundOrderVo;
import com.hjh.mall.vo.order.manager.UpdateOrderBalanceDateVO;
import com.hjh.mall.vo.order.manager.UpdateOrderExceptDeliveryDesc;
import com.hjh.mall.vo.order.manager.UpdateOrderPayProofVO;
import com.hjh.mall.vo.order.manager.UpdateOrderPostageAndSoOnVo;
import com.hjh.mall.vo.order.manager.UpdateOrderPostageVo;
import com.hjh.mall.vo.order.manager.UpdateOrderRemarkVO;

/**
 * Created by qiuxianxiang on 17/5/17.
 */

@Api("订单后端服务接口")
@RestController
@Controller
@RequestMapping("/order-manager-service")
public class OrderManagerController {

	@Value("${operation_mobile}")
	String operationMobile;

	@Reference(version = "1.0.0")
	private OrderCloudService orderCloudService;

	@Resource
	private UploadService uploadService;

	@Resource
	private SmsSendMessageService smsSendMessageService;  // OrderSms

	@Autowired
	private CacheHelper cacheHelper;

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "查询订单列表", notes = "条件之间是\"AND\"关系")
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public Map<String, Object> listOrder(@RequestBody QueryOrderVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}

		APIResponse<QueryResult<Order>> apiResponse = orderCloudService.listOrder(vo);

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单详情,包括订单购买商品条目", notes = "")
	@RequestMapping(value = "/order/orderId", method = RequestMethod.POST)
	public Map<String, Object> getOrder(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Order> apiResponse = orderCloudService.getOrder(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "删除订单", notes = "必须确保该订单是这个用户的,否则抛出异常")
	@RequestMapping(value = "/order/orderId/delete", method = RequestMethod.POST)
	public Map<String, Object> deleteOrder(@RequestBody DeleteOrderVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.deleteOrder(vo.getOrderId(), getUserId());
		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单总额(不包含物流费)", notes = "用户支付费用=订单总额+物流费")
	@RequestMapping(value = "/order/orderId/transaction/amount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionAmount(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionAmount(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取用户支付总额(订单总额+物流费)", notes = "用户支付费用=订单总额+物流费")
	@RequestMapping(value = "/order/orderId/transaction/pay/amount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionPayAmount(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Double> apiResponse = orderCloudService
				.getOrderTransactionPayAmount(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单交易定金", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/getdeposit", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionDeposit(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionDeposit(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单实际支付金额", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/pay/actualAmount", method = RequestMethod.POST)
	public Map<String, Object> getOrderTransactionActualPayAmount(@RequestBody DeleteOrderVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Double> apiResponse = orderCloudService.getOrderTransactionActualPayAmount(vo.getOrderId(),
				getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单邮费", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/getpostage", method = RequestMethod.POST)
	public Map<String, Object> getOrderPostage(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Double> apiResponse = orderCloudService.getOrderPostage(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新订单邮费信息", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/updatepostage", method = RequestMethod.POST)
	public Map<String, Object> updateOrderPostage(@RequestBody UpdateOrderPostageVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderPostage(vo.getOrderId(), vo.getPostage(), getUserId());

		if (apiResponse.isSuccess()) {  // 发送邮费已经核算的短信提醒

			sendSmsToRemindBuyer(vo.getOrderId());

		}

		return translateResultMap(apiResponse);
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新订单预计发货时间描述", notes = "虽然是描述,但推荐直接设置日期格式字符串")
	@RequestMapping(value = "/order/orderId/logistics/deliveryDesc", method = RequestMethod.POST)
	public Map<String, Object> updateOrderExceptDeliveryDesc(@RequestBody UpdateOrderExceptDeliveryDesc vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderExceptDeliveryDesc(vo.getOrderId(),
				vo.getExceptDeliveryDesc(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新订单余款到期时间", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/balance/upadateDate", method = RequestMethod.POST)
	public Map<String, Object> updateOrderBalanceDate(@RequestBody UpdateOrderBalanceDateVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderBalanceDate(vo.getOrderId(), vo.getBalanceDateCount(),
				getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新订单邮费等信息", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/postageAndDeliveryDate", method = RequestMethod.POST)
	public Map<String, Object> updateOrderPostage1(@RequestBody UpdateOrderPostageAndSoOnVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderPostage(vo.getOrderId(), vo.getPostage(),
				vo.getEstimateDeliveryDesc(), vo.getBalanceDateCount(), getUserId());

		if (apiResponse.isSuccess()) {  // 发送邮费已经核算的短信提醒

			sendSmsToRemindBuyer(vo.getOrderId());

		}

		return translateResultMap(apiResponse);
	}

	private void sendSmsToRemindBuyer(String orderId) {
		APIResponse<Order> order = orderCloudService.getOrder(orderId,null);

		if (order.getData()!=null) {
			String userMobile = order.getData().getUserMobile();
			sendSmsMsg(order.getData().getOrderNo(), userMobile ,OrderSms.ORDER_PER_PAYMENT);
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新订单备注", notes = "")
	@RequestMapping(value = "/order/orderId/remark", method = RequestMethod.POST)
	public Map<String, Object> updateOrderRemark(@RequestBody UpdateOrderRemarkVO vo

	) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderRemark(vo.getOrderId(), getUserId(), vo.getRemark());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取订单备注", notes = "")
	@RequestMapping(value = "/order/orderId/getremark", method = RequestMethod.POST)
	public Map<String, Object> getOrderRemark(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<String> apiResponse = orderCloudService.getOrderRemark(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "新增订单支付凭证", notes = "")
	@RequestMapping(value = "/order/orderId/pay/addOrderPayProof", method = RequestMethod.POST)
	public Map<String, Object> addOrderPayProof(@RequestBody AddOrderPayProofVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.addOrderPayProof(vo.getOrderId(), getUserId(),
				vo.getPaymentUserName(), vo.getBankAccount(), vo.getPaymentAccount(), vo.getAmount(),
				vo.getPaymentProofSnapshot());

		if (apiResponse.isSuccess()) {  // 发送邮费已经核算的短信提醒

			APIResponse<Order> orderAPIResponse = orderCloudService.getOrder(vo.getOrderId(), null);
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

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获取支付凭证列表", notes = "")
	@RequestMapping(value = "/order/orderId/pay/getproofs", method = RequestMethod.POST)
	public Map<String, Object> getOrderPayProof(@RequestBody OrderIdVO vo

	) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<List<OrderPaymentProof>> apiResponse = orderCloudService.getOrderPayProof(vo.getOrderId(),
				getUserId());

		return translateResultMap(apiResponse);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "更新用户支付凭证", notes = "")
	@RequestMapping(value = "/order/orderId/pay/proof", method = RequestMethod.POST)
	public Map<String, Object> updateOrderPayProof(@RequestBody UpdateOrderPayProofVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateOrderPayProof(vo.getOrderId(), vo.getPayProofId(), getUserId(),
				vo.getPaymentUserName(), vo.getBankAccount(), vo.getPaymentAccount(), vo.getAmount(),
				vo.getPaymentProofSnapshot());

		return translateResultMap(apiResponse);

	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "邮费是否核算", notes = "true[已经核算]  false[未核算]")
	@RequestMapping(value = "/order/orderId/transaction/postage/status", method = RequestMethod.POST)
	public Map<String, Object> isPostageCalculation(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Boolean> apiResponse = orderCloudService.isPostageCalculation(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "支付预付款", notes = "true[已经核算]  false[未核算]")
	@RequestMapping(value = "/order/orderId/transaction/deposit", method = RequestMethod.POST)
	public Map<String, Object> payTransactionDeposit(@RequestBody PayTransactionDepositVO vo

	) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.payTransactionDeposit(vo.getOrderId(), vo.getDeposit(),
				getUserId());


		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "支付余款", notes = "")
	@RequestMapping(value = "/order/orderId/transaction/balance", method = RequestMethod.POST)
	public Map<String, Object> payTransactionBalance(@RequestBody PayTransactionBalanceVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.payTransactionBalance(vo.getOrderId(), vo.getBalance(),
				getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "全额付款", notes = "")
	@RequestMapping(value = "/order/transaction/actualAmount", method = RequestMethod.POST)
	public Map<String, Object> payTransactionAmount(@RequestBody PayTransactionAmountVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService
				.payTransactionAmount(vo.getOrderId(), vo.getAmount(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked" })
	@ApiOperation(value = "获得物流信息", notes = "获得物流信息")
	@RequestMapping(value = "/order/orderId/logistics/getLogistics", method = RequestMethod.POST)
	public Map<String, Object> getLogistics(@RequestBody GetLogisticsVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse<Logistics> apiResponse = orderCloudService.getLogistics(vo);

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "商家已发货", notes = "商家在 物流模块 记录订单的物流信息后, 更改订单状态为 [卖家已发货]")
	@RequestMapping(value = "/order/orderId/logistics/delivery", method = RequestMethod.POST)
	public Map<String, Object> toShippedProduct(@RequestBody AddLogisticsVo vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		vo.setCreateUserId(getManagerId());
		vo.setCreateUserName(getOperatorName());
		
		// 快递单快照 可以不填
		if (StringUtils.isNoneBlank(vo.getLogisticsNoteSnapshot())) {
			String path = uploadService.uploadImg(UploadType.LOGISTICS_NOTE_SNAPSHOT.getDescription(),
					vo.getLogisticsNoteSnapshot());
			vo.setLogisticsNoteSnapshot(path);
		}

		APIResponse addLogApiResponse = orderCloudService.addLogistics(vo);
		if (!addLogApiResponse.isSuccess()) { // 调用 addLogistics 失败
			addLogApiResponse.setCode(BasicErrorCodes.COMMON_ERROR);
			addLogApiResponse.setMessage("");
			return translateResultMap(addLogApiResponse);
		}

		APIResponse apiResponse = orderCloudService.toShippedProduct(vo.getOrderId(), null);
		if (apiResponse.isSuccess()) {
			APIResponse<Order> order = orderCloudService.getOrder(vo.getOrderId(), null);
			if (order.getData()!=null) {
				String userMobile = order.getData().getUserMobile();

				sendSmsMsg(order.getData().getOrderNo(), userMobile, OrderSms.ORDER_DELIVER_GOODS);
			}



		}

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "用户已收货", notes = "买家在  记录订单收获后, 更改订单状态为 [用户已收货]")
	@RequestMapping(value = "/order/orderId/logistics/received", method = RequestMethod.POST)
	public Map<String, Object> toUserReceived(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.toUserReceived(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}


	@ApiOperation(value = "获取订单进度条", notes = "")
	@RequestMapping(value = "/order/progress", method = RequestMethod.POST)
	public Map<String, Object> getOrderProgress(
			@RequestBody OrderIdVO vo) {


		APIResponse<List<OrderOperationLog>> apiResponse = orderCloudService.getOrderProgress(vo.getOrderId(),
				null);

		return translateResultMap(apiResponse);
	}





	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "用户交易成功", notes = "")
	@RequestMapping(value = "/order/orderId/status/success", method = RequestMethod.POST)
	public Map<String, Object> toOrderSuccess(@RequestBody OrderIdVO vo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.toOrderSuccess(vo.getOrderId(), getUserId());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "取消订单")
	@RequestMapping(value = "/order/cancelOrder", method = RequestMethod.POST)
	public Map<String, Object> cancelOrder(@RequestBody CancelOrderVo cancelOrderVo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.cancelOrder(cancelOrderVo.getOrderId(), null, false,
				cancelOrderVo.getCancelReason());

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "申请退款")
	@RequestMapping(value = "/order/refundOrder", method = RequestMethod.POST)
	public Map<String, Object> refundOrder(@RequestBody CancelOrderVo cancelOrderVo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}

		APIResponse apiResponse = orderCloudService.refundOrder(cancelOrderVo.getOrderId(), null, false,
				cancelOrderVo.getCancelReason());

		if (apiResponse.isSuccess()) {
			APIResponse<Order> order = orderCloudService.getOrder(cancelOrderVo.getOrderId(),null);

			sendSmsMsg(order.getData().getOrderNo(), operationMobile, OrderSms.ORDER_APPLY_REFUND);

		}

		return translateResultMap(apiResponse);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "申请退款 完成")
	@RequestMapping(value = "/order/refundOrderComplete", method = RequestMethod.POST)
	public Map<String, Object> refundOrderComplete(@RequestBody RefundOrderVo refundOrderVo) {
		Object userType = getUserType();
		if (userType.getClass().equals(HashMap.class)) {
			return (Map<String, Object>) userType;
		}
		APIResponse apiResponse = orderCloudService.updateRefundingInfo(refundOrderVo.getOrderId(),
				refundOrderVo.getRefundingAmount(), refundOrderVo.getRefundingExplain());

		if (apiResponse.isSuccess()) {
			APIResponse<Order> order = orderCloudService.getOrder(refundOrderVo.getOrderId(), null);
			if (order.getData() != null) {
				String userMobile = order.getData().getUserMobile();
				sendSmsMsg(order.getData().getOrderNo(), userMobile, OrderSms.ORDER_REFUND_COMPLETE);
			}

		}

		return translateResultMap(apiResponse);
	}

	@ApiOperation(value = "", notes = "获取每种订单类型的数量")
	@RequestMapping(value = "/order/ordercountlist", method = RequestMethod.POST)
	public Map<String, Object> getManagerOrderCount() {

		APIResponse<Map<String, Object>> managerOrderCount = orderCloudService.getManagerOrderCount();

		return translateResultMap(managerOrderCount);

	}



	private String getManagerId() { // 获取登陆管理员ID
		String userId = null;

		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		if (sessionIdentity != null) {
			userId = sessionIdentity.getClientId();
		}

		return userId;
	}

	private String getOperatorName() { // 获取登陆管理员 用户名
		String operatorName = null;

		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		if (sessionIdentity != null) {
			operatorName = sessionIdentity.getOperatorName();
		}

		return operatorName;
	}


	private String getUserId() {  // 获取订单用户的ID, 默认为空
		return null;
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

	private Object getUserType() {
		String operator = HJYBizDataContext.getSessionIdentity().getOperatorName();
		if (StringUtil.isBlank(operator)) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.INVALID_OPT_INTO);
		}
		return operator;
	}

	private void sendSmsMsg(String orderId, String mobile, OrderSms orderSms) {


		SendMessageEntity<Map<String,Object>> sendMessageEntity = new SendMessageEntity<>();
		sendMessageEntity.setMobile(mobile);
		sendMessageEntity.setTemp(orderSms.getDescription());

		Map<String, Object> messages = VOUtil.genEmptyResult();
		messages.put(MallFields.ORDER_ID,orderId);
		sendMessageEntity.setMessage(messages);

		smsSendMessageService.sendOrderMessage(sendMessageEntity);

	}
}
