/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 用户修改个人头像
 * @author 曾繁林
 * @date 2016年6月29日
 * @version V1.0
 */
@ApiModel
public class UpdateClientAvatar extends HJYVO {

	private static final long serialVersionUID = 1L;
	@EnumValue(enumClass = UploadType.class)
	@NotBlank
	@ApiModelProperty("图片类型")
	private String upload_type;// 图片类型
	@NotBlank
	@Length(min = 1, max = 50)
	@ApiModelProperty("图片路径")
	private String image_key;// 图片路径

	public String getUpload_type() {
		return upload_type;
	}

	public void setUpload_type(String upload_type) {
		this.upload_type = upload_type;
	}

	public String getImage_key() {
		return image_key;
	}

	public void setImage_key(String image_key) {
		this.image_key = image_key;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientAvatar [upload_type=");
		builder.append(upload_type);
		builder.append(", image_key=");
		builder.append(image_key);
		builder.append("]");
		return builder.toString();
	}

}
