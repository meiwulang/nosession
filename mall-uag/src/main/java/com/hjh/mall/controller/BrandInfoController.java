package com.hjh.mall.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.BizBrandInfoService;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAdd;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppDisplay;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppVo;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoQuery;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoUpdate;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoVo;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQueryById;
import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.service.OperatorService;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.util.BusiSessionHelper;

import io.swagger.annotations.ApiOperation;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
@Controller
public class BrandInfoController {
	@Reference(version = "1.0.0")
	private BizBrandInfoService bizBrandInfoService;
	@Resource
	private UploadService uploadService;
	@Resource
	private CacheHelper cacheHelper;
	@Reference(version = "1.0.0")
	private BizGoodsBrandService bizGoodsBrandService;
	@Resource
	private BusiSessionHelper busiSessionHelper;
	@Resource
	private OperatorService operatorService;
	
	/** 
	 * @date 2017年5月9日
	 * @Description: 查询品牌介绍 后台使用
	 * @param BrandInfoQuery
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "查询品牌介绍", notes = "品牌信息介绍实体BrandInfo对象添加")
	@RequestMapping(value="/brandinfo/query", method = RequestMethod.POST)
	public Map<String, Object> query(@RequestBody BrandInfoQuery hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		List<BrandInfoVo> list = bizBrandInfoService.queryBrandInfoByParameter(hjyvo);
		for (BrandInfoVo brandInfoVo : list) {
			//获取品牌名称
			GoodsBrandQueryById id = new GoodsBrandQueryById(brandInfoVo.getBrand_id());
			Map<String,Object> resultmap=bizGoodsBrandService.findGoodsBrandById(id);
			if(BasicFields.SUCCESS.equals(resultmap.get(BasicFields.ERROR_NO))){
				Map goodbrand=JSONUtil.trans(resultmap.get(BasicFields.RESULT_LIST), Map.class);
				brandInfoVo.setBrand_name((String)goodbrand.get(MallFields.BRAND_NAME));
			}
			
			//获取添加者 信息
			Operator creater_user = operatorService.get(brandInfoVo.getCreate_user());
			
			//获取修改者 信息
			Operator update_user = operatorService.get(brandInfoVo.getUpdate_user());
			
			brandInfoVo.setCreate_user(creater_user.getOperator_name());
			
			brandInfoVo.setUpdate_user(update_user.getOperator_name());
			
		}
		//TODO 修改分页返回类，数量直接传回（改造项）
		result.put(BasicFields.TOTAL_NUM, bizBrandInfoService.queryBrandInfoCountByParameter(hjyvo));
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	@NoLogin
	@ResponseBody
	@ApiOperation(value = "通过id查询品牌介绍", notes = "通过id查找品牌介绍")
	@RequestMapping(value="/brandinfo/brand_info/{brand_info_id}", method = RequestMethod.POST)
	public Map<String, Object> queryById(@PathVariable String brand_info_id,@RequestBody HJYVO vo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		BrandInfoVo entity=bizBrandInfoService.findBrandInfoById(brand_info_id);
		
		
		
			GoodsBrandQueryById id = new GoodsBrandQueryById(entity.getBrand_id());
			Map<String,Object> resultmap=bizGoodsBrandService.findGoodsBrandById(id);
			if(BasicFields.SUCCESS.equals(resultmap.get(BasicFields.ERROR_NO))){
				Map map=JSONUtil.trans(resultmap.get(BasicFields.RESULT_LIST), Map.class);
				entity.setBrand_name((String)map.get(MallFields.BRAND_NAME));
			}		
		
			//获取添加者 信息
			Operator creater_user = operatorService.get(entity.getCreate_user());
			
			//获取修改者 信息
			Operator update_user = operatorService.get(entity.getUpdate_user());
			if(creater_user!=null){
				entity.setCreate_user_name(creater_user.getOperator_name());
			}
			if(update_user!=null){
				entity.setUpdate_user_name(update_user.getOperator_name());
			}
			
		
		result.put(BasicFields.RESULT_LIST, entity);
		return result;
	}

	/** 
	 * @date 2017年5月9日
	 * @Description: 创建品牌介绍
	 * @param BrandInfoAdd hjyvo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	//@RequestMapping("/add")
	@ApiOperation(value = "创建品牌介绍", notes = "品牌信息介绍实体BrandInfo对象添加")
	//@ApiImplicitParam(name = "brand_id", value = "品牌信息介绍实体BrandInfo", required = true, dataType = "hjyvo")
    @RequestMapping(value = "/brandinfo/add", method = RequestMethod.POST)  
	public Map<String, Object> saveBrandInfo(@RequestBody BrandInfoAdd hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		//String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		if(!StringUtil.isBlank(hjyvo.getBrand_info_img())){
			hjyvo.setBrand_info_img(uploadService.uploadImg(MallFields.BRAND_INFO, hjyvo.getBrand_info_img()));
		}
		if(!StringUtil.isBlank(hjyvo.getBrand_info_content())){
			hjyvo.setOss_url(uploadService.uploadHtmlByModel(MallFields.BRAND_INFO, hjyvo.getBrand_info_content()));
		}
		
		//hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		bizBrandInfoService.addBrandInfo(hjyvo);
		return result;
	}
	/** 
	 * @date 2017年5月9日
	 * @Description: 修改品牌修改
	 * @param BrandInfoUpdate
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "修改品牌介绍", notes = "通过id修改品牌信息介绍实体")
	@RequestMapping(value="/brandinfo/update",method = RequestMethod.POST)
	public Map<String, Object> updateBrandInfo(@RequestBody BrandInfoUpdate hjyvo) {
		//String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		if(!StringUtil.isBlank(hjyvo.getBrand_info_img())){
			hjyvo.setBrand_info_img(uploadService.uploadImg(MallFields.BRAND_INFO, hjyvo.getBrand_info_img()));
		}
		if(!StringUtil.isBlank(hjyvo.getBrand_info_content())){
			hjyvo.setOss_url(uploadService.uploadHtmlByModel(MallFields.BRAND_INFO, hjyvo.getBrand_info_content()));
		}
		//hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizBrandInfoService.updateBrandInfo(hjyvo);
	}
	
	/** 
	 * @date 2017年5月9日
	 * @Description: 修改品牌修改
	 * @param BrandInfoUpdate
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "上架下架品牌介绍", notes = "上架下架品牌介绍")
	@RequestMapping(value="/brandinfo/updateappdisplay",method = RequestMethod.POST)
	public Map<String, Object> updateBrandInfoAppDisplay(@RequestBody BrandInfoAppDisplay hjyvo) {
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		
		//hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizBrandInfoService.updateBrandInfoAppDisplay(hjyvo);
	}
	/** 
	 * @date 2017年5月9日
	 * @Description: app列表
	 * @param BrandInfoQuery
	 * @return
	 * Map<String,Object>
	 */
	@NoLogin
	@ResponseBody
	@ApiOperation(value = "app获取品牌列表", notes = "app查询，参数暂时用空{}")
	@RequestMapping(value="/json/brandinfo/queryapp", method = RequestMethod.POST)
	public Map<String, Object> queryApp(@RequestBody BrandInfoQuery hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		hjyvo.setApp_display(Status.ENABLED.getVal());
		List<BrandInfoAppVo> list = bizBrandInfoService.queryBrandInfoByParameterApp(hjyvo);
		for (BrandInfoAppVo brandInfoVo : list) {
			GoodsBrandQueryById id = new GoodsBrandQueryById(brandInfoVo.getBrand_id());
			Map<String,Object> resultmap=bizGoodsBrandService.findGoodsBrandById(id);
			if(BasicFields.SUCCESS.equals(resultmap.get(BasicFields.ERROR_NO))){
				Map map=JSONUtil.trans(resultmap.get(BasicFields.RESULT_LIST), Map.class);
				brandInfoVo.setBrand_name((String)map.get(MallFields.BRAND_NAME));
			}
		}
		//TODO 修改分页返回类，数量直接传回（改造项）
		result.put(BasicFields.TOTAL_NUM, bizBrandInfoService.queryBrandInfoCountByParameterApp(hjyvo));
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	
	/** 
	 * @date 2017年5月9日
	 * @Description: app列表
	 * @param BrandInfoQuery
	 * @return
	 * Map<String,Object>
	 */
	@NoLogin
	@ResponseBody
	@ApiOperation(value = "app首页获取品牌列表", notes = "app查询，参数暂时用空{}")
	@RequestMapping(value="/json/brandinfo/queryappindex", method = RequestMethod.POST)
	public Map<String, Object> queryAppIndex(@RequestBody BrandInfoQuery hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		hjyvo.setLimit(7);
		hjyvo.setPage(0);
		hjyvo.setApp_display(Status.ENABLED.getVal());
		List<BrandInfoAppVo> list = bizBrandInfoService.queryBrandInfoByParameterApp(hjyvo);
		for (BrandInfoAppVo brandInfoVo : list) {
			GoodsBrandQueryById id = new GoodsBrandQueryById(brandInfoVo.getBrand_id());
			Map<String,Object> resultmap=bizGoodsBrandService.findGoodsBrandById(id);
			if(BasicFields.SUCCESS.equals(resultmap.get(BasicFields.ERROR_NO))){
				Map map=JSONUtil.trans(resultmap.get(BasicFields.RESULT_LIST), Map.class);
				brandInfoVo.setBrand_name((String)map.get(MallFields.BRAND_NAME));
			}
		}
		//TODO 修改分页返回类，数量直接传回（改造项）
		result.put(BasicFields.TOTAL_NUM, bizBrandInfoService.queryBrandInfoCountByParameterApp(hjyvo));
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	
}
