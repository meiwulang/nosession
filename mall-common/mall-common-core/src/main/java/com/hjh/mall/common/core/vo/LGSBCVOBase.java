package com.hjh.mall.common.core.vo;

import com.hjh.mall.common.core.vo.LGSBCVO;

/**
 * @author chengjia
 *
 */
public class LGSBCVOBase implements LGSBCVO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
    protected String access_token;
        
    protected String functionId;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}


	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LGSBCVOBase [access_token=");
		builder.append(access_token);
		builder.append(", functionId=");
		builder.append(functionId);
		builder.append("]");
		return builder.toString();
	}


    
    
    

}
