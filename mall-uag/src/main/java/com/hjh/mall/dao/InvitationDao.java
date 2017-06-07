package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.Invitation;
/**
 * @Project: hjy-middle
 * @Description 邀请码管理业务层
 * @author 李慧峰
 * @date 2017年02月20日
 * @version V1.0
 */
public interface InvitationDao extends DAOBase<Invitation, String> {
	
	public List<Invitation> queryBlur(Map map);
	
	
	public int countBlur(Map map);

}