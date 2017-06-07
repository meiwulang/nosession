package com.hjh.mall.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.ClientDetail;

/**
 * @Project: hjy-middle
 * @Description 客户详情业务层
 * @author 曾繁林
 * @date 2016年07月11日
 * @version V1.0
 */
public interface ClientDetailService extends ServiceBase<ClientDetail, String> {
	public Map<String, String> getByClientId(ClientDetail clientDetail);

	public void updateClientById(ClientDetail clientDetail);

	public List<ClientDetail> getClientByIds(ClientDetail clientDetail);

	public int countByCommunity(ClientDetail clientDetail);
}
