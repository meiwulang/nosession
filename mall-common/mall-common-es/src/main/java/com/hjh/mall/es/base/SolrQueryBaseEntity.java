package com.hjh.mall.es.base;

import com.hjh.mall.common.core.vo.WebPagedQueryVO;


public class SolrQueryBaseEntity extends WebPagedQueryVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Boolean iscounts = false;

	public Boolean getIscounts() {
		return iscounts;
	}

	public void setIscounts(Boolean iscounts) {
		this.iscounts = iscounts;
	}

}
