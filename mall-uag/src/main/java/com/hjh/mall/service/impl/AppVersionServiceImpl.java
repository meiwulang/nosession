package com.hjh.mall.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.dao.AppVersionDao;
import com.hjh.mall.entity.AppVersion;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.AppType;
import com.hjh.mall.field.type.AppUpdateType;
import com.hjh.mall.field.type.SortType;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.field.type.VersionStatusType;
import com.hjh.mall.service.AppVersionService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.vo.appversion.AddAppVersionVO;
import com.hjh.mall.vo.appversion.DelVersion;
import com.hjh.mall.vo.appversion.GetVersionList;
import com.hjh.mall.vo.appversion.QueryAppVersionVO;
import com.hjh.mall.vo.appversion.UpdateVersion;
import com.hjh.mall.vo.appversion.UpdateVersionStatus;



/**
 * @Project: mall-web
 * @Description 使用王斌jike 版本控制
 * @author 李慧峰
 * @date 2017年5月10日
 * @version V1.0 
 */
@Service
public class AppVersionServiceImpl extends HJYServiceImplBase<AppVersion, String> implements AppVersionService {
	@Resource
	private AppVersionDao appVersionDao;

	

	/**
	 * @date 2016年8月2日
	 * @Description: 获取最新版本信息
	 * @return AppVersion
	 */
	@Override
	public AppVersion queryLastVersion(QueryAppVersionVO queryAppVersionVO) {
		AppVersion appVersion = JSONUtil.trans(queryAppVersionVO, AppVersion.class);
		return appVersionDao.queryLastVersion(appVersion);
	}
	
	@Override
	public Map<String, Object> getAppVersionList(GetVersionList getVersionList) {
		Map<String,Object> map = JSONUtil.trans(getVersionList,Map.class);
		//AppVersion appVersion = new AppVersion();
		//try {
		//	BeanUtil.reflectionAttr(getVersionList, appVersion);
		//} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		map.put(MallFields.STATUS,Status.ENABLED.getVal());
		int total_num = appVersionDao.queryCountBlur(map);
		if(getVersionList.getLimit() != null && getVersionList.getPage() != null){
			Pagination page = new Pagination();
			page.setPage_size(getVersionList.getLimit());
			page.setPage_no(getVersionList.getPage());
			page.setTotal_item_num(total_num);
			page.calc();
			map.put("page", page);
		}

				
		List<AppVersion> list = appVersionDao.queryBlur(map);
		//List<AppVersion> list = appVersionDao.getAppVersionList(appVersion);
		List<AppVersion> list1 = getList(list);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.TOTAL_NUM,total_num);
		result.put(BasicFields.RESULT_LIST, list1);
		return result;
	}

	public List<AppVersion> getList(List<AppVersion> list){
		List<AppVersion> result = new ArrayList<>();
		try {
			for(AppVersion app:list){
				
				Date date = (Date) DateTimeUtil.toDate(app.getUpdate_date(),
							DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
				app.setUpdate_date(DateTimeUtil.toString(date).split(" ")[0]);
				AppType appType = EnumUtil.valOf(app.getApp_type(),AppType.class);
				VersionStatusType versionStatusType = EnumUtil.valOf(app.getVersion_status(),VersionStatusType.class);
				AppUpdateType appUpdateType = EnumUtil.valOf(app.getForce_update(),AppUpdateType.class);
				app.setApp_type(appType.getDescription());
				app.setVersion_status(versionStatusType.getDescription());
				app.setForce_update(appUpdateType.getDescription());
				result.add(app);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> updateVersionStatus(UpdateVersionStatus updateVersionStatus) {
		AppVersion appVersion =JSONUtil.trans(updateVersionStatus, AppVersion.class);
		appVersionDao.updateVersionStatus(appVersion);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> updateVersion(UpdateVersion updateVersion) {
		AppVersion appVersion =JSONUtil.trans(updateVersion, AppVersion.class);
		appVersionDao.updateVersion(appVersion);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> publishVersion(AddAppVersionVO addAppVersionVO) {
		AppVersion appVersion =JSONUtil.trans(addAppVersionVO, AppVersion.class);
		/*appVersion.setCreater_id(HJYBizDataContext.getSessionIdentity().getClientId());
		appVersion.setCreater_name(creater_name);*/
		Date date = new Date();
		appVersion.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		appVersion.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		appVersion.setVersion_id(IDUtil.getOrdID16());
		appVersion.initForClearNull();
		appVersionDao.save(appVersion);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> delVersion(DelVersion delVersion) {
		AppVersion appVersion = new AppVersion();
		appVersion.setVersion_id(delVersion.getVersion_id());
		appVersion.setStatus(Status.DISENABLED.getVal());
		appVersionDao.falseDelete(appVersion);
		return VOUtil.genSuccessResult();
	}
	@Override
	protected DAOBase<AppVersion, String> getDAO() {
		return appVersionDao;
	}

}
