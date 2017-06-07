package com.hjh.mall.category.bizImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.metadata.BizMetadataService;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.AddMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByBrand;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataById;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByName;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByType;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataList;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadataStatus;
import com.hjh.mall.category.entity.Metadata;
import com.hjh.mall.category.service.MetadataService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;

/**
 * @Project: mall-category
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */

@Component
public class BizMetadataServiceImpl implements BizMetadataService {

	@Resource
	private MetadataService metadataService;

	@Resource
	private KeyGenerate keyGenerate;

	@Resource
	private CacheHelper cacheHelper;

	/**
	 * 添加元数据
	 */
	@Override
	public Map<String, Object> addMetadata(AddMetadata addMetadata) {
		Metadata metadataOld = new Metadata();
		metadataOld.setMetadata_name(addMetadata.getMetadata_name());
		Map<String, Object> result = VOUtil.genSuccessResult();
		if (checkMetadata(metadataOld, null)) {// 名称重复
			return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.METADATA_EXIST);
		}
		if (StringUtils.isNotBlank(addMetadata.getAlias())) {// 别名重复
			metadataOld.setMetadata_name(null);
			metadataOld.setAlias(addMetadata.getAlias());
			if (checkMetadata(metadataOld, null)) {
				return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.ALIAS_EXIST);
			}
		}
		if (addMetadata.getSort() != null && !addMetadata.getSort().equals(0)) {// 排序重复
			metadataOld.setMetadata_name(null);
			metadataOld.setAlias(null);
			metadataOld.setSort(addMetadata.getSort());
			metadataOld.setType(addMetadata.getType());
			if (checkMetadata(metadataOld, null)) {
				return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.SORT_EXIST);
			}
		}
		Metadata metadata = JSONUtil.trans(addMetadata, Metadata.class);
		metadata.setMetadata_id(keyGenerate.getKeyGenerate(MallFields.METADATA));
		createInit(metadata);
		metadataService.save(metadata);

		// 存缓存
		Map<String, String> map = new HashMap<>();
		map.put(MallFields.METADATA_ID, metadata.getMetadata_id());
		map.put(MallFields.ALIAS, metadata.getAlias());
		map.put(MallFields.METADATA_NAME, metadata.getMetadata_name());
		cacheHelper.hmset(CacheKeys.METADATA_ID_PREFIX + metadata.getMetadata_id(), map);

		return result;
	}

	/**
	 * 修改元数据
	 */
	@Override
	public Map<String, Object> updateMetadata(UpdateMetadata updateMetadata) {
		Metadata metadataOld = new Metadata();
		metadataOld.setMetadata_name(updateMetadata.getMetadata_name());
		Map<String, Object> result = VOUtil.genSuccessResult();
		if (checkMetadata(metadataOld, updateMetadata.getMetadata_id())) {// 名称重复
			return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.METADATA_EXIST);
		}
		if (StringUtils.isNotBlank(updateMetadata.getAlias())) {// 别名重复
			if (checkMetadata(metadataOld, updateMetadata.getMetadata_id())) {
				return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.ALIAS_EXIST);
			}
		}
		if (updateMetadata.getSort() != null && !updateMetadata.getSort().equals(0)) {// 排序重复
			metadataOld.setMetadata_name(null);
			metadataOld.setAlias(null);
			metadataOld.setSort(updateMetadata.getSort());
			metadataOld.setType(updateMetadata.getType());
			if (checkMetadata(metadataOld, updateMetadata.getMetadata_id())) {
				return VOUtil.genErrorResult(MallErrorCode.MetaDataErrorCode.SORT_EXIST);
			}
		}
		Metadata metadata = JSONUtil.trans(updateMetadata, Metadata.class);
		setUpdateTime(metadata);
		metadataService.update(metadata);

		// 存缓存
		Map<String, String> map = new HashMap<>();
		map.put(MallFields.METADATA_ID, metadata.getMetadata_id());
		map.put(MallFields.ALIAS, metadata.getAlias());
		map.put(MallFields.METADATA_NAME, metadata.getMetadata_name());
		cacheHelper.hmset(CacheKeys.METADATA_ID_PREFIX + metadata.getMetadata_id(), map);
		return result;
	}

	// 校验元数据是否重复
	public boolean checkMetadata(Metadata metadata, String id) {
		boolean flag = false;
		List<Metadata> list = metadataService.query(metadata);
		if (list != null && list.size() > 0) {// 名称重复
			if (!StringUtils.isBlank(id)) {
				for (Metadata m : list) {
					if (!m.getMetadata_id().equals(id)) {
						flag = true;
						continue;
					}
				}
			} else {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 后台启用、禁用
	 */
	@Override
	public Map<String, Object> updateMetadataStatus(UpdateMetadataStatus updateMetadataStatus) {
		Metadata metadata = JSONUtil.trans(updateMetadataStatus, Metadata.class);
		setUpdateTime(metadata);
		metadataService.update(metadata);
		return VOUtil.genSuccessResult();
	}

	/**
	 * 后台查询元数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMetadataList(GetMetadataList getMetadataList) {
		Map<String, Object> map = JSONUtil.trans(getMetadataList, Map.class);
		int total = metadataService.countBySelf(map);
		Pagination page = new Pagination();
		page.setPage_size(getMetadataList.getLimit());
		page.setPage_no(getMetadataList.getPage());
		page.setTotal_item_num(total);
		page.calc();
		map.put(MallFields.PAGINATION, page);
		List<Metadata> list = metadataService.getMetadataList(map);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	// 按照元数据类型查找数据
	@Override
	public Map<String, Object> getMetadataListByType(GetMetadataByType getMetadataByType) {
		Metadata metadata = new Metadata();
		metadata.setType(getMetadataByType.getType());
		metadata.setStatus(Status.ENABLED.getVal());
		List<Map<String, Object>> list = metadataService.getMetadataListByType(metadata);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	// 加载所有的元数据到缓存
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> reloadAllMetadata() {
		Metadata metadata = new Metadata();
		metadata.setStatus(Status.ENABLED.getVal());
		List<Map<String, Object>> list = metadataService.getMetadataListByType(metadata);
		for (Map m : list) {
			m.remove(BasicFields.TYPE);
			m.remove(MallFields.SORT);
			cacheHelper.hmset(CacheKeys.METADATA_ID_PREFIX + m.get(MallFields.METADATA_ID).toString(), m);
		}
		return VOUtil.genSuccessResult();
	}

	// 设置创建时间和修改时间
	public static void createInit(Metadata metadata) {
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		metadata.setInit_date(date);
		metadata.setInit_time(time);
		metadata.setUpdate_date(date);
		metadata.setUpdate_time(time);
		metadata.initForClearNull();
	}

	// 设置修改时间
	public static void setUpdateTime(Metadata metadata) {
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		metadata.setUpdate_date(date);
		metadata.setUpdate_time(time);
	}

	// 根据元数据名称匹配
	@Override
	public Map<String, Object> getMetadataByName(GetMetadataByName getMetadataByName) {
		Metadata metadata = new Metadata();
		metadata.setType(getMetadataByName.getType());
		metadata.setStatus(Status.ENABLED.getVal());
		metadata.setMetadata_name(getMetadataByName.getMetadata_name());
		List<Map<String, Object>> list = metadataService.getMetadataListByName(metadata);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	public Map<String, Object> getMetadataById(GetMetadataById getMetadataById) {

		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.METADATA_ID_PREFIX + getMetadataById.getMetadata_id());
		Map<String, Object> result = VOUtil.genSuccessResult();
		if (map == null || map.isEmpty()) {
			Metadata metadata = new Metadata();
			metadata.setMetadata_id(getMetadataById.getMetadata_id());
			Map<String, Object> map1 = metadataService.getMetadataById(metadata);
			if (map1 != null) {
				result.putAll(map1);
			}
		} else {
			result.putAll(map);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMetadataBybrand(GetMetadataByBrand getMetadataByBrand) {
		List<String> ids = cacheHelper.getList(CacheKeys.BRAND_CAR_METADATA_PREFIX + getMetadataByBrand.getBrand_id(),
				0, -1);
		List<Map<String, Object>> list = new ArrayList<>();
		if (ids != null && ids.size() > 0) {
			for (String id : ids) {
				Map<String, Object> map = JSONUtil.trans(cacheHelper.hgetAll(CacheKeys.METADATA_ID_PREFIX + id),
						Map.class);

				if (map == null || map.isEmpty()) {
					Metadata metadata = new Metadata();
					metadata.setMetadata_id(id);
					Map<String, Object> map1 = metadataService.getMetadataById(metadata);
					if (map1 != null) {
						list.add(map1);
					}
				} else {
					list.add(map);
				}
			}
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	public boolean checkMetadataName(UpdateMetadata updateMetadata) {
		Metadata metadata = metadataService.get(updateMetadata.getMetadata_id());
		if (!updateMetadata.getMetadata_name().equals(metadata.getMetadata_name())) {
			return true;
		}
		return false;
	}

}
