package com.hjh.mall.entity;

import com.hjh.mall.common.core.entity.Updatable;

public class Feedback extends Updatable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String feedback_id;//编号

    private String type;//问题类型

    private String content;//反馈内容

    private String contact;//联系方式
    
    private String reply_content;//回复人
    
    private String reply_user;//回复人
    
    private String reply_date;//回复人
    
    private String source;//来源
    
    private String create_time;//创建日期
    
    private String create_date;//创建日期
    
    private String create_user;//创建人id
    
    private String create_user_name;//创建人姓名


    public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}



	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}



	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}



	/**
	 * @return the create_time
	 */
	public String getCreate_time() {
		return create_time;
	}



	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}



	/**
	 * @return the create_date
	 */
	public String getCreate_date() {
		return create_date;
	}



	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}



	/**
	 * @return the create_user
	 */
	public String getCreate_user() {
		return create_user;
	}



	/**
	 * @param create_user the create_user to set
	 */
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}



	/**
	 * @return the reply_content
	 */
	public String getReply_content() {
		return reply_content;
	}



	/**
	 * @param reply_content the reply_content to set
	 */
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}



	/**
	 * @return the reply_user
	 */
	public String getReply_user() {
		return reply_user;
	}



	/**
	 * @param reply_user the reply_user to set
	 */
	public void setReply_user(String reply_user) {
		this.reply_user = reply_user;
	}



	/**
	 * @return the reply_date
	 */
	public String getReply_date() {
		return reply_date;
	}



	/**
	 * @param reply_date the reply_date to set
	 */
	public void setReply_date(String reply_date) {
		this.reply_date = reply_date;
	}



	/**
	 * @return the init_date
	 */
	public String getInit_date() {
		return init_date;
	}



    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}
	public String getInit_time() {
		return init_time;
	}
	public void setInit_time(String init_time) {
		this.init_time = init_time;
	}

	public String getFeedback_id() {
        return feedback_id;
    }
}