package com.hjh.mall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.ClientAddress;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.service.ClientAddressService;
import com.hjh.mall.vo.AddClientAddress;
import com.hjh.mall.vo.DeleteClientAddress;
import com.hjh.mall.vo.UpdateClientAddress;

/**
 * * @author csj
 * 
 * @date 创建时间：2017年2月20日 上午10:56:05
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
public class ClientAddressController {
	@Resource
	private ClientAddressService clientAddressService;
	@Resource
	private KeyGenerate keyGenerate;

	@RequestMapping("/json/900401")
	@ResponseBody
	public Map<String, Object> AddClientAddress(
			@RequestBody AddClientAddress addClientAddress) {
		ClientAddress entity = JSONUtil.trans(addClientAddress,
				ClientAddress.class);
		// HJYBizDataContext.getAccess_token();
		entity.setClient_id(HJYBizDataContext.getSessionIdentity()
				.getClientId());
		Date date = new Date();
		entity.setInit_date(DateTimeUtil.toString(date,
				DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setInit_time(DateTimeUtil.toString(date,
				DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setClient_address_id(keyGenerate
				.getKeyGenerate(MallFields.CLIENT_ADDRESS));
		entity.initForClearNull();
		clientAddressService.save(entity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put("client_address_id", entity.getClient_address_id());
		return result;
	}

	@RequestMapping("/json/900402")
	@ResponseBody
	public Map<String, Object> updateClientAddress(
			@RequestBody UpdateClientAddress updateClientAddress) {
		ClientAddress entity = JSONUtil.trans(updateClientAddress,
				ClientAddress.class);
		clientAddressService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping("/json/900405")
	@ResponseBody
	public Map<String, Object> updateClientAddressIsDefault(
			@RequestBody DeleteClientAddress vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		ClientAddress entity = new ClientAddress();
		entity.setIs_default("1");
		entity.setClient_id(HJYBizDataContext.getSessionIdentity()
				.getClientId());
		List<ClientAddress> list = clientAddressService.query(entity);
		if (!list.isEmpty()) {
			entity.setIs_default("0");
			entity.setClient_address_id(list.get(0).getClient_address_id());
			clientAddressService.update(entity);
			if (!list.get(0).getClient_address_id()
					.equals(vo.getClient_address_id())) {
				entity.setIs_default("1");
				entity.setClient_address_id(vo.getClient_address_id());
				clientAddressService.update(entity);
			}
		} else {
			entity.setClient_address_id(vo.getClient_address_id());
			clientAddressService.update(entity);
		}
		return result;
	}

	@RequestMapping("/json/900403")
	@ResponseBody
	public Map<String, Object> queryClientAddressList(@RequestBody HJYVO vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		ClientAddress entity = new ClientAddress();
		entity.setClient_id(HJYBizDataContext.getSessionIdentity()
				.getClientId());
		AddsortParam(entity);
		result.put(BasicFields.RESULT_LIST, clientAddressService.query(entity));
		return result;
	}

	/**
	 * @param entity
	 */
	private void AddsortParam(ClientAddress entity) {
		List<SortMarker> sortMarkers = new ArrayList<SortMarker>();
		SortMarker sortMarker = new SortMarker();
		sortMarker.setField("is_default");
		sortMarker.setAsc(false);
		sortMarkers.add(sortMarker);
		SortMarker sortMarker1 = new SortMarker();
		sortMarker1.setField("client_address_id");
		sortMarker1.setAsc(false);
		sortMarkers.add(sortMarker1);
		entity.setSortMarkers(sortMarkers);
	}

	@RequestMapping("/json/900404")
	@ResponseBody
	public Map<String, Object> deleteClientAddress(
			@RequestBody DeleteClientAddress vo) {
		clientAddressService.delete(vo.getClient_address_id());
		return VOUtil.genSuccessResult();
	}
}
