package com.hjh.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.ClientDetailDao;
import com.hjh.mall.entity.ClientDetail;
import com.hjh.mall.service.ClientDetailService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 客户详情业务层
 * @author 王斌
 * @date 2016年6月27日
 * @version V1.0
 */

@Service
public class ClientDetailServiceImpl extends HJYServiceImplBase<ClientDetail, String> implements ClientDetailService {
	@Resource
	private ClientDetailDao clientDetailDao;

	@Override
	protected DAOBase<ClientDetail, String> getDAO() {
		return clientDetailDao;
	}

	@Override
	public Map<String, String> getByClientId(ClientDetail clientDetail) {
		return clientDetailDao.getByClientId(clientDetail);
	}

	@Override
	@Transactional
	public void updateClientById(ClientDetail clientDetail) {
		clientDetailDao.updateClientById(clientDetail);
	}

	@Override
	public List<ClientDetail> getClientByIds(ClientDetail clientDetail) {
		// TODO Auto-generated method stub
		return clientDetailDao.getClientByIds(clientDetail);
	}

	@Override
	public int countByCommunity(ClientDetail clientDetail) {
		return clientDetailDao.countByCommunity(clientDetail);
	}

}
