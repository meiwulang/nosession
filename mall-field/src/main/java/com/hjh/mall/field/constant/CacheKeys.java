package com.hjh.mall.field.constant;

/**
 * @author chengjia
 *
 */
public class CacheKeys {

	public static final String NAMESPACE = "hjh.mall.";

	public static final String MALL_WEB_ACCESS_TOKEN_PREFIX = "hjh.hjy.access_token.";
	public static final String ACCESS_TOKEN_PREFIX = NAMESPACE + "access_token.";

	public static final String CLIENT_ID_PREFIX = NAMESPACE + "client_id.";

	public static final String SESSION_CLIENT_INFO_PREFIX = NAMESPACE + "session_client_info";

	public static final String OPERATOR_ID_PREFIX = NAMESPACE + "operator_id.";

	public static final String ROLE_PERMISSION_PREFIX = NAMESPACE + "role_permission.";

	public static final String OPERATOR_PERMISSION_PREFIX = NAMESPACE + "operator_permission.";

	public static final String COMCLIENT = NAMESPACE + "900001";

	// 角色权限集合
	public static final String CLIENT_ROLE_PREFIX = NAMESPACE + "client_role";
	public static final String CATEGORY_NAME_BY_CATEGORY_ID = NAMESPACE + ".category_name_by_category_id.";
	public static final String BRAND_NAME_BY_BRAND_ID = NAMESPACE + ".brand_name_by_brand_id.";

	// 存用户详情
	public static final String SESSION_CLIENT_DETAIL_PREFIX = NAMESPACE + "session_client_detail.";

	// 存用户企业详情
	public static final String CLIENT_ENTERPRISE_DETAIL_PREFIX = NAMESPACE + "client_enterprise_detail.";

	// 存元数据
	public static final String METADATA_ID_PREFIX = NAMESPACE + ".metadata_id.";
	// 存app展示全部类目
	public static final String ALL_LEVEL_NAVIGATION = NAMESPACE + ".first_level_navigation.";

	// 存车型
	public static final String CAR_MODELS_ID_PREFIX = NAMESPACE + ".car_models_id.";

	public static final String BRAND_CAR_METADATA_PREFIX = NAMESPACE + "brand.car.metadata.";

	public static final String BRAND_CAR_METADATA_NAMES_PREFIX = NAMESPACE + "brand.car.metadata:names:";

	public static String getCarModelsByBrandAndMetadata(String brand_id, String metadata_id) {
		return String.format(NAMESPACE + "brand_id:%s:%s:%s", brand_id, "metadata_id", metadata_id);

	}

	// 车辆品牌列表
	public static final String BRAND_CAR_LIST = NAMESPACE + "brand:list.car";
	// 车辆品牌列表锁
	public static final String BRAND_CAR_LIST_LOCK = NAMESPACE + "brand:list.car.lock";
	// 车辆首页品牌列表
	public static final String BRAND_CAR_FIRST = NAMESPACE + "brand:first.car";
	// 车辆首页品牌列表锁
	public static final String BRAND_CAR_FIRST_LOCK = NAMESPACE + "brand:first.car.lock";
	// 商品品牌列表
	public static final String BRAND_GOODS_LIST_PREFIX = NAMESPACE + "brand:list.goods";
	// 商品品牌列表锁
	public static final String BRAND_GOODS_LIST_LOCK = NAMESPACE + "brand:list.goods.lock";
	// 商品首页品牌列表
	public static final String BRAND_GOODS_FIRST_PREFIX = NAMESPACE + "brand:first.goods";
	// 商品首页品牌列表锁
	public static final String BRAND_GOODS_FIRST_LOCK = NAMESPACE + "brand:first.goods.lock";
	// 邀请码
	public static final String INVITE_CODE_INFO = NAMESPACE + "invite_code_info.";

}
