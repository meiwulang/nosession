package com.hjh.mall.vo.feedback;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.vo.PageQueryVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class FeedBackQueryVo extends PageQueryVO{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Length(max = 2)
	@ApiModelProperty(notes="问题类型")
	private String type;//问题类型

	@Length(max = 50)
	@ApiModelProperty(notes="反馈内容")
	private String content;//反馈内容

	@Length(max = 50)
	@ApiModelProperty(notes="联系方式")
	private String contact;//联系方式
	@ApiModelProperty(notes="状态")
	private String status;
	@ApiModelProperty(notes="id")
	private String feedback_id;
	@ApiModelProperty(notes="查询开始时间")
	private String date_end;//查询开始时间
	@ApiModelProperty(notes="查询结束时间")
	private String date_start;//查询结束时间
	@ApiModelProperty(notes="反馈人id")
	private String create_user;//反馈人id
	@ApiModelProperty(notes="反馈人name")
	private String create_user_name;//反馈人name



    public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public String getDate_start() {
		return date_start;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

   
}
