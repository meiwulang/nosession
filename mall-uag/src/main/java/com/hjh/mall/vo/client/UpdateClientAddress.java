/**
 *
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.Length;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: hjy-api
 * @Description 修改用户的地址信息
 * @author 曾繁林
 * @date 2016年8月10日
 * @version V1.0
 */
@ApiModel
public class UpdateClientAddress extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	@Length(min = 1, max = 100)
	@ApiModelProperty("常住地址")
	private String address;// 常住地址
	@Length(min = 1, max = 100)
	@ApiModelProperty("地区编码")
	private String area_code;// 地区编码

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientAddress [address=");
		builder.append(address);
		builder.append(", area_code=");
		builder.append(area_code);
		builder.append("]");
		return builder.toString();
	}

}
