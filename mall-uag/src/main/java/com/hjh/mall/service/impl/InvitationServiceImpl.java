package com.hjh.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.dao.ClientDao;
import com.hjh.mall.dao.InvitationDao;
import com.hjh.mall.entity.Client;
import com.hjh.mall.entity.Invitation;
import com.hjh.mall.service.InvitationService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 邀请码管理业务层
 * @author 李慧峰
 * @date 2017年02月20日
 * @version V1.0
 */

@Service
public class InvitationServiceImpl extends HJYServiceImplBase<Invitation, String> implements InvitationService {
	@Resource
	private InvitationDao invitationDao;
	
	@Resource 
	private ClientDao clientDao;

	@Override
	protected DAOBase<Invitation, String> getDAO() {
		return invitationDao;
	}

	@Override
	public Map<String, Object> queryBlur(Map map) {
		Map<String, Object> result=new HashMap<String, Object>();
		
		Invitation entity = new Invitation();
		int total_num = invitationDao.countBlur(map);
		Pagination p = new Pagination();// 分页相关
		p.setPage_no((int)map.get("page"));
		p.setPage_size((int)map.get("limit"));
		p.setTotal_item_num(total_num);
		p.calc();
		
		List<SortMarker> sortlist = new ArrayList<SortMarker>();
		SortMarker s1 = new SortMarker();
		s1.setField("invite_id");
		s1.setAsc(false);
		sortlist.add(s1);
		entity.setPage(p);
		entity.setSortMarkers(sortlist);
		Map parm=JSONUtil.trans(entity, Map.class);
		map.putAll(parm);
		List<Invitation> list = invitationDao.queryBlur(map);
		result.put(BasicFields.TOTAL_NUM, total_num);
		result.put(BasicFields.RESULT_LIST, dealClient(list));
		
		return VOUtil.setSuccessResult(result);

	}
	
	private List<Map> dealClient(List<Invitation> list){
		List listM = new LinkedList();
		for (Invitation invitation : list) {
			Client entity = new Client();
			entity.setInvite_code(invitation.getInvite_code());
			int client_num=clientDao.count(entity);
			Map client=JSONUtil.trans(invitation, Map.class);
			client.put("client_num", client_num);
			listM.add(client);
		}
		
		return listM;
	}

}
