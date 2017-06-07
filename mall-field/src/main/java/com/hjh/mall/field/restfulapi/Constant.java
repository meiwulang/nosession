package com.hjh.mall.field.restfulapi;

/**
 * @Project: hjy-filed
 * @Description TODO
 * @author 徐庆火
 * @date 2016年07月06日
 * @version V1.0
 */
public class Constant {

	/**
	 * 校验邀请码
	 */
	public static final String CHECK_INVITE_CODE = "900201";

	/**
	 * 获取验证码
	 */
	public static final String GET_VALIDATE_NUMBER = "900202";

	/**
	 * 普通用户注册
	 */
	public static final String USER_REGIST_NUMBER = "900203";

	/**
	 * 用户登录
	 */
	public static final String USER_LOGIN_NUMBER = "900204";

	/**
	 * 用户退出
	 */
	public static final String CLIENT_OUT = "900205";

	/**
	 * 获取个人信息
	 */
	public static final String GET_CLIENT_INFO = "900206";

	/**
	 * 用户修改个人信息（头像）
	 */
	public static final String UPDATE_CLIENT_AVATAR = "900207";

	/**
	 * 用户修改个人信息
	 */
	public static final String UPDATE_CLIENT_INFO = "900208";

	/**
	 * 重置密码
	 */
	public static final String RESETPWD = "900209";

	/**
	 * 修改密码
	 */
	public static final String UPDATE_CLIENT_PWD = "900210";

	/**
	 * 用户修改手机号码
	 */
	public static final String UPDATE_MOBILE = "900211";

	/**
	 * 更改号码时先验证密码
	 */
	public static final String CHECK_PWD = "900212";

	/**
	 * 修改用户电话
	 */
	public static final String UPDATE_MOBILE_TEL = "900213";
	/**
	 * 验证密码
	 */
	public static final String VALIDATE_PASSWD = "900214";

	/**
	 * 校验验证码
	 */
	public static final String CHECK_VALIDATE_NUMBER = "900215";

	/**
	 * 修改昵称
	 */
	public static final String UPDATE_NAME = "900216";

	/**
	 * 修改地址
	 */
	public static final String UPDATE_ADDRESS = "900217";

	/**
	 * 修改地址
	 */
	public static final String UPDATE_SEX = "900218";

	/**
	 * 获取登录状态
	 */
	public static final String CHECK_LOGIN_STATUS = "900219";

	/**
	 * 意见反馈添加
	 */
	public static final String FEEDBACKADD = "900601";
	/**
	 * 显示意见反馈信息
	 */
	public static final String FEEDBACKQUERY = "900602";
	/**
	 * 回复意见反馈信息
	 */
	public static final String FEEDBACKREPLY = "900603";
	/**
	 * 删除意见反馈信息
	 */
	public static final String FEEDBACKDELETE = "900604";

	/**
	 * 获取首页图片
	 */
	public static final String GET_BANNER_LIST = "900701";

	// goods
	/**
	 * 商品列表
	 */
	public static final String QUERY_GOODS_LIST = "900301";
	/**
	 * 查询单个商品
	 */
	public static final String GET_GOODS = "900302";
	/**
	 * app查询列表，使用page 和limit的分页参数
	 */
	public static final String QUERY_PAGE_LIMIT = "900303";
	/**
	 * 机型分页
	 */
	public static final String QUERY_MODEL_BY_GOODS = "900304";

	/**
	 * 查询solr
	 */
	public static final String QUERY_SOLR = "900305";
	// goods
	// -------order start---------
	public static final String CREAT_EORDER = "900100";

	public static final String QUERY_ORDER = "900101";

	public static final String EXPORT_ORDER = "900102";

	public static final String QUERY_CLIENTS_ORDERS = "900103";

	// -------order end---------

	// -------元数据 start---------
	public static final String QUERY_METADATA_BY_BRAND = "900801";
	// -------元数据 end---------

	// car brand app显示品牌，拼音排序
	public static final String CAR_BRAND_FIRST_PAGE_LIST = "900901";
	// 所有品牌 ，拼音排序
	public static final String CAR_ALL_BRAND_LIST = "900902";
	// goods brand app显示品牌，排序字段排序
	public static final String CAR_BRAND_APP_SORT_LIST = "900903";
	// car brand end

	// goods brand app显示品牌，拼音排序
	public static final String GOODS_BRAND_FIRST_PAGE_LIST = "901001";
	// 所有品牌 ，拼音排序
	public static final String GOODS_ALL_BRAND_LIST = "901002";
	// goods end
	// goods brand app显示品牌，排序字段排序
	public static final String GOODS_BRAND_APP_SORT_LIST = "901003";
	// ----------购物车---------------
	public static final String ADD_PREORDER = "901101";
	public static final String DEL_PREORDER = "901102";
	public static final String BATCH_DEL_PREORDER = "901103";
	public static final String QUERY_PREORDERS = "901104";
	public static final String DEL_DETAIL = "901105";
	public static final String UPDATE_DETAIL = "901106";
	public static final String GET_LAST_INFO = "901107";
	// ----------购物车---------------
	// ----------活动---------------
	public static final String ADD_ACTIVITY = "901201";
	public static final String QUERY_ACTIVITYS_FOR_WEB = "901202";
	public static final String QUERY_ACTIVITYS_FOR_APP = "901203";
	public static final String UPDATE_ACTIVITY = "901204";
	public static final String ADD_GOODS_FOR_ACTIVITY = "901205";
	public static final String DEL_ACTIVITYS_GOODS = "901206";
	public static final String DEL_GOODS_ACTIVITY = "901207";
	public static final String QUERY_ACTIVITYS_GOODS = "901208";
	public static final String QUERY_ACTIVITYS_DETAIL = "901209";
	public static final String UPLOAD_IMG = "901210";
	public static final String UPLOAD_HTML = "901211";
	public static final String UPDATE_AND_UPLOAD_HTML = "901212";
	public static final String GET_HTML_INFO = "901213";
	public static final String GET_GOODS_WITHOUT_ACTIVITY = "901214";
	// ----------活动---------------

	// ----------版本控制-----------
	/**
	 * 查询app最新版本信息
	 */
	public static final String QUERYLASTVERSION = "100801";
	/**
	 * 更新app版本信息
	 */
	public static final String UPDATEAPPVERSIONINFO = "100802";

	/**
	 * web端获取版本列表
	 */
	public static final String WEB_APP_VERSION_LIST = "100803";

	/**
	 * web端生效或者失效版本
	 */
	public static final String MAKE_ACTITY_OR_NOT = "100804";

	/**
	 * web用
	 */
	public static final String QUERY_WEB = "100808";
	// ----------版本控制-----------
}
