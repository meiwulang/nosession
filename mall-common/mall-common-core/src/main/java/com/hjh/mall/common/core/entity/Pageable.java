package com.hjh.mall.common.core.entity;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hjh.mall.common.core.entity.LEGBCSEntityBase;
import com.hjh.mall.common.core.entity.Pageable;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;



public class Pageable extends LEGBCSEntityBase {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected Pagination page;
    
    protected List<SortMarker> sortMarkers;

    protected List<String> ids;
    
   
    public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	private static Map<String, Map<String, String>> dbFieldMaps = new HashMap<String, Map<String, String>>();
    
    private static Lock dbFieldMapLock = new ReentrantLock();
    
    public SortMarker getSortMarker(Pageable pageable, String prop, boolean asc) {
        String className = getClass().getName();
        Map<String, String> dbFieldMap = dbFieldMaps.get(className);
        if (null == dbFieldMap) {
            dbFieldMapLock.lock();
            try {
                dbFieldMap = dbFieldMaps.get(className);
                if (null == dbFieldMap) {
                    dbFieldMap = new HashMap<String, String>();
                    BeanInfo beanInfo = Introspector.getBeanInfo(pageable.getClass());
                    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                    for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                        String propName = propertyDescriptor.getName();
                        // propName -> PROP_NAME
                        String fieldName = propName.replaceAll("([A-Z])", "_$1").toUpperCase();
                        dbFieldMap.put(propName, fieldName);
                    }
                    dbFieldMaps.put(className, dbFieldMap);
                }
            } catch (Exception e) {
                throw new RuntimeException("init dbFieldMap failed", e);
            } finally {
                dbFieldMapLock.unlock();
            }
        }
        String dbField = dbFieldMap.get(prop);
        if (null != dbField) {
            return new SortMarker(dbField, asc);
        }
        return null;
    }
    
    public Pageable pagination(Pagination page) {
        setPage(page);
        return this;
    }
    
    public Pageable addSortMarker(SortMarker sortMarker) {
        if (null == sortMarkers) {
            sortMarkers = new LinkedList<SortMarker>();
        }
        sortMarkers.add(sortMarker);
        return this;
    }
    
    public Pageable addSortMarker(SortMarker... sortMarkersToAdd) {
        if (null != sortMarkersToAdd && 0 < sortMarkersToAdd.length) {
            if (null == sortMarkers) {
                sortMarkers = new LinkedList<SortMarker>();
            }
            for (SortMarker sortMarker : sortMarkersToAdd) {
                sortMarkers.add(sortMarker);
            }
        }
        return this;
    }
    
    public Pagination getPage() {
        return page;
    }
    
    public void setPage(Pagination page) {
        this.page = page;
    }
    
    public List<SortMarker> getSortMarkers() {
        return sortMarkers;
    }
    
    public void setSortMarkers(List<SortMarker> sortMarkers) {
        this.sortMarkers = sortMarkers;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pageable [page=").append(page).append("]");
        return builder.toString();
    }
    
}
