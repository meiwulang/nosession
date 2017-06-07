package com.hjh.mall.category.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.Metadata;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 元数据业务层
 * @author zfl
 * @date 2017年03月13日
 * @version V1.0
 */
public interface MetadataDao extends DAOBase<Metadata, String> {
	public List<Metadata> getMetadataList(Map<String, Object> map);

	public List<Map<String, Object>> getMetadataListByType(Metadata metadata);

	public int countBySelf(Map<String, Object> map);

	public List<Map<String, Object>> getMetadataListByName(Metadata metadata);

	public Map<String, Object> getMetadataById(Metadata metadata);
}