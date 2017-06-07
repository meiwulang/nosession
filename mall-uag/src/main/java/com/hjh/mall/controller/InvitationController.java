package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.ExcelUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Invitation;
import com.hjh.mall.model.InvitationModel;
import com.hjh.mall.service.InvitationService;
import com.hjh.mall.vo.Invitation.InvitationAddVo;
import com.hjh.mall.vo.Invitation.InvitationDelVo;
import com.hjh.mall.vo.Invitation.InvitationEditVo;
import com.hjh.mall.vo.Invitation.InvitationQueryVo;

/**
 * @Project: hjh.mall
 * @Description TODO
 * @author 李慧峰
 * @date 2017年2月20日
 * @version V1.0
 */
@Controller
public class InvitationController {
	@Resource
	private InvitationService invitationService = null;
	@Resource
	private KeyGenerate keyGenerate;

	@Resource
	private CacheHelper cacheHelper;

	/**
	 * @Project: hjy-mall
	 * @Description 邀请码管理
	 * @author 李慧峰
	 * @date 2017年2月20日
	 * @version V1.0
	 */
	@RequestMapping(value="invitation_query",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInvitation(@RequestBody InvitationQueryVo vo) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONUtil.trans(vo, Map.class);
		if (StringUtil.isBlank(vo.getInvite_code_start())) {
			map.remove("invite_code_start");
		}
		if (StringUtil.isBlank(vo.getInvite_code_end())) {
			map.remove("invite_code_end");
		}
		return invitationService.queryBlur(map);
	}

	/**
	 * @Project: hjy-mall
	 * @Description 邀请码管理
	 * @author 李慧峰
	 * @date 2017年2月25日
	 * @version V1.0
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="invitation_import",method=RequestMethod.GET)
	public void importInvitation(InvitationQueryVo vo, HttpServletResponse response) throws IOException {
		ExcelUtil et = ExcelUtil.getInstance();
		vo.setLimit(100000);
		Map<String, Object> map = JSONUtil.trans(vo, Map.class);
		if (StringUtil.isBlank(vo.getInvite_code_start())) {
			map.remove("invite_code_start");
		}
		if (StringUtil.isBlank(vo.getInvite_code_end())) {
			map.remove("invite_code_end");
		}
		if (StringUtil.isBlank(vo.getStart_date())) {
			map.remove("start_date");
			map.remove("start_time");
		}
		if (StringUtil.isBlank(vo.getEnd_date())) {
			map.remove("end_date");
			map.remove("end_time");
		}
		Map<String, Object> resul = invitationService.queryBlur(map);
		List<Map> list = JSONUtil.transInSide((List<Invitation>)resul.get(BasicFields.RESULT_LIST), Map.class);
		List<InvitationModel> listModel = null;
		if (list != null) {
			listModel = JSONUtil.transInSide(list, InvitationModel.class);
			System.out.println(list.size());
		}
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "邀请码" + System.currentTimeMillis();
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		// for (int i = 0; i < listModel.size(); i++) {
		// int j = i + 1;
		// listModel.get(i).setSort(j);
		// }
		et.exportObj2Excel(fOut, listModel, InvitationModel.class, false);
		fOut.flush();
		fOut.close();
	}

	/**
	 * @Project: hjy-mall
	 * @Description 邀请码管理
	 * @author 李慧峰
	 * @date 2017年2月20日
	 * @version V1.0
	 */
	@RequestMapping(value="invitation_del",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delInvitation(@RequestBody InvitationDelVo vo) {
		invitationService.delete(vo.getInvite_id());
		return VOUtil.genSuccessResult();
	}

	/**
	 * @Project: hjy-mall
	 * @Description 邀请码信息添加
	 * @author 李慧峰
	 * @date 2017年2月20日
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="invitation_add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInvitation(@RequestBody InvitationAddVo vo) {
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		Invitation invitation = new Invitation();
		invitation.setInvite_code(vo.getInvite_code());
		List<Invitation> list = invitationService.query(invitation);
		if (list != null && list.size() > 0) {
			return VOUtil.genErrorResult("邀请码重复");
		}
		Invitation entity = JSONUtil.trans(vo, Invitation.class);
		entity.setInvite_id(keyGenerate.getKeyGenerate("invitation"));
		entity.setCreate_user(operator_id);
		entity.setCreate_user_name(operator_name);
		entity.setUpdate_user_name(operator_name);
		entity.setUpdate_user(operator_id);
		createInit(entity);
		invitationService.save(entity);
		// 存缓存
		cacheHelper.hmset(com.hjh.mall.field.constant.CacheKeys.INVITE_CODE_INFO + vo.getInvite_code(),
				JSONUtil.trans(vo, Map.class));
		return VOUtil.genSuccessResult();
	}

	// 设置创建时间和修改时间
	public static void createInit(Invitation invitation) {
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		invitation.setInit_date(date);
		invitation.setInit_time(time);
		invitation.setUpdate_date(date);
		invitation.setUpdate_time(time);
		invitation.initForClearNull();
	}

	// 设置修改时间
	public static void setUpdateTime(Invitation invitation) {
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		invitation.setUpdate_date(date);
		invitation.setUpdate_time(time);
	}

	/**
	 * @Project: hjy-mall
	 * @Description 邀请码信息修改
	 * @author 李慧峰
	 * @date 2017年2月20日
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="invitation_edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editInvitation(@RequestBody InvitationEditVo vo) {
		Invitation entity = JSONUtil.trans(vo, Invitation.class);
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		entity.setUpdate_user_name(operator_name);
		entity.setUpdate_user(operator_id);
		setUpdateTime(entity);
		invitationService.update(entity);
		// 清除缓存
		cacheHelper.destroy(com.hjh.mall.field.constant.CacheKeys.INVITE_CODE_INFO + vo.getInvite_code());
		// 存缓存
		cacheHelper.hmset(com.hjh.mall.field.constant.CacheKeys.INVITE_CODE_INFO + vo.getInvite_code(),
				JSONUtil.trans(vo, Map.class));
		return VOUtil.genSuccessResult();
	}

}
