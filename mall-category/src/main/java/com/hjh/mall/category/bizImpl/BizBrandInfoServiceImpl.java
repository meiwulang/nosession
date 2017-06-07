package com.hjh.mall.category.bizImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.BizBrandInfoService;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAdd;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppDisplay;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoAppVo;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoQuery;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoStatus;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoUpdate;
import com.hjh.mall.category.bizapi.bizserver.brandinfo.vo.BrandInfoVo;
import com.hjh.mall.category.entity.BrandInfo;
import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.category.service.BrandInfoService;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.constant.MallFields;

/**
 * @Project: mall-category
 * @Description TODO
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
@Component
public class BizBrandInfoServiceImpl implements BizBrandInfoService {
	
	@Resource
	private BrandInfoService brandInfoService;
	@Resource
	private KeyGenerate keyGenerate;

	@Override
	public List<BrandInfoVo> queryBrandInfoByParameter(BrandInfoQuery hjyvo) {
		BrandInfo bi = new BrandInfo();
		try {
			BeanUtil.reflectionAttr(hjyvo, bi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int total = brandInfoService.count(bi);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		bi.setPage(page);
		SortMarker sortmarkerd = new SortMarker(MallFields.UPDATE_DATE, false);
		SortMarker sortmarkert = new SortMarker(MallFields.UPDATE_TIME, false);
		bi.addSortMarker(sortmarkerd);
		bi.addSortMarker(sortmarkert);
		List<BrandInfo> list=brandInfoService.query(bi);
		return JSONUtil.transInSide(list, BrandInfoVo.class);
	}
	@Override
	public Integer queryBrandInfoCountByParameter(BrandInfoQuery hjyvo) {
		BrandInfo bi = new BrandInfo();
		try {
			BeanUtil.reflectionAttr(hjyvo, bi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int num=brandInfoService.count(bi);
		return num;
	}
	@Override
	public Map<String, Object> addBrandInfo(BrandInfoAdd hjyvo) throws HJHBCSErrInfoException {
		BrandInfo entity = JSONUtil.trans(hjyvo, BrandInfo.class);
		isExistBrand(entity);
		isExist(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		entity.setBrand_info_id(keyGenerate.getKeyGenerate(MallFields.BRAND_INFO));
		initAddDate(entity);
		brandInfoService.save(entity);
		return result;
	}

	@Override
	public Map<String, Object> updateBrandInfo(BrandInfoUpdate hjyvo) throws HJHBCSErrInfoException {
		BrandInfo entity = JSONUtil.trans(hjyvo, BrandInfo.class);
		Map<String, Object> result = VOUtil.genSuccessResult();
		initUpdateDate(entity);
		isExistBrand(entity);
		isExist(entity);
		brandInfoService.update(entity);
		return result;
	}

	@Override
	public Map<String, Object> updateBrandInfoAppDisplay(BrandInfoAppDisplay hjyvo) {
		BrandInfo entity = JSONUtil.trans(hjyvo, BrandInfo.class);
		Map<String, Object> result = VOUtil.genSuccessResult();
		initUpdateDate(entity);
		brandInfoService.update(entity);
		return result;
	}
	@Override
	public Map<String, Object> updateBrandInfoStatus(BrandInfoStatus hjyvo) {
		BrandInfo entity = JSONUtil.trans(hjyvo, BrandInfo.class);
		Map<String, Object> result = VOUtil.genSuccessResult();
		initUpdateDate(entity);
		brandInfoService.update(entity);
		return result;
	}
	@Override
	public BrandInfoVo findBrandInfoById(String id) {
		BrandInfo brandinfo = brandInfoService.get(id);
		return JSONUtil.trans(brandinfo, BrandInfoVo.class);
	}

	@Override
	public List<BrandInfoAppVo> queryBrandInfoByParameterApp(BrandInfoQuery hjyvo) {
		BrandInfo bi = new BrandInfo();
		try {
			BeanUtil.reflectionAttr(hjyvo, bi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int total = brandInfoService.count(bi);
		Pagination page = new Pagination();
		page.setPage_size(hjyvo.getLimit());
		page.setPage_no(hjyvo.getPage());
		page.setTotal_item_num(total);
		page.calc();
		bi.setPage(page);
		SortMarker sortmarker = new SortMarker(MallFields.SORT, true);
		bi.addSortMarker(sortmarker);
		
		List<BrandInfo> list=brandInfoService.query(bi);
		return JSONUtil.transInSide(list, BrandInfoAppVo.class);
	}
	
	@Override
	public Integer queryBrandInfoCountByParameterApp(BrandInfoQuery hjyvo) {
		BrandInfo bi = new BrandInfo();
		try {
			BeanUtil.reflectionAttr(hjyvo, bi);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int num=brandInfoService.count(bi);
		return num;
	}

	private void initAddDate(BrandInfo vo){
		Date date=new Date();
		vo.setCreate_user(vo.getUpdate_user());
		vo.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		vo.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		vo.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		vo.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}
	
	private void initUpdateDate(BrandInfo vo){
		Date date=new Date();
		vo.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		vo.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}
	
	/**
	 * @date 2017年5月18日
	 * @Description: 是否存在重复排序
	 * @param entity
	 *            void
	 */
	private void isExist(BrandInfo entity) {
		BrandInfo cd = new BrandInfo();
		if (!StringUtil.isBlank(entity.getBrand_info_id())) {
			cd.setBrand_info_id(entity.getBrand_info_id());
		}
		cd.setSort(entity.getSort());
		List<BrandInfo> list = brandInfoService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014006");
		}
	}
	
	/**
	 * @date 2017年5月18日
	 * @Description: 是否存在重复品牌介绍
	 * @param entity
	 *            void
	 */
	private void isExistBrand(BrandInfo entity) {
		BrandInfo cd = new BrandInfo();
		if (!StringUtil.isBlank(entity.getBrand_info_id())) {
			cd.setBrand_info_id(entity.getBrand_info_id());
		}
		cd.setBrand_id(entity.getBrand_id());;
		List<BrandInfo> list = brandInfoService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014007");
		}
	}
	
	

	



}
