package com.hjh.mall.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.navigation.BizNavigationService;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.CreateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QuerySecondLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryThirdLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigation;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.filed.ImageType;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.ExcelTemplate;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.IOUtil;
import com.hjh.mall.common.core.util.ImageUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.dao.CategoryDao;
import com.hjh.mall.entity.Category;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.service.CategoryService;
import com.hjh.mall.service.GoodsService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.store.service.HjyStoreService;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.AddCategory;

/**
 * @Project: hjy-middle
 * @Description 类目业务层
 * @author 陈士俊
 * @date 2017年02月21日
 * @version V1.0
 */

@Service
public class CategoryServiceImpl extends HJYServiceImplBase<Category, String> implements CategoryService {
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private GoodsService goodsService;
	@Value("${http_proxy_ip: }")
	private String http_proxy_ip;

	@Value("${http_proxy_port: }")
	private String http_proxy_port;

	@Override
	protected DAOBase<Category, String> getDAO() {
		return categoryDao;
	}

	@Resource
	private HjyStoreService hjyStoreService;
	@Reference(version = "1.0.0")
	private BizNavigationService service;

	@Resource
	private BusiSessionHelper busiSessionHelper;

	@Override
	public void addLabel(AddCategory paramsEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public int countByLike(Map<String, Object> map) {
		return categoryDao.countByLike(map);
	}

	@Override
	public List<Map<String, String>> queryCategoryIdNames() {
		return categoryDao.queryCategoryIdNames();
	}

	@Override
	public List<Map<String, String>> queryLabelsBylike(Map<String, Object> map) {
		return categoryDao.queryLabelsBylike(map);
	}

	/**
	 * @date 2017年3月14日
	 * @Description: 上传图片
	 * @author：王斌
	 * @param imagecontext
	 *            ：图片内容
	 * @param type
	 *            ：图片类型
	 * @return String
	 */
	private String uploadOss(String imagecontext, String type) {
		if (imagecontext.length() < 139 && !imagecontext.contains("data")) {
			return imagecontext;
		}
		if (StringUtils.isNotBlank(imagecontext) && imagecontext.indexOf(",") > 0) {
			imagecontext = imagecontext.split(",")[1];
		}
		byte[] bytes = IOUtil.base64StringToBytes(imagecontext);
		ImageType image_type = ImageUtil.getImageType(bytes);
		if (image_type == null) {
			throw new HJHBCSErrInfoException(ErrorCode.ImageCode.ERROR_TYPE);
		}
		String suffix = image_type.getSuffix();
		UploadType uploadType = EnumUtil.valOf(type, UploadType.class);
		final StringBuffer key = new StringBuffer();
		key.append(uploadType.getDescription()).append("/").append(IDUtil.getOrdID16()).append(".").append(suffix);

		final InputStream inputStream = new ByteArrayInputStream(bytes);
		new Thread(new Runnable() {
			public void run() {
				hjyStoreService.setProxy(http_proxy_ip, http_proxy_port);
				hjyStoreService.uploadFile(key.toString(), inputStream); // 调用oss接口上传图片到阿里云

			}
		}).start();
		return key.toString();
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 创建导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	public Map<String, Object> createNavigation(@RequestBody CreateNavigation vo) {
		SessionIdentity operator = HJYBizDataContext.getSessionIdentity();
		if (operator == null) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		String operatorName = operator.getOperatorName();
		if (StringUtil.isBlank(operatorName)) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.INVALID_OPT_INTO);
		}
		vo.setUpdater(operatorName);
		vo.setUpdateUser(operator.getClientId());
		vo.setIcon(uploadOss(vo.getIcon(), UploadType.BRAND_LOGO.getVal()));
		String hotIcon = vo.getHotIcon();
		if (StringUtil.isNotBlank(hotIcon)) {
			vo.setHotIcon(uploadOss(hotIcon, UploadType.BRAND_LOGO.getVal()));
		}
		Map<String, Object> result = service.createNavigation(vo);
		if (result.get(BasicFields.ERROR_NO).equals("0")) {
			result.put(BasicFields.ACCESS_TOKEN, getNewAccessToken(vo));
			return result;
		} else {
			return result;
		}
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 编辑导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@ResponseBody
	public Map<String, Object> updateNavigation(UpdateNavigation vo) {
		SessionIdentity operator = HJYBizDataContext.getSessionIdentity();
		if (operator == null) {
			return VOUtil.genErrorResult(BasicErrorCodes.NOT_LOGGED);
		}
		String operatorName = operator.getOperatorName();
		if (StringUtil.isBlank(operatorName)) {
			return VOUtil.genErrorResult(ErrorCode.UserErrorCode.INVALID_OPT_INTO);
		}
		vo.setUpdater(operator.getOperatorName());
		vo.setUpdateUser(operator.getClientId());
		String icon = vo.getIcon();
		if (StringUtil.isNotBlank(icon)) {
			vo.setIcon(uploadOss(icon, UploadType.BRAND_LOGO.getVal()));
		}
		String hotIcon = vo.getHotIcon();
		if (StringUtil.isNotBlank(hotIcon)) {
			vo.setHotIcon(uploadOss(hotIcon, UploadType.BRAND_LOGO.getVal()));
		}
		if (StringUtil.isNotBlank(vo.getCategoryName())) {
			final UpdateNavigation solrVo = vo;
			new Thread() {
				public void run() {
					updateSolrInfo(solrVo);
				}
			}.start();
		}
		return service.updateNavigation(vo);
	}

	private String getNewAccessToken(HJYVO vo) {
		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		busiSessionHelper.destroySession(vo.getAccess_token());
		String access_token = busiSessionHelper.renewAccess_token(sessionIdentity);
		String operator_noKey = busiSessionHelper.getClient_idKey(sessionIdentity.getClientId());
		// 根据会话令牌保存信息到缓存
		busiSessionHelper.setInfoForSession(operator_noKey, access_token);
		return access_token;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> exportSecondLevelNavigations(QuerySecondLevelNavigations vo, OutputStream opts) {
		vo.setPage(0);
		vo.setLimit(Integer.MAX_VALUE);
		List<Map<String, Object>> list = (List<Map<String, Object>>) service.querySecondLevelNavigations(vo).get(
				BasicFields.RESULT_LIST);
		ExcelTemplate et = ExcelTemplate.getInstance().readTemplateByClasspath("/excel/secondLevelNavigation.xls");
		et.createNewRow();
		if (list != null) {
			int j = 1;
			for (int i = 0; i < list.size(); i++) {
				et.createCell(j++);// 记录条数
				if (list.get(i).get(MallFields.BID) != null) {
					et.createCell(list.get(i).get(MallFields.BID).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ANAME) != null) {
					et.createCell(list.get(i).get(MallFields.ANAME).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.BNAME) != null) {
					et.createCell(list.get(i).get(MallFields.BNAME).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ICON) != null) {
					et.createCell(list.get(i).get(MallFields.ICON).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.SORT) != null) {
					et.createCell(list.get(i).get(MallFields.SORT).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.INIT_DATE) != null) {
					et.createCell(StringUtil.generateDate(list.get(i).get(MallFields.INIT_DATE).toString(), list.get(i)
							.get(MallFields.INIT_TIME).toString()));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.CREATER) != null) {
					et.createCell(list.get(i).get(MallFields.CREATER).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.UPDATE_DATE) != null) {
					et.createCell(StringUtil.generateDate(list.get(i).get(MallFields.UPDATE_DATE).toString(),
							list.get(i).get(MallFields.UPDATE_TIME).toString()));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.UPDATER) != null) {
					et.createCell(list.get(i).get(MallFields.UPDATER).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.STATUS) != null) {
					et.createCell(StringUtil.generateStatus(list.get(i).get(MallFields.STATUS).toString()));
				} else {
					et.createCell(" ");
				}
				et.createNewRow();
			}
		}
		Map<String, String> datas = new HashMap<String, String>();
		String title = "二级类目 " + DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD);
		datas.put("title", title);
		et.replaceFinalData(datas);
		et.wirteToStream(opts);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.OUTPUTSTREAM, opts);
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> exporThirdLevelNavigations(QueryThirdLevelNavigations vo, OutputStream opts) {
		vo.setLimit(Integer.MAX_VALUE);
		vo.setPage(0);
		List<Map<String, Object>> list = (List<Map<String, Object>>) service.queryThirdLevelNavigations(vo).get(
				BasicFields.RESULT_LIST);
		ExcelTemplate et = ExcelTemplate.getInstance().readTemplateByClasspath("/excel/thirdLevelNavigation.xls");
		et.createNewRow();
		if (list != null) {
			int j = 1;
			for (int i = 0; i < list.size(); i++) {
				et.createCell(j++);// 记录条数
				if (list.get(i).get(MallFields.CID) != null) {
					et.createCell(list.get(i).get(MallFields.CID).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ANAME) != null) {
					et.createCell(list.get(i).get(MallFields.ANAME).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.BNAME) != null) {
					et.createCell(list.get(i).get(MallFields.BNAME).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.CNAME) != null) {
					et.createCell(list.get(i).get(MallFields.CNAME).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ICON) != null) {
					et.createCell(list.get(i).get(MallFields.ICON).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.SORT) != null) {
					et.createCell(list.get(i).get(MallFields.SORT).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.INIT_DATE) != null) {
					et.createCell(StringUtil.generateDate(list.get(i).get(MallFields.INIT_DATE).toString(), list.get(i)
							.get(MallFields.INIT_TIME).toString()));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.CREATER) != null) {
					et.createCell(list.get(i).get(MallFields.CREATER).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.UPDATE_DATE) != null) {
					et.createCell(StringUtil.generateDate(list.get(i).get(MallFields.UPDATE_DATE).toString(),
							list.get(i).get(MallFields.UPDATE_TIME).toString()));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.UPDATER) != null) {
					et.createCell(list.get(i).get(MallFields.UPDATER).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.STATUS) != null) {
					et.createCell(StringUtil.generateStatus(list.get(i).get(MallFields.STATUS).toString()));
				} else {
					et.createCell(" ");
				}
				et.createNewRow();
			}
		}
		Map<String, String> datas = new HashMap<String, String>();
		String title = "三级类目 " + DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD);
		datas.put("title", title);
		et.replaceFinalData(datas);
		et.wirteToStream(opts);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.OUTPUTSTREAM, opts);
		return result;
	}

	private void updateSolrInfo(UpdateNavigation vo) {
		GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
		String categoryId = vo.getCategoryId();
		String categoryName = vo.getCategoryName();
		if ("1".equals(vo.getLevel())) {
			goodsTimerVo.setFirst_category_id(categoryId);
			goodsTimerVo.setFirst_category_name(categoryName);
		}
		if ("2".equals(vo.getLevel())) {
			goodsTimerVo.setSecond_category_id(categoryId);
			goodsTimerVo.setSecond_category_name(categoryName);
		}
		if ("3".equals(vo.getLevel())) {
			goodsTimerVo.setThird_category_id(categoryId);
			goodsTimerVo.setThird_category_name(categoryName);
		}
		goodsService.updateGood(goodsTimerVo);
	}
}
