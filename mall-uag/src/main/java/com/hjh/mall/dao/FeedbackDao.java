package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.Feedback;

/**
 * @Project: hjy-middle
 * @Description 反馈管理业务层
 * @author 李慧峰
 * @date 2016年08月18日
 * @version V1.0
 */
public interface FeedbackDao extends DAOBase<Feedback, String> {

	public List<Map<String, Object>> findFeedbackByParm(Feedback entity);

	public int findFeedbackCountByParm(Feedback entity);
	
	public List<Map<String, Object>> queryBlur(Map<String,Object> appVersion);

	public int queryCountBlur(Map<String,Object> appVersion);

}