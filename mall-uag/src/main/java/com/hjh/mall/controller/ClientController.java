package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.CookieUtil;
import com.hjh.mall.common.core.util.ExcelTemplate;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.constants.SysContants;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Client;
import com.hjh.mall.entity.ClientEnterprise;
import com.hjh.mall.entity.Invitation;
import com.hjh.mall.field.Constant;
import com.hjh.mall.field.MallErrorCode;
import com.hjh.mall.field.RestFulAPI;
import com.hjh.mall.field.constant.CacheKeys;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.service.ClientEnterpriseService;
import com.hjh.mall.service.ClientService;
import com.hjh.mall.service.InvitationService;
import com.hjh.mall.service.PictureService;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.UpdateEnterprise;
import com.hjh.mall.vo.client.CheckInviteCode;
import com.hjh.mall.vo.client.CheckPwd;
import com.hjh.mall.vo.client.CheckVerify;
import com.hjh.mall.vo.client.ClientLogin;
import com.hjh.mall.vo.client.GetClientListVo;
import com.hjh.mall.vo.client.GetVerify;
import com.hjh.mall.vo.client.RegistClient;
import com.hjh.mall.vo.client.ResetPwd;
import com.hjh.mall.vo.client.ResetPwdVo;
import com.hjh.mall.vo.client.UpdateClient;
import com.hjh.mall.vo.client.UpdateClientAddress;
import com.hjh.mall.vo.client.UpdateClientAvatar;
import com.hjh.mall.vo.client.UpdateClientName;
import com.hjh.mall.vo.client.UpdateClientPwd;
import com.hjh.mall.vo.client.UpdateClientSex;
import com.hjh.mall.vo.client.UpdateMoblie;
import com.hjh.mall.vo.client.UpdateStatusVo;
import com.hjh.mall.vo.client.enterprise.EnterpriseQueryVo;
import com.hjh.mall.vo.client.enterprise.EnterpriseUpdateVo;

import io.swagger.annotations.ApiOperation;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 曾繁林
 * @date 2017年2月20日
 * @version V1.0
 */
@Controller
public class ClientController {
	@Resource
	private ClientService clientService;
	@Resource
	private BusiSessionHelper busiSessionHelper;
	@Resource
	private PictureService pictureService;

	@Resource
	private InvitationService invitationService;

	@Resource
	private ClientEnterpriseService clientEnterpriseService;
	@Resource
	private CacheHelper cacheHelper;

