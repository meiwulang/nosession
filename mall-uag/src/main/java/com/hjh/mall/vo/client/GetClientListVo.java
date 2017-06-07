/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.vo.WebPagedQueryVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh-user-api
 * @Description web端获取会员列表
 * @author 曾繁林
 * @date 2016年9月14日
 * @version V1.0
 */
@ApiModel
public class GetClientListVo extends WebPagedQueryVO {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("用户编号")
	private String client_id;
	@ApiModelProperty("账号状态")
	private String status;// 账号状态
	@ApiModelProperty("用户昵称")
	private String nick_name;// 用户昵称
	@ApiModelProperty("联系电话")
	private String mobile_tel;// 联系电话
	@ApiModelProperty("分页参数")
	private Pagination pagination;
	@ApiModelProperty("开始时间")
	private String start_date;// 开始时间
	@ApiModelProperty("结束时间")
	private String end_date;// 结束时间
	@ApiModelProperty("邀请码")
	private String invite_code;// 邀请码
	@ApiModelProperty("用户编号")
	private String client_code;// 用户编号
	@ApiModelProperty("邀请码start")
	private String invite_code_start;// 邀请码start
	@ApiModelProperty("邀请码end")
	private String invite_code_end;// 邀请码end

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getMobile_tel() {
		return mobile_tel;
	}

	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getInvite_code_start() {
		return invite_code_start;
	}

	public void setInvite_code_start(String invite_code_start) {
		this.invite_code_start = invite_code_start;
	}

	public String getInvite_code_end() {
		return invite_code_end;
	}

	public void setInvite_code_end(String invite_code_end) {
		this.invite_code_end = invite_code_end;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetClientListVo [client_id=");
		builder.append(client_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", nick_name=");
		builder.append(nick_name);
		builder.append(", mobile_tel=");
		builder.append(mobile_tel);
		builder.append(", pagination=");
		builder.append(pagination);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append(", end_date=");
		builder.append(end_date);
		builder.append(", invite_code=");
		builder.append(invite_code);
		builder.append(", client_code=");
		builder.append(client_code);
		builder.append(", invite_code_start=");
		builder.append(invite_code_start);
		builder.append(", invite_code_end=");
		builder.append(invite_code_end);
		builder.append("]");
		return builder.toString();
	}
}
