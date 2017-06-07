package com.hjh.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.AccountReceiptDao;
import com.hjh.mall.entity.AccountReceipt;
import com.hjh.mall.service.AccountReceiptService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 收款账号业务层
 * @author boochy
 * @date 2017年05月18日
 * @version V1.0
 */

@Service
public class AccountReceiptServiceImpl extends HJYServiceImplBase<AccountReceipt, String>
		implements
			AccountReceiptService {
	@Resource
	private AccountReceiptDao accountReceiptDao;
	@Override
	protected DAOBase<AccountReceipt, String> getDAO() {
		return accountReceiptDao;
	}
	
	@Override
	public List<AccountReceipt> isExist(AccountReceipt example) {
		return accountReceiptDao.isExist(example);
	}

}
