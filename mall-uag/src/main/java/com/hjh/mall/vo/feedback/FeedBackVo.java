package com.hjh.mall.vo.feedback;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.FeedBackType;

import io.swagger.annotations.ApiModelProperty;

public class FeedBackVo extends HJYVO{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(max = 2)
	@EnumValue(enumClass = FeedBackType.class)
	@ApiModelProperty(notes="问题类型")
	private String type;//问题类型
	@NotBlank
	@Length(max = 500)
	@ApiModelProperty(notes="反馈内容")
	private String content;//反馈内容
	
	@Length(max = 50)
	@ApiModelProperty(notes="联系方式")
	private String contact;//联系方式


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
