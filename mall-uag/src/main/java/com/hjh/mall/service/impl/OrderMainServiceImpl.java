package com.hjh.mall.service.impl;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.category.bizapi.bizserver.goodscar.vo.AddGoodsCarModelsBatch;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.entity.SessionIdentity;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.ExcelTemplate;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.AppPagedQueryVO;
import com.hjh.mall.constants.CacheKeys;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.dao.ClientEnterpriseDao;
import com.hjh.mall.dao.OrderDetailDao;
import com.hjh.mall.dao.OrderMainDao;
import com.hjh.mall.entity.ClientEnterprise;
import com.hjh.mall.entity.OrderDetail;
import com.hjh.mall.entity.OrderMain;
import com.hjh.mall.field.MallErrorCode;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.service.OrderMainService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.CreateOrderVo;
import com.hjh.mall.vo.OrderDetailVo;
import com.hjh.mall.vo.QueryOrdersVo;

/**
 * @Project: hjh-mall
 * @Description 订单业务层
 * @author 王斌
 * @date 2017年02月20日
 * @version V1.0
 */

@Service
public class OrderMainServiceImpl extends HJYServiceImplBase<OrderMain, String> implements OrderMainService {
	@Resource
	private OrderMainDao orderMainDao;
	@Resource
	private KeyGenerate keyGenerate;
	@Resource
	private CacheHelper cacheHelper;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private ClientEnterpriseDao clientEnterpriseDao;
	@Resource
	private BusiSessionHelper busiSessionHelper;
	private static DecimalFormat format = new DecimalFormat("#.00");
	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;

	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

