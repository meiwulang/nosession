package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.GoodsPicDao;
import com.hjh.mall.entity.GoodsPic;
import com.hjh.mall.service.GoodsPicService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */

@Service
public class GoodsPicServiceImpl extends HJYServiceImplBase<GoodsPic, String>
		implements
			GoodsPicService {
	@Resource
	private GoodsPicDao goodsPicDao;
	@Override
	protected DAOBase<GoodsPic, String> getDAO() {
		return goodsPicDao;
	}

}
