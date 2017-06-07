package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.ClientEnterpriseDao;
import com.hjh.mall.entity.ClientEnterprise;
import com.hjh.mall.service.ClientEnterpriseService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月21日
 * @version V1.0
 */
@Service
public class ClientEnterpriseServiceImpl extends HJYServiceImplBase<ClientEnterprise, String>
		implements ClientEnterpriseService {

	@Resource
	private ClientEnterpriseDao clientEnterpriseDao;

	@Override
	protected DAOBase<ClientEnterprise, String> getDAO() {
		return clientEnterpriseDao;
	}

}
