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
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQuery;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandQueryById;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandSort;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.vo.GoodsBrandStatus;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.category.service.GoodsBrandService;
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
import com.hjh.mall.field.type.SortType;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category
 * @Description 商品品牌controller
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0
 */
@Component
public class BizGoodsBrandServiceImpl implements BizGoodsBrandService {
	@Resource
	private GoodsBrandService goodsBrandService;
	@Resource
	private KeyGenerate keyGenerate;
	@Resource
	private CacheHelper cacheHelper;

	@Override
	public Map<String, Object> queryAllGoodsBrand(GoodsBrandQuery hjyvo) {
		GoodsBrand entity = new GoodsBrand();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!StringUtil.isBlank(hjyvo.getStatus())) {
			entity.setStatus(hjyvo.getStatus());
		}
		int total = goodsBrandService.count(entity);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		entity.setPage(page);
		SortMarker sort = new SortMarker("sort", true);
		entity.addSortMarker(sort);

		List<GoodsBrand> list = goodsBrandService.query(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> queryGoodsBrandByParams(GoodsBrandQuery hjyvo) {
		Map entity = JSONUtil.trans(hjyvo, Map.class);
		int total = goodsBrandService.countBlur(entity);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		entity.put("page", page);
		
		if(StringUtil.isBlank(hjyvo.getSortType()) || SortType.PINYINASC.getVal().equals(hjyvo.getSortType())){
			List<SortMarker> sortMarkers = new LinkedList<SortMarker>();
			SortMarker smpinyin = new SortMarker("pinyin", true);
			sortMarkers.add(smpinyin);
			entity.put("sortMarkers", sortMarkers);
		}else{
			List<SortMarker> sortMarkers = new LinkedList<SortMarker>();
			SortMarker smdate = new SortMarker("update_date", false);
			sortMarkers.add(smdate);
			entity.put("sortMarkers", sortMarkers);
			SortMarker smtime = new SortMarker("update_time", false);
			sortMarkers.add(smtime);
			entity.put("sortMarkers", sortMarkers);
		}

		List<GoodsBrand> list = goodsBrandService.queryBlur(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	@Override
	public Map<String, Object> addGoodsBrand(HJYVO hjyvo) throws HJHBCSErrInfoException {
		GoodsBrand entity = JSONUtil.trans(hjyvo, GoodsBrand.class);
		if(Status.ENABLED.getVal().equals(entity.getIs_top()))
		isExistSort(entity);
		isExist(entity);
		entity.setBrand_id(keyGenerate.getKeyGenerate("car_brand"));
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
		if (StringUtil.isBlank(entity.getStatus())) {
			entity.setStatus("1");
		}
		initAddGoodsBrand(entity);
		goodsBrandService.save(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateGoodsBrand(HJYVO hjyvo) throws HJHBCSErrInfoException {
		GoodsBrand entity = JSONUtil.trans(hjyvo, GoodsBrand.class);
		if(Status.ENABLED.getVal().equals(entity.getIs_top()))
		isExistSort(entity);
		//判断是否有品牌名称重复
		isExist(entity);
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
		initUpdateGoodsBrand(entity);
		goodsBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> deleteGoodsBrand(HJYVO hjyvo) {
		GoodsBrand entity = JSONUtil.trans(hjyvo, GoodsBrand.class);
		goodsBrandService.delete(entity.getBrand_id());
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateGoodsBrandStatus(GoodsBrandStatus hjyvo) {
		GoodsBrand entity = JSONUtil.trans(hjyvo, GoodsBrand.class);
		initUpdateGoodsBrand(entity);
		goodsBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateGoodsBrandSort(GoodsBrandSort hjyvo) {
		GoodsBrand entity = JSONUtil.trans(hjyvo, GoodsBrand.class);
		initUpdateGoodsBrand(entity);
		goodsBrandService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> findGoodsBrandById(GoodsBrandQueryById hjyvo) {
		GoodsBrand entity = goodsBrandService.get(hjyvo.getBrand_id());
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, entity);
		return result;
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 是否存在重复品牌名称
	 * @param entity
	 *            void
	 */
	private void isExist(GoodsBrand entity) {
		GoodsBrand gd = new GoodsBrand();
		gd.setBrand_name(entity.getBrand_name());
		if (!StringUtil.isBlank(entity.getBrand_id())) {
			gd.setBrand_id(entity.getBrand_id());
		}
		List<GoodsBrand> list = goodsBrandService.isExist(gd);
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
	private void isExistSort(GoodsBrand entity) {
		GoodsBrand cd = new GoodsBrand();
		if (!StringUtil.isBlank(entity.getBrand_id())) {
			cd.setBrand_id(entity.getBrand_id());
		}
		cd.setSort(entity.getSort());
		List<GoodsBrand> list = goodsBrandService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014006");
		}
	}

	/** 
	 * @date 2017年4月11日
	 * @Description: 添加时,时间创建人统一处理
	 * @param entity
	 * void
	 */
	private void initAddGoodsBrand(GoodsBrand entity) {
		Date date = new Date();
		entity.setCreate_user(entity.getUpdate_user());
		entity.setCreate_user_name(entity.getUpdate_user_name());
		entity.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}

	/** 
	 * @date 2017年4月11日
	 * @Description: 修改时,时间 修改人 处理
	 * @param entity
	 * void
	 */
	private void initUpdateGoodsBrand(GoodsBrand entity) {
		Date date = new Date();
		entity.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchStatus(GoodsBrandStatus example) {
		Map map = new HashMap();
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
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
		map.put("status", example.getStatus());
		goodsBrandService.batchStatus(map);
		return 1;
	}

	/**
	 *排序字段是否重复
	 */
	@Override
	public boolean isSortExist(GoodsBrandQuery hjyvo) {
		boolean result = false;
		GoodsBrand entity = new GoodsBrand();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<GoodsBrand> list = goodsBrandService.isExist(entity);
		if (list.size() > 0) {
			result = true;
		}
		return result;
	}
}
