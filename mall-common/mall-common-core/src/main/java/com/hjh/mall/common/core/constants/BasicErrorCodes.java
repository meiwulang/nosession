package com.hjh.mall.common.core.constants;

public class BasicErrorCodes {

	public static final String __LEGBCS_ERROR_PREFIX = "8888";

	/*
	 * 执行成功：0
	 */
	/**
	 * 执行成功
	 */
	public static final String SUCCESS = "0";

	/*
	 * 通用错误：1 ~ 99
	 */
	/**
	 * 执行错误
	 */
	public static final String COMMON_ERROR = __LEGBCS_ERROR_PREFIX + "0001";
	/**
	 * 业务服务错误
	 */
	public static final String SERVICE_ERROR = __LEGBCS_ERROR_PREFIX + "0002";
	/**
	 * 非法访问
	 */
	public static final String ILLEGAL_ACCESS = __LEGBCS_ERROR_PREFIX + "0003";
	/**
	 * 功能号不能为空
	 */
	public static final String SUCH_FUNCTION_ERROR = __LEGBCS_ERROR_PREFIX + "0004";
	/**
	 * 并发修改失败
	 */
	public static final String CONCURRENT_MODIFY = __LEGBCS_ERROR_PREFIX + "0010";
	/**
	 * 重复请求
	 */
	public static final String DUPLICATE_REQUEST = __LEGBCS_ERROR_PREFIX + "0011";
	/**
	 * 访问超时（包括connectTimeout的情况）
	 */
	public static final String ACCESS_TIMEOUT = __LEGBCS_ERROR_PREFIX + "0012";
	/**
	 * 处理超时（包括socketTimeout的情况）
	 */
	public static final String PROCESSING_TIMEOUT = __LEGBCS_ERROR_PREFIX + "0013";
	/**
	 * 数据库操作错误
	 */
	public static final String DB_OPERATE_ERROR = __LEGBCS_ERROR_PREFIX + "0014";
	/**
	 * 未登录或已超时
	 */
	public static final String NOT_LOGGED = __LEGBCS_ERROR_PREFIX + "0020";
	/**
	 * 没有权限
	 */
	public static final String NO_PERMISSION = __LEGBCS_ERROR_PREFIX + "0021";

	/**
	 * 功能号不匹配
	 */
	public static final String FUNCTION_NOT_MAPPING = __LEGBCS_ERROR_PREFIX + "0022";

	/**
	 * 客户不存在
	 */
	public static final String NO_SUCH_CLIENT = __LEGBCS_ERROR_PREFIX + "005";

	/**
	 * JSON无法转换
	 */
	public static final String NO_JSON_FORMATE = __LEGBCS_ERROR_PREFIX + "024";
	/*
	 * 参数错误：100 ~ 999
	 */
	/**
	 * 参数错误
	 */
	public static final String PARAM_ERROR = __LEGBCS_ERROR_PREFIX + "0100";

	/*
	 * 业务错误：1000 ~
	 */
}
