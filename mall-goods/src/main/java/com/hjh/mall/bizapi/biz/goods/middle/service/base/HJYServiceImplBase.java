package com.hjh.mall.bizapi.biz.goods.middle.service.base;

import org.springframework.transaction.annotation.Transactional;

import com.hjh.mall.bizapi.biz.goods.middle.util.OpMarkHelper;
import com.hjh.mall.common.core.entity.Pageable;
import com.hjh.mall.common.core.entity.Updatable;
import com.hjh.mall.common.core.service.base.ServiceImplBase;

public abstract class HJYServiceImplBase<T extends Pageable, K> extends ServiceImplBase<T, K> {
	@Transactional
	@Override
	public void save(T entity) {
		if (entity instanceof Updatable) {
			Updatable updatable = (Updatable) entity;
			OpMarkHelper.markOperator(updatable);
		}
		entity.initForClearNull();
		getDAO().save(entity);
		if (entity instanceof Updatable) {
			Updatable updatable = (Updatable) entity;
			updatable.setUpdate_version(0L);
		}
	}

	@Override
	public int update(T entity) {
		if (entity instanceof Updatable) {
			OpMarkHelper.markOperator((Updatable) entity);
		}
		return getDAO().update(entity);
	}

}
