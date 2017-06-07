/**
 * 
 */
package com.hjh.mall.type;

/**
 * @Project: hjy-filed
 * @Description 性别
 * @author 曾繁林
 * @date 2016年8月10日
 * @version V1.0 
 */
public enum SexType {

	/**
     * 男
     */
    MAN("1", "男"),
    
    /**
     * 女
     */
    WOMAN("0", "女"),
    
    ;
	
    private final String val;
    
    private final String description;
    
    private String toString;
    
    private SexType(String val, String description) {
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
            toString = new StringBuilder().append("SexType[").append(val).append(':').append(description)
                    .append(']').toString();
        }
        return toString;
    }
}
