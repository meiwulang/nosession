package com.hjh.mall.vo.appversion;

import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.AppNameType;
import com.hjh.mall.field.type.AppType;

/**
 * @Project: hjy-middle
 * @Description app版本查询vo
 * @author 王斌
 * @date 2016年8月2日
 * @version V1.0
 */
public class QueryAppVersionVO extends HJYVO {
	private static final long serialVersionUID = 1L;
	@NotBlank
	@EnumValue(enumClass = AppType.class)
	private String app_type;// 更新安装包类型(0:安卓，1：ios)
	@NotBlank
	@EnumValue(enumClass = AppNameType.class)
	private String app_name;// 更新app名 0：集客/1：全知道,2 机惠多）

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	@Override
	public String toString() {
		return "QueryAppVersionVO [app_type=" + app_type + ", app_name=" + app_name + "]";
	}

}
