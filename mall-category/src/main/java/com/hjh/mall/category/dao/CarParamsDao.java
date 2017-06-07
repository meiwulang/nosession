package com.hjh.mall.category.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.category.entity.CarParams;
import com.hjh.mall.common.core.dao.base.DAOBase;

/**
 * @Project: hjy-middle
 * @Description 车型业务层
 * @author 陈士俊
 * @date 2017年03月14日
 * @version V1.0
 */
public interface CarParamsDao extends DAOBase<CarParams, String> {
	List<Map<String, String>> queryCarParamsByIds(List<String> ids);

	void batchUpdate(List<CarParams> carList);

	void batchSavaCarParams(List<CarParams> carList);

	void batchDeleteCarParams(List<CarParams> carList);
}