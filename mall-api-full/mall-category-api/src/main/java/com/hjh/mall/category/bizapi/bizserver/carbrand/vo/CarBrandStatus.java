package com.hjh.mall.category.bizapi.bizserver.carbrand.vo;



import com.hjh.mall.common.core.annotation.EnumValue;
import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.field.type.Status;

import io.swagger.annotations.ApiModelProperty;


/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class CarBrandStatus extends HJYVO {
    private static final long serialVersionUID = 1L;
	// brand_id
    @ApiModelProperty(name = "brand_id",dataType="String",notes="品牌名称")
    private String brand_id;
    // 状态（0：禁用；1：启用） 
    @NotBlank
    @ApiModelProperty(name = "status",dataType="String",notes="状态")
    @EnumValue(enumClass = Status.class)
    private String status;

    public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	 //更新人
    private String update_user;
    
    private String update_user_name;
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
	public String getBrand_id() {
		return brand_id;
	}
	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
