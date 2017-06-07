package com.hjh.mall.common.sms.base;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.sms.base.service.SmsMessageServiceBase;
import com.hjh.mall.common.sms.entity.SendMessageEntity;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public abstract class SmsMessageBaseImpl<T> implements
		SmsMessageServiceBase<Map<String,Object>> {

	public abstract String getSgin();

	public abstract TaobaoClient getSmsClient();


	public AlibabaAliqinFcSmsNumSendRequest creatSendObject(
			SendMessageEntity<Map<String,Object>> sendMessageEntity) {
		AlibabaAliqinFcSmsNumSendRequest rsq = new AlibabaAliqinFcSmsNumSendRequest();		
		rsq.setSmsType(sendMessageEntity.getSmsType().getVal());
		rsq.setSmsTemplateCode(sendMessageEntity.getTemp());	
		rsq.setSmsFreeSignName(getSgin());
		rsq.setRecNum(sendMessageEntity.getMobile());
		JSONObject jsonObject =(JSONObject) JSONObject.toJSON(sendMessageEntity.getMessage());
		rsq.setSmsParam(jsonObject.toJSONString());
		return rsq;

	}

	@Override
	public Map<String, Object> sendMessage(SendMessageEntity<Map<String,Object>> sendMessageEntity) {
		if(sendMessageEntity==null
				||StringUtil.isBlank(sendMessageEntity.getTemp())||StringUtil.isBlank(sendMessageEntity.getMobile())){
			return VOUtil.genCommonErrorResult();
		}
		AlibabaAliqinFcSmsNumSendRequest rsq = this.creatSendObject(sendMessageEntity);
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = getSmsClient().execute(rsq);
			System.out.println(rsp.getBody());
/*			rsp.ge
			rsp.getResult();*/
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return VOUtil.genSuccessResult();
	}

}
