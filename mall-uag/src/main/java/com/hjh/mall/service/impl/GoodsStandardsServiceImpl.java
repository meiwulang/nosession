package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.GoodsStandardsDao;
import com.hjh.mall.entity.GoodsStandards;
import com.hjh.mall.service.GoodsStandardsService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */

@Service
public class GoodsStandardsServiceImpl extends HJYServiceImplBase<GoodsStandards, String>
		implements
			GoodsStandardsService {
	@Resource
	private GoodsStandardsDao goodsStandardsDao;
	@Override
	protected DAOBase<GoodsStandards, String> getDAO() {
		return goodsStandardsDao;
	}

}
