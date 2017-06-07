package com.hjh.mall.common.core.constants;

import com.hjh.mall.common.core.constants.BasicErrorCodes;

public interface ValidationErrorCodes {

	/*
	 * 参数错误：100 ~ 999
	 */
	/**
	 * 缺少参数
	 */
	public static final String PARAM_REQUIRED = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0101";
	/**
	 * 参数长度错误
	 */
	public static final String PARAM_LENGTH_ERROR = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0102";
	/**
	 * 格式错误
	 */
	public static final String PARAM_MALFORMED = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0103";
	/**
	 * 不是可选值
	 */
	public static final String PARAM_NOT_AN_OPTION = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0104";
	/**
	 * 参数超出范围
	 */
	public static final String PARAM_RANGE_ERROR = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0105";
	/**
	 * 参数不一致
	 */
	public static final String PARAM_INCONSISTENT = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0106";
	/**
	 * 参数不是日期时间
	 */
	public static final String PARAM_NOT_DATE = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0107";
	/**
	 * 参数时间过早
	 */
	public static final String PARAM_NOT_FUTURE = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0108";
	/**
	 * 参数时间过晚
	 */
	public static final String PARAM_NOT_PAST = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0109";
	/**
	 * 字符格式错误
	 */
	public static final String STRING_ERROR = BasicErrorCodes.__LEGBCS_ERROR_PREFIX + "0110";

}
