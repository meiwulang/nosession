package com.hjh.mall.dao;

import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.ClientEnterprise;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月21日
 * @version V1.0
 */
public interface ClientEnterpriseDao extends DAOBase<ClientEnterprise, String> {
	public Map<String, Object> getEnterpriseByClientId(ClientEnterprise clientEnterprise);

}
