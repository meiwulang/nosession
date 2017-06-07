package com.hjh.mall.common.core.model;

import java.io.Serializable;

/**
 * @author chengjia
 *
 */
public class Pagination implements Serializable {
    
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 页面大小，一页的条目数
     */
    private int page_size = DEFAULT_PAGE_SIZE;
    
    /**
     * 总条目数量
     */
    private int total_item_num;
    
    /**
     * 总页数，从1开始，没有条目也有1页
     */
    private int total_page_num;
    
    /**
     * 页码，从1开始，没有条目也有1页
     */
    private Integer page_no;
    
    /**
     * 开始的条目位置，从1开始
     */
    private Integer start_index;
    
    /**
     * 结束的条目位置，从1开始
     */
    private int end_index;
    
    /**
     * 该页的项目数
     */
    private int cur_page_item_num;
    
    /**
     * 计算分页，<br />
     * 如果设置了页码，按页码分，<br />
     * 如果没有设置页码，设置了开始条目位置，则先根据开始条目位置计算页码再分页，<br />
     * 如果两个都没设置，则按第一页算
     * 
     * @return
     */
    public boolean calc() {
        if (!checkPageSize()) {
            return false;
        }
        
        if (!checkTotalItemNum()) {
            return false;
        }
        
        // 计算总页数
        total_page_num = total_item_num / page_size;
        if (total_page_num * page_size < total_item_num) {
            total_page_num++;
        }
        
        if (null != page_no) {
            calcByPageNo();
        } else if (null != start_index) {
            calcByStartIndex();
        } else {
            page_no = 1;
            calcByPageNo();
        }
        
        return true;
    }
    
    private void calcByPageNo() {
        // 页码小于等于0，取第一页
        if (page_no <= 0) {
            page_no = 1;
        }
        // 超过总页数，取最后一页
        if (page_no > total_page_num) {
            page_no = total_page_num;
        }
        
        // 重新根据页码计算开始条目（到前一页为止的总条目数再加1）
        start_index = (page_no - 1) * page_size + 1;
        // 计算结束的条目位置
        calcEndIndex();
    }
    
    private void calcByStartIndex() {
        // 开始的条目位置小于等于0，取第一条（checkTotalItemNum已经校验了总条目数不为0）
        if (start_index <= 0) {
            start_index = 1;
        }
        // 超过总条目数，取最后一条
        if (start_index > total_item_num) {
            start_index = total_item_num;
        }
        
        // 计算页码
        page_no = start_index / page_size;
        // 开始的条目位置正好等于这么多页的条目数，则其实是前一页的页尾
        if (page_no * page_size == start_index) {
            page_no--;
        }
        // 如果是第0页，则调整为第一页
        if (page_no == 0) {
            page_no = 1;
        }
        
        // 重新根据页码计算开始条目（到前一页为止的总条目数再加1）
        start_index = (page_no - 1) * page_size + 1;
        // 计算结束的条目位置
        calcEndIndex();
    }
    
    private void calcEndIndex() {
        // 从开始条目位置完后偏移页面大小减1
        end_index = start_index + (page_size - 1);
        // 如果结束的条目位置超过总条目数，调整为总条目数
        if (end_index > total_item_num) {
            end_index = total_item_num;
        }
    }
    
    private boolean checkPageSize() {
        // page_size小于0，表示不分页，取所有
        // page_size等于0，没结果
        // page_size大于0，正常按page_size分页
        if (page_size == 0) {
            page_no = 1;
            start_index = 0;
            end_index = 0;
            total_item_num = 0;
            cur_page_item_num = 0;
            total_page_num = 1;
            return false;
        }
        if (page_size < 0) {
            page_size = total_item_num;
            page_no = 1;
            start_index = 1;
            end_index = page_size;
            cur_page_item_num = page_size;
            total_page_num = 1;
            return false;
        }
        return true;
    }
    
    private boolean checkTotalItemNum() {
        if (total_item_num <= 0) {
            total_item_num = 0;
            page_no = 1;
            start_index = 0;
            end_index = 0;
            cur_page_item_num = 0;
            total_page_num = 1;
            return false;
        }
        return true;
    }
    
    /**
     * 获取页面大小，一页的条目数
     * 
     * @return
     */
    public int getPage_size() {
        return page_size;
    }
    
    /**
     * 设置页面大小，一页的条目数
     * 
     * @param page_size
     */
    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
    
    /**
     * 总条目数量
     * 
     * @return
     */
    public int getTotal_item_num() {
        return total_item_num;
    }
    
    /**
     * 设置总条目数量
     * 
     * @param total_item_num
     */
    public void setTotal_item_num(int total_item_num) {
        this.total_item_num = total_item_num;
    }
    
    /**
     * 获取页码，从1开始，没有条目也有1页
     * 
     * @return
     */
    public Integer getPage_no() {
        return page_no;
    }
    
    /**
     * 设置页码，从1开始，没有条目也有1页
     * 
     * @param page_no
     */
    public void setPage_no(Integer page_no) {
        this.page_no = page_no;
    }
    
    /**
     * 获取开始的条目位置，从1开始
     * 
     * @return
     */
    public Integer getStart_index() {
        return start_index;
    }
    
    /**
     * 设置开始的条目位置，从1开始
     * 
     * @param start_index
     */
    public void setStart_index(Integer start_index) {
        this.start_index = start_index;
    }
    
    /**
     * 获取总页数，从1开始，没有条目也有1页
     * 
     * @return
     */
    public int getTotal_page_num() {
        return total_page_num;
    }
    
    /**
     * 设置总页数，从1开始，没有条目也有1页<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param total_page_num
     */
    public void setTotal_page_num(int total_page_num) {
        this.total_page_num = total_page_num;
    }
    
    /**
     * 获取结束的条目位置，从1开始
     * 
     * @return
     */
    public int getEnd_index() {
        return end_index;
    }
    
    /**
     * 设置结束的条目位置，从1开始<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param end_index
     */
    public void setEnd_index(int end_index) {
        this.end_index = end_index;
    }
    
    /**
     * 获取该页项目数
     * 
     * @return
     */
    public int getCur_page_item_num() {
        return cur_page_item_num;
    }
    
    /**
     * 设置该页项目数<br />
     * <strong>该方法只作为自动注入调用，业务代码不应该调用该方法</strong>
     * @param cur_page_item_num
     */
    public void setCur_page_item_num(int cur_page_item_num) {
        this.cur_page_item_num = cur_page_item_num;
    }
    
    public Integer getOffset() {
        return start_index > 0 ? start_index - 1 : 0;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Pagination [page_size=").append(page_size).append(", total_item_num=")
                .append(total_item_num).append(", total_page_num=").append(total_page_num).append(", page_no=")
                .append(page_no).append(", start_index=").append(start_index).append(", end_index=")
                .append(end_index).append(", cur_page_item_num=").append(cur_page_item_num).append(", offset=")
                .append(getOffset()).append("]");
        return builder.toString();
    }
}
    
