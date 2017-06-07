package com.hjh.mall.category.bizapi.bizserver.carbrand;

import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandSort;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandStatus;
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
public interface BizCarBrandService {
	@BizService(functionId = "610301", name = "查询全部车辆品牌", desc = "查询全部品牌")
	public Map<String, Object> queryAllCarBrand(CarBrandQuery hjyvo);

	@BizService(functionId = "610302", name = "按条件查询车辆品牌", desc = "按条件查询品牌")
	public Map<String, Object> queryCarBrandByParams(CarBrandQuery hjyvo);

	@BizService(functionId = "610303", name = "添加车辆品牌", desc = "添加品牌")
	public Map<String, Object> addCarBrand(HJYVO hjyvo) throws HJHBCSErrInfoException;

	@BizService(functionId = "610304", name = "更新车辆品牌", desc = "更新品牌")
	public Map<String, Object> updateCarBrand(HJYVO hjyvo) throws HJHBCSErrInfoException;
	
	@BizService(functionId = "610305", name = "更新车辆品牌状态", desc = "更新品牌状态")
	public Map<String, Object> updateGoodsBrandStatus(CarBrandStatus hjyvo);
	
	@BizService(functionId = "610306", name = "更新车辆品牌排序", desc = "更新品牌状态")
	public Map<String, Object> updateGoodsBrandSort(CarBrandSort hjyvo);

	@BizService(functionId = "610307", name = "删除车辆品牌", desc = "删除品牌")
	public Map<String, Object> deleteCarBrand(HJYVO hjyvo);
	
	@BizService(functionId = "610308", name = "通过id查找车辆品牌", desc = "通过id查找车辆品牌")
	public Map<String, Object> findCarBrandById(CarBrandQueryById hjyvo);
	@BizService(functionId = "610309", name = "批量更新车辆品牌状态", desc = "更新品牌状态")
	public int batchStatus(CarBrandStatus hjyvo);
	
	@BizService(functionId = "610310", name = "按条件查询品牌是否重", desc = "按条件查询品牌是否重")
	public boolean isSortExist(CarBrandQuery hjyvo);
	
}
