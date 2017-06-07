package com.hjh.mall.dao;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.VerifyMsg;

public interface VerifyMsgDao extends DAOBase<VerifyMsg, String> {

	public int getRequestsTotal(String mobile_tel);

	public int getRequestsTimes(VerifyMsg verifyMsg);

	public VerifyMsg getVerifyMsg(VerifyMsg verifyMsg);

	public void deleteAllVerify();
}