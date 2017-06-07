package com.hjh.mall.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.vo.operator.GetOperatorByName;
import com.hjh.mall.vo.operator.OperatorVo;
import com.hjh.mall.vo.operator.QueryOperatorVo;
import com.hjh.mall.vo.operator.UpdateOperatorVo;

/**
 * @Project: hjy-middle
 * @Description 操作员业务层
 * @author 程嘉
 * @date 2016年07月25日
 * @version V1.0
 */
public interface OperatorService extends ServiceBase<Operator, String> {
	/**
	 * @date 2016年7月26日
	 * @Description: 管理员登录
	 * @param UpdateOperatorVo
	 * @return Operator
	 */
	public Operator login(OperatorVo operatorVo);

	/**
	 * @date 2016年9月26日
	 * @Description: 根据名字模糊查询
	 * @param UpdateOperatorVo
	 * @return Operator
	 */
	public List<String> queryIdByName(String operator_name);

	/**
	 * @date 2016年10月29日
	 * @Description: 在活动列表添加发布人的名称
	 * @author 杨益桦
	 * @param map
	 * @return Map<String,Object>
	 */
	public Map<String, Object> addOperatorNameToActivity(Map<String, Object> map);

	/**
	 * @date 2016年10月31日
	 * @Description: 查询所有启用的operator
	 * @author 杨益桦
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAllOperators();

	/**
	 * @date 2016年12月7日
	 * @Description: 添加管理员
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> addOperator(UpdateOperatorVo vo);

	/**
	 * @date 2016年12月13日
	 * @Description: 模糊查询管理员列表
	 * @author：王斌
	 * @param exaple
	 * @return List<Operator>
	 */
	Map<String, Object> queryByLike(QueryOperatorVo hjyvo);

	public Map<String, Object> getOperatorByName(GetOperatorByName getOperatorByName);

}
