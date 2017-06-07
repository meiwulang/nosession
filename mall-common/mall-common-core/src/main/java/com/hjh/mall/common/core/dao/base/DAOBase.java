package com.hjh.mall.common.core.dao.base;

import java.util.List;

public interface DAOBase<T, K> {
    
    public void save(T entity);
    
    public T get(K key);
    
    public int count(T example);
    
    public List<T> query(T example);
    
    public int update(T entity);
    
    public int delete(K key);
    
}
