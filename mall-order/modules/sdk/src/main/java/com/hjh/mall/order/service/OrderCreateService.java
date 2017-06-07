package com.hjh.mall.order.service;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.order.vo.CreateOrderAddressVo;
import com.hjh.mall.order.vo.CreateOrderUserVo;
import com.hjh.mall.order.vo.CreateOrderVo;

import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/15.
 */
public interface OrderCreateService {


    /**
     * 商品下单接口,默认商品列表组成一个单,如果商品列表存在分单,则抛出异常
     *
     * 业务逻辑实现:
     *     0. 检查所有商品是否可购买(商品是否下架、库存、限购等),否则抛出异常
     *     1. 检查商品列表是否可分一个单, 否则抛出异常
     *     2. 其他
     *     3. 订单入库(T_ORDER & T_ORDER_ITEM )
     *          1) 查询商品信息
     *          2) 计算金额
     *          3) 其他
     *     4. 返回订单ID
     *
     * @param productCompoundInfo       商品下单的组合信息, 其构成为:  productId_规格Id_数量
     *                                         1. 以下划线划分
     *                                         //2. 如果规格不存在,则以字符串 NULL代替。 如:  34_NULL_89
     *                                            表示   购买 商品ID=34 规格Id 的商品 89 个
     * @param deliveryAddressId         收获地址ID
     * @param consigneeAddress,         收获地址
     * @param buyerComments             买家订单留言
     * @param userId                    创建订单的用户ID
     * @return                          订单ID
     */

    public String createSingleOrder(
            CreateOrderVo createOrderVo,
            CreateOrderAddressVo createOrderAddressVo,
            CreateOrderUserVo createOrderUserVo
    );

}
