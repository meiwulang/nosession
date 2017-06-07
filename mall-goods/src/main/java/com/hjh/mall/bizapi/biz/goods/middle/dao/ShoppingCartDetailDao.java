package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.ShoppingCartDetail;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface ShoppingCartDetailDao extends DAOBase<ShoppingCartDetail, String> {
	public ArrayList<Map<String, Object>> queryByLike(Map<String, Object> example);

	public ArrayList<Map<String, Object>> queryBydetails(ShoppingCartDetail example);

	public ArrayList<Map<String, Object>> getLastshopcartDetailListByPks(ShoppingCartDetail example);

	public int countByLike(Map<String, Object> example);

	public int batchSave(List<ShoppingCartDetail> inputParmas);

	public int batchDel(ShoppingCartDetail example);

	List<String> getshoppingCartStandards(ShoppingCartDetail example);

	/**
	 * @date 2017年5月25日
	 * @Description: 批量修改数据
	 * @author：王斌
	 * @param details
	 *            int
	 */
	int batchUpdateDetail(List<ShoppingCartDetail> details);
}