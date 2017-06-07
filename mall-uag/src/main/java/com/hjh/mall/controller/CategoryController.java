package com.hjh.mall.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.Category;
import com.hjh.mall.field.MallErrorCode;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.service.CategoryService;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.vo.AddCategory;
import com.hjh.mall.vo.QueryCategoryByLike;
import com.hjh.mall.vo.UpdateCategory;
import com.hjh.mall.vo.WebPagedQueryVO;
import com.hjh.mall.vo.client.UploadPicture;

/**
 * * @author csj:
 * 
 * @date 创建时间：2017年2月21日 下午4:15:51
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
public class CategoryController {
	@Resource
	private CategoryService categoryService;
	@Resource
	private UploadService uploadService;
	@Resource
	private KeyGenerate keyGenerate;

	@RequestMapping(value="/json/900501",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCategory(@RequestBody AddCategory paramsEntity) {
		String operator = HJYBizDataContext.getSessionIdentity().getOperatorName();
		// paramsEntity.setUpdate_user(HJYBizDataContext.getSessionIdentity().getClientId());
		String imgStr = paramsEntity.getIcon();
		if (!StringUtil.isBlank(imgStr)) {
			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(imgStr);
			uploadPicture.setUpload_type(UploadType.CATEGORYLOGO.getVal());
			String icon = (String) uploadService.uploadPic(uploadPicture).get(BasicFields.IMAGE_KEY);
			paramsEntity.setIcon(icon);
		}
		String labelName = paramsEntity.getCategory_name().trim();
		if (categoryService.query(new Category(labelName)).size() > 0) {
			return VOUtil.genErrorResult(MallErrorCode.CategoryErrorCode.CATEGORY_EXIT);
			// return null;
		}
		Category label = JSONUtil.trans(paramsEntity, Category.class);
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		label.setInit_date(dateString);
		label.setInit_time(timeString);
		label.setCreate_date(dateString);
		label.setCreate_time(timeString);
		label.setUpdate_date(dateString);
		label.setUpdate_time(timeString);
		label.setUpdate_user(HJYBizDataContext.getSessionIdentity().getClientId());
		label.setUpdater(operator);
		label.setCategory_id(keyGenerate.getKeyGenerate("category"));
		label.initForClearNull();
		categoryService.save(label);
		return VOUtil.genSuccessResult();
	}

	@RequestMapping(value="/json/900502",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateLabel(@RequestBody UpdateCategory paramsEntity) {
		String operator = HJYBizDataContext.getSessionIdentity().getOperatorName();
		String labelName = paramsEntity.getCategory_name().trim();
		if (categoryService.query(new Category(labelName)).size() > 1) {
			return VOUtil.genErrorResult(MallErrorCode.CategoryErrorCode.CATEGORY_EXIT);
		}
		String imgStr = paramsEntity.getIcon();
		if (!StringUtil.isBlank(imgStr)) {
			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(imgStr);
			uploadPicture.setUpload_type(UploadType.CATEGORYLOGO.getVal());
			String icon = (String) uploadService.uploadPic(uploadPicture).get(BasicFields.IMAGE_KEY);
			paramsEntity.setIcon(icon);
		}
		Category label = JSONUtil.trans(paramsEntity, Category.class);
		Date date = new Date();
		String dateString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK);
		String timeString = DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK);
		label.setUpdate_date(dateString);
		label.setUpdate_time(timeString);
		label.setUpdate_user(HJYBizDataContext.getSessionIdentity().getClientId());
		label.setUpdater(operator);
		categoryService.update(label);
		return VOUtil.genSuccessResult();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/json/900503",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryLabelsBylike(@RequestBody QueryCategoryByLike paramsEntity) {
		Category temp = new Category();
		try {
			BeanUtil.reflectionAttr(paramsEntity, temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> queryParams = JSONUtil.trans(temp, Map.class);
		int total = categoryService.countByLike(queryParams);
		setPageAndSort(paramsEntity, temp, total);
		Map<String, Object> result = VOUtil.genSuccessResult();
		queryParams = JSONUtil.trans(temp, Map.class);
		queryParams.put("start_date", paramsEntity.getStart_date());
		queryParams.put("end_date", paramsEntity.getEnd_date());
		result.put(BasicFields.RESULT_LIST, categoryService.queryLabelsBylike(queryParams));
		result.put(MallFields.TOTAL_NUM, total);
		return result;
	}

	private void setPageAndSort(WebPagedQueryVO paramsEntity, Category label, int total) {
		Pagination page = new Pagination();
		page.setPage_size(paramsEntity.getLimit());
		page.setPage_no(paramsEntity.getPage());
		page.setTotal_item_num(total);
		page.calc();
		List<SortMarker> sortMarkers = new ArrayList<>();
		// sortMarkers.add(new SortMarker(mall_Fileds.SORT_NUM, false));
		// sortMarkers.add(new SortMarker(mall_Fileds.PINYIN, true));
		sortMarkers.add(new SortMarker(MallFields.UPDATE_DATE, false));
		sortMarkers.add(new SortMarker(MallFields.UPDATE_TIME, false));
		label.setSortMarkers(sortMarkers);
		label.setPage(page);
	}

	@RequestMapping(value="/json/900504",method=RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryCategory(@RequestBody HJYVO vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, categoryService.queryCategoryIdNames());
		return result;
	}

	@RequestMapping(value="/queryCategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCategoryWeb(@RequestBody HJYVO vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, categoryService.queryCategoryIdNames());
		return result;
	}

	@RequestMapping(value="/deleteCategory",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCategory(@RequestBody UpdateCategory vo) {
		categoryService.delete(vo.getCategory_id());
		return VOUtil.genSuccessResult();
	}
}
