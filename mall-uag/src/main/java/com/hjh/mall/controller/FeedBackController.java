package com.hjh.mall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Client;
import com.hjh.mall.entity.ClientDetail;
import com.hjh.mall.entity.Feedback;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.field.RestFulAPI;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.FeedBackSource;
import com.hjh.mall.field.type.FeedBackStatus;
import com.hjh.mall.service.ClientDetailService;
import com.hjh.mall.service.ClientService;
import com.hjh.mall.service.FeedbackService;
import com.hjh.mall.service.OperatorService;
import com.hjh.mall.vo.feedback.FeedBackDelete;
import com.hjh.mall.vo.feedback.FeedBackQueryVo;
import com.hjh.mall.vo.feedback.FeedBackReplyVo;
import com.hjh.mall.vo.feedback.FeedBackVo;

/**
 * @Project: hjy-middle
 * @Description
 * @author 李慧峰
 * @date 2016年8月2日
 * @version V1.0
 */
@Controller
public class FeedBackController {
/*	@Reference(version = "1.0.0")
	private BizClientService bizClientService;*/
	@Resource
	private FeedbackService feedBackService;

	@Resource
	private OperatorService operatorService;
	
	@Resource
	private ClientService clientService;

	@Resource
	private ClientDetailService clientDetailService;
	
	@Resource
	private KeyGenerate keyGenerate;
	
	@Resource
	private CacheHelper cacheHelper;

	/**
	 * app意见反馈
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value=RestFulAPI.Feedback.FEEDBACKADD,method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> feedBackSave(@RequestBody FeedBackVo feedBackVo) {
		Feedback entity = JSONUtil.trans(feedBackVo, Feedback.class);
		Date date = new Date();
		entity.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setCreate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setCreate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setStatus(FeedBackStatus.ENABLED.getVal());
		entity.setSource(FeedBackSource.JIHUIDUO.getVal());
		entity.setCreate_user(HJYBizDataContext.getSessionIdentity().getClientId());
		ClientDetail clientDetail = clientDetailService.get(HJYBizDataContext.getSessionIdentity().getClientId());
		if(clientDetail!=null){
			String create_user_name = clientDetail.getNick_name();
			entity.setCreate_user_name(create_user_name);
		}
		entity.setUpdate_user(HJYBizDataContext.getSessionIdentity().getClientId());
		String feedback_id = keyGenerate.getKeyGenerate(MallFields.FEEDBACK);
		entity.setFeedback_id(feedback_id);
		feedBackService.save(entity);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping(value="feedback/query",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> Query(@RequestBody FeedBackQueryVo vo) {
		Map<String,Object> map = JSONUtil.trans(vo, Map.class);
		
		/*Feedback entity = new Feedback();
		if (!StringUtil.isBlank(vo.getStatus())) {
			entity.setStatus(vo.getStatus());
		}
		if (!StringUtil.isBlank(vo.getContent())) {
			entity.setContent(vo.getContent());
		}
		if (!StringUtil.isBlank(vo.getType())) {
			entity.setType(vo.getType());
		}
		if (!StringUtil.isBlank(vo.getFeedback_id())) {
			entity.setFeedback_id(vo.getFeedback_id());
		}*/
		// entity.setSource("1");
		int total_num = feedBackService.queryCountBlur(map);
		
		if(vo.getLimit() != null && vo.getPage() != null){
			Pagination page = new Pagination();
			page.setPage_size(vo.getLimit());
			page.setPage_no(vo.getPage());
			page.setTotal_item_num(total_num);
			page.calc();
			map.put("page", page);
		}

		List<SortMarker> sortlist = new ArrayList<SortMarker>();
		SortMarker s1 = new SortMarker(MallFields.FEEDBACK_ID,false);
		
		sortlist.add(s1);
		map.put("sortMarkers", sortlist);
		
		List<Map<String, Object>> list = feedBackService.queryBlur(map);
		//list = (List<Map<String, Object>>) bizClientService.getUserInfoForFeedBack(new ClientsVo(list))
				//.get(BasicFields.RESULT_LIST);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(BasicFields.TOTOL_NUM, total_num);
		result.put(BasicFields.RESULT_LIST, dealAddExtend(list));
		return VOUtil.setSuccessResult(result);
	}

	@RequestMapping(value="feedback/reply",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> feedbackUpdate(@RequestBody FeedBackReplyVo vo) {
		String client_id = HJYBizDataContext.getSessionIdentity().getClientId();
		Feedback entity = new Feedback();
		if (!StringUtil.isBlank(vo.getFeedback_id())) {
			entity.setFeedback_id(vo.getFeedback_id());
		}
		if (!StringUtil.isBlank(vo.getReply_content())) {
			entity.setReply_content(vo.getReply_content());
		}
		entity.setReply_user(client_id);
		Date date = new Date();
		entity.setStatus(FeedBackStatus.REPLYED.getVal());
		entity.setReply_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDDHHMMSS));
		feedBackService.update(entity);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping(value="feedback/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> feedbackDelete(@RequestBody FeedBackDelete vo) {
		Feedback entity = new Feedback();
		if (!StringUtil.isBlank(vo.getFeedback_id())) {
			entity.setFeedback_id(vo.getFeedback_id());
		}
		entity.setStatus(FeedBackStatus.DISENABLED.getVal());
		feedBackService.update(entity);
		return VOUtil.genSuccessResult();
	}
	
	private List<Map<String, Object>> dealAddExtend(List<Map<String, Object>> list){
		//List<Map<String, Object>> result = new LinkedList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			String create_user = (String) map.get("create_user");
			map.put(MallFields.MOBILE_TEL,"");
			if(!StringUtil.isBlank(create_user)){
				Client client=clientService.get(create_user);
				if(client!=null){
					String mobile_tel = client.getMobile_tel();//注册手机
					map.put(MallFields.MOBILE_TEL,mobile_tel);
				}
				
				
			}
			//result.add(map);
			
			String reply_user = (String) map.get("reply_user");
			map.put(MallFields.REPLY_USRR_NAME,"");
			if(!StringUtil.isBlank(reply_user)){
				Operator operator=operatorService.get(reply_user);
				if(operator!=null){
					String reply_user_name=operator.getOperator_name();
					map.put(MallFields.REPLY_USRR_NAME,reply_user_name);
				}
				
			}
			
		}
		return list;
	}
}
