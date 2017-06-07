package com.hjh.mall.common.core.filed;

public enum UploadType {

	/**
	 * 用户头像：1
	 */
	USERIMAGE("01", "USERIMAGE"),
	/**
	 * 圈子：2
	 */
	COMMUNITY("02", "COMMUNITY"),
	/**
	 * 产品：3
	 */
	PRODUCT("03", "PRODUCT"),
	/**
	 * 新闻：4
	 */
	NEWS("04", "NEWS"),
	/**
	 * 主页轮播：5
	 */
	MAIN("05", "MAIN"),
	/**
	 * 企业认证：6
	 */
	ENTERPRISE("06", "ENTERPRISE"),
	// 营业执照
	BUSSINESSLICENSE("07", "BUSSINESSLICENSE"),
	// 品牌授权
	BRANDLICENSE("08", "BRANDLICENSE"),
	// 技术证书
	TECHNICALCERTIFICATE("09", "TECHNICALCERTIFICATE"),
	// 名片正面
	CARDFRONT("10", "BRANDLICENSE"),
	// 名片反面面
	CARDBACK("11", "BRANDLICENSE"),
	// 店铺照片
	STOREIMG("12", "BRANDLICENSE"),

	/**
	 * 品牌LOGO
	 */
	BRAND_LOGO("13", "BRANDLOGO"),

	/**
	 * 实景图
	 */
	REAL_PICTURE("14", "REALPICTURE"),
	/**
	 * 轮播图
	 */
	SHUFFLING_PICTURE("15", "SHUFFLING_PICTURE"),
	/**
	 * 活动
	 */
	ACTIVITY("19", "ACTIVITY"),
	/**
	 * 活动
	 */
	CATEGORYLOGO("20", "CATEGORYLOGO"),
	/**
	 * 商品详情
	 */
	GOODS_DETAIL("21", "GOODS_DETAIL"),
	/**
	 * 商品banner图
	 */
	GOODS_BANNER("22", "GOODS_BANNER"),
	/**
	 * 商品占位图
	 */
	GOODS_AD("23", "GOODS_AD"),
	/**
	 * 商品首页展示
	 */
	GOODS_SHOW("24", "GOODS_SHOW"),
	/**
	 *付款凭证
	 */
	PAY_PROOF("25", "PAY_PROOF"),
	
	/**
	 *物流信息照片
	 */
	LOGISTICS_NOTE_SNAPSHOT("26", "LOGISTICS_NOTE_SNAPSHOT");

	private final String val;

	private final String description;

	private String toString;

	private UploadType(String val, String description) {
		this.val = val;
		this.description = description;
	}

	public String getVal() {
		return val;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		if (null == toString) {
			toString = new StringBuilder().append("UploadType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}

}
