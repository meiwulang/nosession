package com.hjh.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.sms.MD5Marker;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.dao.OperatorDao;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.service.OperatorService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.vo.operator.GetOperatorByName;
import com.hjh.mall.vo.operator.OperatorVo;
import com.hjh.mall.vo.operator.QueryOperatorVo;
import com.hjh.mall.vo.operator.UpdateOperatorVo;

/**
 * @Project: hjy-middle
 * @Description 操作员业务层
 * @author 王斌
 * @date 2016年6月27日
 * @version V1.0
 */

@Service
public class OperatorServiceImpl extends HJYServiceImplBase<Operator, String> implements OperatorService {
	@Resource
	private OperatorDao operatorDao;
	@Resource
	private KeyGenerate keyGenerate;

	@Override
	protected DAOBase<Operator, String> getDAO() {
		return operatorDao;
	}

	/**
	 * @date 2016年7月26日
	 * @Description: 用户登录
	 * @param clientLogin
	 * @return Map<String,Object>
	 */
	public Operator login(OperatorVo operatorVo) {
		Operator operator = new Operator();
		operator.setOperator_name(operatorVo.getOperator_name());
		operator.setPassword(operatorVo.getPassword());
		operator.setStatus("1");
		List<Operator> list = operatorDao.query(operator);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<String> queryIdByName(String operator_name) {
		return operatorDao.queryIdByName(operator_name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> addOperatorNameToActivity(Map<String, Object> map) {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) map.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> temp : resultList) {
			String operatorId = temp.get(MallFields.PUBLISH_CLIENT_ID).toString();
			String operatorName = operatorId;
			Operator operator = get(operatorId);
			// 在查到的情况下，优先使用商户名称
			if (operator != null) {
				operatorName = operator.getOperator_name();
			}
			temp.put(MallFields.PUBLISH_CLIENT_NAME, operatorName);
		}
		return map;
	}

	@Override
	public Map<String, Object> getAllOperators() {
		// Operator operator = new Operator();
		// operator.setStatus(BasicFields.ENABLE); status字段没有用起来
		List<Map<String, Object>> operators = operatorDao.querySimple();
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, operators);
		return result;
	}

	@Override
	public Map<String, Object> addOperator(UpdateOperatorVo vo) {
		Operator entity = JSONUtil.trans(vo, Operator.class);
		entity.setOperator_id(keyGenerate.getKeyGenerate(MallFields.OPERATOR_ID));
		entity.setPassword(MD5Marker.getMD5Str(entity.getPassword()));
		this.save(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> queryByLike(QueryOperatorVo hjyvo) {
		Operator example = new Operator();
		example.setOperator_id(hjyvo.getOperator_id());
		example.setOperator_name(hjyvo.getOperator_name());
		example.setStatus(hjyvo.getStatus());
		Pagination page = new Pagination();
		int size = hjyvo.getLimit();
		int start = hjyvo.getPage();
		page.setPage_size(size);
		page.setStart_index(start > 1 ? ((start - 1) * size + 1) : (start - 1) * size);

		example.setPage(page);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, operatorDao.queryByLike(example));
		result.put(BasicFields.TOTAL_NUM, operatorDao.countByLike(example));
		return result;
	}

	@Override
	public Map<String, Object> getOperatorByName(GetOperatorByName getOperatorByName) {
		Operator operator = JSONUtil.trans(getOperatorByName, Operator.class);
		List<Operator> list = operatorDao.queryByLike(operator);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

}
