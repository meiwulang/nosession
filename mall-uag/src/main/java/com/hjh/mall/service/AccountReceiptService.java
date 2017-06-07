package com.hjh.mall.service;

import java.util.List;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.AccountReceipt;

/**
 * @Project: hjy-middle
 * @Description 收款账号业务层
 * @author boochy
 * @date 2017年05月18日
 * @version V1.0
 */
public interface AccountReceiptService extends ServiceBase<AccountReceipt, String> {
	
	public List<AccountReceipt> isExist(AccountReceipt example);

}
