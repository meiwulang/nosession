package com.hjh.mall.category.bizapi.bizserver.goodsbrand;

import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandSort;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandStatus;
import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0
 */
public interface BizGoodsBrandService {
	@BizService(functionId = "610301", name = "查询全部商品品牌", desc = "查询全部品牌")
	public Map<String, Object> queryAllGoodsBrand(GoodsBrandQuery hjyvo);

	@BizService(functionId = "610302", name = "按条件查询商品品牌", desc = "按条件查询品牌")
	public Map<String, Object> queryGoodsBrandByParams(GoodsBrandQuery hjyvo);

	@BizService(functionId = "610303", name = "添加商品品牌", desc = "添加品牌")
	public Map<String, Object> addGoodsBrand(HJYVO hjyvo) throws HJHBCSErrInfoException;

	@BizService(functionId = "610304", name = "更新商品品牌", desc = "更新品牌")
	public Map<String, Object> updateGoodsBrand(HJYVO hjyvo) throws HJHBCSErrInfoException;

	@BizService(functionId = "610305", name = "更新商品品牌状态", desc = "更新品牌状态")
	public Map<String, Object> updateGoodsBrandStatus(GoodsBrandStatus hjyvo);

	@BizService(functionId = "610306", name = "更新商品品牌排序", desc = "更新品牌排序")
	public Map<String, Object> updateGoodsBrandSort(GoodsBrandSort hjyvo);

	@BizService(functionId = "610307", name = "删除商品品牌", desc = "删除品牌")
	public Map<String, Object> deleteGoodsBrand(HJYVO hjyvo);

	@BizService(functionId = "610308", name = "通过id查找商品品牌", desc = "通过id查找商品品牌")
	public Map<String, Object> findGoodsBrandById(GoodsBrandQueryById hjyvo);
	
	@BizService(functionId = "610305", name = "批量更新商品品牌状态", desc = "批量更新品牌状态")
	public int batchStatus(GoodsBrandStatus hjyvo);
	
	@BizService(functionId = "610310", name = "按条件查询品牌是否重", desc = "按条件查询品牌是否重")
	public boolean isSortExist(GoodsBrandQuery hjyvo);

}
