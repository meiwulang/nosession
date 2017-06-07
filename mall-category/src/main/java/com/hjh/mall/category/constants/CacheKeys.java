package com.hjh.mall.category.constants;

/**
 * @author chengjia
 *
 */
public class CacheKeys {

	public static final String NAMESPACE = "hjh:hjy:";

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

	// 存元数据
	public static final String METADATA_ID_PREFIX = NAMESPACE + ".metadata_id.";
	
	// 存车辆品牌
	public static final String CAR_BRAND_ID_PREFIX = NAMESPACE + "car_brand_id_prefix.";
	
	// 存商品品牌
	public static final String GOODS_BRAND_ID_PREFIX = NAMESPACE + "goods_brand_id_prefix.";

}
