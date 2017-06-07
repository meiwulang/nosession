package com.hjh.mall.order.dto.common;

import java.text.MessageFormat;


public class APIResponse<T> implements java.io.Serializable {
	private static final long serialVersionUID = 12129127391L;


	public static final String NOT_INITIALIZED = "not-initialized";

	public static final String SUCCESS = "0";

	public static final String FAIL = "fail";

	public static final String VALIDATION_FAIL = "validation-fail";

	public static final String BAD_PARAMETER = "bad-parameter";

	public static final String UNAUTHORIZED = "unauthorized";

	public static final String USER_NOT_LOGIN = "user-not-login";

	public static final String RPC_FAIL = "rpc-fail";


	protected String code;
	protected String error_no = SUCCESS;
	protected String error_info;

	protected T data;

	protected String message;

	public APIResponse() {
		this(NOT_INITIALIZED, null, null);
	}

	public APIResponse(String code) {
		this(code, null, null);
		setError_no(code);
	}

	public APIResponse(String code, T data) {
		this(code, data, null);
		setError_no(code);
	}

	public APIResponse(String code, T data, String message) {
		this.code = code;
		this.data = data;
		setError_no(code);
		setError_info(message);
		this.message = message;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		setError_no(code);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
		setError_info(message);
	}


	public boolean isSuccess() {
		return SUCCESS.equals(this.getCode());
	}

	public static <T> APIResponse<T> success(T data) {
		return new APIResponse(SUCCESS, data);
	}

	public static APIResponse success() {
		return success(null);
	}

	public static boolean isSuccess(APIResponse apiResponse) {
		if (apiResponse == null) {
			return false;
		}

		return apiResponse.isSuccess();
	}

	public static APIResponse fail(String message) {
		return new APIResponse(FAIL, null, message);
	}

	public static APIResponse fail(Throwable t) {
		return fail(t.getMessage());
	}

	public static APIResponse widthCode(String code) {
		return new APIResponse(code);
	}

	public static <T> APIResponse<T> widthCode(String code, T data) {
		return new APIResponse<>(code, data);
	}

	public static APIResponse withCodeAndMessageArgs(String code, String... args) {
		APIResponse ret = widthCode(code, null);
		try {
			ret.setMessage(MessageFormat.format(ret.getMessage(), args));
		} catch (Exception e) {
			throw e;
		}
		return ret;
	}


	public static APIResponse response(String code) {
		return new APIResponse(code);
	}

	public static <T> APIResponse<T> response(String code, T data) {
		return new APIResponse(code, data);
	}

	public static void assertSuccess(APIResponse apiResponse) {
		if (!isSuccess(apiResponse)) {
			throw new RuntimeException(apiResponse.getMessage());
		}
	}

	public String getError_no() {
		return error_no;
	}

	public void setError_no(String error_no) {
		this.error_no = error_no;
	}

	public String getError_info() {
		return error_info;
	}

	public void setError_info(String error_info) {
		this.error_info = error_info;
	}

}
