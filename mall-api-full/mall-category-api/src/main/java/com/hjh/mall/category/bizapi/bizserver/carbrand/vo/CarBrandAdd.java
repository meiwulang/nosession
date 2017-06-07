package com.hjh.mall.category.bizapi.bizserver.carbrand.vo;



import com.hjh.mall.common.core.annotation.NotBlank;
import com.hjh.mall.common.core.vo.HJYVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Project: mall-category-api
 * @Description TODO
 * @author 李慧峰
 * @date 2017年3月15日
 * @version V1.0 
 */
public class CarBrandAdd extends HJYVO {
    private static final long serialVersionUID = 1L;
	// 品牌名称
    @NotBlank
    @ApiModelProperty(name = "brand_name",dataType="String",notes="品牌名称")
    private String brand_name;
    // logo url 地址 
    @ApiModelProperty(name = "brand_logo",dataType="String",notes="logo url 地址 ")
    private String brand_logo;
    // 排序字段 
    @ApiModelProperty(name = "sort",dataType="String",notes="sort")
    private Integer sort;
    // 备注 
    @ApiModelProperty(name = "remark",dataType="String",notes="备注")
    private String remark;
    // 状态（0：禁用；1：启用） 
    @ApiModelProperty(name = "status",dataType="String",notes="状态（0：禁用；1：启用）")
    private String status;
    // 品牌拼音简写 
    @ApiModelProperty(name = "pinyin",dataType="String",notes="品牌拼音简写 ")
    private String pinyin;

    // 是否首页显示 ,默认首页不显示
    @ApiModelProperty(name = "is_top",dataType="String",notes="是否首页显示 ,默认首页不显示")
    private String is_top;
    //创建人
    private String create_user;
    //更新人

    private String update_user;

    private String update_user_name;
    // 创建时间 
    private String init_time;

    // 创建日期 
    private String init_date;
    
    private String update_date;
    
    private String update_time;

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public String getBrand_logo() {
		return brand_logo;
	}

	public void setBrand_logo(String brand_logo) {
		this.brand_logo = brand_logo;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getIs_top() {
		return is_top;
	}

	public void setIs_top(String is_top) {
		this.is_top = is_top;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getInit_time() {
		return init_time;
	}

	public void setInit_time(String init_time) {
		this.init_time = init_time;
	}

	public String getInit_date() {
		return init_date;
	}

	public void setInit_date(String init_date) {
		this.init_date = init_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user_name() {
		return update_user_name;
	}

	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarBrandAdd [brand_name=").append(brand_name)
				.append(", brand_logo=").append(brand_logo).append(", sort=")
				.append(sort).append(", remark=").append(remark)
				.append(", status=").append(status).append(", pinyin=")
				.append(pinyin).append(", is_top=").append(is_top)
				.append(", create_user=").append(create_user)
				.append(", update_user=").append(update_user)
				.append(", update_user_name=").append(update_user_name)
				.append(", init_time=").append(init_time)
				.append(", init_date=").append(init_date)
				.append(", update_date=").append(update_date)
				.append(", update_time=").append(update_time).append("]");
		return builder.toString();
	}
	
	

}
