package com.hjh.mall.util;

import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.hjh.mall.constants.CacheKeys;
import com.hjh.mall.field.MallFields;
import com.hjh.mall.type.OrderSms;
import org.apache.commons.lang3.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiuxianxiang on 17/5/31.
 */
public class OrderSmsSenderUtil {

    // 订单ID  -注释: 改成订单编号了
    public static final String SMS_ORDER_ID = "SMS_ORDER_ID_";
    // 订单用户手机号码
    public static final String SMS_MOBILE = "SMS_MOBILE_";
    // 具体的余款延期时间, 单位毫秒
    public static final String SMS_ORDER_BALANCE_DATE="SMS_ORDER_BALANCE_DATE_";

    private static final int LENGTH = 3;


    public static final String SPLIT = "_";


    public static String getPayBalanceRemindKey() {

        return CacheKeys.ORDER_PAYMENT_SMS;

    }

    public static Map<String,String> parseSmsCompoundInfo(String smsCompoundInfo) {
        Map<String,String> map = new HashMap<>();

        String[] infos = smsCompoundInfo.split(SPLIT);
        check(infos,smsCompoundInfo);


        map.put(SMS_ORDER_ID,infos[0]);
        map.put(SMS_MOBILE,infos[1]);
        map.put(SMS_ORDER_BALANCE_DATE,infos[2]);

        return map;
    }

    private static void check(String[] infos , String smsCompoundInfo) {

        if (infos == null || infos.length != LENGTH) {
            throw new IllegalArgumentException("订单模块余款延期发送短信的数据格式不正确[" + smsCompoundInfo + "]");
        }

        for (String v : infos) {
            if (org.apache.commons.lang3.StringUtils.isBlank(v)) {
                throw new IllegalArgumentException("订单模块余款延期发送短信的数据格式不正确[" + smsCompoundInfo + "]");
            }
        }

    }


    public static String generateSmsCompoundInfo(String orderId, String mobile,long time) {
        return orderId + SPLIT + mobile + SPLIT + time;

    }






}
