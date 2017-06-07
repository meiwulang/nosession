package com.hjh.mall.vo.feedback;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModelProperty;

public class FeedBackDelete extends PageQueryVO{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@ApiModelProperty(notes="id")
	private String feedback_id;



    /**
	 * @return the feedback_id
	 */
	public String getFeedback_id() {
		return feedback_id;
	}

	/**
	 * @param feedback_id the feedback_id to set
	 */
	public void setFeedback_id(String feedback_id) {
		this.feedback_id = feedback_id;
	}

   
}
