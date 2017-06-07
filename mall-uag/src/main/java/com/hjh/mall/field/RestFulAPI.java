package com.hjh.mall.field;

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
		 * 商品列表
		 */
		public static final String QUERY_GOODS_LIST = URLCONTENT + Constant.QUERY_GOODS_LIST;
		/**
		 * 查询单个商品
		 */
		public static final String QUERY_GOODS = URLCONTENT + Constant.QUERY_GOODS;
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

}
