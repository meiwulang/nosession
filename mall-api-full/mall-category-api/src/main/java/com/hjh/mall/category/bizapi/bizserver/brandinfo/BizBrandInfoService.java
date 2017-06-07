package com.hjh.mall.category.bizapi.bizserver.brandinfo;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAdd;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppDisplay;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppVo;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoQuery;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoStatus;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoUpdate;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoVo;
import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;

/**
 * @Project: mall-category-api
 * @Description 品牌详情
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public interface BizBrandInfoService {
	//后台管理 
	@BizService(functionId = "610301", name = "按条件查品牌信息列表", desc = "按条件查品牌信息列表")
	public List<BrandInfoVo> queryBrandInfoByParameter(BrandInfoQuery hjyvo);
	
	//后台管理 
	@BizService(functionId = "610302", name = "按条件查品牌信息数量", desc = "按条件查品牌信息数量")
	public Integer queryBrandInfoCountByParameter(BrandInfoQuery hjyvo);


	@BizService(functionId = "610303", name = "添加品牌信息", desc = "添加品牌信息")
	public Map<String, Object> addBrandInfo(BrandInfoAdd hjyvo) throws HJHBCSErrInfoException;

	@BizService(functionId = "610304", name = "更新品牌信息", desc = "更新品牌信息")
	public Map<String, Object> updateBrandInfo(BrandInfoUpdate hjyvo) throws HJHBCSErrInfoException;
	
	@BizService(functionId = "610305", name = "品牌信息上架和下架", desc = "品牌信息上架和下架")
	public Map<String, Object> updateBrandInfoAppDisplay(BrandInfoAppDisplay hjyvo);
	
	@BizService(functionId = "610306", name = "修改品牌信息启用禁用", desc = "修改品牌信息启用禁用")
	public Map<String, Object> updateBrandInfoStatus(BrandInfoStatus hjyvo);
	
	@BizService(functionId = "610307", name = "通过id 查看详情", desc = "通过id 查看详情")
	public BrandInfoVo findBrandInfoById(String id);
	
	@BizService(functionId = "610308", name = "app展示品牌信息列表", desc = "app展示品牌信息列表")
	public List<BrandInfoAppVo> queryBrandInfoByParameterApp(BrandInfoQuery hjyvo);
	
	@BizService(functionId = "610309", name = "app展示品牌信息数量", desc = "app展示品牌信息数量")
	public Integer queryBrandInfoCountByParameterApp(BrandInfoQuery hjyvo);
	

	
	
}
