package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.navigation.BizNavigationService;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.CreateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryFirstLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsByLike;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsByparentId;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryNavigationsForApp;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QuerySecondLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryThirdLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigationStatus;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdatesingleNavigationStatus;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicErrorCodes;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.error.ErrorCode;
import com.hjh.mall.service.CategoryService;
import com.hjh.mall.util.BusiSessionHelper;

@Controller
public class NavigationController {
	private final String baseUrl = "/json/";
	@Reference(version = "1.0.0")
	private BizNavigationService service;
	@Resource
	private BusiSessionHelper busiSessionHelper;
	@Resource
	private CategoryService categoryService;

	/**
	 * @date 2017年3月15日
	 * @Description: 创建导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900505", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createNavigation(@RequestBody CreateNavigation vo) {
		return categoryService.createNavigation(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 编辑导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900506", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNavigation(@RequestBody UpdateNavigation vo) {
		return categoryService.updateNavigation(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 批量编辑导航状态
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900513", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNavigationStatus(@RequestBody UpdateNavigationStatus vo) {
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
		return service.batchUpdateNavigationStatus(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 修改单条导航状态
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900517", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatesingleNavigationStatus(@RequestBody UpdatesingleNavigationStatus vo) {
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
		return service.updateNavigationStatus(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 模糊查询1级导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900507", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryFirstLevelNavigations(@RequestBody QueryFirstLevelNavigations vo) {

		return service.queryFirstLevelNavigations(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 模糊查询2级导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900508", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> querySecondLevelNavigations(@RequestBody QuerySecondLevelNavigations vo) {
		return service.querySecondLevelNavigations(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 导出2级导航
	 * @author：王斌
	 * @param vo
	 * @param opts
	 * @return Map<String,Object>
	 * @throws IOException
	 */
	@RequestMapping(value = baseUrl + "900509", method = RequestMethod.GET)
	@ResponseBody
	@NoLogin
	public Map<String, Object> exportSecondLevelNavigations(QuerySecondLevelNavigations vo,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "二级类目" + DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD);
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		fOut = (OutputStream) categoryService.exportSecondLevelNavigations(vo, fOut).get(MallFields.OUTPUTSTREAM);
		fOut.flush();
		fOut.close();
		return null;
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 导出3级导航
	 * @author：王斌
	 * @param vo
	 * @param opts
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900510", method = RequestMethod.GET)
	@ResponseBody
	@NoLogin
	public Map<String, Object> exporThirdLevelNavigations(QueryThirdLevelNavigations vo, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "三级类目" + DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD);
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		fOut = (OutputStream) categoryService.exporThirdLevelNavigations(vo, fOut).get(MallFields.OUTPUTSTREAM);
		fOut.flush();
		fOut.close();
		return null;
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 模糊查询3级导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900511", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryThirdLevelNavigations(@RequestBody QueryThirdLevelNavigations vo) {
		return service.queryThirdLevelNavigations(vo);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 查询app导航
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900512", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryNavigationsForApp(@RequestBody QueryNavigationsForApp vo) {
		return service.queryNavigationsForApp(vo);
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 模糊查询启用状态的类目 app用
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900514", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryNavigationsByLike(@RequestBody QueryNavigationsByLike vo) {
		return service.queryNavigationsByLike(vo);
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 按父级编号查询类目
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900515", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryNavigationsByparentId(@RequestBody QueryNavigationsByparentId vo) {
		return service.queryNavigationsByparentId(vo);
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 查询二三级全部可用类目
	 * @author：王斌
	 * @param vo
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = baseUrl + "900516", method = RequestMethod.POST)
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryAllNavigationsForApp(@RequestBody HJYVO vo) {
		return service.queryAllNavigationsForApp(vo);
	}
}
