package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.Operator;

public interface OperatorDao extends DAOBase<Operator, String> {
	public List<String> queryIdByName(String operator_name);

	/**
	 * @date 2016年10月31日
	 * @Description: 查询所有的id和name
	 * @author 杨益桦
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> querySimple();

	/**
	 * @date 2016年12月13日
	 * @Description: 模糊查询管理员列表
	 * @author：王斌
	 * @param exaple
	 * @return List<Operator>
	 */
	public List<Operator> queryByLike(Operator example);

	/**
	 * @date 2016年12月13日
	 * @Description: 模糊查询管理员列表
	 * @author：王斌
	 * @param exaple
	 * @return List<Operator>
	 */
	public int countByLike(Operator example);
}