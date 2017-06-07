package com.hjh.mall.quartz;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.order.api.OrderCloudService;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.hjh.mall.field.MallFields;
import com.hjh.mall.service.SmsSendMessageService;
import com.hjh.mall.type.OrderSms;
import com.hjh.mall.util.OrderSmsSenderUtil;


/**
 * Created by qiuxianxiang on 17/5/31.
 */

@Component
public class OrderPayBalanceRemindTask extends SimpleLeaderTakeTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPayBalanceRemindTask.class);

    @Autowired
    private CacheHelper cacheHelper;

    @Resource
    private SmsSendMessageService smsSendMessageService;

    @Reference(version = "1.0.0")
    private OrderCloudService orderCloudService;

    @Override
    public void doTask() {
        try {

            // 在数据量超过百万后,该代码就不适用了。
            List<String> smsCompoundInfoList = cacheHelper.getList(OrderSmsSenderUtil.getPayBalanceRemindKey(), 0, -1);
            if (smsCompoundInfoList == null || smsCompoundInfoList.size() == 0 ) {
                return;
            }

            Iterator<String> iterator = smsCompoundInfoList.iterator();
            while (iterator.hasNext()) {

                String smsCompoundInfo = iterator.next();

                doService(smsCompoundInfo);

            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }


    private void doService(String smsCompoundInfo) {
        try {

            Map<String, String> smsSendInfo = OrderSmsSenderUtil.parseSmsCompoundInfo(smsCompoundInfo);


            // 1. 判断是否已经到余款延期时间, 到了则发送短信提醒
            long balanceDate = Long.parseLong(smsSendInfo.get(OrderSmsSenderUtil.SMS_ORDER_BALANCE_DATE));
            if (new Date().getTime()  <  balanceDate) {
                return;
            }
            String orderId = smsSendInfo.get(OrderSmsSenderUtil.SMS_ORDER_ID);
            String mobile = smsSendInfo.get(OrderSmsSenderUtil.SMS_MOBILE);

            // 2. 判断当前订单状态,确定是否应该发送短信
            Order order = orderCloudService.getOrder(orderId, null).getData();
            if (needSendSms(order)) {
                sendSmsMsg(order.getOrderNo(),mobile, OrderSms.ORDER_PAY_BALANCE);
            }


            // 删除不再需要发送短信的数据
            removeCache(OrderSmsSenderUtil.getPayBalanceRemindKey(),smsCompoundInfo);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }


    }

    private boolean needSendSms(Order order) {
        if (order != null                               //订单存在
                && !order.isPayBalance()                //没有上传过余款凭证
                && order.getOrderStatus() == OrderStatus.PART_PAID.getCode()  // 订单状态处于定金支付状态
        ) {
            return true;
        }

        return false;
    }


    // 发送短消息,提示用户支付余款
    private void sendSmsMsg(String orderId, String mobile, OrderSms orderSms) {


        SendMessageEntity<Map<String,Object>> sendMessageEntity = new SendMessageEntity<>();

        Map<String, Object> messages = VOUtil.genEmptyResult();
        messages.put(MallFields.ORDER_ID,orderId);
        sendMessageEntity.setMessage(messages);

        sendMessageEntity.setMobile(mobile);
        sendMessageEntity.setTemp(orderSms.getDescription());


        smsSendMessageService.sendOrderMessage(sendMessageEntity);


    }


    //删除缓存数据
    private void removeCache(String keyName, String value) {
        cacheHelper.lrem(keyName, 0, value);
    }


}
