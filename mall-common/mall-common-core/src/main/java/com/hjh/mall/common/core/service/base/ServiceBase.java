package com.hjh.mall.common.core.service.base;

import java.util.List;

import com.hjh.mall.common.core.entity.Pageable;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;

public interface ServiceBase<T extends Pageable, K> {

	public void save(T entity);

	public T get(K key);

	public int count(T example);

	public List<T> query(T example);

	public List<T> queryAll();

	public List<T> queryWithPagination(T example, Pagination page);

	public List<T> queryWithPagination(T example, Pagination page, SortMarker... sortMarkers);

	public int update(T entity);

	public int updateCAS(T entity);

	public int updateCAS(T entity, boolean throwException);

	public int delete(K key);

}
