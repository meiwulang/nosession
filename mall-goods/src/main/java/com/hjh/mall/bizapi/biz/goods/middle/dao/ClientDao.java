package com.hjh.mall.bizapi.biz.goods.middle.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.bizapi.biz.goods.middle.entity.Client;
import com.hjh.mall.common.core.dao.base.DAOBase;

public interface ClientDao extends DAOBase<Client, String> {

	public Client getClientByTel(Client client);

	public int countClient(Map<String, Object> map);

	public List<Map<String, Object>> getClientList(Map<String, Object> map);

	public void updateStatus(Client client);

	public void resetPwd(Client client);

	public List<Map<String, String>> queryNickNamesByMobile(Client client);

	/**
	 * @date 2016年12月6日
	 * @Description:批量保存
	 * @author：王斌
	 * @param clients
	 *            void
	 */
	void batchSave(List<Client> clients);

	List<Map<String, Object>> queryByMobileAndName(Client client);

}
