/** * @author  csj
 * @date 创建时间：2017年3月15日 下午7:20:19 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.category.bizapi.bizserver.goodscar.vo;

import java.util.List;

import com.hjh.mall.common.core.vo.PageQueryVO;

public class AddGoodsCarModelsBatch extends PageQueryVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String goods_id;

	private List<String> carModelList;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public List<String> getCarModelList() {
		return carModelList;
	}

	public void setCarModelList(List<String> carModelList) {
		this.carModelList = carModelList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AddGoodsCarModelsBatch [goods_id=");
		builder.append(goods_id);
		builder.append(", carModelList=");
		builder.append(carModelList);
		builder.append("]");
		return builder.toString();
	}

}
