package com.hjh.mall.vo.banner;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.vo.WebPagedQueryVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjh-blog-api
 * @Description TODO
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
public class GetBannerListVo extends WebPagedQueryVO {
	private static final long serialVersionUID = 1L;
	private String title;
	private String category_id;
	@EnumValue(enumClass = Status.class)
	private String status;
	@ApiModelProperty(notes="是否跳转")
	private String is_skip;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIs_skip() {
		return is_skip;
	}

	public void setIs_skip(String is_skip) {
		this.is_skip = is_skip;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetBannerListVo [title=");
		builder.append(title);
		builder.append(", category_id=");
		builder.append(category_id);
		builder.append(", status=");
		builder.append(status);
		builder.append(", is_skip=");
		builder.append(is_skip);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
