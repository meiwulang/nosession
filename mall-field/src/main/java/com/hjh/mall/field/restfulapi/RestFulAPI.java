package com.hjh.mall.field.restfulapi;

public interface RestFulAPI {

	public static final String URLCONTENT = "/json/";

	public class User {

		/**
		 * 校验邀请码
		 */
		public static final String CHECK_INVITE_CODE = URLCONTENT + Constant.CHECK_INVITE_CODE;

		/**
		 * 获取验证码
		 */
		public static final String GETREGISTVERIFY = URLCONTENT + Constant.GET_VALIDATE_NUMBER;

		/**
		 * 用户注册
		 */
		public static final String REGIST = URLCONTENT + Constant.USER_REGIST_NUMBER;

		/**
		 * 用户登录
		 */
		public static final String LOGIN = URLCONTENT + Constant.USER_LOGIN_NUMBER;

		/**
		 * 获取用户信息
		 */
		public static final String GET_INFO = URLCONTENT + Constant.GET_CLIENT_INFO;

		/**
		 * 修改用户头像
		 */
		public static final String UPDATE_AVATAR = URLCONTENT + Constant.UPDATE_CLIENT_AVATAR;

		/**
		 * 修改用户信息
		 */
		public static final String UPDATE_INFO = URLCONTENT + Constant.UPDATE_CLIENT_INFO;

		/**
		 * 重置密码
		 */
		public static final String RESETPWD = URLCONTENT + Constant.RESETPWD;

		/**
		 * 修改密码
		 */
		public static final String UPDATEPWD = URLCONTENT + Constant.UPDATE_CLIENT_PWD;

		/**
		 * 修改电话号码
		 */
		public static final String UPDATE_MOBILE_TEL = URLCONTENT + Constant.UPDATE_MOBILE_TEL;
		/**
		 * 验证密码
		 */
		public static final String VALIDATE_PASSWD = URLCONTENT + Constant.VALIDATE_PASSWD;

		/**
		 * 登出
		 */
		public static final String LOGOUT = URLCONTENT + Constant.CLIENT_OUT;

		/**
		 * 修改手机号
		 */
		public static final String UPDATE_MOBILE = URLCONTENT + Constant.UPDATE_MOBILE;

		/**
		 * 修改手机号码时先校验密码
		 */
		public static final String CHECK_PWD = URLCONTENT + Constant.CHECK_PWD;

		/**
		 * 校验验证码
		 */
		public static final String CHECKVERIFY = URLCONTENT + Constant.CHECK_VALIDATE_NUMBER;

		/**
		 * 修改昵称
		 */
		public static final String UPDATE_NAME = URLCONTENT + Constant.UPDATE_NAME;

		/**
		 * 修改地址
		 */
		public static final String UPDATE_ADDRESS = URLCONTENT + Constant.UPDATE_ADDRESS;

		/**
		 * 修改性别
		 */
		public static final String UPDATE_SEX = URLCONTENT + Constant.UPDATE_SEX;

		/**
		 * 获取登录状态
		 */
		public static final String CHECK_LOGIN_STATUS = URLCONTENT + Constant.CHECK_LOGIN_STATUS;

	}

	public class Feedback {
		/**
		 * 意见反馈添加
		 */
		public static final String FEEDBACKADD = URLCONTENT + Constant.FEEDBACKADD;
		/**
		 * 显示意见反馈信息
		 */
		public static final String FEEDBACKQUERY = URLCONTENT + Constant.FEEDBACKQUERY;

		/**
		 * 回复意见反馈信息
		 */
		public static final String FEEDBACKREPLY = URLCONTENT + Constant.FEEDBACKREPLY;

		/**
		 * 删除意见反馈信息
		 */
		public static final String FEEDBACKDELETE = URLCONTENT + Constant.FEEDBACKDELETE;

	}

	public class Banner {
		/**
		 * 获取首页图片
		 */
		public static final String GET_BANNER_LIST = URLCONTENT + Constant.GET_BANNER_LIST;

	}

	public class GoodsCode {
		/**
		 * 查询solr
		 */
		public static final String QUERY_SOLR = URLCONTENT + Constant.QUERY_SOLR;

		/**
		 * 商品列表
		 */
		public static final String QUERY_GOODS_LIST = URLCONTENT + Constant.QUERY_GOODS_LIST;

		/**
		 * 查询单个商品
		 */
		public static final String GET_GOODS = URLCONTENT + Constant.GET_GOODS;

		/**
		 * app查询列表，使用page 和limit的分页参数
		 */
		public static final String QUERY_PAGE_LIMIT = URLCONTENT + Constant.QUERY_PAGE_LIMIT;

		/**
		 * 机型分页
		 */
		public static final String QUERY_MODEL_BY_GOODS = URLCONTENT + Constant.QUERY_MODEL_BY_GOODS;

	}

	public class OrderCode {

		/**
		 * 创建订单
		 */
		public static final String CREAT_EORDER = URLCONTENT + Constant.CREAT_EORDER;
		/**
		 * 查询订单
		 */
		public static final String QUERY_ORDER = URLCONTENT + Constant.QUERY_ORDER;
		/**
		 * 导出订单
		 */
		public static final String EXPORT_ORDER = URLCONTENT + Constant.EXPORT_ORDER;
		/**
		 * 查询我的订单
		 */
		public static final String QUERY_CLIENTS_ORDERS = URLCONTENT + Constant.QUERY_CLIENTS_ORDERS;

	}

	public class MetadataCode {

		/**
		 * 根据品牌ID获取机械类型
		 */
		public static final String QUERY_METADATA_BY_BRAND = URLCONTENT + Constant.QUERY_METADATA_BY_BRAND;

	}

