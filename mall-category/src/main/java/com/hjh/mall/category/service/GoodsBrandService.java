package com.hjh.mall.category.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.GoodsBrand;
import com.hjh.mall.common.core.service.base.ServiceBase;

/**
 * @Project: hjy-middle
 * @Description 商品品牌业务层
 * @author boochy
 * @date 2017年03月15日
 * @version V1.0
 */
public interface GoodsBrandService extends ServiceBase<GoodsBrand, String> {
	/** 
	 * @date 2017年3月15日
	 * @Description: 模糊查询 ，获得数量
	 * @param example
	 * @return int
	 * int
	 */
	public int countBlur(Map example);
	    
	/** 
	 * @date 2017年3月15日
	 * @Description: 模糊查询 ，获得list
	 * @param example
	 * @return List<GoodsBrand>
	 * List<GoodsBrand>
	 */
	public List<GoodsBrand> queryBlur(Map example);
	
	/** 
	 * @date 2017年3月15日
	 * @Description: 判断是否存在重复名称
	 * @param example
	 * @return List<GoodsBrand>
	 * List<GoodsBrand>
	 */
	public List<GoodsBrand> isExist(GoodsBrand example);
	
	/** 
	 * @date 2017年3月22日
	 * @Description: 批量更改状态
	 * @param example
	 * void
	 */
	public void batchStatus(Map example);
	
	
}
