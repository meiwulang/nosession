package com.hjh.mall.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.entity.AppVersion;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.field.restfulapi.Constant;
import com.hjh.mall.field.restfulapi.RestFulAPI;
import com.hjh.mall.service.AppVersionService;
import com.hjh.mall.vo.appversion.AddAppVersionVO;
import com.hjh.mall.vo.appversion.DelVersion;
import com.hjh.mall.vo.appversion.GetVersionList;
import com.hjh.mall.vo.appversion.QueryAppVersionVO;
import com.hjh.mall.vo.appversion.UpdateVersion;
import com.hjh.mall.vo.appversion.UpdateVersionStatus;

import io.swagger.annotations.ApiOperation;


/**
 * @Project: mall-web
 * @Description 整合集客版本控制
 * @author 李慧峰
 * @date 2017年5月10日
 * @version V1.0 
 */
@Controller
public class AppVersionController {
	@Resource
	private AppVersionService appVersionService;

	/** 
	 * @date 2017年5月10日
	 * @Description: 100801 对app提供接口 获取最新版本
	 * @param queryAppVersionVO
	 * @return
	 * Map<String,Object>
	 */
	@NoLogin
	@RequestMapping(value=RestFulAPI.AppVersion.QUERYLASTVERSION,method = RequestMethod.POST)
	@ApiOperation(value = "app提供接口 获取最新版本", notes = "<table style=\"width:100%;font-size:14px;border-collapse: collapse;\"><thead><tr style=\"background-color:rgba(0, 136, 204,0.8); color: rgb(255, 255, 255);\"><th style=\"width:25%;text-align:left\">键</th><th style=\"width:25%;text-align:left\">类型</th><th style=\"width:48%;text-align:left\">说明</th></tr></thead><tbody><tr><td style=\"text-align:left\">version_status</td><td style=\"text-align:left\">string</td><td style=\"text-align:left\">1：激活，0 失效</td></tr></tbody></table>")
	@ResponseBody
	public Map<String, Object> queryLastVersion(@RequestBody QueryAppVersionVO queryAppVersionVO) {
		AppVersion appversion = appVersionService.queryLastVersion(queryAppVersionVO);
		if (null == appversion) {
			return VOUtil.genErrorResult(ErrorCode.ScheduleErrorCode.WITHOUT_RESULT);
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONUtil.trans(appversion, Map.class);
		return VOUtil.setSuccessResult(map);
	}
	
	@RequestMapping(value=RestFulAPI.AppVersion.UPDATEAPPVERSIONINFO,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAppVersionInfo(@RequestBody AddAppVersionVO versionVO) {
		AppVersion appVersion = JSONUtil.trans(versionVO, AppVersion.class);
		appVersion.setVersion_id(IDUtil.getOrdID16());
		appVersion.setUpdate_date(DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		appVersionService.save(appVersion);
		return VOUtil.genSuccessResult();
	}

	
	/** 
	 * @date 2017年5月10日
	 * @Description: 获取最新app
	 * @param queryAppVersionVO
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value=RestFulAPI.AppVersion.QUERY_WEB,method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> query(QueryAppVersionVO queryAppVersionVO) {
		AppVersion appversion = appVersionService.queryLastVersion(queryAppVersionVO);
		if (null == appversion) {
			return VOUtil.genErrorResult(ErrorCode.ScheduleErrorCode.WITHOUT_RESULT);
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> map = JSONUtil.trans(appversion, Map.class);
		return VOUtil.setSuccessResult(map);
	}
	
	
	/** 
	 * @date 2017年5月12日
	 * @Description: web端获取版本列表 100803
	 * @param getVersionList
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/appversion/query",method = RequestMethod.POST)
	@ApiOperation(value = "web端获取版本列表", notes = "web端获取版本列表")
	@ResponseBody
	public Map<String, Object> getAppVersion(@RequestBody GetVersionList getVersionList) {
		//getVersionList.setFunctionId(Constant.WEB_APP_VERSION_LIST);
		return appVersionService.getAppVersionList(getVersionList);
	}
	
	/** 
	 * @date 2017年5月12日
	 * @Description: web端生效或者失效版本 /json/100804
	 * @param updateVersionStatus
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/appversion/updatestatus",method = RequestMethod.POST)
	@ApiOperation(value = "web端生效或者失效版本", notes = "web端生效或者失效版本")
	@ResponseBody
	public Map<String, Object> updateVersionStatus(@RequestBody UpdateVersionStatus updateVersionStatus) {
		//updateVersionStatus.setFunctionId(Constant.MAKE_ACTITY_OR_NOT);
		return appVersionService.updateVersionStatus(updateVersionStatus);
	}
	
	/** 
	 * @date 2017年5月12日
	 * @Description: web端修改app版本信息   100805
	 * @param updateVersion
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/appversion/update",method = RequestMethod.POST)
	@ApiOperation(value = "web端修改app版本信息", notes = "web端修改app版本信息")
	@ResponseBody
	public Map<String, Object> updateVersion(@RequestBody UpdateVersion updateVersion) {
		//updateVersion.setFunctionId(Constant.UPDATE_APP_VERSION);
		return appVersionService.updateVersion(updateVersion);
	}
	
	/** 
	 * @date 2017年5月12日
	 * @Description: web端发布新的版本信息
	 * @param addAppVersionVO
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/appversion/add",method = RequestMethod.POST)
	@ApiOperation(value = "web端发布新的版本信息",notes="web端发布新的版本信息")
	@ResponseBody
	public Map<String, Object> publishVersion(@RequestBody AddAppVersionVO addAppVersionVO) {
		//addAppVersionVO.setFunctionId(Constant.PUBLISH_APP_VERSION);
		return appVersionService.publishVersion(addAppVersionVO);
	}
	
	/** 
	 * @date 2017年5月12日
	 * @Description: web端逻辑删除版本信息
	 * @param delVersion
	 * @return
	 * Map<String,Object>
	 */
	@RequestMapping(value="/appversion/del",method = RequestMethod.POST)
	@ApiOperation(value = "web端逻辑删除版本信息", notes = "web端逻辑删除版本信息")
	@ResponseBody
	public Map<String, Object> delVersion(@RequestBody DelVersion delVersion) {
		//delVersion.setFunctionId(Constant.DEL_APP_VERSION);
		return appVersionService.delVersion(delVersion);
	}
}
