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
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandAdd;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandDelete;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandSort;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandStatus;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandUpdate;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.ExcelUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.SortType;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.model.BrandModel;
import com.hjh.mall.service.GoodsService;
import com.hjh.mall.service.UploadService;


/**
 * @Project: mall-web
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
@RequestMapping("/goodsbrand")
@Controller
public class GoodsBrandController {
	@Reference(version = "1.0.0")
	private BizGoodsBrandService bizGoodsBrandService;
	@Resource
	private UploadService uploadService;
	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;
	@Resource
	private GoodsService goodsService;


	
	/** 
	 * @date 2017年3月16日
	 * @Description: 查找条件的数量
	 * @param queryAllBrandVo
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/queryexist",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllBrandCount(@RequestBody GoodsBrandQuery queryAllBrandVo) {
		boolean isSortExist = bizGoodsBrandService.isSortExist(queryAllBrandVo);
		Map<String, Object> result = VOUtil.genSuccessResult();
		if(isSortExist){
			result.put(MallFields.EXIST,1);
		}else{
			result.put(MallFields.EXIST,0);
		}
		
		return result;
	}
	
	@RequestMapping(value="/query",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllBrand(@RequestBody GoodsBrandQuery queryAllBrandVo) {
		queryAllBrandVo.blankStringToNull();
		Map<String, Object> resultcb = bizGoodsBrandService.queryAllGoodsBrand(queryAllBrandVo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb = (List<Map>) resultcb.get(BasicFields.RESULT_LIST);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryGoodsVo queryGoodsVo = new QueryGoodsVo();
				queryGoodsVo.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizGoodsService.queryGoods(queryGoodsVo);
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
	public Map<String, Object> queryAllBrandForWeb(@RequestBody GoodsBrandQuery queryAllBrandVo) {
	
		queryAllBrandVo.blankStringToNull();
		Map<String, Object> result = VOUtil.genSuccessResult();
		Map<String, Object> resultcb = bizGoodsBrandService.queryGoodsBrandByParams(queryAllBrandVo);
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
	public Map<String, Object> saveBrand(@RequestBody GoodsBrandAdd saveBrandVo) {
		String img_path = null;
		if(!StringUtil.isBlank(saveBrandVo.getBrand_logo())){
			img_path=uploadService.uploadImg("goodsbrand", saveBrandVo.getBrand_logo());
		}
		saveBrandVo.setBrand_logo(img_path);
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		saveBrandVo.setUpdate_user_name(operatorname);
		saveBrandVo.setUpdate_user(operatorid);
		return bizGoodsBrandService.addGoodsBrand(saveBrandVo);

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
	public Map<String, Object> deleteBrand(@RequestBody GoodsBrandDelete delBrandVo) {
		return bizGoodsBrandService.deleteGoodsBrand(delBrandVo);

	}


	/** 
	 * @date 2017年3月15日
	 * @Description: TODO
	 * @param modifyBrandVo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update",method=RequestMethod.POST)
	public Map<String, Object> updateBrand(@RequestBody GoodsBrandUpdate modifyBrandVo) {
		Map<String, Object> map = bizGoodsBrandService.findGoodsBrandById(new GoodsBrandQueryById(modifyBrandVo.getBrand_id()));
		String brand_name_new = modifyBrandVo.getBrand_name();
		String brand_name_old=null;
		if("0".equals(map.get(BasicFields.ERROR_NO))){
			@SuppressWarnings("unchecked")
			Map<String,Object> CarBrandMap = JSONUtil.trans(map.get(BasicFields.RESULT_LIST),Map.class);
			brand_name_old = (String) CarBrandMap.get(MallFields.BRAND_NAME);
		}
		
		
		if(!StringUtil.isBlank(modifyBrandVo.getBrand_logo())){
			String img_path=uploadService.uploadImg("goodsbrand", modifyBrandVo.getBrand_logo());
			modifyBrandVo.setBrand_logo(img_path);
		}
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		modifyBrandVo.setUpdate_user_name(operatorname);
		modifyBrandVo.setUpdate_user(operatorid);
		if(StringUtil.isBlank(modifyBrandVo.getBrand_logo())){
			modifyBrandVo.setBrand_logo(null);
		}
		
		
		Map<String,Object> result= bizGoodsBrandService.updateGoodsBrand(modifyBrandVo);
		if("0".equals(result.get(BasicFields.ERROR_NO))){//如果执行成功，并且品牌名称改变，则更新商品
			if(!StringUtil.isBlank(brand_name_new) && !StringUtil.isBlank(brand_name_old)){
				if(!brand_name_old.equals(brand_name_new)){
					GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
					goodsTimerVo.setBrand_id(modifyBrandVo.getBrand_id());
					goodsTimerVo.setBrand_name(brand_name_new);
					goodsService.updateGood(goodsTimerVo);
				}
			}
		}
		
		return result;
	}
	
	/** 
	 * @date 2017年3月15日
	 * @Description: TODO
	 * @param GoodsBrandSort
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update_sort",method=RequestMethod.POST)
	public Map<String, Object> updateBrandSort(@RequestBody GoodsBrandSort hjyvo) {
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizGoodsBrandService.updateGoodsBrandSort(hjyvo);
	}
	
	/** 
	 * @date 2017年3月15日
	 * @Description: TODO
	 * @param GoodsBrandStatus
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/brand_update_status",method=RequestMethod.POST)
	public Map<String, Object> updateBrandStatus(@RequestBody GoodsBrandStatus hjyvo) {
		String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);
		return bizGoodsBrandService.updateGoodsBrandStatus(hjyvo);
	}

	/** 
	 * @date 2017年3月15日
	 * @Description: TODO
	 * @param GoodsBrandStatus
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="/batchstatus",method=RequestMethod.POST)
	public Map<String, Object> batchstatus(@RequestBody GoodsBrandStatus hjyvo) {
		
		bizGoodsBrandService.batchStatus(hjyvo);
		/*String operatorname = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String operatorid = HJYBizDataContext.getSessionIdentity().getClientId();
		hjyvo.setUpdate_user_name(operatorname);
		hjyvo.setUpdate_user(operatorid);*/
		return bizGoodsBrandService.updateGoodsBrandStatus(hjyvo);
	}

	
	
	/**
	 * @Description: 获取所有品牌
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping(value="queryblur",method=RequestMethod.POST)
	public Map<String, Object> getAllBrand(@RequestBody GoodsBrandQuery vo) {
		vo.blankStringToNull();
		vo.setSortType(SortType.UPDATETIMEDESC.getVal());
		Map<String, Object> resultcb = bizGoodsBrandService.queryGoodsBrandByParams(vo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb = JSONUtil.transInSide((List<GoodsBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryGoodsVo queryGoodsVo = new QueryGoodsVo();
				queryGoodsVo.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizGoodsService.queryGoods(queryGoodsVo);
				int model_num = (int) countresult.get(BasicFields.TOTAL_NUM);
				map.put("model_num", model_num);
				listcbr.add(map);
			}
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, listcbr);
		result.put(BasicFields.TOTAL_NUM, resultcb.get(BasicFields.TOTAL_NUM));
		return result;
	}
	/**
	 * @Description: 获取所有品牌
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value="export",method=RequestMethod.GET)
	public void importBrand(GoodsBrandQuery vo,HttpServletResponse response) {
		vo.blankStringToNull();
		Map<String, Object> resultcb = bizGoodsBrandService.queryGoodsBrandByParams(vo);
		List<Map> listcbr=new LinkedList<Map>();
		List<Map> listcb = JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if("0".equals(resultcb.get(BasicFields.ERROR_NO))){
			for (Map map : listcb) {
				QueryGoodsVo queryGoodsVo = new QueryGoodsVo();
				queryGoodsVo.setBrand_id((String) map.get("brand_id"));
				Map<String, Object> countresult = bizGoodsService.queryGoods(queryGoodsVo);
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
	    String codedFileName = "品牌导出"+System.currentTimeMillis();
	    codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");  
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
        OutputStream fOut = response.getOutputStream();
        ExcelUtil eu = ExcelUtil.getInstance();
        eu.exportObj2Excel(fOut, list, BrandModel.class, true);
        fOut.flush();  
        fOut.close();    
	}

}
