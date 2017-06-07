package com.hjh.mall.order.service;

import java.util.Date;

import com.hjh.mall.order.dto.Logistics;
import com.hjh.mall.order.vo.AddLogisticsVo;
import com.hjh.mall.order.vo.GetLogisticsVo;

/**
 * Created by qiuxianxiang on 17/5/16.
 */
public interface LogisticsService {

    /** 
     * @date 2017年5月24日
     * @Description: 获得物流详情
     * @author 杨益桦
     * @param getLogisticsVo
     * @return
     * Logistics
     */
    Logistics getLogistics(GetLogisticsVo getLogisticsVo);

    /** 
     * @date 2017年5月24日
     * @Description: 添加物流信息
     * @author 杨益桦
     * @param addLogisticsVo
     * void
     */
    void addLogistics(AddLogisticsVo addLogisticsVo);

    /**
     *
     * @param logisticsId
     */
    void deleteLogistics(String logisticsId);

    /**
     *
     * @param logisticsId
     * @param logisticsNo
     * @param logisticsNoteSnapshot
     * @param senderName
     * @param senderMobile
     * @param logisticsCompany
     * @param deliveryDate
     */
    void updateLogistics(String logisticsId,
                         String logisticsNo,
                         String logisticsNoteSnapshot,
                         String senderName,
                         String senderMobile,
                         String logisticsCompany,
                         Date deliveryDate,String createUserId,
                         String createUserName);

}
