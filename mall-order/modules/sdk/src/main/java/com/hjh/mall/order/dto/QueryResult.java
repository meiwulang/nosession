package com.hjh.mall.order.dto;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by qiuxianxiang on 17/5/17.
 */
public class QueryResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = 127391567391L;

    private long total;
    private List<T> items;

    public QueryResult() {
    }

    public QueryResult(long total,List<T> items){
        this.total = total;
        this.items = items;
    }

    public QueryResult(Page<T> page){
        PageInfo pageInfo = page.toPageInfo();
        this.total = pageInfo.getTotal();
        this.items = pageInfo.getList();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
