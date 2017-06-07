/**
 * 
 */
package com.hjh.mall.vo.client;

import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjy-api
 * @Description 更改用户坐标
 * @author 曾繁林
 * @date 2016年7月16日
 * @version V1.0
 */
public class UpdateClientPosition extends HJYVO {

	private static final long serialVersionUID = 1L;
	@NotBlank
	private String position;// 坐标(23.12,12.23)

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UpdateClientPosition [position=");
		builder.append(position);
		builder.append("]");
		return builder.toString();
	}

}
