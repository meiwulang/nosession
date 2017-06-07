package com.hjh.mall.common.core.exception;

public class HJHBCSErrInfoException extends RuntimeException {
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    protected String errorNo;
    
    protected String errorInfo;
    
    protected String errorOn;
    
    protected Object errorValue;
    
    public HJHBCSErrInfoException(String errorNo, String errorInfo) {
        this(errorInfo, errorNo, errorInfo);
    }
    
    public HJHBCSErrInfoException(Throwable cause, String errorNo, String errorInfo) {
        this(errorInfo, cause, errorNo, errorInfo);
    }
    
    public HJHBCSErrInfoException(String message, String errorNo, String errorInfo) {
        super(message);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }
    
    public HJHBCSErrInfoException(String message, Throwable cause, String errorNo, String errorInfo) {
        super(message, cause);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
    }
    
    public HJHBCSErrInfoException(String errorNo, String errorInfo, String errorOn, Object errorValue) {
        this(buildErrorMessageWithDetail(errorInfo, errorOn, errorValue), errorNo, errorInfo, errorOn, errorValue);
    }
    
    public HJHBCSErrInfoException(String message, String errorNo, String errorInfo, String errorOn, Object errorValue) {
        super(message);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
        this.errorOn = errorOn;
        this.errorValue = errorValue;
    }
    
    public HJHBCSErrInfoException(String message, Throwable cause, String errorNo, String errorInfo, String errorOn,
            Object errorValue) {
        super(message, cause);
        this.errorNo = errorNo;
        this.errorInfo = errorInfo;
        this.errorOn = errorOn;
        this.errorValue = errorValue;
    }
    
    public HJHBCSErrInfoException(Throwable cause, String errorNo, String errorInfo, String errorOn, Object errorValue) {
        this(buildErrorMessageWithDetail(errorInfo, errorOn, errorValue), cause, errorNo, errorInfo, errorOn,
                errorValue);
    }
    
    public HJHBCSErrInfoException(String errorNo) {
        this.errorNo = errorNo;
    }
    
    public HJHBCSErrInfoException(Throwable cause, String errorNo) {
        super(cause);
        this.errorNo = errorNo;
    }
    
    public HJHBCSErrInfoException(String message, Throwable cause, String errorNo, Object errorValue) {
        this(message, cause, errorNo, null, null, errorValue);
    }
    
    public HJHBCSErrInfoException(String message, String errorNo, Object errorValue) {
        this(message, errorNo, null, null, errorValue);
    }
    
    public HJHBCSErrInfoException(String message, Throwable cause, String errorNo, String errorOn, Object errorValue) {
        this(message, cause, errorNo, null, errorOn, errorValue);
    }
    
    public static String buildErrorMessageWithDetail(String baseMessage, String errorOn, Object errorValue) {
        return new StringBuilder().append(null == baseMessage ? "" : baseMessage).append('[').append(errorOn)
                .append('=').append(errorValue).append(']').toString();
    }
    
    public boolean isErrorOf(String errorNo) {
        return null != this.errorNo && this.errorNo.equals(errorNo);
    }
    
    public boolean isErrorIn(String... errorNos) {
        if (null != this.errorNo) {
            if (null != errorNos && 0 < errorNos.length) {
                for (String errorNo : errorNos) {
                    if (this.errorNo.equals(errorNo)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public String getErrorNo() {
        return errorNo;
    }
    
    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }
    
    public String getErrorInfo() {
        return errorInfo;
    }
    
    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
    
    public String getErrorOn() {
        return errorOn;
    }
    
    public void setErrorOn(String errorOn) {
        this.errorOn = errorOn;
    }
    
    public Object getErrorValue() {
        return errorValue;
    }
    
    public void setErrorValue(Object errorValue) {
        this.errorValue = errorValue;
    }
    
}