	public class CarBrand {
		/*
		 * app车辆品牌,拼音排序
		 */
		public static final String CAR_BRAND_FIRST_PAGE_LIST = URLCONTENT + Constant.CAR_BRAND_FIRST_PAGE_LIST;

		/*
		 * 根据车辆所有品牌
		 */
		public static final String CAR_ALL_BRAND_LIST = URLCONTENT + Constant.CAR_ALL_BRAND_LIST;

		/*
		 * app车辆所有品牌，,sort排序
		 */
		public static final String CAR_BRAND_APP_SORT_LIST = URLCONTENT + Constant.CAR_BRAND_APP_SORT_LIST;
	}

	public class GoodsBrand {
		/*
		 * 商品app品牌,拼音排序
		 */
		public static final String GOODS_BRAND_FIRST_PAGE_LIST = URLCONTENT + Constant.GOODS_BRAND_FIRST_PAGE_LIST;

		/*
		 * 商品所有品牌
		 */
		public static final String GOODS_ALL_BRAND_LIST = URLCONTENT + Constant.GOODS_ALL_BRAND_LIST;

		/*
		 * 商品app品牌，sort 排序
		 */
		public static final String GOODS_BRAND_APP_SORT_LIST = URLCONTENT + Constant.GOODS_BRAND_APP_SORT_LIST;
	}

	public class ShoppingCart {
		/**
		 * 加入购物车
		 */
		public static final String ADD_PREORDER = URLCONTENT + Constant.ADD_PREORDER;
		/**
		 * 删除购物车单个商品
		 */
		public static final String DEL_PREORDER = URLCONTENT + Constant.DEL_PREORDER;
		/**
		 * 删除购物车多个商品
		 */
		public static final String BATCH_DEL_PREORDER = URLCONTENT + Constant.BATCH_DEL_PREORDER;
		/**
		 * 查询我的购物车商品
		 */
		public static final String QUERY_PREORDERS = URLCONTENT + Constant.QUERY_PREORDERS;
		/**
		 * 删除规格
		 */
		public static final String DEL_DETAIL = URLCONTENT + Constant.DEL_DETAIL;
		/**
		 * 修改规格信息
		 */
		public static final String UPDATE_DETAIL = URLCONTENT + Constant.UPDATE_DETAIL;
		/**
		 * 获取最新信息
		 */
		public static final String GET_LAST_INFO = URLCONTENT + Constant.GET_LAST_INFO;
	}

	public class Activity {
		/**
		 * 新增活动
		 */
		public static final String ADD_ACTIVITY = URLCONTENT + Constant.ADD_ACTIVITY;
		/**
		 * web分页查询活动
		 */
		public static final String QUERY_ACTIVITYS_FOR_WEB = URLCONTENT + Constant.QUERY_ACTIVITYS_FOR_WEB;
		/**
		 * app查询活动列表
		 */
		public static final String QUERY_ACTIVITYS_FOR_APP = URLCONTENT + Constant.QUERY_ACTIVITYS_FOR_APP;
		/**
		 * 更新活动
		 */
		public static final String UPDATE_ACTIVITY = URLCONTENT + Constant.UPDATE_ACTIVITY;
		/**
		 * 活动添加商品
		 */
		public static final String ADD_GOODS_FOR_ACTIVITY = URLCONTENT + Constant.ADD_GOODS_FOR_ACTIVITY;
		/**
		 * 活动删除商品
		 */
		public static final String DEL_ACTIVITYS_GOODS = URLCONTENT + Constant.DEL_ACTIVITYS_GOODS;
		/**
		 * 删除下架商品与活动关联关系
		 */
		public static final String DEL_GOODS_ACTIVITY = URLCONTENT + Constant.DEL_GOODS_ACTIVITY;
		/**
		 * web分页查询指定活动的商品
		 */
		public static final String QUERY_ACTIVITYS_GOODS = URLCONTENT + Constant.QUERY_ACTIVITYS_GOODS;
		/**
		 * app查询活动详情
		 */
		public static final String QUERY_ACTIVITYS_DETAIL = URLCONTENT + Constant.QUERY_ACTIVITYS_DETAIL;
		/**
		 * 上传图片
		 */
		public static final String UPLOAD_IMG = URLCONTENT + Constant.UPLOAD_IMG;
		/**
		 * 上传复文本
		 */
		public static final String UPLOAD_HTML = URLCONTENT + Constant.UPLOAD_HTML;
		/**
		 * 编辑复文本
		 */
		public static final String UPDATE_AND_UPLOAD_HTML = URLCONTENT + Constant.UPDATE_AND_UPLOAD_HTML;
		/**
		 * 获取复文本详情
		 */
		public static final String GET_HTML_INFO = URLCONTENT + Constant.GET_HTML_INFO;
		/**
		 * 获取未关联当前活动的商品
		 */
		public static final String GET_GOODS_WITHOUT_ACTIVITY = URLCONTENT + Constant.GET_GOODS_WITHOUT_ACTIVITY;
	}

	public class AppVersion {
		/**
		 * appVerion最新版本查询
		 */
		public static final String QUERYLASTVERSION = URLCONTENT + Constant.QUERYLASTVERSION;
		/**
		 * 更新app版本信息
		 */
		public static final String UPDATEAPPVERSIONINFO = URLCONTENT + Constant.UPDATEAPPVERSIONINFO;

		/**
		 * web端获取版本列表
		 */
		public static final String WEB_APP_VERSION_LIST = URLCONTENT + Constant.WEB_APP_VERSION_LIST;

		/**
		 * web端生效或者失效版本
		 */
		public static final String MAKE_ACTITY_OR_NOT = URLCONTENT + Constant.MAKE_ACTITY_OR_NOT;

		/**
		 * web 用
		 */
		public static final String QUERY_WEB = URLCONTENT + Constant.QUERY_WEB;

	}

}