	/**
	 * 获取验证码
	 *
	 * @param getVerify
	 * @return
	 */
	@NoLogin
	@RequestMapping(value=RestFulAPI.User.GETREGISTVERIFY,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getValidate(@RequestBody GetVerify getVerify) {
		getVerify.setFunctionId(Constant.GET_VALIDATE_NUMBER);
		return clientService.getVerify(getVerify);
	}

	/**
	 * 校验邀请码
	 *
	 * @param getVerify
	 * @return
	 */
	@NoLogin
	@RequestMapping(value=RestFulAPI.User.CHECK_INVITE_CODE,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkInviteCode(@RequestBody CheckInviteCode checkInviteCode) {
		checkInviteCode.setFunctionId(Constant.CHECK_INVITE_CODE);
		Invitation invitation = new Invitation();
		invitation.setInvite_code(checkInviteCode.getInvite_code());
		List<Invitation> list = invitationService.query(invitation);
		Map<String, Object> result = VOUtil.genEmptyResult();
		if (list != null && list.size() > 0) {
			Client client = new Client();
			client.setInvite_code(checkInviteCode.getInvite_code());
			List<Client> list2 = clientService.query(client);
			if (list2 != null && list2.size() > 0) {
				result.put(BasicFields.ERROR_NO, MallErrorCode.InviteCodeErrorCode.INVIT_CODE_IS_NULL);
			} else {
				result.put(BasicFields.ERROR_NO, BasicFields.SUCCESS);
			}
		} else {
			result.put(BasicFields.ERROR_NO, MallErrorCode.InviteCodeErrorCode.INVIT_CODE_IS_NULL);
		}
		return result;
	}

	/**
	 * @Description 用户操作
	 * @param registClient
	 * 900203
	 * @return
	 */
	@NoLogin
	@RequestMapping(value=RestFulAPI.User.REGIST,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerUser(@RequestBody RegistClient registClient, HttpServletResponse response) {
		registClient.setFunctionId(Constant.USER_REGIST_NUMBER);
		Map<String, Object> result = clientService.registClient(registClient);
		String user_token = (String) result.get(BasicFields.ACCESS_TOKEN);// 从结果中获取user_token
		if (!StringUtil.isBlank(user_token)) {
			HJYBizDataContext.setAccess_token(user_token);
			// 把access_token写到cookie
			CookieUtil.setCookieHttpOnly(response, SysContants.COOKIE_NAME_ACCESS_TOKEN, user_token);
		}
		return result;
	}

	@NoLogin
	@RequestMapping(value=RestFulAPI.User.LOGIN,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestBody ClientLogin clientLogin, HttpServletResponse response) {
		clientLogin.setFunctionId(Constant.USER_LOGIN_NUMBER);

		Map<String, Object> result = clientService.login(clientLogin);

		String user_token = (String) result.get(BasicFields.ACCESS_TOKEN);// 从结果中获取access_token
		if (!StringUtil.isBlank(user_token)) {
			HJYBizDataContext.setAccess_token(user_token);
			// 把access_token写到cookie
			CookieUtil.setCookieHttpOnly(response, SysContants.COOKIE_NAME_ACCESS_TOKEN, user_token);
		}

		return result;
	}

	@RequestMapping(value=RestFulAPI.User.LOGOUT,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(@RequestBody HJYVO hjyvo) {
		hjyvo.setFunctionId(Constant.CLIENT_OUT);
		// 删除用户会话
		return clientService.logOut(hjyvo);
	}

	/**
	 * @Decription 获取用户信息。端口号900206
	 * @author boochy (修改 2017.5.25，增加用户企业信息)
	 * @param hjyvo
	 * @return
	 */
	@RequestMapping(value=RestFulAPI.User.GET_INFO,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getClientInfo(@RequestBody HJYVO hjyvo) {
		hjyvo.setFunctionId(Constant.GET_CLIENT_INFO);
		System.out.println(HJYBizDataContext.getSessionIdentity().getEnterprise_linkman());
		System.out.println(HJYBizDataContext.getSessionIdentity().getInvite_code());
		System.out.println(HJYBizDataContext.getSessionIdentity().getMobile_tel());
		return clientService.getClientInfo(hjyvo);
	}

	@RequestMapping(value=RestFulAPI.User.UPDATE_AVATAR,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAvatar(@RequestBody UpdateClientAvatar updateClientAvatar) {
		updateClientAvatar.setFunctionId(Constant.UPDATE_CLIENT_AVATAR);
		return clientService.updateClientAvatar(updateClientAvatar);
	}

	@RequestMapping(value=RestFulAPI.User.UPDATE_INFO,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateClientInfo(@RequestBody UpdateClient updateClient) {
		updateClient.setFunctionId(Constant.UPDATE_CLIENT_INFO);
		return clientService.updateClientInfo(updateClient);
	}

	// 重置密码
	@NoLogin
	@RequestMapping(value=RestFulAPI.User.RESETPWD,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPwd(@RequestBody ResetPwd resetPwd) {
		resetPwd.setFunctionId(Constant.RESETPWD);
		return clientService.resetPwd(resetPwd);
	}

	// 修改密码
	@RequestMapping(value=RestFulAPI.User.UPDATEPWD,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePwd(@RequestBody UpdateClientPwd updateClientPwd) {
		updateClientPwd.setFunctionId(Constant.UPDATE_CLIENT_PWD);
		return clientService.updateClientPwd(updateClientPwd);
	}

	// 修改手机号码
	@RequestMapping(value=RestFulAPI.User.UPDATE_MOBILE,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMobile(@RequestBody UpdateMoblie updateMoblie) {
		updateMoblie.setFunctionId(Constant.UPDATE_MOBILE);
		CheckPwd checkPwd = new CheckPwd();
//		checkPwd.setUser_token(updateMoblie.getUser_token());
		checkPwd.setPassword(updateMoblie.getPassword());
		Map<String, Object> checkPwdResult = clientService.checkPwd(checkPwd);
		if (checkPwdResult.get(BasicFields.ERROR_NO).toString().equals(BasicFields.SUCCESS)) {// 先校验密码的正确性
			return clientService.updateMoblie(updateMoblie);
		}
		return checkPwdResult;
	}

	// 修改手机号码是先校验密码
	@RequestMapping(value=RestFulAPI.User.CHECK_PWD,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPwd(@RequestBody CheckPwd checkPwd) {
		checkPwd.setFunctionId(Constant.CHECK_PWD);
		return clientService.checkPwd(checkPwd);
	}

	// 校验验证码
	@NoLogin
	@RequestMapping(value=RestFulAPI.User.CHECKVERIFY,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkVerify(@RequestBody CheckVerify checkVerify) {
		checkVerify.setFunctionId(Constant.CHECK_VALIDATE_NUMBER);
		return clientService.checkVerify(checkVerify);
	}

	// 修改昵称
	@RequestMapping(value=RestFulAPI.User.UPDATE_NAME,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateName(@RequestBody UpdateClientName updateClientName) {
		updateClientName.setFunctionId(Constant.UPDATE_NAME);
		return clientService.updateClientName(updateClientName);
	}

	// 修改地址
	@RequestMapping(value=RestFulAPI.User.UPDATE_ADDRESS,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddress(@RequestBody UpdateClientAddress updateClientAddress) {
		updateClientAddress.setFunctionId(Constant.UPDATE_ADDRESS);
		return clientService.updateClientAddress(updateClientAddress);
	}

	// 修改性别
	@RequestMapping(value=RestFulAPI.User.UPDATE_SEX,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSex(@RequestBody UpdateClientSex updateClientSex) {
		updateClientSex.setFunctionId(Constant.UPDATE_SEX);
		return clientService.updateClientSex(updateClientSex);
	}

	@NoLogin
	@RequestMapping(value=RestFulAPI.User.CHECK_LOGIN_STATUS,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkLoginStatus(@RequestBody HJYVO hjyvo) {
		String user_token = hjyvo.getAccess_token();
		SessionIdentity sessionIdentity = busiSessionHelper.fetchSessionIdentityByAccess_token(user_token);
		Map<String, Object> result = VOUtil.genEmptyResult();
		if (sessionIdentity != null) {// 登录状态
			String business_hotline = "";
			if (StringUtils.isNotBlank(sessionIdentity.getInvite_code())) {
				Map<String, String> map = cacheHelper
						.hgetAll(CacheKeys.INVITE_CODE_INFO + sessionIdentity.getInvite_code());
				business_hotline = map.get(MallFields.BUSINESS_HOTLINE);
				if (map.get(MallFields.INVITE_CODE) == null) {
					Invitation invitation = new Invitation();
					invitation.setInvite_code(sessionIdentity.getInvite_code());
					List<Invitation> list = invitationService.query(invitation);
					if (list != null && list.size() > 0) {
						business_hotline = list.get(0).getBusiness_hotline();
					}
				}

			}
			result.put(BasicFields.ERROR_NO, BasicFields.SUCCESS);
			result.put(MallFields.BUSINESS_HOTLINE, business_hotline);
		} else {
			result.put(BasicFields.ERROR_NO, BasicErrorCodes.NOT_LOGGED);
		}
		return result;
	}

	// 获取用户列表
	@RequestMapping(value="/getClientList",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getClientList(@RequestBody GetClientListVo getClientListVo) {
		// getClientListVo.setFunctionId("111111");
		return clientService.getClientList(getClientListVo);
	}

	// 修改用户状态
	@RequestMapping(value="/updateClientStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStatus(@RequestBody UpdateStatusVo updateStatusVo) {
		// updateStatusVo.setFunctionId("600114");
		return clientService.updateStatus(updateStatusVo);
	}

	// 重置密码
	@RequestMapping(value="/resetPwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPwd(@RequestBody ResetPwdVo resetPwdVo) {
		// resetPwdVo.setFunctionId("600115");
		return clientService.resetPwd(resetPwdVo);
	}

	// 修改用户信息
	@RequestMapping(value="updateClientInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateClientInfoWeb(@RequestBody UpdateClient updateClient) {
		// resetPwdVo.setFunctionId("600115");
		return clientService.updateClientInfo(updateClient);
	}
	
	
	
	
	/**
	 * @Description 修改客户企业信息app 使用
	 * @param vo
	 * @return
	 */
	@ApiOperation(value = "修改客户企业信息app", notes = "修改客户企业信息app")
	@RequestMapping(value="json/enterprise/update",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEnterprise(@RequestBody EnterpriseUpdateVo vo) {
		// resetPwdVo.setFunctionId("600115");
		ClientEnterprise clientEnterprise = new ClientEnterprise();
		clientEnterprise.setClient_enterprise_id(vo.getClient_enterprise_id());
		//clientEnterprise.setEnterprise_short_name(updateEnterprise.getEnterprise_short_name());
		clientEnterprise.setEnterprise_province(vo.getEnterprise_province());
		clientEnterprise.setEnterprise_city(vo.getEnterprise_city());
		clientEnterprise.setEnterprise_area(vo.getEnterprise_area());
		clientEnterprise.setEnterprise_street(vo.getEnterprise_street());
		clientEnterprise.setEnterprise_linkman(vo.getEnterprise_linkman());
		clientEnterprise.setEnterprise_tel(vo.getEnterprise_tel());
		clientEnterprise.setEnterprise_name(vo.getEnterprise_name());
		clientEnterprise.setMajor_business(vo.getMajor_business());
		StringBuilder sb=new StringBuilder();
		sb.append(vo.getEnterprise_province()).append(vo.getEnterprise_city()).append(vo.getEnterprise_area()).append(vo.getEnterprise_street());
		clientEnterprise.setEnterprise_address(sb.toString());
		clientEnterpriseService.update(clientEnterprise);
		return VOUtil.genSuccessResult();
		
	}
	
	
	/**
	 * Description 查看客户企业信息 app使用
	 * @param 通过id 或者 client＿id　　都可使用
	 * @return
	 */
	@ApiOperation(value = "查看客户企业信息", notes = "查看客户企业信息app")
	@RequestMapping(value="json/enterprise/find",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getEnterprise(@RequestBody EnterpriseQueryVo vo) {
		// resetPwdVo.setFunctionId("600115");
		ClientEnterprise clientEnterprise = new ClientEnterprise();
		//clientEnterprise.setClient_enterprise_id(vo.getClient_enterprise_id());
		clientEnterprise.setClient_id(HJYBizDataContext.getSessionIdentity().getClientId());
		List<ClientEnterprise> list=clientEnterpriseService.query(clientEnterprise);
		Map<String,Object> result=VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	

	// 修改公司简称
	@RequestMapping(value="/updateEnterpriseName",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEnterpriseName(@RequestBody UpdateEnterprise updateEnterprise) {
		// resetPwdVo.setFunctionId("600115");
		ClientEnterprise clientEnterprise = new ClientEnterprise();
		clientEnterprise.setClient_enterprise_id(updateEnterprise.getClient_enterprise_id());
		clientEnterprise.setEnterprise_short_name(updateEnterprise.getEnterprise_short_name());
		clientEnterpriseService.update(clientEnterprise);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping(value="/export_client_excel",method=RequestMethod.GET)
	public void exportExcel(GetClientListVo getClientListVo, HttpServletResponse response) throws IOException {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "机惠多用户列表" + System.currentTimeMillis();
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) clientService.getClientList(getClientListVo)
				.get(BasicFields.RESULT_LIST);
		exportToExcel(fOut, list);
		fOut.flush();
		fOut.close();
	}

	public void exportToExcel(OutputStream opts, List<Map<String, Object>> list) {
		ExcelTemplate et = ExcelTemplate.getInstance().readTemplateByClasspath("/excel/user_center.xls");
		et.createNewRow();
		if (list != null) {
			int j = 1;
			for (int i = 0; i < list.size(); i++) {
				et.createCell(j++);
				et.createCell(list.get(i).get("client_code").toString());
				if (list.get(i).get("invite_code") != null) {
					et.createCell(list.get(i).get("invite_code").toString());
				} else {
					et.createCell(" ");
				}

				if (list.get(i).get(MallFields.NICK_NAME) != null) {
					et.createCell(list.get(i).get(MallFields.NICK_NAME).toString());
				} else {
					et.createCell(" ");
				}

				if (list.get(i).get(MallFields.SEX) != null) {
					et.createCell(list.get(i).get(MallFields.SEX).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ADDRESS) != null) {
					et.createCell(list.get(i).get(MallFields.ADDRESS).toString());
				} else {
					et.createCell(" ");
				}
				et.createCell(list.get(i).get(MallFields.MOBILE_TEL).toString());

				if (list.get(i).get(MallFields.INIT_DATE) != null) {
					et.createCell(list.get(i).get(MallFields.INIT_DATE).toString());
				} else {
					et.createCell(" ");
				}

				if (list.get(i).get("enterprise_name") != null) {
					et.createCell(list.get(i).get("enterprise_name").toString());
				} else {
					et.createCell(" ");
				}

				if (list.get(i).get("enterprise_short_name") != null) {
					et.createCell(list.get(i).get("enterprise_short_name").toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get("enterprise_address") != null) {
					et.createCell(list.get(i).get("enterprise_address").toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get("major_business") != null) {
					et.createCell(list.get(i).get("major_business").toString());
				} else {
					et.createCell(" ");
				}

				if (list.get(i).get("enterprise_linkman") != null) {
					et.createCell(list.get(i).get("enterprise_linkman").toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get("enterprise_tel") != null) {
					et.createCell(list.get(i).get("enterprise_tel").toString());
				} else {
					et.createCell(" ");
				}

				// if (list.get(i).get(BasicFields.STATUS) != null) {
				// et.createCell(list.get(i).get(BasicFields.STATUS).toString());
				// } else {
				// et.createCell(" ");
				// }
				if (i != list.size() - 1) {
					et.createNewRow();
				}
			}
		}
		Map<String, String> datas = new HashMap<String, String>();
		String title = "机惠多用户列表";
		datas.put("title", title);
		et.replaceFinalData(datas);
		et.wirteToStream(opts);
	}
}
