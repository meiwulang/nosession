package com.hjh.mall.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.category.bizapi.bizserver.carbrand.BizCarBrandService;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQuery;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.dto.Brand;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.restfulapi.RestFulAPI;

/**
 * @Project: mall-web
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月20日
 * @version V1.0
 */
@NoLogin
@Controller
public class AppBrandController {
	@Reference(version = "1.0.0")
	private BizCarBrandService bizCarBrandService;

	@Reference(version = "1.0.0")
	private BizGoodsBrandService bizGoodsBrandService;

	@Resource
	private CacheHelper cacherHelper;
	
	/**
	 * @Description: 获取车辆首页显示品牌，拼音排序
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.CarBrand.CAR_BRAND_FIRST_PAGE_LIST,method=RequestMethod.POST)
	public Map<String, Object> getFirstPageCarBrand(@RequestBody CarBrandQuery vo) {
		//String isCache = cacherHelper.get("carbrandapppinyin:list:lock");
		List<Brand> list = null;
		if (false) {
			list = cacherHelper.getList("carbrandfirst:list");
		} else {
			list = getAllAppCarbrand();
			// cacherHelper.setList("carbrandfirst:list", list, -1);
			// cacherHelper.set("carbrandfirst:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}

	/**
	 * @Description: 获取车辆所有900902
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.CarBrand.CAR_ALL_BRAND_LIST,method=RequestMethod.POST)
	public Map<String, Object> getAllCarBrandPinYinAsc(@RequestBody CarBrandQuery vo) {
		String isCache = cacherHelper.get("carbrand:list:lock");
		List<Brand> list = null;
		if (false) {
			list = cacherHelper.getList("carbrand:list");
		} else {
			list = getAllCarbrand();
			// cacherHelper.setList("carbrand:list", list, -1);
			// cacherHelper.set("carbrand:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}
	
	/**
	 * @Description: 获取车辆首页显示品牌，排序字段排序 ，900903
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.CarBrand.CAR_BRAND_APP_SORT_LIST,method=RequestMethod.POST)
	public Map<String, Object> getAppCarBrand(@RequestBody CarBrandQuery vo) {
		//String isCache = cacherHelper.get("carbrandappsort:list:lock");
		List<Brand> list = null;
		if (false) {
			list = cacherHelper.getList("carbrandfirst:list");
		} else {
			list = getFirstPageCarbrand();
			// cacherHelper.setList("carbrandfirst:list", list, -1);
			// cacherHelper.set("carbrandfirst:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}

	/**
	 * @Description: 获取首页品牌，排序字段 排序 使用/json/901001
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.GoodsBrand.GOODS_BRAND_FIRST_PAGE_LIST,method=RequestMethod.POST)
	public Map<String, Object> getFirstGoodsPageBrand(@RequestBody HJYVO vo) {
		//String isCache = cacherHelper.get("goodsbrandfirst:list:lock");
		List<Brand> list = null;
		if (false) {
			list = cacherHelper.getList("goodsbrandapppinyin:list");
		} else {
			list = getAPPGoodsbrand();
			// cacherHelper.setList("goodsbrandfirst:list", list, -1);
			// cacherHelper.set("goodsbrandfirst:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}

	/**
	 * @Description: 获取所有品牌，拼音 排序 使用/json/901002
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.GoodsBrand.GOODS_ALL_BRAND_LIST,method=RequestMethod.POST)
	public Map<String, Object> getAllGoodsBrandPinYinAsc(@RequestBody HJYVO vo) {
		//String isCache = cacherHelper.get("goodsbrand:list:lock");
		List<Brand> list = null;
		if (false) {
			list = cacherHelper.getList("goodsbrand:list");
		} else {
			list = getAllGoodsbrand();
			// cacherHelper.setList("goodsbrand:list", list, -1);
			// cacherHelper.set("goodsbrand:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}
	/**
	 * @Description: 获取车辆首页显示品牌，SORT排序900903
	 * @author 李慧峰
	 * @date 2017年3月15日
	 * @param modifyBrandVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value=RestFulAPI.GoodsBrand.GOODS_BRAND_APP_SORT_LIST,method=RequestMethod.POST)
	public Map<String, Object> getAppSortCarBrand(@RequestBody CarBrandQuery vo) {
		//String isCache = cacherHelper.get("goodbrandappsort:list:lock");
		List<Brand> list = null;
		if (false) {
			//list = cacherHelper.getList("carbrandfirst:list");
		} else {
			list = getFirstPageGoodsbrand();
			// cacherHelper.setList("carbrandfirst:list", list, -1);
			// cacherHelper.set("carbrandfirst:list:lock", "y", -1);
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;

	}



	// 所有车辆App品牌,排序字段排序
	private List<Brand> getFirstPageCarbrand() {
		CarBrandQuery qb = new CarBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setIs_top("1");
		qb.setLimit(10000);
		qb.setPage(0);
		Map<String, Object> resultcb = bizCarBrandService.queryAllCarBrand(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}
				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}

	// 所有车辆品牌
	private List<Brand> getAllCarbrand() {
		CarBrandQuery qb = new CarBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setLimit(10000);
		qb.setPage(0);
		Map<String, Object> resultcb = bizCarBrandService.queryCarBrandByParams(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}

				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}
	
	// App车辆品牌，拼音排序
	private List<Brand> getAllAppCarbrand() {
		CarBrandQuery qb = new CarBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setLimit(10000);
		qb.setPage(0);
		qb.setIs_top("1");
		Map<String, Object> resultcb = bizCarBrandService.queryCarBrandByParams(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<CarBrand>)resultcb.get(BasicFields.RESULT_LIST), Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}

				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}

	// 首页排序，排序字段排序
	private List<Brand> getFirstPageGoodsbrand() {
		GoodsBrandQuery qb = new GoodsBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setIs_top("1");
		qb.setLimit(10000);
		qb.setPage(0);
		Map<String, Object> resultcb = bizGoodsBrandService.queryAllGoodsBrand(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<GoodsBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}
				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}

	// 所有商品品牌 拼音排序
	private List<Brand> getAllGoodsbrand() {
		GoodsBrandQuery qb = new GoodsBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setLimit(10000);
		qb.setPage(0);
		Map<String, Object> resultcb = bizGoodsBrandService.queryGoodsBrandByParams(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<GoodsBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}

				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}
	// 所有app商品品牌，拼音排序
	private List<Brand> getAPPGoodsbrand() {
		GoodsBrandQuery qb = new GoodsBrandQuery();
		//qb.setStatus(Status.ENABLED.getVal());
		qb.setLimit(10000);
		qb.setIs_top("1");
		qb.setPage(0);
		Map<String, Object> resultcb = bizGoodsBrandService.queryGoodsBrandByParams(qb);
		List<Brand> listcbr = new LinkedList<Brand>();
		List<Map> listcb = JSONUtil.transInSide((List<GoodsBrand>)resultcb.get(BasicFields.RESULT_LIST),Map.class);
		if ("0".equals(resultcb.get(BasicFields.ERROR_NO))) {
			for (Map map : listcb) {
				Brand brand = new Brand();
				String pinyin = (String) map.get("pinyin");
				if (!StringUtil.isBlank(pinyin)) {
					brand.setPinyin(pinyin);
					brand.setPinyin_first(pinyin.substring(0, 1).toUpperCase());
				}

				brand.setBrand_id((String) map.get(MallFields.BRAND_ID));
				brand.setBrand_name((String) map.get(MallFields.BRAND_NAME));
				brand.setBrand_logo((String) map.get(MallFields.BRAND_LOGO));
				listcbr.add(brand);
			}
		}
		return listcbr;
	}

}
