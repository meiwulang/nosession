package com.hjh.mall.vo.feedback;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-middle
 * @Description 
 * @author 李慧峰
 * @date 2016年8月18日
 * @version V1.0
 */
public class FeedBackReplyVo extends HJYVO{
	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(max = 16)
	@ApiModelProperty(notes="id")
	private String feedback_id;//id
	@NotBlank
	@Length(max = 200)
	@ApiModelProperty(notes="回复内容")
	private String reply_content;//回复内容

	public String getFeedback_id() {
		return feedback_id;
	}
	public void setFeedback_id(String feedback_id) {
		this.feedback_id = feedback_id;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeedBackReplyVo [");
		if (feedback_id != null) {
			builder.append("feedback_id=");
			builder.append(feedback_id);
			builder.append(", ");
		}
		if (reply_content != null) {
			builder.append("reply_content=");
			builder.append(reply_content);
		}
		builder.append("]");
		return builder.toString();
	}
	


	
}
