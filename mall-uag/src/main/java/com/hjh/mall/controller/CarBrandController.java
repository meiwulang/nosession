package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.category.bizapi.bizserver.car.BizCarModelService;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModels;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryIdsByMetadataOrBrand;
import com.hjh.mall.category.bizapi.bizserver.carbrand.BizCarBrandService;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandAdd;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandDelete;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandSort;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandStatus;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandUpdate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.ExcelUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.SortType;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.model.BrandModel;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.service.impl.CarModelServiceImp;


/**
 * @Project: mall-web
 * @Description 车辆品牌controller
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
@RequestMapping("/carbrand")
@Controller
public class CarBrandController {
	@Reference(version = "1.0.0")
	private BizCarBrandService bizCarBrandService;
	@Resource
	private UploadService uploadService;
	@Reference(version = "1.0.0")
	private BizCarModelService bizCarModelService;

	@Resource
	private CarModelServiceImp carModelServiceImp;

	@Resource
	private CacheHelper cacheHelper;
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;


	
	
	/** 
	 * @date 2017年3月16日
	 * @Description: 查找条件的数量
	 * @param queryAllBrandVo
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/queryexist",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBrandExist(@RequestBody CarBrandQuery queryAllBrandVo) {
		queryAllBrandVo.blankStringToNull();
		boolean isSortExist = bizCarBrandService.isSortExist(queryAllBrandVo);
		Map<String, Object> result = VOUtil.genSuccessResult();
		if(isSortExist){
			result.put(MallFields.EXIST,1);
		}else{
			result.put(MallFields.EXIST,0);
		}
		
		return result;
	}
	
	
	/** 
	 * @date 2017年3月16日
	 * @Description: sort 排序 首页 使用
	 * @param queryAllBrandVo
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllBrand(@RequestBody CarBrandQuery queryAllBrandVo) {
		queryAllBrandVo.blankStringToNull();
		Map<String, Object> resultcb = bizCarBrandService.queryAllCarBrand(queryAllBrandVo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb = (List<Map>) resultcb.get(BasicFields.RESULT_LIST);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryCarModels qm = new QueryCarModels();
				qm.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizCarModelService.countCarModellike(qm);
				int model_num = (int) countresult.get(BasicFields.TOTAL_NUM);
				map.put("model_num", model_num);
				listcbr.add(map);
			}
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, listcbr);
		result.put(MallFields.TOTAL_NUM, resultcb.get(BasicFields.TOTAL_NUM));
		return result;
	}
	
	@RequestMapping(value="/queryforweb",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllBrandForWeb(@RequestBody CarBrandQuery queryAllBrandVo) {
	
		queryAllBrandVo.blankStringToNull();
		Map<String, Object> result = VOUtil.genSuccessResult();
		Map<String, Object> resultcb = bizCarBrandService.queryCarBrandByParams(queryAllBrandVo);
		List<Map> listcb = (List<Map>) resultcb.get(BasicFields.RESULT_LIST);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			result.put(BasicFields.RESULT_LIST, listcb);
		}
		result.put(MallFields.TOTAL_NUM, resultcb.get(BasicFields.TOTAL_NUM));
		return result;

	}

	/**
	 * @Description:保存品牌 101402
	 * @author 李慧峰
	 * @date 2016年8月29日
	 * @param saveBrandVo
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public Map<String, Object> saveBrand(@RequestBody CarBrandAdd saveBrandVo) {
		String img_path = null;
		if(!StringUtil.isBlank(saveBrandVo.getBrand_logo())){
			img_path=uploadService.uploadImg("carbrand", saveBrandVo.getBrand_logo());
		}
		saveBrandVo.setBrand_logo(img_path);
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		saveBrandVo.setUpdate_user_name(operatorname);
		saveBrandVo.setUpdate_user(operatorid);
		return bizCarBrandService.addCarBrand(saveBrandVo);

	}

	/**
	 * @Description: 删除品牌101403
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param delBrandVo
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Map<String, Object> deleteBrand(@RequestBody CarBrandDelete delBrandVo) {
		return bizCarBrandService.deleteCarBrand(delBrandVo);

	}


	/** 
	 * @date 2017年3月15日
	 * @Description: 品牌信息修改
	 * @param modifyBrandVo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update",method=RequestMethod.POST)
	public Map<String, Object> updateBrand(@RequestBody CarBrandUpdate modifyBrandVo) {
		Map<String, Object> map = bizCarBrandService.findCarBrandById(new CarBrandQueryById(modifyBrandVo.getBrand_id()));
		String brand_name_new = modifyBrandVo.getBrand_name();
		String brand_name_old=null;
		if("0".equals(map.get(BasicFields.ERROR_NO))){
			Map<String,Object> carBrandMap = JSONUtil.trans(map.get(BasicFields.RESULT_LIST),Map.class);
			brand_name_old = (String) carBrandMap.get(MallFields.BRAND_NAME);
		}
		
		
		if(!StringUtil.isBlank(modifyBrandVo.getBrand_logo())){
			String img_path=uploadService.uploadImg("carbrand", modifyBrandVo.getBrand_logo());
			modifyBrandVo.setBrand_logo(img_path);
		}
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		modifyBrandVo.setUpdate_user_name(operatorname);
		modifyBrandVo.setUpdate_user(operatorid);
		if(StringUtil.isBlank(modifyBrandVo.getBrand_logo())){
			modifyBrandVo.setBrand_logo(null);
		}
		Map<String,Object> result= bizCarBrandService.updateCarBrand(modifyBrandVo);
		if("0".equals(result.get(BasicFields.ERROR_NO))){//如果执行成功，并且品牌名称改变，则更新商品
			if(!StringUtil.isBlank(brand_name_new) && !StringUtil.isBlank(brand_name_old)){
				if(!brand_name_old.equals(brand_name_new)){

					QueryIdsByMetadataOrBrand queryIdsByMetadataOrBrand = new QueryIdsByMetadataOrBrand();
					queryIdsByMetadataOrBrand.setBrand_id(modifyBrandVo.getBrand_id());
					List<String> carIds = bizCarModelService.getIdsByMetadataOrBrand(queryIdsByMetadataOrBrand);
					if (carIds != null && carIds.size() > 0) {

						// 更新机型相关缓存
						for (String id : carIds) {
							Map<String, String> map1 = cacheHelper.hgetAll(CacheKeys.CAR_MODELS_ID_PREFIX + id);
							map1.put(MallFields.CAR_BRAND_NAME, modifyBrandVo.getBrand_name());
							cacheHelper.hmset(CacheKeys.CAR_MODELS_ID_PREFIX + id, map1);
						}
						
						List<String> goodIds = bizGoodsCarModelService.queryGoodsIdsByCarIds(carIds);
						if (goodIds != null && goodIds.size() > 0) {
							MyThread mythread = new MyThread();
							mythread.setIds(goodIds);
							mythread.run();
						}

					}

				}
			}
		}
		
		return result;
	}
	
	/** 
	 * @date 2017年3月15日
	 * @Description: 修改排序
	 * @param CarBrandSort
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update_sort",method=RequestMethod.POST)
	public Map<String, Object> updateBrandSort(@RequestBody CarBrandSort hjyvo) {
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizCarBrandService.updateGoodsBrandSort(hjyvo);
	}
	
	/** 
	 * @date 2017年3月15日
	 * @Description: 修改状态
	 * @param CarBrandStatus
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update_status",method=RequestMethod.POST)
	public Map<String, Object> updateBrandStatus(@RequestBody CarBrandStatus hjyvo) {
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizCarBrandService.updateGoodsBrandStatus(hjyvo);
	}

	/**
	 * @Description: 获取所有品牌模糊查询，拼音 排序 首页 使用
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="queryblur",method=RequestMethod.POST)
	public Map<String, Object> getAllBrand(@RequestBody CarBrandQuery vo) {
		vo.blankStringToNull();
		vo.setSortType(SortType.UPDATETIMEDESC.getVal());
		Map<String, Object> resultcb = bizCarBrandService.queryCarBrandByParams(vo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb =  JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryCarModels qm = new QueryCarModels();
				qm.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizCarModelService.countCarModellike(qm);
				int model_num = (int) countresult.get(BasicFields.TOTAL_NUM);
				map.put("model_num", model_num);
				listcbr.add(map);
			}
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, listcbr);
		result.put(MallFields.TOTAL_NUM, resultcb.get(BasicFields.TOTAL_NUM));
		return result;
		
	}
	
	/** 
	 * @date 2017年3月15日
	 * @Description: 批量状态修改
	 * @param GoodsBrandStatus
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/batchstatus",method=RequestMethod.POST)
	public Map<String, Object> batchstatus(@RequestBody CarBrandStatus hjyvo) {
		
		bizCarBrandService.batchStatus(hjyvo);
		/*String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);*/
		return VOUtil.genSuccessResult();
	}
	
	/**
	 * @Description: 导出查询出来的数据
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value="export",method=RequestMethod.GET)
	public void importBrand(CarBrandQuery vo,HttpServletResponse response) {
		vo.blankStringToNull();
		Map<String, Object> resultcb = bizCarBrandService.queryCarBrandByParams(vo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb = JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryCarModels qm = new QueryCarModels();
				qm.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizCarModelService.countCarModellike(qm);
				int model_num = (int) countresult.get(BasicFields.TOTAL_NUM);
				map.put("model_num", model_num);
				listcbr.add(map);
			}
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, listcbr);
		result.put(MallFields.TOTAL_NUM, resultcb.get(BasicFields.TOTAL_NUM));
		try {
			exportExcel( listcbr,response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/** 
	 * @date 2017年3月18日
	 * @Description: 对象绑定输出流 ，输出
	 * @param list
	 * @param response
	 * @throws IOException
	 * void
	 */
	private void exportExcel( List<Map> list,HttpServletResponse response) throws IOException {
		// 生成提示信息，  
	    response.setContentType("application/vnd.ms-excel");
	    //生成文件名
	    String codedFileName = "车辆品牌导出"+System.currentTimeMillis();
	    codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");  
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
        OutputStream fOut = response.getOutputStream();
        ExcelUtil eu = ExcelUtil.getInstance();
        eu.exportObj2Excel(fOut, list, BrandModel.class, true);
        fOut.flush();  
        fOut.close();    
	}
	
	public class MyThread implements Runnable {

		private List<String> ids;

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		@Override
		public void run() {
			GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
			goodsTimerVo.setIds(ids);
			carModelServiceImp.UpdateSolrVo(goodsTimerVo);

		}

	}

}
