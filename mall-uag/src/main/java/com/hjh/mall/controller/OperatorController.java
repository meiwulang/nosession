package com.hjh.mall.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.category.entity.CarBrand;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.filed.OperationType;
import com.hjh.mall.common.core.util.CookieUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.constants.SysContants;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.service.OperatorService;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.operator.OperatorVo;
import com.hjh.mall.vo.operator.QueryOperatorVo;
import com.hjh.mall.vo.operator.UpdateOperatorVo;

//保证线程安全
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OperatorController {

	@Resource
	private OperatorService operatorService;
	@Resource
	private BusiSessionHelper busiSessionHelper;

	@RequestMapping({ "/login", "/" })
	@NoLogin
	public String login() {
		return "login";
	}

	@RequestMapping(value="/html/{page}",method=RequestMethod.GET)
	public String toPage(@PathVariable String page) {
		return page;
	}

	@RequestMapping(value="/logindo",method=RequestMethod.POST)
	@NoLogin
	public @ResponseBody Map<String, Object> login(@RequestBody OperatorVo operatorVo, HttpServletRequest resq,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		Operator operator = operatorService.login(operatorVo);
		if (operator == null) {
			result.put(BasicFields.ERROR_INFO, "用户名或密码错误");
			return VOUtil.setErrorResult(result, "1");
		}
		resq.getSession().setAttribute("operator", operator);

		String access_token = saveNewClientIdSession(operator);
		result.put(BasicFields.ACCESS_TOKEN, access_token);
		// ULIBizDataContext.setAccess_token(access_token);
		HJYBizDataContext.setAccess_token(access_token);
		// 把access_token写到cookie
		CookieUtil.setCookie(response, SysContants.COOKIE_NAME_ACCESS_TOKEN, access_token, 60 * 60 * 24);

		return VOUtil.setSuccessResult(result);
	}

	@RequestMapping("/logout")
	public String loginout(HttpServletResponse response) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		CookieUtil.setCookieHttpOnly(response, SysContants.COOKIE_NAME_ACCESS_TOKEN, null);
		if (StringUtils.isNotEmpty(operator_id)) {
			removeOnlineClientIDSession(operator_id);
		}
		return "login";
	}

	private String saveNewClientIdSession(Operator operator) {
		String operator_id = operator.getOperator_id();
		// 删除在线用户
		removeOnlineClientIDSession(operator_id);
		// 保存用户信息
		SessionIdentity identity = new SessionIdentity();
		identity.setClientId(operator_id);
		identity.setOperatorName(operator.getOperator_name());
		identity.setUserType(SysContants.SESSION_USER_TYPE_OPERATOR);
		String access_token = busiSessionHelper.renewAccess_token(identity);
		saveNewClientIdSession(access_token, operator_id);
		return access_token;
	}

	private void removeOnlineClientIDSession(String operator_id) {
		// 根据client_id获取已登录的access_token，如果获取到access_token把当前的会话注销掉
		String clientIdToken = busiSessionHelper.getSessionTokenByClientId(operator_id);
		// 客户已经存在access_token，销毁会话
		if (StringUtils.isNotBlank(clientIdToken)) {
			// 销毁会话令牌
			busiSessionHelper.destroySession(clientIdToken);
		}
	}

	private void saveNewClientIdSession(String access_token, String operator_id) {
		// 获得client_id会话令牌key getClient_idKey
		String operator_noKey = busiSessionHelper.getClient_idKey(operator_id);
		// 根据会话令牌保存信息到缓存
		busiSessionHelper.setInfoForSession(operator_noKey, access_token);
	}

	@RequestMapping(value="/getAllOperators",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllOperators(@RequestBody HJYVO hjyvo) {
		return operatorService.getAllOperators();
	}

	@RequestMapping(value="/getOperators",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getOperators(@RequestBody QueryOperatorVo hjyvo) {
		return operatorService.queryByLike(hjyvo);
	}

	@RequestMapping(value="/updateOperator",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOperator(@RequestBody UpdateOperatorVo vo, HttpServletRequest request) {
		String operator = HJYBizDataContext.getSessionIdentity().getOperatorName();
		// 非admin管理员操作别人信息，返回权限不足
		if (StringUtil.isBlank(operator)
				|| (!vo.getOperator_name().equals(operator) && !MallFields.ADMIN.equals(operator))) {
			return VOUtil.genErrorResult(BasicErrorCodes.NO_PERMISSION);
		}
		// 自己重置自己的密码，返回权限不足
		if (vo.getOperator_name().equals(operator) && vo.getOperate_type().equals(OperationType.RESET_PWD.getVal())) {
			return VOUtil.genErrorResult(BasicErrorCodes.NO_PERMISSION);
		}
		// 自己禁用自己，返回权限不足
		if (vo.getOperator_name().equals(operator) && vo.getOperate_type().equals(OperationType.CHANGE_STATUS.getVal())) {
			return VOUtil.genErrorResult(BasicErrorCodes.NO_PERMISSION);
		}
		Operator entity = JSONUtil.trans(vo, Operator.class);
		operatorService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping(value="/addOperator",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOperator(@RequestBody UpdateOperatorVo vo, HttpServletRequest request) {
		String operator = HJYBizDataContext.getSessionIdentity().getOperatorName();
		if (StringUtil.isBlank(operator) || !MallFields.ADMIN.equals(operator)) {
			return VOUtil.genErrorResult(BasicErrorCodes.NO_PERMISSION);
		}

		if (operatorService.query(new Operator(vo.getOperator_name())).size() > 0) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.EXIST_ACCOUNT);
		}
		return operatorService.addOperator(vo);
	}

}
