package com.hjh.mall.category.bizImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;




import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.carbrand.BizCarBrandService;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandSort;
import com.hjh.mall.category.bizapi.bizserver.carbrand.vo.CarBrandStatus;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.service.CarBrandService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.PinyinUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.SortType;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0
 */
@Component
public class BizCarBrandServiceImpl implements BizCarBrandService {
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private KeyGenerate keyGenerate;

	@Resource
	private CacheHelper cacheHelper;

	@Override
	public Map<String, Object> queryAllCarBrand(CarBrandQuery hjyvo) {
		CarBrand entity = new CarBrand();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!StringUtil.isBlank(hjyvo.getStatus())) {
			entity.setStatus(hjyvo.getStatus());
		}
		int total = carBrandService.count(entity);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		entity.setPage(page);
		SortMarker sortmarker = new SortMarker(MallFields.SORT, true);
		entity.addSortMarker(sortmarker);

		List<CarBrand> list = carBrandService.query(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> queryCarBrandByParams(CarBrandQuery hjyvo) {
		Map entity = JSONUtil.trans(hjyvo, Map.class);
		int total = carBrandService.countBlur(entity);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		entity.put("page", page);
		List<SortMarker> sortMarkers = new LinkedList<SortMarker>();
		//根据sortType， 品牌排序 （null 或者 1），修改时间排序（2）
		if(StringUtil.isBlank(hjyvo.getSortType()) || SortType.PINYINASC.getVal().equals(hjyvo.getSortType())){
			SortMarker smpinyin = new SortMarker("pinyin", true);
			sortMarkers.add(smpinyin);
			entity.put("sortMarkers", sortMarkers);
		}else{
			SortMarker smdate = new SortMarker("update_date", false);
			sortMarkers.add(smdate);
			entity.put("sortMarkers", sortMarkers);
			SortMarker smtime = new SortMarker("update_time", false);
			sortMarkers.add(smtime);
			entity.put("sortMarkers", sortMarkers);
		}
		SortMarker smpinyin = new SortMarker("pinyin", true);
		sortMarkers.add(smpinyin);
		entity.put("sortMarkers", sortMarkers);

		List<CarBrand> list = carBrandService.queryBlur(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	@Override
	public Map<String, Object> addCarBrand(HJYVO hjyvo) throws HJHBCSErrInfoException {
		CarBrand entity = JSONUtil.trans(hjyvo, CarBrand.class);
		isExist(entity);
		if(Status.ENABLED.getVal().equals(entity.getIs_top()))
		isExistSort(entity);
		// entity.setBrand_id(IDUtil.getOrdID16());
		entity.setBrand_id(keyGenerate.getKeyGenerate("car_brand"));
		if (StringUtil.isBlank(entity.getPinyin())) {
			String pinyin = PinyinUtil.getPingYin(entity.getBrand_name());
			if (!StringUtil.isBlank(pinyin)) {
				//如果拼音长度超过50，取前50个字符
				if (pinyin.length() > 50) {
					entity.setPinyin(pinyin.substring(0, 50));
				} else {
					entity.setPinyin(pinyin);
				}
			}
		}
		if (StringUtil.isBlank(entity.getStatus())) {
			entity.setStatus(Status.ENABLED.getVal());
		}

		initAddCarBrand(entity);
		carBrandService.save(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> findCarBrandById(CarBrandQueryById hjyvo) {
		CarBrand entity = carBrandService.get(hjyvo.getBrand_id());
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, entity);
		return result;
	}

	@Override
	public Map<String, Object> updateCarBrand(HJYVO hjyvo) throws HJHBCSErrInfoException {
		CarBrand entity = JSONUtil.trans(hjyvo, CarBrand.class);
		isExist(entity);
		if(Status.ENABLED.getVal().equals(entity.getIs_top()))
		isExistSort(entity);
		if (StringUtil.isBlank(entity.getPinyin())) {
			String pinyin = PinyinUtil.getPingYin(entity.getBrand_name());
			if (!StringUtil.isBlank(pinyin)) {
				if (pinyin.length() > 50) {
					entity.setPinyin(pinyin.substring(0, 50));
				} else {
					entity.setPinyin(pinyin);
				}
			}
		}

		initUpdateCarBrand(entity);
		carBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> deleteCarBrand(HJYVO hjyvo) {
		CarBrand entity = JSONUtil.trans(hjyvo, CarBrand.class);
		carBrandService.delete(entity.getBrand_id());
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateGoodsBrandStatus(CarBrandStatus hjyvo) {
		CarBrand entity = JSONUtil.trans(hjyvo, CarBrand.class);
		initUpdateCarBrand(entity);
		carBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateGoodsBrandSort(CarBrandSort hjyvo) {
		CarBrand entity = JSONUtil.trans(hjyvo, CarBrand.class);
		initUpdateCarBrand(entity);
		carBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	public void initAddCarBrand(CarBrand entity) {
		Date date = new Date();
		entity.setCreate_user(entity.getUpdate_user());
		entity.setCreate_user_name(entity.getUpdate_user_name());
		entity.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}

	public void initUpdateCarBrand(CarBrand entity) {
		Date date = new Date();
		entity.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 是否存在重复品牌名称
	 * @param entity
	 *            void
	 */
	private void isExist(CarBrand entity) {
		CarBrand cd = new CarBrand();
		if (!StringUtil.isBlank(entity.getBrand_id())) {
			cd.setBrand_id(entity.getBrand_id());
		}
		cd.setBrand_name(entity.getBrand_name());
		List<CarBrand> list = carBrandService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014005");
		}
	}
	
	/**
	 * @date 2017年5月17日
	 * @Description: 是否存在重复品牌sort
	 * @param entity
	 *            void
	 */
	private void isExistSort(CarBrand entity) {
		CarBrand cd = new CarBrand();
		if (!StringUtil.isBlank(entity.getBrand_id())) {
			cd.setBrand_id(entity.getBrand_id());
		}
		cd.setSort(entity.getSort());
		List<CarBrand> list = carBrandService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014006");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int batchStatus(CarBrandStatus example) {
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		Map map = new HashMap();
		if (!StringUtil.isBlank(example.getBrand_id())) {
			map.put("ids", example.getBrand_id().split(","));
		}
		String operateInfo = cacheHelper.get("hjh.hjy.access_token." + example.getAccess_token());
		Map<String, String> mapInfo = JSONUtil.trans(operateInfo, Map.class);
		map.put("status", example.getStatus());
		map.put("update_date", dateString);
		map.put("update_time", timeString);
		map.put("update_user", mapInfo.get("clientId"));
		map.put("update_user_name", mapInfo.get("operatorName"));
		carBrandService.batchStatus(map);
		return 1;
	}

	@Override
	public boolean isSortExist(CarBrandQuery hjyvo) {
		boolean result = false;
		CarBrand entity = new CarBrand();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CarBrand> list = carBrandService.isExist(entity);
		if (list.size() > 0) {
			result = true;
		}
		return result;
	}

}
