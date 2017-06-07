package com.hjh.mall.order.util;

import cn.evun.sweet.framework.core.mvc.BusinessException;
import com.hjh.mall.order.constants.Errors;
import com.hjh.mall.order.constants.OrderStatus;
import com.hjh.mall.order.constants.TransactionType;
import com.hjh.mall.order.dao.OrderDao;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qiuxianxiang on 17/5/15.
 */
public class OrderUtil {

    public final static long ONE_DAY  = 1000 * 60 * 60 * 24;


    /**
     * 检查 该订单是否为该用户的,否则抛出异常
     * @param userId
     * @param orderId
     */
    public static void checkIsUserOrder(OrderDao orderDao, String userId, String orderId) {

        //如果userId未空,则不校验[认为是后端操作]
        if (StringUtils.isBlank(userId)) {
            return;
        }

        Integer count = orderDao.checkIsUserOrder(orderId, userId);

        if (count != null && count.intValue() > 0) {
            return;
        }

        throw BusinessException.withErrorCode(Errors.Common.ORDER_NOT_MATCH_USER);
    }


    public static void isOrderCanDelete(OrderStatus orderStatus) {

        // 暂定以下这些状态,可以删除订单
        switch (orderStatus) {
            case NON_PAYMENT:
            case RECEIVED:
            case SUCCESS:
            case CLOSED_BUYER_CANCEL:
            case CLOSED_SELLER_CANCEL:
            case CLOSED_REFUNDED:
            case CLOSED_RETURN_GOODS:
            case CLOSED_EXCEPTION:
                return;
        }

        // 非以上状态,则抛出异常
        throw BusinessException.withErrorCode(Errors.Order.ORDER_DELETE_ERROR);

    }


    public static void isOrderCanPayDeposit(TransactionType transactionType, OrderStatus orderStatus) {
        if (transactionType==TransactionType.NORMAL) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_TYPE_ERROR);
        }

        if (orderStatus != OrderStatus.NON_PAYMENT) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_STATUS_ERROR);
        }
    }

    public static void canPayTransactionBalance(TransactionType transactionType, OrderStatus orderStatus) {

        if (transactionType==TransactionType.NORMAL) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_TYPE_ERROR);
        }

        if (orderStatus != OrderStatus.PART_PAID) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_STATUS_ERROR);
        }


    }




    /**
     * 检查余款延期时间, 支付余款必须过了这个 时间后才能支付
     * @param balanceProofDate
     * @param delayDay
     */
    public static void isTimeToPayTransactionBalance(Date balanceProofDate, int delayDay) {

        if (balanceProofDate == null) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_BALANCE_DATE_ERROR);
        }

        if ((new Date().getTime() - balanceProofDate.getTime())  <  delayDay * ONE_DAY) {

            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_BALANCE_DATE_ERROR);

        }

    }


    public static void canPayTransactionAmount(TransactionType transactionType, OrderStatus orderStatus) {

        if (transactionType==TransactionType.DEPOSIT) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_TYPE_ERROR);
        }

        if (orderStatus != OrderStatus.NON_PAYMENT) {
            throw BusinessException.withErrorCode(Errors.Order.ORDER_PAYMENT_STATUS_ERROR);
        }


    }



    public static void main(String[] args) {

        BigDecimal productPrices = new BigDecimal(100).multiply(new BigDecimal(2));
        System.out.println(productPrices.doubleValue());
    }
}
