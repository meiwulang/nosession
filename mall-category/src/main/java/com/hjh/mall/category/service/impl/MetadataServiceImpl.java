package com.hjh.mall.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.category.dao.MetadataDao;
import com.hjh.mall.category.entity.Metadata;
import com.hjh.mall.category.service.MetadataService;
import com.hjh.mall.category.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 元数据业务层
 * @author zfl
 * @date 2017年03月13日
 * @version V1.0
 */

@Service
public class MetadataServiceImpl extends HJYServiceImplBase<Metadata, String> implements MetadataService {
	@Resource
	private MetadataDao metadataDao;

	@Override
	protected DAOBase<Metadata, String> getDAO() {
		return metadataDao;
	}

	@Override
	public List<Metadata> getMetadataList(Map<String, Object> map) {
		return metadataDao.getMetadataList(map);
	}

	@Override
	public List<Map<String, Object>> getMetadataListByType(Metadata metadata) {
		return metadataDao.getMetadataListByType(metadata);
	}

	@Override
	public int countBySelf(Map<String, Object> map) {
		return metadataDao.countBySelf(map);
	}

	@Override
	public List<Map<String, Object>> getMetadataListByName(Metadata metadata) {
		return metadataDao.getMetadataListByName(metadata);
	}

	@Override
	public Map<String, Object> getMetadataById(Metadata metadata) {
		return metadataDao.getMetadataById(metadata);
	}

}
