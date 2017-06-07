package com.hjh.mall.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.ClientCodeGenerate;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.cache.cache.sequence.NickNameGenerate;
import com.hjh.mall.common.core.annotation.LogInfo;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.filed.LogInfoType;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.sms.MD5Marker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.sms.alibaba.VerifyType;
import com.hjh.mall.constants.CacheKeys;
import com.hjh.mall.constants.SysContants;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.dao.ClientDao;
import com.hjh.mall.dao.ClientDetailDao;
import com.hjh.mall.dao.ClientEnterpriseDao;
import com.hjh.mall.dao.InvitationDao;
import com.hjh.mall.entity.Client;
import com.hjh.mall.entity.ClientDetail;
import com.hjh.mall.entity.ClientEnterprise;
import com.hjh.mall.entity.Invitation;
import com.hjh.mall.field.MallErrorCode;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.service.ClientDetailService;
import com.hjh.mall.service.ClientService;
import com.hjh.mall.service.VerifyMsgService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.type.SexType;
import com.hjh.mall.util.BusiSessionHelper;
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
 * @date 2016年6月27日
 * @version V1.0
 */

@Service
@Transactional
public class ClientServiceImpl extends HJYServiceImplBase<Client, String> implements ClientService {
	@Resource
	private VerifyMsgService verifyMsgService;

	@Resource
	private ClientDao clientDao;
	@Resource
	private InvitationDao invitationDao;
	@Resource
	private ClientDetailDao detailDao;
	@Resource
	private ClientEnterpriseDao clientEnterpriseDao;

	@Resource
	private NickNameGenerate nickNameGenerate;
	@Resource
	private ClientCodeGenerate clientCodeGenerate;

	@Resource
	private BusiSessionHelper busiSessionHelper;

	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private KeyGenerate keyGenerate;
	@Value("${http_proxy_ip: }")
	private String http_proxy_ip;
	@Value("${http_proxy_port: }")
	private String http_proxy_port;

	@Override
	protected DAOBase<Client, String> getDAO() {
		return clientDao;
	}

