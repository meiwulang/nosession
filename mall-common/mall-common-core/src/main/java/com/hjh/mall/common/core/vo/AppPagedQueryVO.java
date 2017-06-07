/**
 * 
 */
package com.hjh.mall.common.core.vo;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.annotation.NotNull;
import com.hjh.mall.common.core.vo.LGSBCVOBase;

/**
 * @Project: hjy-common
 * @Description app分页操作时继承
 * @author 曾繁林
 * @date 2016年7月18日
 * @version V1.0 
 */
public class AppPagedQueryVO extends LGSBCVOBase{
	
	private static final long serialVersionUID = 1L;
	@NotNull
	private Integer page_size;//每页大小
	@NotBlank
	private String start_id;//起始ID
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
	public String getStart_id() {
		return start_id;
	}
	public void setStart_id(String start_id) {
		this.start_id = start_id;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AppPagedQueryVO [page_size=");
		builder.append(page_size);
		builder.append(", start_id=");
		builder.append(start_id);
		builder.append("]");
		return builder.toString();
	}
}
