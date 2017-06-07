/** * @author  csj
 * @date 创建时间：2017年4月5日 上午10:10:23 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.util;

import java.util.List;

public class ThreadUtil implements Runnable {
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public void run() {

	}

}
