package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsInfoDao;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsInfo;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsInfoService;
import com.hjh.mall.bizapi.biz.goods.middle.service.base.HJYServiceImplBase;
import com.hjh.mall.common.core.dao.base.DAOBase;

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
