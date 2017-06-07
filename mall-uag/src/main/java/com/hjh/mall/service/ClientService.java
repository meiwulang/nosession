package com.hjh.mall.service;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.entity.Client;
import com.hjh.mall.vo.client.CheckPwd;
import com.hjh.mall.vo.client.CheckVerify;
import com.hjh.mall.vo.client.ClientLogin;
import com.hjh.mall.vo.client.GetClientListVo;
import com.hjh.mall.vo.client.GetVerify;
import com.hjh.mall.vo.client.LoginByVerify;
import com.hjh.mall.vo.client.RegistClient;
import com.hjh.mall.vo.client.ResetPwd;
import com.hjh.mall.vo.client.ResetPwdVo;
import com.hjh.mall.vo.client.UpdateClient;
import com.hjh.mall.vo.client.UpdateClientAddress;
import com.hjh.mall.vo.client.UpdateClientAvatar;
import com.hjh.mall.vo.client.UpdateClientName;
import com.hjh.mall.vo.client.UpdateClientPosition;
import com.hjh.mall.vo.client.UpdateClientPwd;
import com.hjh.mall.vo.client.UpdateClientSex;
import com.hjh.mall.vo.client.UpdateMoblie;
import com.hjh.mall.vo.client.UpdateStatusVo;

/**
 * @Project: hjy-middle
 * @Description 用户业务层
 * @author 王斌
 * @date 2016年06月27日
 * @version V1.0
 */
public interface ClientService extends ServiceBase<Client, String> {

	public Client getClientByTel(Client client);

	public Map<String, Object> updateClientPwd(UpdateClientPwd updateClientPwd);

	public int countClient(Map<String, Object> map);

	public List<Map<String, Object>> getClientList(Map<String, Object> map);

	public void updateStatus(Client client);

	public void resetPwd(Client client);

	public List<Map<String, String>> queryNickNamesByMobile(Client client);

	// 获取验证码
	public Map<String, Object> getVerify(GetVerify getVerify);

	// 校验验证码
	public Map<String, Object> checkVerify(CheckVerify checkVerify);

	// 登录
	public Map<String, Object> login(ClientLogin clientLogin);

	public Map<String, Object> verifyLogin(LoginByVerify loginByVerify);

	// 注册
	public Map<String, Object> registClient(RegistClient registClient);

	// 修改个人信息（头像）
	public Map<String, Object> updateClientAvatar(UpdateClientAvatar updateClientAvatar);

	// 修改昵称
	public Map<String, Object> updateClientName(UpdateClientName updateClientName);

	// 修改地址
	public Map<String, Object> updateClientAddress(UpdateClientAddress updateClientAddress);

	// 修改昵称
	public Map<String, Object> updateClientSex(UpdateClientSex updateClientSex);

	public Map<String, Object> getClientInfo(HJYVO hjyvo);

	// 用户修改个人信息
	public Map<String, Object> updateClientInfo(UpdateClient updateClient);

	// 退出系统
	public Map<String, Object> logOut(HJYVO hjyvo);

	// 重置密码
	public Map<String, Object> resetPwd(ResetPwd resetPwd);

	// 修改坐标
	public Map<String, Object> updatePosition(UpdateClientPosition updateClientPosition);

	// 用户修改手机号码
	public Map<String, Object> updateMoblie(UpdateMoblie updateMoblie);

	// 校验密码
	public Map<String, Object> checkPwd(CheckPwd checkPwd);

	// 后台管理获取会员列表
	public Map<String, Object> getClientList(GetClientListVo getClientListVo);

	// 后台修改用户状态
	public Map<String, Object> updateStatus(UpdateStatusVo updateStatusVo);

	// 后台重置用户密码
	public Map<String, Object> resetPwd(ResetPwdVo resetPwdVo);

	// 获取昵称根据clientId
	public Map<String, Object> getNickNameById(String clientId);

	// 获取昵称根据电话号
	public Map<String, Object> getClientByMobile(String mobile);

}
