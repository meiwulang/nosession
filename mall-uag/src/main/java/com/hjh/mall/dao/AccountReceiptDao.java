package com.hjh.mall.dao;

import java.util.List;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.AccountReceipt;
/**
 * @Project: hjy-middle
 * @Description 收款账号业务层
 * @author boochy
 * @date 2017年05月18日
 * @version V1.0
 */
public interface AccountReceiptDao extends DAOBase<AccountReceipt, String> {
	
	public List<AccountReceipt> isExist(AccountReceipt example);

}