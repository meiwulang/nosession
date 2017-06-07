package com.hjh.mall.common.core.filed;

public enum LogInfoType {

	 /**
     * 新增：1
     */
    ADD("1", "新增"),    
    /**
     * 删除：2
     */
    DEL("2", "删除"),
    /**
     * 修改：3
     */
    MOD("3", "修改"), 
    /**
     * 查询：4
     */
    QUERY("4", "查询"),
    ;
    

    
    private final String val;
    
    private final String description;
    
    private String toString;
    
    private LogInfoType(String val, String description) {
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
			toString = new StringBuilder().append("LogInfoType[").append(val).append(':').append(description)
					.append(']').toString();
		}
		return toString;
	}
    

}
