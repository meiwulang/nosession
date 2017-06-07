package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.ClientAddressDao;
import com.hjh.mall.entity.ClientAddress;
import com.hjh.mall.service.ClientAddressService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjh-mall
 * @Description 收货地址业务层
 * @author csj
 * @date 2017年02月20日
 * @version V1.0
 */

@Service
public class ClientAddressServiceImpl extends HJYServiceImplBase<ClientAddress, String> implements ClientAddressService {
	@Resource
	private ClientAddressDao ClientAddressDao;

	@Override
	protected DAOBase<ClientAddress, String> getDAO() {
		return ClientAddressDao;
	}

}
