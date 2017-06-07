package com.hjh.mall.common.core.service.base;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.entity.Pageable;
import com.hjh.mall.common.core.entity.Updatable;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.common.core.util.ValueUtil;

public abstract class ServiceImplBase<T extends Pageable, K> implements ServiceBase<T, K> {

	public void save(T entity) {
		getDAO().save(entity);
		if (entity instanceof Updatable) {
			Updatable updatable = (Updatable) entity;
			if (null == updatable.getUpdate_version()) {
				updatable.setUpdate_version(0L);
			}
		}
	}

	public T get(K key) {
		if (ValueUtil.isEmpty(key)) {
			return null;
		}
		return getDAO().get(key);
	}

	@Override
	public int count(T example) {
		return getDAO().count(example);
	}

	public List<T> query(T example) {
		return getDAO().query(example);
	}

	public List<T> queryAll() {
		return getDAO().query(null);
	}

	@Override
	public List<T> queryWithPagination(T example, Pagination page) {
		int count = count(example);
		if (null == page) {
			page = example.getPage();
		}
		page.setTotal_item_num(count);
		page.calc();
		example.setPage(page);
		return query(example);
	}

	@Override
	public List<T> queryWithPagination(T example, Pagination page, SortMarker... sortMarkers) {
		example.addSortMarker(sortMarkers);
		return queryWithPagination(example, page);
	}

	public int updateCAS(T entity) {
		return updateCAS(entity, true);
	}

	public int updateCAS(T entity, boolean throwException) {
		int updateNum = update(entity);
		if (0 == updateNum) {
			if (throwException) {
				throw new HJHBCSErrInfoException(BasicErrorCodes.CONCURRENT_MODIFY);
			} else {
//				 LOGGER.error("concurrent modify: " + entity);
			}
		} else {
			if (entity instanceof Updatable) {
				Updatable updatable = (Updatable) entity;
				long updateVersion = ValueUtil.getLong(updatable.getUpdate_version());
				updatable.setUpdate_version(updateVersion + 1);
			}
		}
		return updateNum;
	}

	public int update(T entity) {
		return getDAO().update(entity);
	}

	public int delete(K key) {
		return getDAO().delete(key);
	}

	protected abstract DAOBase<T, K> getDAO();

}
