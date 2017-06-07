package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.ClientDetail;

public interface ClientDetailDao extends DAOBase<ClientDetail, String> {
	public Map<String, String> getByClientId(ClientDetail clientDetail);

	public void updateClientById(ClientDetail clientDetail);

	public List<ClientDetail> getClientByIds(ClientDetail clientDetail);

	public int countByCommunity(ClientDetail clientDetail);

	/**
	 * @date 2016年12月6日
	 * @Description:批量添加
	 * @author：王斌
	 * @param clientDetails
	 *            void
	 */
	void batchSave(List<ClientDetail> clientDetails);
}