	@Override
	protected DAOBase<OrderMain, String> getDAO() {
		return orderMainDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> createOrder(CreateOrderVo vo) {
		// 对比订单信息和商品信息
		QueryGoodsVo goodsVo = new QueryGoodsVo();
		goodsVo.setGoods_id(vo.getPrdt_id());
		Map<String, Object> goods = queryGoods(goodsVo);
		String errorNo = (String) goods.get(BasicFields.ERROR_NO);
		// 商品不存在停止下单
		if (!BasicFields.SUCCESS.equals(errorNo)) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOODS_NOT_EXIST);
		}
		goods = ((List<Map<String, Object>>) goods.get(BasicFields.RESULT_LIST)).get(0);
		String goodStatus = (String) goods.get(com.hjh.mall.field.constant.MallFields.GOODS_STATUS);
		// 商品已下架
		if (StringUtil.isBlank(goodStatus) || !Status.ENABLED.getVal().equals(goodStatus)) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOODS_NOT_EXIST);
		}
		// 商品名称变化
		Object goodName = goods.get(com.hjh.mall.field.constant.MallFields.GOODS_NAME);
		if (StringUtil.isBlank((String) goodName) || !goodName.equals(vo.getPrdt_name())) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS);
		}
		// 判断传入的每条规格是否真实有效
		List<Map<String, Object>> standardList = (List<Map<String, Object>>) goods
				.get(com.hjh.mall.field.constant.MallFields.STANDARDLIST);
		int existStandard = 0;// 真实存在的规格数量
		List<OrderDetailVo> orderDetailVos = vo.getOrderDetailVos();// 传入的规格
		int StandsSize = orderDetailVos.size();// 传入的规格数量
		if (orderDetailVos == null || StandsSize < 1) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_SPEC);
		}
		for (Map<String, Object> standard : standardList) {
			for (OrderDetailVo detail : orderDetailVos) {
				Integer prdt_num = detail.getPrdt_num();
				if (prdt_num == null || prdt_num < 1) {// 数量不合法
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_NUM);
				}
				BigDecimal prdt_price = detail.getPrdt_price();
				if (prdt_price == null || prdt_price.compareTo(new BigDecimal(0)) < 0) {// 价格不合法
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_PRICE);
				}
				String standard_must = detail.getStandard_must();
				if (StringUtil.isBlank(standard_must)) {// 规格不存在
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_MUST_SPEC);
				}
				String voStandardId = detail.getStandard_id();
				if (StringUtil.isBlank(voStandardId)) {// 规格主键不存在
					return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS_ID);
				}
				String standardId = (String) standard.get(com.hjh.mall.field.constant.MallFields.STANDARD_ID);
				if (voStandardId.equals(standardId)) {// 规格是同一条时处理
					existStandard++;
					if (!standard_must.equals(standard.get(com.hjh.mall.field.constant.MallFields.STANDARD_MUST))) {// 必填规格不一致
						return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS);
					}
					String standardFirstSpc = (String) standard
							.get(com.hjh.mall.field.constant.MallFields.OPTIONAL_FIRST);
					String detailFirstSpc = detail.getOptional_first();
					if (StringUtil.isNotBlank(standardFirstSpc) && !standardFirstSpc.equals(detailFirstSpc)) {// 选填规格1不一致
						return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS);
					}
					String standardSecdSpc = (String) standard
							.get(com.hjh.mall.field.constant.MallFields.OPTIONAL_SECOND);
					String detailSecdSpc = detail.getOptional_second();
					if (StringUtil.isNotBlank(standardSecdSpc) && !standardSecdSpc.equals(detailSecdSpc)) {// 选填规格2不一致
						return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS);
					}
					BigDecimal standardPrice = new BigDecimal(
							(String) standard.get(com.hjh.mall.field.constant.MallFields.PRICE));
					if (!standardPrice.equals(detail.getPrdt_price())) {// 商品价格不一致
						return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOODS_ERROR_PRICE);
					}
					String standardUnitName = (String) standard.get(com.hjh.mall.field.constant.MallFields.UNIT_NAME);
					String detailUnitName = detail.getMetadata_name();
					if (StringUtil.isBlank(detailUnitName) || !standardUnitName.equals(detailUnitName)) {// 商品价格单位不一致
						return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS);
					}
				}
			}
		}
		// 如果传入的规格数量不等于真实存在的规格数量
		if (existStandard != StandsSize) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.GOOGS_ERROR_STANDANDS_ID);
		}
		// 下单处理
		OrderMain orderMain = JSONUtil.trans(vo, OrderMain.class);
		List<OrderDetail> orderDetails = JSONUtil.transInSide(vo.getOrderDetailVos(), OrderDetail.class);
		// 规格数小于1
		int orderListSize = orderDetails.size();
		if (orderListSize < 1) {
			return VOUtil.genErrorResult(MallErrorCode.OrderErrorCode.ERROR_SPEC);
		}
		// 生成必要字段
		String currentTime = DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDDHHMMSS_NO_BREAK);
		String primaryId = keyGenerate.getKeyGenerate(MallFields.ORDER_MAIN);
		String serializeNum = new StringBuffer().append(currentTime.substring(0, 8))
				.append(String.format("%s", primaryId.substring(primaryId.length() - 5))).toString();
		SessionIdentity sessionIdentity = HJYBizDataContext.getSessionIdentity();
		String clientId = sessionIdentity.getClientId();

		// 字段处理
		orderMain.setOrder_main_id(primaryId);
		orderMain.setSerialize_num(serializeNum);
		orderMain.setInit_date(currentTime.substring(0, 8));
		orderMain.setInit_time(currentTime.substring(8));
		String standard_ids = "";
		BigDecimal totalMoney = new BigDecimal(0.0);
		for (OrderDetail t : orderDetails) {
			String odDtlPk = keyGenerate.getKeyGenerate(MallFields.ORDER_DETAIL);
			t.setOrder_detail_id(odDtlPk);
			standard_ids += odDtlPk + ",";
			t.setPrdt_name(vo.getPrdt_name());
			totalMoney = totalMoney.add(new BigDecimal(t.getPrdt_num()).multiply(t.getPrdt_price()));
			t.initForClearNull();
		}
		orderMain.setTotal_money(totalMoney);
		orderMain.setStandard_ids(standard_ids);
		orderMain.setInvite_code(
				cacheHelper.hget(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + clientId, MallFields.INVITE_CODE));
		orderMain.setRegist_tel(
				cacheHelper.hget(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + clientId, MallFields.MOBILE_TEL));
		orderMain.setNick_name(
				cacheHelper.hget(CacheKeys.SESSION_CLIENT_DETAIL_PREFIX + clientId, MallFields.NICK_NAME));
		orderMain.setClient_id(clientId);
		orderMain.initForClearNull();
		// 保存
		save(orderMain);
		orderDetailDao.batchSave(orderDetails);
		// 重新生成新会话
		String accessToken = refreshSession(vo.getAccess_token(), sessionIdentity);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.ACCESS_TOKEN, accessToken);
		return result;
	}

	/**
	 * @date 2017年2月22日
	 * @Description:重新生成会话
	 * @author：王斌
	 * @param vo
	 * @param sessionIdentity
	 * @return String
	 */
	private String refreshSession(String accessToken, SessionIdentity sessionIdentity) {
		busiSessionHelper.destroySession(accessToken);
		String access_token = busiSessionHelper.renewAccess_token(sessionIdentity);
		String operator_noKey = busiSessionHelper.getClient_idKey(sessionIdentity.getClientId());
		// 根据会话令牌保存信息到缓存
		busiSessionHelper.setInfoForSession(operator_noKey, access_token);
		return access_token;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryOrder(QueryOrdersVo vo) {
		Map<String, Object> queryParams = JSONUtil.trans(vo, Map.class);
		Map<String, Object> result = VOUtil.genSuccessResult();
		if (vo.getLimit() != null && vo.getPage() != null) {
			Pagination page = new Pagination();
			page.setPage_size(vo.getLimit());
			page.setPage_no(vo.getPage());
			page.setTotal_item_num(orderMainDao.countByLike(queryParams));
			page.calc();
			queryParams.put("page", page);

		}
		List<SortMarker> sortMarkers = new ArrayList<>();
		sortMarkers.add(new SortMarker(MallFields.INIT_DATE, false));
		sortMarkers.add(new SortMarker(MallFields.INIT_TIME, false));
		queryParams.put("sortMarkers", sortMarkers);
		List<Map<String, Object>> resultList = orderMainDao.queryByLike(queryParams);
		for (Map<String, Object> map : resultList) {
			map.put(MallFields.TOTAL_MONEY, format.format(map.get(MallFields.TOTAL_MONEY)));
			OrderDetail example = new OrderDetail();
			example.setIds(Arrays.asList(((String) map.get(MallFields.STANDARD_IDS)).split(",")));
			List<Map<String, Object>> orderDetails = orderDetailDao.queryDetailsByIn(example);
			for (Map<String, Object> detail : orderDetails) {
				detail.put(MallFields.PRDT_PRICE, format.format(detail.get(MallFields.PRDT_PRICE)));
			}
			map.put(MallFields.STANDARD_DETAIL, orderDetails);
		}
		result.put(BasicFields.RESULT_LIST, resultList);
		result.put(BasicFields.TOTAL_NUM, orderMainDao.countByLike(queryParams));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutputStream exportOrder(QueryOrdersVo vo, OutputStream opts) {
		// 要求是全部导出
		vo.setLimit(Integer.MAX_VALUE);
		vo.setPage(0);
		List<Map<String, Object>> list = (List<Map<String, Object>>) queryOrder(vo).get(BasicFields.RESULT_LIST);
		ExcelTemplate et = ExcelTemplate.getInstance().readTemplateByClasspath("/excel/order_detail.xls");
		et.createNewRow();
		if (list != null) {
			int j = 1;
			HSSFDataFormat df = (HSSFDataFormat) et.getWb().createDataFormat();
			for (int i = 0; i < list.size(); i++) {
				et.createCell(j++);// 记录条数
				// et.getSheet().getRow(et.getCurRowIndex()).getCell(i).setCellStyle(curStyle);
				if (list.get(i).get(MallFields.SERIALIZE_NUM) != null) {
					et.createCell(list.get(i).get(MallFields.SERIALIZE_NUM).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.PRDT_ID) != null) {
					et.createCell(list.get(i).get(MallFields.PRDT_ID).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.PRDT_NAME) != null) {
					et.createCell(list.get(i).get(MallFields.PRDT_NAME).toString());
				} else {
					et.createCell(" ");
				}
				List<Map<String, Object>> standardDetailList = (List<Map<String, Object>>) list.get(i)
						.get(MallFields.STANDARD_DETAIL);
				int margeColsize = -1;
				if (standardDetailList != null && standardDetailList.size() > 0) {
					margeColsize = standardDetailList.size() - 1;
					for (int k = 0; k <= margeColsize; k++) {
						Map<String, Object> t = standardDetailList.get(k);
						String must = (String) t.get(MallFields.STANDARD_MUST);
						String first = (String) t.get(MallFields.OPTIONAL_FIRST);
						String second = (String) t.get(MallFields.OPTIONAL_SECOND);
						String cellString = must;
						if (StringUtil.isNotBlank(first)) {
							cellString += "|" + first;
						}
						if (StringUtil.isNotBlank(second)) {
							cellString += "|" + second;
						}
						et.createCell(cellString);
						String prdt_price = (String) t.get(MallFields.PRDT_PRICE);
						String metadata_name = (String) t.get(MallFields.METADATA_NAME);
						if (prdt_price != null) {
							// DecimalFormat dcmFmt = new DecimalFormat("0.00");
							// et.createCell(dcmFmt.format(prdt_price) + "/" +
							// metadata_name);
							et.createCell(prdt_price + "/" + metadata_name);
						} else {
							et.createCell(" ");
						}
						Long prdtNum = (Long) t.get(MallFields.PRDT_NUM);
						if (prdtNum != null) {
							et.createCell("" + prdtNum);
						} else {
							et.createCell(" ");
						}
						// 当超过子选项，补全Excel内容
						if (k != margeColsize) {
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							// 创建新行，继续填充非选项cell
							et.createNewRow();
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
							et.createCell(" ");
						} else {
							for (int l = 0; l < 4 && margeColsize > 0; l++) {
								// 合并
								et.getSheet().addMergedRegion(new CellRangeAddress(
										et.getCurRowIndex() - 1 - margeColsize, et.getCurRowIndex() - 1, l, l));
							}
						}
					}
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.TOTAL_MONEY) != null) {
					// DecimalFormat dcmFmt = new DecimalFormat("0.00");
					// et.createCell(dcmFmt.format((Double)
					// (list.get(i).get(MallFields.TOTAL_MONEY))));
					et.createCell((String) (list.get(i).get(MallFields.TOTAL_MONEY)));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.INIT_DATE) != null) {
					et.createCell(generateDate(list.get(i).get(MallFields.INIT_DATE).toString(),
							list.get(i).get(MallFields.INIT_TIME).toString()));
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.REGIST_TEL) != null) {
					String registTel = list.get(i).get(MallFields.REGIST_TEL).toString();
					String hgetInfo = cacheHelper.hget(CacheKeys.SESSION_CLIENT_INFO_PREFIX + registTel,
							com.hjh.mall.field.MallFields.CLIENT_CODE);
					if (StringUtil.isNotBlank(hgetInfo)) {

						et.createCell(hgetInfo.toString());
					} else {
						et.createCell(" ");
					}
					et.createCell(registTel);
				} else {
					et.createCell(" ");
					et.createCell(" ");
				}
				String clientId = (String) list.get(i).get(MallFields.CLIENT_ID);
				if (clientId != null) {
					String entInfo = cacheHelper.hget(
							com.hjh.mall.field.constant.CacheKeys.CLIENT_ENTERPRISE_DETAIL_PREFIX + clientId,
							MallFields.ENTERPRISE_NAME);
					if (StringUtil.isNotBlank(entInfo)) {
						et.createCell(entInfo.toString());
					} else {
						ClientEnterprise example = new ClientEnterprise();
						example.setClient_id(clientId);
						example = clientEnterpriseDao.query(example).get(0);
						if (example != null) {
							et.createCell(example.getEnterprise_name());
						} else {
							et.createCell(" ");
						}
					}
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.INVITE_CODE) != null) {
					et.createCell(list.get(i).get(MallFields.INVITE_CODE).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.CONSIGNEE) != null) {
					et.createCell(list.get(i).get(MallFields.CONSIGNEE).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.CONSIGNEE_TEL) != null) {
					et.createCell(list.get(i).get(MallFields.CONSIGNEE_TEL).toString());
				} else {
					et.createCell(" ");
				}
				if (list.get(i).get(MallFields.ADDRESS_INFO) != null) {
					et.createCell(list.get(i).get(MallFields.ADDRESS_INFO).toString());
				} else {
					et.createCell(" ");
				}
				if (margeColsize != -1) {
					for (int l = 7; l < 16 && margeColsize > 0; l++) {
						// for (int l = 7; l < 15 && margeColsize > 0; l++) {
						// for (int l = 7; l < list.get(i).size() - 1 &&
						// margeColsize > 0; l++) {

						Cell cell = et.getSheet().getRow(et.getCurRowIndex() - 1 - margeColsize).getCell(l);
						// 赋值
						cell.setCellValue(et.getCurRow().getCell(l).getStringCellValue());
						// 合并
						et.getSheet().addMergedRegion(new CellRangeAddress(et.getCurRowIndex() - 1 - margeColsize,
								et.getCurRowIndex() - 1, l, l));
					}
				}
				et.createNewRow();
			}
		}
		Map<String, String> datas = new HashMap<String, String>();
		String title = "机惠多订单 " + DateTimeUtil.getCurrentDateString(DateTimeUtil.FORMAT_YYYYMMDD);
		datas.put("title", title);
		et.replaceFinalData(datas);
		et.wirteToStream(opts);

		return opts;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> getClientsOrders(AppPagedQueryVO appPagedQueryVO) {
		String clientId = HJYBizDataContext.getSessionIdentity().getClientId();
		OrderMain orderMain = new OrderMain();
		orderMain.setClient_id(clientId);
		Pagination page = new Pagination();
		page.setPage_size(appPagedQueryVO.getPage_size());
		if (!"0".equals(appPagedQueryVO.getStart_id())) {
			orderMain.setOrder_main_id(appPagedQueryVO.getStart_id());
		}
		orderMain.setPage(page);
		List<OrderMain> queryList = orderMainDao.queryMinePaged(orderMain);
		List<Map> resultList = JSONUtil.transInSide(queryList, Map.class);
		for (Map<String, Object> map : resultList) {
			OrderDetail example = new OrderDetail();
			example.setIds(Arrays.asList(((String) map.get(MallFields.STANDARD_IDS)).split(",")));
			map.put(MallFields.STANDARD_DETAIL, orderDetailDao.queryDetailsByIn(example));
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@SuppressWarnings("static-access")
	private String generateDate(String dateString, String timeString) {
		String tempdate = "";
		if (dateString.length() == 8) {
			tempdate = dateString.format("%s-%s-%s", dateString.substring(0, 4), dateString.substring(4, 6),
					dateString.substring(6, 8));
		}
		if (timeString.length() == 6) {
			tempdate += timeString.format(" %s:%s:%s", timeString.substring(0, 2), timeString.substring(2, 4),
					timeString.substring(4, 6));
		}
		return tempdate;
	}

	/**
	 * @date 2017年4月12日
	 * @Description: 获取商品详情
	 * @author：王斌
	 * @param queryGoodsVo
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo) {
		Map<String, Object> result = bizGoodsService.queryGoods(queryGoodsVo);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.get(BasicFields.RESULT_LIST);
		for (Map<String, Object> map : resultList) {
			AddGoodsCarModelsBatch addGoodsCarModelsBatch = new AddGoodsCarModelsBatch();
			addGoodsCarModelsBatch.setGoods_id(map.get(MallFields.GOODS_ID).toString());
			addGoodsCarModelsBatch.setPage(1);
			addGoodsCarModelsBatch.setLimit(1000000);
			Map<String, Object> modelResult = bizGoodsCarModelService.queryGoodsCarModelList(addGoodsCarModelsBatch);
			map.put(com.hjh.mall.field.constant.MallFields.CAR_MODEL_LIST, modelResult.get(BasicFields.RESULT_LIST));
		}
		return result;
	}
}
