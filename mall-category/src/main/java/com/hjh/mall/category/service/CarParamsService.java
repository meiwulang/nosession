package com.hjh.mall.category.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.CarParams;
import com.hjh.mall.common.core.service.base.ServiceBase;

/**
 * @Project: hjy-middle
 * @Description 车型业务层
 * @author 陈士俊
 * @date 2017年03月14日
 * @version V1.0
 */
public interface CarParamsService extends ServiceBase<CarParams, String> {
	public List<Map<String, String>> queryCarParamsByIds(List<String> ids);

	void batchUpdate(List<CarParams> carList);

	void batchSavaCarParams(List<CarParams> carList);

	void batchDelete(List<CarParams> carList);
}
