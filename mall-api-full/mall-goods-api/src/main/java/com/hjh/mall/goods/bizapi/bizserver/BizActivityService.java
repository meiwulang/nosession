package com.hjh.mall.goods.bizapi.bizserver;

import java.util.Map;

import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.restfulapi.RestFulAPI;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsForActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivityDetailVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoods4ActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateActivityVo;

/**
 * @Project: mall-goods-api-bran
 * @Description 活动服务
 * @author 王斌
 * @date 2017年4月28日
 * @version V1.0
 */
public interface BizActivityService {

	@BizService(functionId = RestFulAPI.Activity.ADD_ACTIVITY, name = "添加活动")
	Map<String, Object> addActivity(AddActivityVo vo);

	@BizService(functionId = RestFulAPI.Activity.QUERY_ACTIVITYS_FOR_WEB, name = "查询活动列表")
	Map<String, Object> queryActivitysForWeb(PageQueryVO vo);

	@BizService(functionId = RestFulAPI.Activity.QUERY_ACTIVITYS_FOR_APP, name = "app查询活动列表")
	Map<String, Object> queryActivitysForApp(HJYVO vo);

	@BizService(functionId = RestFulAPI.Activity.UPDATE_ACTIVITY, name = "更新活动")
	Map<String, Object> updateActivity(UpdateActivityVo vo);

	@BizService(functionId = RestFulAPI.Activity.ADD_GOODS_FOR_ACTIVITY, name = "活动添加商品")
	Map<String, Object> addGoodsForActivity(AddGoodsForActivityVo vo);

	@BizService(functionId = RestFulAPI.Activity.DEL_ACTIVITYS_GOODS, name = "删除下架商品与活动关联关系")
	Map<String, Object> delActivitysGoods(DelActivitysGoodsVo vo);

	@BizService(functionId = RestFulAPI.Activity.QUERY_ACTIVITYS_GOODS, name = "web分页查询指定活动的商品")
	Map<String, Object> queryActivitysGoods(QueryActivitysGoodsVo vo);

	@BizService(functionId = RestFulAPI.Activity.QUERY_ACTIVITYS_DETAIL, name = "app查询活动详情")
	Map<String, Object> queryActivitysDetail(QueryActivityDetailVo vo);

	@BizService(functionId = RestFulAPI.Activity.GET_GOODS_WITHOUT_ACTIVITY, name = "web查询未关联活动的商品")
	Map<String, Object> queryGoods(QueryGoods4ActivityVo queryGoodsVo);
}
