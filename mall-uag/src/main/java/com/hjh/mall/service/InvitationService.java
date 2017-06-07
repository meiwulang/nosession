package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.Invitation;

/**
 * @Project: hjy-middle
 * @Description 邀请码管理业务层
 * @author 李慧峰
 * @date 2017年02月20日
 * @version V1.0
 */
public interface InvitationService extends ServiceBase<Invitation, String> {
	public Map<String,Object> queryBlur(Map map);

}