	@Override
	public Map<String, Object> getVerify(GetVerify getVerify) {
		VerifyType type = EnumUtil.valOf(getVerify.getAuthmsg_busi_type(), VerifyType.class);
		Client client = new Client();
		client.setMobile_tel(getVerify.getMobile_tel());
		client = getClientByTel(client);
		switch (type) {
		case LOGIN:// 验证码登录
			if (client == null) {// 手机号码不存在
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.MOBLIE_TEL_IS_NOT_EXSIT);
			}
			break;
		case REGIST:// 注册时获取验证码
			if (client != null) {// 手机号码已注册
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.USER_ALREADY_REGIST);
			}
			break;
		case UPDATEPWD:// 重置密码获取验证码
			if (client == null) {// 手机号码不存在
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.MOBLIE_TEL_IS_NOT_EXSIT);
			}
			break;
		case UPDATEINFO:// 修改手机号码获取验证码
			if (client != null) {// 手机号码已注册
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.USER_ALREADY_REGIST);
			}
			break;
		case IDENTITY_VERIFICATION:// 身份认证时获取验证码
			if (client == null) {// 手机号码不存在
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.MOBLIE_TEL_IS_NOT_EXSIT);
			}
			break;

		default:
			break;
		}
		// 先拿环境变量
		String ip = System.getenv("http_proxy_ip");
		String port = System.getenv("http_proxy_port");
		if (StringUtils.isBlank(ip) && StringUtils.isBlank(port)) {
			System.getProperties().setProperty("http.proxyHost", http_proxy_ip);
			System.getProperties().setProperty("http.proxyPort", http_proxy_port);
		}
		return verifyMsgService.getValidate(getVerify.getMobile_tel(), getVerify.getAuthmsg_busi_type());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> registClient(RegistClient registClient) {
		if (!registClient.getAuthmsg_busi_type().equals(VerifyType.REGIST.getVal())) {// 校验验证码类型不匹配
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.CHECK_NOT_MATCHING);
		}

		if (!checkInviteCode(registClient.getInvite_code())) {// 邀请码不正确
			return VOUtil.genErrorResult(MallErrorCode.InviteCodeErrorCode.INVIT_CODE_IS_NULL);
		}

		// 校验验证码
		Map<String, Object> result = verifyMsgService.validataParam(registClient.getVerify_msg(),
				registClient.getVerify_msg_id(), registClient.getAuthmsg_busi_type(), registClient.getMobile_tel());
		if (BasicFields.SUCCESS.equals(result.get(BasicFields.ERROR_NO))) {
			Client client = new Client();
			client.setMobile_tel(registClient.getMobile_tel());
			client = getClientByTel(client);
			if (client != null) {// 手机号码已注册
				return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.USER_ALREADY_REGIST);
			}
			client = JSONUtil.trans(registClient, Client.class);
			client.setClient_id(keyGenerate.getKeyGenerate(MallFields.CLIENT));
			// 密码需要MD5加密
			client.setPassword(MD5Marker.getMD5Str(registClient.getPassword()));
			client.setStatus(Status.ENABLED.getVal());// 将用户设置为激活状态
			String init_date = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
			String init_time = DateTimeUtil
					.getCurrentDateString(DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			client.setInit_date(init_date);
			client.setInit_time(init_time);
			client.setClient_code(clientCodeGenerate.getClientCode(registClient.getInvite_code()));
			// 注册成功之后直接登录返回user_token
			String user_token = saveNewClientIdSession(client);

			Map<String, String> member = JSONUtil.trans(client, Map.class);
			// 将登录用户信息放入redis的hash中 用户信息在修改后需要变更缓存
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + client.getMobile_tel(), member);
			client.initForClearNull();
			clientDao.save(client);

			ClientDetail clientDetail = new ClientDetail();
			clientDetail = JSONUtil.trans(registClient, ClientDetail.class);
			clientDetail.setClient_id(client.getClient_id());
			// 注册时给用户一个默认昵称
			setDefaultNickName(clientDetail);
			clientDetail.initForClearNull();
			detailDao.save(clientDetail);

			// 存用户企业信息
			ClientEnterprise clientEnterprise = new ClientEnterprise();
			clientEnterprise = JSONUtil.trans(registClient, ClientEnterprise.class);
			clientEnterprise.setClient_enterprise_id(keyGenerate.getKeyGenerate(MallFields.CLIENTENTERPRISE));
			clientEnterprise.setInvite_code(registClient.getInvite_code());
			StringBuffer address = new StringBuffer();
			address.append(registClient.getEnterprise_province().trim())
					.append(registClient.getEnterprise_city().trim()).append(registClient.getEnterprise_area().trim())
					.append(registClient.getEnterprise_street().trim());
			clientEnterprise.setEnterprise_address(address.toString());
			clientEnterprise.setStatus(Status.ENABLED.getVal());
			clientEnterprise.setClient_id(client.getClient_id());
			clientEnterprise.setInit_date(init_date);
			clientEnterprise.setInit_time(init_time);
			clientEnterprise.initForClearNull();
			clientEnterpriseDao.save(clientEnterprise);
			Map<String, String> clientEnterpriseDetail = JSONUtil.trans(clientEnterprise, Map.class);
			clientEnterpriseDetail.remove(BasicFields.UPDATE_VERSION);
			// 将用户企业信息放入redis的hash中 用户扩展信息在修改后需要变更缓存
			cacheHelper.hmset(
					com.hjh.mall.field.constant.CacheKeys.CLIENT_ENTERPRISE_DETAIL_PREFIX + client.getClient_id(),
					clientEnterpriseDetail);

			Map<String, String> detail = JSONUtil.trans(clientDetail, Map.class);
			detail.remove(BasicFields.UPDATE_VERSION);
			detail.put(MallFields.MOBILE_TEL, registClient.getMobile_tel());
			detail.put(MallFields.INIT_DATE, init_date);
			detail.put(MallFields.INIT_TIME, init_time);
			detail.put(MallFields.INVITE_CODE, registClient.getInvite_code());
			// 将用户扩展信息放入redis的hash中 用户扩展信息在修改后需要变更缓存
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client.getClient_id(), detail);

			result = VOUtil.genSuccessResult();
			result.put(BasicFields.ACCESS_TOKEN, user_token);
			result.put(MallFields.CLIENT_ID, client.getClient_id());
		}
		return result;
	}

	// 验证邀请码
	public boolean checkInviteCode(String invite_code) {
		Invitation invitation = new Invitation();
		invitation.setInvite_code(invite_code);
		List<Invitation> list = invitationDao.query(invitation);
		boolean result = false;
		if (list != null && list.size() > 0) {
			Client client = new Client();
			client.setInvite_code(invite_code);
			List<Client> clients = clientDao.query(client);
			if (clients != null && clients.size() > 0) {
				result = false;
			} else {
				result = true;
			}
		}
		return result;
	}

	// 根据邀请码获取用户编号
	public String getClientCode(String invite_code) {
		return clientCodeGenerate.getClientCode(invite_code);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfo(enumClass = LogInfoType.MOD, content = "修改个人头像")
	public Map<String, Object> updateClientAvatar(UpdateClientAvatar updateClientAvatar) {
		// pictureService.uploadPicture(updateClientAvatar.get, imagetype, key);
		if (!UploadType.USERIMAGE.getVal().equals(updateClientAvatar.getUpload_type())) {
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.CHECK_NOT_MATCHING);
		}
		UploadType type = EnumUtil.valOf(updateClientAvatar.getUpload_type(), UploadType.class);
		String key = updateClientAvatar.getImage_key();
		if (!key.startsWith(type.getDescription() + "/")) {// 判断路径是否正确
			return VOUtil.genErrorResult(ErrorCode.ImageCode.ERROR_PATH);
		}
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		// String client_id = "1111111111111113";
		ClientDetail clientDetail = new ClientDetail();
		clientDetail.setClient_id(client_id);
		clientDetail.setAvatar_path(key);
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里没有，即数据库没有 ,执行新增
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {// 数据库没有
				String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
				String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
				clientDetail.setInit_date(date);
				clientDetail.setInit_time(time);
				clientDetail.initForClearNull();
				detailDao.save(clientDetail);
				map = JSONUtil.trans(clientDetail, Map.class);
			} else {// 执行修改
				detailDao.updateClientById(clientDetail);
				map.put(MallFields.AVATAR_PATH, key);
			}
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		} else {// 执行修改
			detailDao.updateClientById(clientDetail);
			map.put(MallFields.AVATAR_PATH, key);
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.AVATAR_PATH, key);
		return result;
	}

	// 修改个人登录密码
	@Override
	public Map<String, Object> updateClientPwd(UpdateClientPwd updateClientPwd) {
		checkPwdSame(updateClientPwd);

		checkPwdTrue(updateClientPwd.getPassword_old());

		String pwdMd5 = MD5Marker.getMD5Str(updateClientPwd.getPassword());
		updatePwd(pwdMd5);

		updateCache();

		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	private void updateCache() {
		Client client = get(HJYBizDataContext.getSessionIdentity().getClientId());
		Map<String, String> member = JSONUtil.trans(client, Map.class);
		member.remove(BasicFields.UPDATE_VERSION);
		cacheHelper.hmset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + client.getMobile_tel(), member);
	}

	/**
	 * @Description: 修改密码
	 * @author 杨益桦
	 * @date 2016年8月10日
	 * @param pwd
	 *            void
	 */
	private void updatePwd(String pwd) {
		Client client = new Client();
		client.setClient_id(HJYBizDataContext.getSessionIdentity().getClientId());
		client.setPassword(pwd);
		clientDao.update(client);
	}

	/**
	 * @Description: 比较输入的密码是否正确，错误抛出异常
	 * @author 杨益桦
	 * @date 2016年8月10日
	 * @param pwd
	 *            void
	 */
	private void checkPwdTrue(String pwd) {
		Client client = clientDao.get(HJYBizDataContext.getSessionIdentity().getClientId());
		if (!client.getPassword().equals(pwd)) {// 密码错误
			throw new HJHBCSErrInfoException(ErrorCode.UserErrorCode.USER_IS_NULL);
		}
	}

	/**
	 * @Description: 比较密码与确认密码，不一致抛出异常
	 * @author 杨益桦
	 * @date 2016年8月10日
	 * @param updateClientPwd
	 *            void
	 */
	private void checkPwdSame(UpdateClientPwd updateClientPwd) {
		if (!updateClientPwd.getPassword().equals(updateClientPwd.getPassword_verify())) {
			throw new HJHBCSErrInfoException(ErrorCode.UserErrorCode.PASSWORD_NOT_MATCHING);
		}
	}

	public Map<String, Object> login(ClientLogin clientLogin) {
		Client client = new Client();
		client.setMobile_tel(clientLogin.getMobile_tel());
		HashMap<String, String> member1 = new HashMap<>();
		member1 = (HashMap<String, String>) cacheHelper
				.hgetAll(CacheKeys.SESSION_CLIENT_INFO_PREFIX + client.getMobile_tel());
		client = JSONUtil.trans(member1, Client.class);
		if (client.getClient_id() == null) {
			client = JSONUtil.trans(clientLogin, Client.class);
			client = getClientByTel(client);
		}
		if (client == null) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.USER_IS_NULL);
		}
		if (!clientLogin.getPassword().equals(client.getPassword())) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.USER_IS_NULL);
		}
		if (Status.DISENABLED.getVal().equals(client.getStatus())) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.USER_IS_DISENABLED);
		}
		String user_token = saveNewClientIdSession(client);
		@SuppressWarnings("unchecked")
		Map<String, String> member = JSONUtil.trans(client, Map.class);
		member.remove(BasicFields.UPDATE_VERSION);
		// 将登录用户信息放入redis的hash中 用户信息在修改后需要变更缓存
		cacheHelper.hmset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + client.getMobile_tel(), member);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.ACCESS_TOKEN, user_token);
		return result;

	}
	
	//改造 ,增加用户信息
	private String saveNewClientIdSession(Client user) {
		//获取用户企业信息
		ClientEnterprise queryClientEnterprise=new ClientEnterprise();
		queryClientEnterprise.setClient_id(user.getClient_id());
		List<ClientEnterprise> clientEnterprise=clientEnterpriseDao.query(queryClientEnterprise);
		
		
		String client_id = user.getClient_id();
		// 删除在线用户
		removeOnlineClientIDSession(client_id);
		// 保存用户信息
		SessionIdentity identity = new SessionIdentity();
		
		//保持企业联系人
		if(clientEnterprise!=null && clientEnterprise.size()>0){
			identity.setEnterprise_linkman(clientEnterprise.get(0).getEnterprise_linkman());
		}
		
		identity.setClientId(client_id);
		identity.setMobile_tel(user.getMobile_tel());
		identity.setUserType(SysContants.SESSION_USER_TYPE_CLIENT);
		identity.setInvite_code(user.getInvite_code());
		String user_token = busiSessionHelper.renewAccess_token(identity);
		saveNewClientIdSession(user_token, client_id);

		return user_token;
	}

	/**
	 * 保存client_id到access_token的映射，控制同一个用户只有一个会话
	 * 
	 * @param access_token
	 * @param client_id
	 * @return
	 */
	private void saveNewClientIdSession(String user_token, String client_id) {
		// 获得client_id会话令牌key getClient_idKey
		String operator_noKey = busiSessionHelper.getClient_idKey(client_id);
		// 根据会话令牌保存信息到缓存
		busiSessionHelper.setInfoForSession(operator_noKey, user_token);
	}

	/**
	 * 根据client_id检查该用户当前已登录的会话，如果会话存在把当前的会话注销掉
	 * 
	 * @param client_id
	 * @return
	 */
	private void removeOnlineClientIDSession(String client_id) {
		// 根据client_id获取已登录的access_token，如果获取到access_token把当前的会话注销掉
		String clientIdToken = busiSessionHelper.getSessionTokenByClientId(client_id);
		// 客户已经存在access_token，销毁会话
		if (StringUtils.isNotBlank(clientIdToken)) {
			// 销毁会话令牌
			busiSessionHelper.destroySession(clientIdToken);
		}
	}

	@Override
	public Client getClientByTel(Client client) {
		return clientDao.getClientByTel(client);
	}

	@Override
	public Map<String, Object> updateMoblie(UpdateMoblie updateMoblie) {
		if (!updateMoblie.getAuthmsg_busi_type().equals(VerifyType.UPDATEINFO.getVal())) {// 校验验证码类型不匹配
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.CHECK_NOT_MATCHING);
		}
		Client client = clientDao.get(HJYBizDataContext.getSessionIdentity().getClientId());

		String oldMObileTel = null;
		// 获取旧手机号
		if (null != client.getMobile_tel()) {
			oldMObileTel = client.getMobile_tel();
		}

		Client client1 = new Client();
		client1.setMobile_tel(updateMoblie.getMobile_tel());
		client1 = clientDao.getClientByTel(client1);
		if (client1 != null) {// 手机号码已存在
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.USER_ALREADY_REGIST);
		}
		Map<String, Object> result = verifyMsgService.validataParam(updateMoblie.getVerify_msg(),
				updateMoblie.getVerify_msg_id(), updateMoblie.getAuthmsg_busi_type(), updateMoblie.getMobile_tel());
		if (result.get(BasicFields.ERROR_NO).equals(BasicFields.SUCCESS)) {
			client.setMobile_tel(updateMoblie.getMobile_tel());
			clientDao.update(client);
			// 修改缓存里的detail里的电话号码
			String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
			Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);// 获取缓存
			map.put(MallFields.MOBILE_TEL, updateMoblie.getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
			// 切换完手机号以后销毁token
			busiSessionHelper.destroySession(updateMoblie.getAccess_token());

			// 清除缓存
			cacheHelper.destroy(CacheKeys.SESSION_CLIENT_INFO_PREFIX + oldMObileTel);

			result = VOUtil.genSuccessResult();
		}
		return result;
	}

	@Override
	public int countClient(Map<String, Object> map) {
		return clientDao.countClient(map);
	}

	@Override
	public List<Map<String, Object>> getClientList(Map<String, Object> map) {
		return clientDao.getClientList(map);
	}

	@Override
	public void updateStatus(Client client) {
		clientDao.updateStatus(client);
	}

	@Override
	public void resetPwd(Client client) {
		clientDao.resetPwd(client);
	}

	@Override
	public List<Map<String, String>> queryNickNamesByMobile(Client client) {

		return clientDao.queryNickNamesByMobile(client);
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfo(enumClass = LogInfoType.MOD, content = "更改用户昵称")
	public Map<String, Object> updateClientName(UpdateClientName updateClientName) {
		ClientDetail clientDetail = new ClientDetail();
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		clientDetail.setNick_name(updateClientName.getNick_name());
		List<ClientDetail> clientDetail_exsit = detailDao.query(clientDetail);
		if (clientDetail_exsit != null && clientDetail_exsit.size() > 0) {
			for (ClientDetail detail : clientDetail_exsit) {
				if (!detail.getClient_id().equals(client_id)) {
					return VOUtil.genErrorResult(ErrorCode.UserErrorCode.NICK_NAME_EXIST);
				}
			}
		}
		clientDetail.setClient_id(client_id);
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里没有
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {// 数据库没有
				String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
				String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
				clientDetail.setInit_date(date);
				clientDetail.setInit_time(time);
				clientDetail.initForClearNull();
				detailDao.save(clientDetail);
				map = JSONUtil.trans(clientDetail, Map.class);
			} else {// 执行修改
				detailDao.updateClientById(clientDetail);
				map.put(MallFields.NICK_NAME, updateClientName.getNick_name());
			}
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		} else {// 执行修改
			detailDao.updateClientById(clientDetail);
			map.put(MallFields.NICK_NAME, updateClientName.getNick_name());
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfo(enumClass = LogInfoType.MOD, content = "更改用户地址")
	public Map<String, Object> updateClientAddress(UpdateClientAddress updateClientAddress) {
		ClientDetail clientDetail = new ClientDetail();
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		/* String client_id = "1111111111111113"; */
		clientDetail.setClient_id(client_id);
		clientDetail.setAddress(updateClientAddress.getAddress());
		clientDetail.setArea_code(updateClientAddress.getArea_code());
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里没有
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {// 数据库没有
				String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
				String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
				clientDetail.setInit_date(date);
				clientDetail.setInit_time(time);
				clientDetail.initForClearNull();
				detailDao.save(clientDetail);
				map = JSONUtil.trans(clientDetail, Map.class);
			} else {// 执行修改
				detailDao.updateClientById(clientDetail);
				map.put(MallFields.ADDRESS, updateClientAddress.getAddress());
			}
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		} else {// 执行修改
			detailDao.updateClientById(clientDetail);
			map.put(MallFields.ADDRESS, updateClientAddress.getAddress());
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@LogInfo(enumClass = LogInfoType.MOD, content = "更改用户性别")
	public Map<String, Object> updateClientSex(UpdateClientSex updateClientSex) {

		ClientDetail clientDetail = new ClientDetail();
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		// String client_id = "1111111111111";
		clientDetail.setClient_id(client_id);
		clientDetail.setSex(updateClientSex.getSex());
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里没有
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {// 数据库没有
				String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
				String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
				clientDetail.setInit_date(date);
				clientDetail.setInit_time(time);
				clientDetail.initForClearNull();
				detailDao.save(clientDetail);
				map = JSONUtil.trans(clientDetail, Map.class);
			} else {// 执行修改
				detailDao.updateClientById(clientDetail);
				map.put(MallFields.SEX, updateClientSex.getSex());
			}
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		} else {// 执行修改
			detailDao.updateClientById(clientDetail);
			map.put(MallFields.SEX, updateClientSex.getSex());
			map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> getClientInfo(HJYVO hjyvo) {
		//获取用户id
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		//ClientDetail
		ClientEnterprise clientEnterprise= new ClientEnterprise();
		clientEnterprise.setClient_id(client_id);
		Map<String, Object> clientEnterprisemap=clientEnterpriseDao.getEnterpriseByClientId(clientEnterprise);
		
		//从缓存中获取用户信息
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里面没有
			ClientDetail clientDetail = new ClientDetail();
			clientDetail.setClient_id(client_id);
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {
				map = new HashMap<>();
			} else {// 存入redis
				map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
				cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
			}
		}
		map.put(MallFields.MOBILE_TEL, HJYBizDataContext.getSessionIdentity().getMobile_tel());
		
		Map<String, Object> result = VOUtil.genSuccessResult();
		// result.put(BasicFields.ROLE_ID,
		// HJYBizDataContext.getSessionIdentity().getRoleId());
		if(clientEnterprisemap!=null){
			result.putAll(clientEnterprisemap);//用户企业信息放进返回信息中
		}
		result.putAll(map);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> updateClientInfo(UpdateClient updateClient) {
		ClientDetail clientDetail = JSONUtil.trans(updateClient, ClientDetail.class);
		String client_id = updateClient.getClient_id();
		String mobile_tel = updateClient.getMobile_tel();
		Map<String, String> map = cacheHelper.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id);
		if (map == null || map.size() == 0) {// redis里没有，即数据库没有 ,执行新增
			map = detailDao.getByClientId(clientDetail);
			if (map == null) {// 数据库没有 insert
				String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
				String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
				clientDetail.setInit_date(date);
				clientDetail.setInit_time(time);
				clientDetail.initForClearNull();
				detailDao.save(clientDetail);
				map = JSONUtil.trans(clientDetail, Map.class);

			} else {// 执行修改
				detailDao.updateClientById(clientDetail);
				map = detailDao.getByClientId(clientDetail);
			}
			map.put(MallFields.MOBILE_TEL, mobile_tel);
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		} else {// 执行修改
			detailDao.updateClientById(clientDetail);
			map = detailDao.getByClientId(clientDetail);
			map.put(MallFields.MOBILE_TEL, mobile_tel);
			cacheHelper.hmset(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + client_id, map);
		}
		detailDao.updateClientById(clientDetail);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.putAll(detailDao.getByClientId(clientDetail));
		return result;
	}

	@Override
	public Map<String, Object> logOut(HJYVO hjyvo) {
		busiSessionHelper.destroySession(hjyvo.getAccess_token());
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> resetPwd(ResetPwd resetPwd) {
		CheckVerify checkVerify = JSONUtil.trans(resetPwd, CheckVerify.class);
		Map<String, Object> map = checkVerify(checkVerify);
		if (!BasicFields.SUCCESS.equals(map.get(BasicFields.ERROR_NO))) {
			return map;
		}
		if (!resetPwd.getPassword().equals(resetPwd.getPassword_verify())) {// 密码不一致
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.PASSWORD_NOT_MATCHING);
		}
		Client client = new Client();
		client.setMobile_tel(resetPwd.getMobile_tel());
		client = getClientByTel(client);
		if (client == null) {// 手机号码不存在
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.USER_NOT_REGIST);
		}
		String newPwd = MD5Marker.getMD5Str(resetPwd.getPassword());
		client.setPassword(newPwd);
		clientDao.update(client);
		cacheHelper.hset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + resetPwd.getMobile_tel(),
				com.hjh.mall.field.constant.MallFields.PASSWORD, newPwd);
		// 删除在线用户
		removeOnlineClientIDSession(client.getClient_id());
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updatePosition(UpdateClientPosition updateClientPosition) {
		return null;
	}

	@Override
	public Map<String, Object> checkPwd(CheckPwd checkPwd) {
		Client client = clientDao.get(HJYBizDataContext.getSessionIdentity().getClientId());
		if (!client.getPassword().equals(checkPwd.getPassword())) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.USER_IS_NULL);
		}
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getClientList(GetClientListVo getClientListVo) {
		Map<String, Object> map = VOUtil.genEmptyResult();
		if (StringUtil.isNotBlank(getClientListVo.getNick_name())) {
			map.put(MallFields.NICK_NAME, getClientListVo.getNick_name());
		}
		if (StringUtil.isNotBlank(getClientListVo.getMobile_tel())) {
			map.put(MallFields.MOBILE_TEL, getClientListVo.getMobile_tel());
		}
		if (StringUtil.isNotBlank(getClientListVo.getStatus()) && !"null".equals(getClientListVo.getStatus())) {
			map.put(BasicFields.STATUS, getClientListVo.getStatus());
		}
		if (StringUtil.isNotBlank(getClientListVo.getClient_id())) {
			map.put(MallFields.CLIENT_ID, getClientListVo.getClient_id());
		}
		if (StringUtil.isNotBlank(getClientListVo.getStart_date())) {
			map.put(MallFields.START_DATE, getClientListVo.getStart_date());
		}
		if (StringUtil.isNotBlank(getClientListVo.getEnd_date())) {
			map.put(MallFields.END_DATE, getClientListVo.getEnd_date());
		}

		if (StringUtil.isNotBlank(getClientListVo.getInvite_code())) {
			map.put(MallFields.INVITE_CODE, getClientListVo.getInvite_code());
		}
		if (StringUtil.isNotBlank(getClientListVo.getInvite_code_start())) {
			map.put(MallFields.INVITE_CODE_START, getClientListVo.getInvite_code_start());
		}
		if (StringUtil.isNotBlank(getClientListVo.getInvite_code_end())) {
			map.put(MallFields.INVITE_CODE_END, getClientListVo.getInvite_code_end());
		}

		int totalNum = clientDao.countClient(map);
		if (getClientListVo.getLimit() != null && getClientListVo.getPage() != null) {
			Pagination page = new Pagination();
			page.setPage_size(getClientListVo.getLimit());
			page.setPage_no(getClientListVo.getPage());
			page.setTotal_item_num(totalNum);
			page.calc();
			map.put(com.hjh.mall.field.constant.MallFields.PAGINATION, page);
		}
		List<Map<String, Object>> list = clientDao.getClientList(map);
		List<Map<String, Object>> listNew = new ArrayList<>();
		try {
			for (Map m : list) {
				String client_id = m.get(MallFields.CLIENT_ID).toString();
				ClientEnterprise clientEnterprise = new ClientEnterprise();
				clientEnterprise.setClient_id(client_id);
				Map<String, Object> enterprise = clientEnterpriseDao.getEnterpriseByClientId(clientEnterprise);
				m.putAll(enterprise);
				if (SexType.MAN.getVal().equals(m.get(MallFields.SEX))) {
					m.put(MallFields.SEX, SexType.MAN.getDescription());
				}
				if (SexType.WOMAN.getVal().equals(m.get(MallFields.SEX))) {
					m.put(MallFields.SEX, SexType.WOMAN.getDescription());
				}
				if (Status.ENABLED.getVal().equals(m.get(BasicFields.STATUS))) {
					m.put(BasicFields.STATUS, Status.ENABLED.getDescription());
				}
				if (Status.DISENABLED.getVal().equals(m.get(BasicFields.STATUS))) {
					m.put(BasicFields.STATUS, Status.DISENABLED.getDescription());
				}
				if (StringUtils.isNotBlank(m.get(MallFields.INIT_DATE).toString())) {
					Date date = (Date) DateTimeUtil.toDate(
							(m.get(MallFields.INIT_DATE).toString() + m.get(MallFields.INIT_TIME).toString()),
							DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
					m.put(BasicFields.INIT_DATE, DateTimeUtil.toString(date));
				}
				listNew.add(m);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, listNew);
		result.put(MallFields.TOTAL_NUM, totalNum);
		return result;
	}

	@Override
	public Map<String, Object> updateStatus(UpdateStatusVo updateStatusVo) {
		Client client = new Client();
		client.setClient_id(updateStatusVo.getClient_id());
		client.setStatus(updateStatusVo.getStatus());
		clientDao.updateStatus(client);
		Map<String, String> map = cacheHelper
				.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + updateStatusVo.getMobile_tel());
		if (map != null) {
			// 修改缓存
			cacheHelper.hset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + updateStatusVo.getMobile_tel(), BasicFields.STATUS,
					updateStatusVo.getStatus());
		}
		if (Status.DISENABLED.getVal().equals(updateStatusVo.getStatus())) {// 如果用户禁用，则删除在线用户
			HJYVO hjyvo = new HJYVO();
			String access_token = busiSessionHelper.getSessionTokenByClientId(updateStatusVo.getClient_id());
			hjyvo.setAccess_token(access_token);
			logOut(hjyvo);
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> resetPwd(ResetPwdVo resetPwdVo) {
		Client client = new Client();
		client.setClient_id(resetPwdVo.getClient_id());
		client.setPassword(MallFields.INITIAL_PASSWORD);
		clientDao.resetPwd(client);
		Map<String, String> map = cacheHelper
				.hgetAll(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + resetPwdVo.getMobile_tel());
		if (map != null) {// 修改缓存
			cacheHelper.hset(CacheKeys.SESSION_CLIENT_INFO_PREFIX + resetPwdVo.getMobile_tel(),
					com.hjh.mall.field.constant.MallFields.PASSWORD, MallFields.INITIAL_PASSWORD);
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> getNickNameById(String clientId) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		ClientDetail client = detailDao.get(clientId);
		if (StringUtils.isNotBlank(client.getNick_name())) {
			result.put(MallFields.NICK_NAME, client.getNick_name());
		}
		return result;
	}

	@Override
	public Map<String, Object> getClientByMobile(String mobile) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		Client client = new Client();
		client.setMobile_tel(mobile);
		result.put(BasicFields.RESULT_LIST, clientDao.queryNickNamesByMobile(client));
		return result;
	}

	// 忘记密码时校验验证码
	@Override
	public Map<String, Object> checkVerify(CheckVerify checkVerify) {
		if (!checkVerify.getAuthmsg_busi_type().equals(VerifyType.UPDATEPWD.getVal())) {// 校验验证码类型不匹配
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.CHECK_NOT_MATCHING);
		}
		// 校验验证码
		Map<String, Object> map = verifyMsgService.validataParam(checkVerify.getVerify_msg(),
				checkVerify.getVerify_msg_id(), checkVerify.getAuthmsg_busi_type(), checkVerify.getMobile_tel());
		return map;
	}

	@Override
	public Map<String, Object> verifyLogin(LoginByVerify loginByVerify) {
		Client client = new Client();
		client.setMobile_tel(loginByVerify.getMobile_tel());
		client = clientDao.getClientByTel(client);
		if (client == null) {// 手机号码不存在
			return VOUtil.genErrorResult(ErrorCode.VerifyErrorCode.MOBLIE_TEL_IS_NOT_EXSIT);
		}
		if (client.getStatus().equals(Status.DISENABLED.toString())) {// 用户被禁用
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.USER_IS_DISENABLED);
		}
		Map<String, Object> result = verifyMsgService.validataParam(loginByVerify.getVerify_msg(),
				loginByVerify.getVerify_msg_id(), loginByVerify.getAuthmsg_busi_type(), loginByVerify.getMobile_tel());
		if (BasicFields.SUCCESS.equals(result.get(BasicFields.ERROR_NO))) {// 验证成功
			String user_token = saveNewClientIdSession(client);
			result = VOUtil.genSuccessResult();
			result.put(BasicFields.ACCESS_TOKEN, user_token);
		}
		return result;
	}

	/**
	 * 
	 * @date 2017年1月4日
	 * @Description: 设置唯一的昵称
	 * @param nick_name
	 * @return boolean
	 */
	public void setDefaultNickName(ClientDetail clientDetail) {
		String nick_name = nickNameGenerate.getDefaultNickName();
		clientDetail.setNick_name(nick_name);
		List<ClientDetail> clientDetail_exsit = detailDao.query(clientDetail);
		if (clientDetail_exsit != null && clientDetail_exsit.size() > 0) {
			setDefaultNickName(clientDetail);
		}
	}

}
