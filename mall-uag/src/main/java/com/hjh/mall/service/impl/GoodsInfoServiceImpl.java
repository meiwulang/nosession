package com.hjh.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.GoodsInfoDao;
import com.hjh.mall.entity.GoodsInfo;
import com.hjh.mall.service.GoodsInfoService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */

@Service
public class GoodsInfoServiceImpl extends HJYServiceImplBase<GoodsInfo, String> implements GoodsInfoService {
	@Resource
	private GoodsInfoDao goodsInfoDao;

	@Override
	protected DAOBase<GoodsInfo, String> getDAO() {
		return goodsInfoDao;
	}

}
