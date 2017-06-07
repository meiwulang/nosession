package com.hjh.mall.service;

import java.util.Map;

import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.AppVersion;
import com.hjh.mall.vo.appversion.AddAppVersionVO;
import com.hjh.mall.vo.appversion.DelVersion;
import com.hjh.mall.vo.appversion.GetVersionList;
import com.hjh.mall.vo.appversion.QueryAppVersionVO;
import com.hjh.mall.vo.appversion.UpdateVersion;
import com.hjh.mall.vo.appversion.UpdateVersionStatus;

/**
 * @Project: hjy-middle
 * @Description app版本控制相关业务层
 * @author 王斌
 * @date 2016年08月02日
 * @version V1.0
 */
public interface AppVersionService extends ServiceBase<AppVersion, String> {

	/**
	 * @param queryAppVersionVO
	 * @date 2016年8月2日
	 * @Description: 获取最新版本信息
	 * @return AppVersion
	 */
	public AppVersion queryLastVersion(QueryAppVersionVO queryAppVersionVO);
	
	/**
	 * @date 2016年8月3日
	 * @Description: web端获取APP版本列表
	 * @param getVersionList
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getAppVersionList(GetVersionList getVersionList);
	
	/**
	 * @date 2016年8月3日
	 * @Description: web端更改APP版本更新类型
	 * @param updateVersionStatus
	 * @return Map<String,Object>
	 */
	public Map<String, Object> updateVersionStatus(UpdateVersionStatus updateVersionStatus);
	
	/**
	 * @date 2016年8月4日
	 * @Description: web端更改APP版本信息
	 * @param updateVersion
	 * @return Map<String,Object>
	 */
	public Map<String, Object> updateVersion(UpdateVersion updateVersion);
	
	/**
	 * @date 2016年8月4日
	 * @Description: web端发布新的APP版本信息
	 * @param addAppVersionVO
	 * @return Map<String,Object>
	 */
	public Map<String, Object> publishVersion(AddAppVersionVO addAppVersionVO);
	
	/**
	 * @date 2016年8月4日
	 * @Description: web端逻辑删除APP版本信息
	 * @param delVersion
	 * @return Map<String,Object>
	 */
	public Map<String, Object> delVersion(DelVersion delVersion);
}
