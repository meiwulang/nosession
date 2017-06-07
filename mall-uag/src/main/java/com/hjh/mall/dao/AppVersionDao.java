package com.hjh.mall.dao;

import java.util.List;
import java.util.Map;

import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.entity.AppVersion;

public interface AppVersionDao extends DAOBase<AppVersion, String> {
	public AppVersion queryLastVersion(AppVersion appVersion);

	public List<AppVersion> getAppVersionList(AppVersion appVersion);

	public int countAppVersionList(AppVersion appVersion);
	
	public List<AppVersion> queryBlur(Map<String,Object> appVersion);

	public int queryCountBlur(Map<String,Object> appVersion);

	public void updateVersionStatus(AppVersion appVersion);

	public void updateVersion(AppVersion appVersion);

	public void falseDelete(AppVersion appVersion);
}