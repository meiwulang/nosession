package com.hjh.mall.controller;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.IDUtil;
import com.hjh.mall.common.core.util.IOUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.restfulapi.RestFulAPI;
import com.hjh.mall.goods.bizapi.bizserver.BizActivityService;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsForActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.DelActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivityDetailVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryActivitysGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoods4ActivityVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateActivityVo;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.vo.GetHtmlVo;
import com.hjh.mall.vo.UpdateUploadHtml;
import com.hjh.mall.vo.UploadHtml;
import com.hjh.mall.vo.UploadImg;

@Controller
public class ActivityController {
	@Reference(version = "1.0.0")
	private BizActivityService activityService;
	@Resource
	private UploadService uploadService;
	@Resource
	private KeyGenerate idGenerate;
	@Value("${ossUrl}")
	private String ossUrl;
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.UPLOAD_IMG, method = RequestMethod.POST)
	public Map<String, Object> uploadImg(@RequestBody UploadImg vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.IMG_URI, uploadService
				.uploadImg(EnumUtil.valOf(vo.getImgType(), UploadType.class).getDescription(), vo.getBase64Str()));
		return result;
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.UPLOAD_HTML, method = RequestMethod.POST)
	public Map<String, Object> uploadHtml(@RequestBody UploadHtml vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.ACT_TEXT_ID,
				uploadService.uploadHtml(UploadType.ACTIVITY.getDescription() + "/" + IDUtil.genUUID(), vo.getText()));
		return result;
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.UPDATE_AND_UPLOAD_HTML, method = RequestMethod.POST)
	public Map<String, Object> uploadImg(@RequestBody UpdateUploadHtml vo) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		uploadService.updateUploadHtml(vo.getTextUri(), vo.getText());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.GET_HTML_INFO, method = RequestMethod.POST)
	public Map<String, Object> getHtmlInfo(@RequestBody GetHtmlVo vo) {
		String fileInfo = "";
		URL url;
		String content_url = vo.getTextUri();
		try {
			if (!StringUtil.isBlank(content_url)) {
				url = new URL(ossUrl + content_url);
				InputStream input = url.openStream();
				fileInfo = IOUtil.inputStream2String(input);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put("text", fileInfo);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.ADD_ACTIVITY, method = RequestMethod.POST)
	public Map<String, Object> addActivity(@RequestBody AddActivityVo vo) {
		return activityService.addActivity(vo);
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.QUERY_ACTIVITYS_FOR_WEB, method = RequestMethod.POST)
	public Map<String, Object> queryActivitysForWeb(@RequestBody PageQueryVO vo) {
		return activityService.queryActivitysForWeb(vo);

	}

	@NoLogin
	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.QUERY_ACTIVITYS_FOR_APP, method = RequestMethod.POST)
	public Map<String, Object> queryActivitysForApp(@RequestBody HJYVO vo) {
		return activityService.queryActivitysForApp(vo);
	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.UPDATE_ACTIVITY, method = RequestMethod.POST)
	public Map<String, Object> updateActivity(@RequestBody UpdateActivityVo vo) {
		return activityService.updateActivity(vo);

	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.ADD_GOODS_FOR_ACTIVITY, method = RequestMethod.POST)
	public Map<String, Object> addGoodsForActivity(@RequestBody AddGoodsForActivityVo vo) {
		return activityService.addGoodsForActivity(vo);

	}

	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.DEL_ACTIVITYS_GOODS, method = RequestMethod.POST)
	public Map<String, Object> delActivitysGoods(@RequestBody DelActivitysGoodsVo vo) {
		return activityService.delActivitysGoods(vo);

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.QUERY_ACTIVITYS_GOODS, method = RequestMethod.POST)
	public Map<String, Object> queryActivitysGoods(@RequestBody QueryActivitysGoodsVo vo) {

		Map<String, Object> result = activityService.queryActivitysGoods(vo);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> map : resultList) {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(map.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setPage(1);
			addGoodsCarModelsBatch.setLimit(1000000);
			Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
			map.put(MallFields.CAR_MODEL_LIST, modelResult.get(BasicFields.RESULT_LIST));
		}
		return result;

	}

	@NoLogin
	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.QUERY_ACTIVITYS_DETAIL, method = RequestMethod.POST)
	public Map<String, Object> queryActivitysDetail(@RequestBody QueryActivityDetailVo vo) {
		return activityService.queryActivitysDetail(vo);

	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = RestFulAPI.Activity.GET_GOODS_WITHOUT_ACTIVITY, method = RequestMethod.POST)
	Map<String, Object> queryGoods(@RequestBody QueryGoods4ActivityVo vo) {
		Map<String, Object> result = activityService.queryGoods(vo);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> map : resultList) {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(map.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setPage(1);
			addGoodsCarModelsBatch.setLimit(1000000);
			Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
			map.put(MallFields.CAR_MODEL_LIST, modelResult.get(BasicFields.RESULT_LIST));
		}
		return result;
	}

}
