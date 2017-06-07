package com.hjh.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.dao.FeedbackDao;
import com.hjh.mall.entity.Feedback;
import com.hjh.mall.service.FeedbackService;
import com.hjh.mall.service.base.HJYServiceImplBase;

/**
 * @Project: hjy-middle
 * @Description 反馈管理业务层
 * @author 李慧峰
 * @date 2016年08月18日
 * @version V1.0
 */

@Service
public class FeedbackServiceImpl extends HJYServiceImplBase<Feedback, String> implements FeedbackService {
	@Resource
	private FeedbackDao feedbackDao;

	@Override
	protected DAOBase<Feedback, String> getDAO() {
		return feedbackDao;
	}

	@Override
	public List<Map<String, Object>> findFeedbackByParm(Feedback entity) {
		return feedbackDao.findFeedbackByParm(entity);
	}

	@Override
	public int findFeedbackCountByParm(Feedback entity) {
		return feedbackDao.findFeedbackCountByParm(entity);
	}
	
	@Override
	public List<Map<String, Object>> queryBlur(Map<String,Object> map) {
		return feedbackDao.queryBlur(map);
	}

	@Override
	public int queryCountBlur(Map<String,Object> map) {
		return feedbackDao.queryCountBlur(map);
	}

}
