/** * @author  csj
 * @date 创建时间：2017年3月15日 下午1:42:15 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  */
package com.hjh.mall.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.category.bizapi.bizserver.car.BizCarModelService;
import com.hjh.mall.category.bizapi.bizserver.car.vo.AddCarModel;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelLike;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModels;
import com.hjh.mall.category.bizapi.bizserver.car.vo.QueryCarModelsApp;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdataCarStatusBatch;
import com.hjh.mall.category.bizapi.bizserver.car.vo.UpdateCarModel;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.ExcelUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.UpdateCarModelsVo;
import com.hjh.mall.model.CarModel;
import com.hjh.mall.service.impl.CarModelServiceImp;

@Controller
public class CarModelController {

	@Reference(version = "1.0.0")
	private BizCarModelService bizCarModelService;
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;
	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;
	@Resource
	private CarModelServiceImp carModelServiceImp;

	@RequestMapping("/addCarModel")
	@ResponseBody
	public Map<String, Object> addCarModel(@RequestBody AddCarModel addCarModel) {

		return bizCarModelService.addCarModel(addCarModel);
	}

	@RequestMapping("/updateCarModel")
	@ResponseBody
	public Map<String, Object> updateCarModel(@RequestBody UpdateCarModel updateCarModel) {
		Map<String, Object> result = bizCarModelService.updateCarModel(updateCarModel);
		String carId = updateCarModel.getCar_models_id();
		Map<String, Object> map = bizGoodsCarModelService.queryGoodsNamesByCarId(carId);
		if (map.get(BasicFields.ERROR_NO).toString().equals(BasicFields.DISABLE)) {
			// System.err.println("更新开始了。。。。。。。。。。。");
			List<String> ids = (List<String>) map.get(BasicFields.RESULT_LIST);
			MyThread mythread = new MyThread();
			mythread.setIds(ids);
			mythread.run();
		}
		return result;
	}

	@RequestMapping("/queryCarModelList")
	@ResponseBody
	public Map<String, Object> queryCarModel(@RequestBody QueryCarModels queryCarModels) {
		queryCarModels.blankStringToNull();
		return bizCarModelService.queryCarModelList(queryCarModels);
	}

	public class MyThread implements Runnable {

		private List<String> ids;

		public List<String> getIds() {
			return ids;
		}

		public void setIds(List<String> ids) {
			this.ids = ids;
		}

		@Override
		public void run() {
			GoodsTimerVo goodsTimerVo = new GoodsTimerVo();
			goodsTimerVo.setIds(ids);
			carModelServiceImp.UpdateSolrVo(goodsTimerVo);
			System.err.println("进入方法了。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");

		}

	}

	private void UpdateSolrVo(UpdateCarModelsVo updateModelsVo) {
		GoodsTimerVo goodsTimerVo = JSONUtil.trans(updateModelsVo, GoodsTimerVo.class);
		bizGoodsService.updateGoodsTimer(goodsTimerVo);

		String startDate = DateTimeUtil.toString(DateTimeUtil.getStartOfDay(new Date()),
				DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);
		String endDate = DateTimeUtil.toString(DateTimeUtil.getEndOfDay(new Date()),
				DateTimeUtil.FORMAT_YYYYMMDDHHMMSSSSS_NO_BREAK);

		Map<String, Object> allIdMap = bizGoodsService.queryAllId(startDate, endDate);
		List<Map<String, Object>> list = (List<Map<String, Object>>) allIdMap.get(BasicFields.RESULT_LIST);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (map.get(MallFields.ADAPT_ALL_MODELS).toString().equals(BasicFields.DISABLE)) {
					Map<String, Object> carTypeName = bizGoodsCarModelService.queryNamesByGoodsId(map.get("id")
							.toString());
					Map<String, Object> carModelMap = (Map<String, Object>) carTypeName.get("typeNamesInSolr");
					if (carModelMap != null) {
						map.put("car_brand_name", carModelMap.get("brand_names"));
						map.put("car_models_name", carModelMap.get("car_models_names"));
						map.put("car_type", carModelMap.get("car_types"));
					}
				}
			}
		}
		Map<String, Object> result = bizGoodsService.bathUpdateSolr(list);

	}

	@RequestMapping("/queryCarModelLike")
	@ResponseBody
	public Map<String, Object> queryCarModelLike(@RequestBody QueryCarModelLike queryCarModelLike) {

		return bizCarModelService.queryCarModellike(queryCarModelLike);
	}

	@RequestMapping("/updateCarStatusBatch")
	@ResponseBody
	public Map<String, Object> test(@RequestBody UpdataCarStatusBatch updataCarStatusBatch) {

		return bizCarModelService.updateCarTypeBatch(updataCarStatusBatch);
	}

	@RequestMapping("/exportCar")
	@SuppressWarnings("unchecked")
	public void export(QueryCarModels queryCarModels, HttpServletResponse response) {
		queryCarModels.blankStringToNull();
		Map<String, Object> map = bizCarModelService.exportCarModelList(queryCarModels);
		if (map.get(BasicFields.ERROR_NO).equals(BasicFields.DISABLE)) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(BasicFields.RESULT_LIST);
			try {
				exportExcel(list, response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 对象绑定输出流 ，输出
	 * @param list
	 * @param response
	 * @throws IOException
	 *             void
	 */
	private void exportExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
		// 生成提示信息，
		response.setContentType("application/vnd.ms-excel");
		// 生成文件名
		String codedFileName = "车型列表";
		codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
		response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
		OutputStream fOut = response.getOutputStream();
		ExcelUtil eu = ExcelUtil.getInstance();
		eu.exportObj2Excel(fOut, list, CarModel.class, true);
		// eu.exportObj2Excel("D:\\", list, CarModel.class, true);
		fOut.flush();
		fOut.close();
	}

	@RequestMapping("/json/test1")
	@ResponseBody
	public Map<String, Object> test1(@RequestBody AddGoodsCarModelsBatch addGoodsCarModelsBatch) {

		return bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
	}

	@RequestMapping("/json/test3")
	@ResponseBody
	public Map<String, Object> test3(@RequestBody AddGoodsCarModelsBatch addGoodsCarModelsBatch) {

		return bizGoodsCarModelService.queryGoodsCarModelListIsChoosed(addGoodsCarModelsBatch);
	}

	@RequestMapping("/json/test4")
	@ResponseBody
	public Map<String, Object> test4(@RequestBody AddGoodsCarModelsBatch addGoodsCarModelsBatch) {
		Map<String, Object> map = new HashMap<String, Object>();

		return bizGoodsCarModelService.queryNamesByGoodsId(addGoodsCarModelsBatch.getGoods_id());
	}

	@RequestMapping("/json/901007")
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryCarModelListApp(@RequestBody QueryCarModelsApp queryCarModelsApp) {

		return bizCarModelService.queryCarModelsListApp(queryCarModelsApp);
	}

	@RequestMapping("/queryCarModelListWeb")
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryCarModelListWeb(@RequestBody QueryCarModelsApp queryCarModelsApp) {

		return bizCarModelService.queryCarModelListWeb(queryCarModelsApp);
	}

	@RequestMapping("/json/901008")
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryCarModelNamesListApp(@RequestBody QueryCarModelLike queryCarModelLike) {

		return bizCarModelService.queryCarModellike(queryCarModelLike);
	}

	@RequestMapping("/json/901009")
	@ResponseBody
	@NoLogin
	public Map<String, Object> queryCarTypeByBrandId(@RequestBody QueryCarModelLike queryCarModelLike) {

		return bizCarModelService.queryCarTypeByBrandId(queryCarModelLike);
	}

}
