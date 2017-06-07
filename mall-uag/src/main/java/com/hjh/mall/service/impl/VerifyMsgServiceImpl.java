package com.hjh.mall.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.sms.alibaba.SendMessage;
import com.hjh.mall.dao.VerifyMsgDao;
import com.hjh.mall.entity.VerifyMsg;
import com.hjh.mall.field.SmsCondition;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.service.VerifyMsgService;
import com.hjh.mall.service.base.HJYServiceImplBase;

@Service
public class VerifyMsgServiceImpl extends HJYServiceImplBase<VerifyMsg, String> implements VerifyMsgService {
	@Resource
	private VerifyMsgDao verifyMsgDao;
	@Resource
	private SendMessage sendMessage;

	@Override
	public Map<String, Object> getValidate(String mobile, String type) {
		Map<String, Object> result = VOUtil.genEmptyResult();
		Map<String, Object> resultParam = VOUtil.genEmptyResult();
		int time;
		int requestTotal = 0;
		VerifyMsg verifyMsg = new VerifyMsg();
		verifyMsg.setMobile_tel(mobile);
		verifyMsg.setAuthmsg_busi_type(type);
		VerifyMsg oldMsg = verifyMsg;
		requestTotal = verifyMsgDao.getRequestsTotal(mobile);
		Date start = new Date();
		VerifyMsg newMsg = verifyMsgDao.getVerifyMsg(verifyMsg);
		// 如果电话号是第一次获取验证码，把传入的电话号赋值给newMsg。
		verifyMsg = newMsg == null ? oldMsg : newMsg;
		if (newMsg == null) {
			saveVerifyMsg(verifyMsg, start);
			sendMessage.SendSmsMessageByName(verifyMsg.getMobile_tel(), verifyMsg.getVerify_msg(),
					verifyMsg.getAuthmsg_busi_type(), "【机惠多】");

			// resultParam.put(mall_Fileds.VERIFY_MSG, verifyMsg.getVerify_msg());
			resultParam.put(MallFields.VERIFY_MSG_ID, verifyMsg.getVerify_msg_id());
			return VOUtil.setSuccessResult(resultParam);
		}
		verifyMsg.setConsecutive_requests(verifyMsg.getConsecutive_requests() + 1);
		try {
			String endDate = verifyMsg.getAdd_date();
			String endTime = verifyMsg.getAdd_time();
			String end = endDate + endTime;
			time = DateTimeUtil.getTimeDiff(start,
					DateTimeUtil.toDate(end, DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK));
			if (time < SmsCondition.SMSREQUEST_INTERVAL) {
				return validSmsInterval(result, verifyMsg, start);
			}
			if (verifyMsg.getConsecutive_failures() > SmsCondition.SMSVERIFY_FAILUER) {
				result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.USER_LOCKED);
				verifyMsgDao.update(verifyMsg);
				return result;
			}
			if (time > SmsCondition.SMSVALID_TIME * 30) {
				return resetVerifymsg(result, verifyMsg, start);
			}
			if (verifyMsg.getConsecutive_requests() > SmsCondition.SMSREQUEST_TIMES) {
				return validRequestTimes(result, verifyMsg, start);

			}

			if (requestTotal > SmsCondition.SMSREQUEST_TATLA) {
				return validRequestTatal(result, verifyMsg, start);
			}
		} catch (ParseException e) {
			e.printStackTrace();

		}
		verifyMsg.setAdd_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		sendMessage.SendSmsMessageByName(verifyMsg.getMobile_tel(), verifyMsg.getVerify_msg(),
				verifyMsg.getAuthmsg_busi_type(), "【机惠多】");
		// resultParam.put(mall_Fileds.VERIFY_MSG, verifyMsg.getVerify_msg());
		resultParam.put(MallFields.VERIFY_MSG_ID, verifyMsg.getVerify_msg_id());
		verifyMsgDao.update(verifyMsg);
		return VOUtil.setSuccessResult(resultParam);
	}

	private Map<String, Object> validRequestTimes(Map<String, Object> result, VerifyMsg verifyMsg, Date start) {
		verifyMsg.setLast_fail_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setLast_fail_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.TOO_OFTEN);
		return result;
	}

	private Map<String, Object> validRequestTatal(Map<String, Object> result, VerifyMsg verifyMsg, Date start) {
		result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.TOO_OFTEN);
		verifyMsg.setLast_fail_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setLast_fail_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		verifyMsgDao.update(verifyMsg);
		return result;
	}

	private Map<String, Object> resetVerifymsg(Map<String, Object> result, VerifyMsg verifyMsg, Date start) {
		Map<String, Object> resultParam = VOUtil.genEmptyResult();
		Random rand = new Random();
		int param1 = rand.nextInt(9000) + 1000;
		verifyMsg.setVerify_msg(String.valueOf(param1));
		verifyMsg.setLast_fail_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setLast_fail_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		verifyMsg.setAdd_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setAdd_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		sendMessage.SendSmsMessageByName(verifyMsg.getMobile_tel(), verifyMsg.getVerify_msg(),
				verifyMsg.getAuthmsg_busi_type(), "【机惠多】");

		verifyMsgDao.update(verifyMsg);
		resultParam.put(MallFields.VERIFY_MSG_ID, verifyMsg.getVerify_msg_id());
		return VOUtil.setSuccessResult(resultParam);
	}

	private Map<String, Object> validSmsInterval(Map<String, Object> result, VerifyMsg verifyMsg, Date start) {
		result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.TOO_OFTEN);
		verifyMsg.setLast_fail_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setLast_fail_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		verifyMsg.setConsecutive_failures(verifyMsg.getConsecutive_failures() + 1);
		verifyMsgDao.update(verifyMsg);
		return result;
	}

	private void saveVerifyMsg(VerifyMsg verifyMsg, Date start) {
		Random rand = new Random();
		int param1 = rand.nextInt(9000) + 1000;
		verifyMsg.setVerify_msg_id(IDUtil.getOrdID16());
		verifyMsg.initForClearNull();
		verifyMsg.setConsecutive_requests(1);
		verifyMsg.setMobile_tel(verifyMsg.getMobile_tel());
		verifyMsg.setVerify_msg(String.valueOf(param1));
		verifyMsg.setAdd_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setInit_date(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		verifyMsg.setAdd_time(DateTimeUtil.toString(start, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		verifyMsgDao.save(verifyMsg);
	}

	@Override
	protected DAOBase<VerifyMsg, String> getDAO() {
		return verifyMsgDao;
	}

	@Override
	public Map<String, Object> validataParam(String verify_msg, String verify_msg_id, String type, String mobile_tel) {
		Map<String, Object> result = VOUtil.genEmptyResult();
		Date date = new Date();
		int time;
		VerifyMsg vm = new VerifyMsg();
		vm = verifyMsgDao.get(verify_msg_id);
		if (vm == null) {
			result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.VALIDATA_FASLE);
			return result;
		}
		if (!vm.getMobile_tel().equals(mobile_tel)) {
			result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.VALIDATA_FASLE);
			return result;
		}
		if (!type.equals(vm.getAuthmsg_busi_type())) {
			result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.REUQEST_VER_TYPE);
			return result;
		}
		String endDate = vm.getAdd_date();
		String endTime = vm.getAdd_time();
		vm.setVerify_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		vm.setVerify_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		String end = endDate + endTime;
		try {
			time = DateTimeUtil.getTimeDiff(date,
					DateTimeUtil.toDate(end, DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK));
			if (time > SmsCondition.SMSVALID_TIME * 30) {
				result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.VALIDATA_INVALID);
				verifyMsgDao.update(vm);
				return result;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!vm.getVerify_msg().equals(verify_msg)) {
			vm.setLast_fail_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			vm.setLast_fail_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			vm.setConsecutive_failures(vm.getConsecutive_failures() + 1);
			vm.setStatus("1");
			result.put(BasicFields.ERROR_NO, ErrorCode.VerifyMsgCode.VALIDATA_FASLE);
			verifyMsgDao.update(vm);
			return result;
		}
		vm.setStatus("0");
		verifyMsgDao.update(vm);
		return VOUtil.genSuccessResult();
	}

	@Override
	public void sendTextMessage(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllMessage() {
		verifyMsgDao.deleteAllVerify();

	}

}
