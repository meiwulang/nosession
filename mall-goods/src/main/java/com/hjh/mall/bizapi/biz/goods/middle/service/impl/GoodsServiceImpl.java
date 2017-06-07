package com.hjh.mall.bizapi.biz.goods.middle.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hjh.mall.bizapi.biz.goods.middle.dao.ActivityGoodDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.CategoryDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsInfoDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsPicDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.GoodsStandardsDao;
import com.hjh.mall.bizapi.biz.goods.middle.dao.OperatorDao;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Goods;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsInfo;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsPic;
import com.hjh.mall.bizapi.biz.goods.middle.entity.GoodsStandards;
import com.hjh.mall.bizapi.biz.goods.middle.entity.Product4Order;
import com.hjh.mall.bizapi.biz.goods.middle.service.GoodsService;
import com.hjh.mall.bizapi.biz.goods.middle.service.SearchService;
import com.hjh.mall.bizapi.biz.goods.middle.service.UploadService;
import com.hjh.mall.bizapi.biz.goods.middle.service.base.HJYServiceImplBase;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.EnumUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.PageQueryVO;
import com.hjh.mall.es.base.GoodsSorl;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.enums.SolrFactesFields;
import com.hjh.mall.field.enums.SolrSortFields;
import com.hjh.mall.field.error.MallErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.goods.bizapi.bizserver.vo.AddGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ChmodBathVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.GetJsonGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.ModifyGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryJsonGoodsListVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QueryPageLimitVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.QuerySolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UndercarriageGoodsVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UpdateSolrVo;
import com.hjh.mall.goods.bizapi.bizserver.vo.UploadPicture;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;

/**
 * @Project: hjy-middle
 * @Description 商品业务层
 * @author 杨益桦
 * @date 2017年02月18日
 * @version V1.0
 */

@Service
public class GoodsServiceImpl extends HJYServiceImplBase<Goods, String> implements GoodsService {
	@Resource
	private GoodsDao goodsDao;

	@Resource
	private CategoryDao categoryDao;

	@Resource
	private GoodsStandardsDao goodsStandardsDao;

	@Resource
	private KeyGenerate keyGenerate;

	@Resource
	private GoodsPicDao goodsPicDao;

	@Resource
	private GoodsInfoDao goodsInfoDao;

	@Resource
	private UploadService uploadService;

	@Resource
	private OperatorDao operatorDao;
	@Resource
	private ActivityGoodDao activityGoodDao;
	@Resource
	private SearchService searchService;

	@Override
	protected DAOBase<Goods, String> getDAO() {
		return goodsDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryGoods(QueryGoodsVo queryGoodsVo) {
		Goods goods = new Goods();
		// 将queryGoodsVo转为goods
		transGoods(queryGoodsVo, goods);
		goods.setStatus(BasicFields.ENABLE);

		Map<String, Object> goodsMap = JSONUtil.trans(goods, Map.class);
		goodsMap.put(MallFields.START_DATE, queryGoodsVo.getStart_date());
		goodsMap.put(MallFields.END_DATE, queryGoodsVo.getEnd_date());
		goodsMap.put(MallFields.UPDATE_START_DATE, queryGoodsVo.getUpdate_start_date());
		goodsMap.put(MallFields.UPDATE_END_DATE, queryGoodsVo.getUpdate_end_date());

		int total = goodsDao.countQueryGoods(goodsMap);
		// 添加分页和排序参数
		addSrotAndPageParam(queryGoodsVo, goods, total);
		goodsMap = JSONUtil.trans(goods, Map.class);
		goodsMap.put(MallFields.START_DATE, queryGoodsVo.getStart_date());
		goodsMap.put(MallFields.END_DATE, queryGoodsVo.getEnd_date());
		goodsMap.put(MallFields.UPDATE_START_DATE, queryGoodsVo.getUpdate_start_date());
		goodsMap.put(MallFields.UPDATE_END_DATE, queryGoodsVo.getUpdate_end_date());
		if (queryGoodsVo.getIds() != null && queryGoodsVo.getIds().size() > 0) {
			goodsMap.put("ids", queryGoodsVo.getIds());
		}
		List<Map<String, Object>> goodsList = goodsDao.queryGoods(goodsMap);

		List<Map<String, Object>> resultList = new ArrayList<>();
		// 给每个商品遍历添加类目名称
		ergodicGoods(goodsList, resultList);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, goodsList);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	/**
	 * @date 2017年2月18日
	 * @Description: 将queryGoodsVo转为goods
	 * @author 杨益桦
	 * @param queryGoodsVo
	 * @param goods
	 *            void
	 */
	private void transGoods(QueryGoodsVo queryGoodsVo, Goods goods) {
		try {
			BeanUtil.reflectionAttr(queryGoodsVo, goods);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017年2月18日
	 * @Description: 给每个商品遍历添加类目名称
	 * @author 杨益桦
	 * @param goodsList
	 * @param resultList
	 *            void
	 */
	@SuppressWarnings("unchecked")
	private void ergodicGoods(List<Map<String, Object>> goodsList, List<Map<String, Object>> resultList) {
		for (Map<String, Object> map : goodsList) {
			Goods singleGoods = JSONUtil.trans(map, Goods.class);
			// 获取商品规格
			GoodsStandards goodsStandards = new GoodsStandards();
			goodsStandards.setIds(Arrays.asList(singleGoods.getStandard_ids().split(",")));
			goodsStandards.setStatus(BasicFields.ENABLE);
			List<Map<String, Object>> standardList = goodsStandardsDao.queryByIds(goodsStandards);
			for (Map<String, Object> standard : standardList) {
				BigDecimal bd = (BigDecimal) standard.get(MallFields.PRICE);
				standard.put(MallFields.PRICE, bd.setScale(2).toString());
				BigDecimal referencePrice = (BigDecimal) standard.get(MallFields.REFERENCE_PRICE);
				if("0.00".equals( referencePrice.setScale(2).toString())){
					standard.remove(MallFields.REFERENCE_PRICE);
				}else{
					standard.put(MallFields.REFERENCE_PRICE, referencePrice.setScale(2).toString());
				}
				standard.put(MallFields.MAX_SALE_NUM, standard.get(MallFields.MAX_SALE_NUM));
			}
			map.put("standardList", standardList);
			for (Map<String, Object> standMap : standardList) {
				standMap.put(MallFields.UNIT_NAME, singleGoods.getUnit_name());
			}

			String[] bannerArray = singleGoods.getBanner_ids().split(",");
			List<Map<String, Object>> bannerList = new ArrayList<>();
			for (String id : bannerArray) {
				Map<String, Object> bannerMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picId = goodsPic.getPic_id();
				}
				bannerMap.put(MallFields.BANNER_URL, picUrl);
				bannerMap.put(MallFields.PIC_ID, picId);
				bannerList.add(bannerMap);
			}
			map.put("bannerList", bannerList);

			String[] detailArray = singleGoods.getDetail_ids().split(",");
			List<Map<String, Object>> detailList = new ArrayList<>();
			for (String id : detailArray) {
				Map<String, Object> detailMap = new HashMap<>();
				String picUrl = "";
				String picId = "";
				String picDesc = "";
				GoodsPic goodsPic = goodsPicDao.get(id);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
					picDesc = goodsPic.getPic_desc();
					picId = goodsPic.getPic_id();
				}
				detailMap.put(MallFields.DETAIL_URL, picUrl);
				detailMap.put(MallFields.PIC_ID, picId);
				detailMap.put(MallFields.PIC_DESC, picDesc);
				detailList.add(detailMap);
			}
			map.put("detailList", detailList);

			String[] infoArray = singleGoods.getInfo_ids().split(",");
			List<Map<String, Object>> infoList = new ArrayList<>();
			for (String id : infoArray) {
				Map<String, Object> infoMap = new HashMap<>();
				String infoContent = "";
				String infoTitle = "";
				String infoId = "";
				GoodsInfo goodsInfo = goodsInfoDao.get(id);
				if (goodsInfo != null && !StringUtils.isBlank(goodsInfo.getInfo_title())) {
					infoTitle = goodsInfo.getInfo_title();
					infoContent = goodsInfo.getInfo_content();
					infoId = goodsInfo.getInfo_id();
				}
				infoMap.put(MallFields.INFO_TITLE, infoTitle);
				infoMap.put(MallFields.INFO_ID, infoId);
				infoMap.put(MallFields.INFO_CONTENT, infoContent);
				infoList.add(infoMap);
			}
			map.put(MallFields.INFOLIST, infoList);
			map.put(MallFields.SORT, singleGoods.getSort());

			resultList.add(map);
		}
	}

	/**
	 * @date 2017年2月18日
	 * @Description: 添加分页和排序参数
	 * @author 杨益桦
	 * @param queryGoodsVo
	 * @param goods
	 * @param total
	 *            void
	 */
	private void addSrotAndPageParam(QueryGoodsVo queryGoodsVo, Goods goods, int total) {
		Pagination pagination = createPage(total, queryGoodsVo.getPage(), queryGoodsVo.getLimit());
		goods.setPage(pagination);

		SortMarker date = createMarker(false, MallFields.UPDATE_DATE);
		SortMarker time = createMarker(false, MallFields.UPDATE_TIME);
		List<SortMarker> list = new ArrayList<>();
		list.add(date);
		list.add(time);
		goods.setSortMarkers(list);
	}

	private Pagination createPage(int total, int pageNum, int limit) {
		Pagination page = new Pagination();
		page.setPage_size(limit);
		page.setPage_no(pageNum);
		page.setTotal_item_num(total);
		page.calc();
		return page;
	}

	private SortMarker createMarker(boolean asc, String field) {
		SortMarker sortMarker = new SortMarker();
		sortMarker.setAsc(asc);
		sortMarker.setField(field);
		return sortMarker;
	}

	@Override
	@Transactional
	public Map<String, Object> addGoods(AddGoodsVo addGoodsVo) {
		Date date = new Date();
		Goods goods = JSONUtil.trans(addGoodsVo, Goods.class);

		goods.setUpdate_user(addGoodsVo.getHjy_operator_id());
		goods.setUpdate_user_name(addGoodsVo.getHjy_operator_name());
		goods.setCreate_user_id(addGoodsVo.getHjy_operator_id());
		goods.setCreate_user_name(addGoodsVo.getHjy_operator_name());

		// 图片不用在此处理,直接存路径

		if (!StringUtils.isBlank(addGoodsVo.getAd_url())) {
			// 添加占位图
			// addAdUrl(addGoodsVo, goods);
			goods.setAd_url(addGoodsVo.getAd_url());
		}

		// 添加首页展示图
		// addShowUrl(addGoodsVo, goods);

		// goods.setGoods_status(BasicFields.ENABLE);

		// 批量保存规格
		StringBuilder priceArea = new StringBuilder();
		StringBuilder standardIds = new StringBuilder();
		saveStandardList(addGoodsVo, standardIds, date, priceArea);

		// 修改最低价格
		String minPrice = priceArea.toString().split("-")[0];
		goods.setMin_price(minPrice);

		// 批量保存详情
		StringBuilder detailIds = new StringBuilder();
		saveDetailList(addGoodsVo, detailIds, date);

		// 批量保存banner
		StringBuilder bannerIds = new StringBuilder();
		saveBannerList(addGoodsVo, bannerIds, date);

		// 批量保存简介
		StringBuilder infoIds = new StringBuilder();
		saveInfoList(addGoodsVo, infoIds, date);

		// 保存商品
		saveGoods(goods, standardIds, detailIds, bannerIds, infoIds, date, priceArea.toString());

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(MallFields.GOODS_ID, goods.getGoods_id());
		return result;
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 添加首页展示图
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param goods
	 *            void
	 */
	private void addShowUrl(AddGoodsVo addGoodsVo, Goods goods) {
		UploadPicture uploadPicture = new UploadPicture();
		uploadPicture.setImagecontext(addGoodsVo.getShow_url());
		uploadPicture.setUpload_type(UploadType.GOODS_SHOW.getVal());
		Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
		goods.setShow_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 添加占位图
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param goods
	 *            void
	 */
	private void addAdUrl(AddGoodsVo addGoodsVo, Goods goods) {
		UploadPicture uploadPicture = new UploadPicture();
		uploadPicture.setImagecontext(addGoodsVo.getAd_url());
		uploadPicture.setUpload_type(UploadType.GOODS_AD.getVal());
		Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
		goods.setAd_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 保存商品
	 * @author 杨益桦
	 * @param goods
	 * @param standardIds
	 * @param detailIds
	 * @param bannerIds
	 * @param infoIds
	 *            void
	 */
	private void saveGoods(Goods goods, StringBuilder standardIds, StringBuilder detailIds, StringBuilder bannerIds,
			StringBuilder infoIds, Date date, String priceArea) {
		goods.setGoods_id(addPrimaryId(MallFields.GOODS));
		goods.setDetail_ids(detailIds.toString());
		goods.setInfo_ids(infoIds.toString());
		goods.setBanner_ids(bannerIds.toString());
		goods.setStandard_ids(standardIds.toString());
		goods.setStatus(BasicFields.ENABLE);
		goods.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setPrice_area(priceArea);
		goods.initForClearNull();
		save(goods);
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 批量保存简介
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param infoIds
	 *            void
	 */
	private void saveInfoList(AddGoodsVo addGoodsVo, StringBuilder infoIds, Date date) {
		List<Map<String, Object>> infoList = addGoodsVo.getInfoList();
		for (Map<String, Object> map : infoList) {
			GoodsInfo goodsInfo = JSONUtil.trans(map, GoodsInfo.class);
			goodsInfo.setInfo_id(addPrimaryId(MallFields.GOODS_INFO));
			goodsInfo.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsInfo.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsInfo.setStatus(BasicFields.ENABLE);
			goodsInfo.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsInfo.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsInfo.initForClearNull();
			goodsInfoDao.save(goodsInfo);
			infoIds.append(goodsInfo.getInfo_id()).append(",");
		}
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 批量保存banner
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param bannerIds
	 *            void
	 */
	private void saveBannerList(AddGoodsVo addGoodsVo, StringBuilder bannerIds, Date date) {
		List<Map<String, Object>> bannerList = addGoodsVo.getBannerList();
		for (Map<String, Object> map : bannerList) {
			GoodsPic goodsPic = JSONUtil.trans(map, GoodsPic.class);

			// UploadPicture uploadPicture = new UploadPicture();
			// uploadPicture.setImagecontext(goodsPic.getPic_url());
			// uploadPicture.setUpload_type(UploadType.GOODS_BANNER.getVal());
			// Map<String, Object> uploadResult =
			// uploadService.uploadPic(uploadPicture);
			goodsPic.setPic_url(goodsPic.getPic_url());

			goodsPic.setPic_id(addPrimaryId(MallFields.GOODS_PIC));
			goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsPic.setStatus(BasicFields.ENABLE);
			goodsPic.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsPic.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsPic.initForClearNull();
			goodsPicDao.save(goodsPic);
			bannerIds.append(goodsPic.getPic_id()).append(",");
		}
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 批量保存详情
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param detailIds
	 *            void
	 */
	private void saveDetailList(AddGoodsVo addGoodsVo, StringBuilder detailIds, Date date) {
		List<Map<String, Object>> detailList = addGoodsVo.getDetailList();
		for (Map<String, Object> map : detailList) {
			GoodsPic goodsPic = JSONUtil.trans(map, GoodsPic.class);

			// UploadPicture uploadPicture = new UploadPicture();
			// uploadPicture.setImagecontext(goodsPic.getPic_url());
			// uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
			// Map<String, Object> uploadResult =
			// uploadService.uploadPic(uploadPicture);
			goodsPic.setPic_url(goodsPic.getPic_url());

			goodsPic.setPic_id(addPrimaryId(MallFields.GOODS_PIC));
			goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsPic.setStatus(BasicFields.ENABLE);
			goodsPic.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
			goodsPic.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
			goodsPic.initForClearNull();
			goodsPicDao.save(goodsPic);
			detailIds.append(goodsPic.getPic_id()).append(",");
		}
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 批量保存规格
	 * @author 杨益桦
	 * @param addGoodsVo
	 * @param standardIds
	 *            void
	 */
	private void saveStandardList(AddGoodsVo addGoodsVo, StringBuilder standardIds, Date date,
			StringBuilder priceArea) {
		List<Map<String, Object>> stanardList = addGoodsVo.getStandardList();
		double minPrice = 0;
		double maxPrice = 0;
		boolean first = true;
		if (stanardList != null && stanardList.size() > 0) {
			for (Map<String, Object> map : stanardList) {
				GoodsStandards goodsStandards = JSONUtil.trans(map, GoodsStandards.class);
				goodsStandards.setStandard_id(addPrimaryId(MallFields.GOODS_STANDARD));
				goodsStandards.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsStandards.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsStandards.setStatus(BasicFields.ENABLE);
				Integer maxSaleNum = (Integer) map.get(MallFields.MAX_SALE_NUM);
				maxSaleNum = maxSaleNum <= MallFields.MAX_SALE_NUM_VALUE ? maxSaleNum : MallFields.MAX_SALE_NUM_VALUE;
				goodsStandards.setMax_sale_num(maxSaleNum);
				goodsStandards.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsStandards.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsStandards.initForClearNull();
				goodsStandardsDao.save(goodsStandards);
				standardIds.append(goodsStandards.getStandard_id()).append(",");

				// 找到最大与最小值
				double currentPrice = goodsStandards.getPrice().doubleValue();
				if (first) {
					first = false;
					minPrice = currentPrice;
					maxPrice = currentPrice;
				}
				if (maxPrice < currentPrice) {
					maxPrice = currentPrice;
				}
				if (minPrice > currentPrice) {
					minPrice = currentPrice;
				}
			}
		}
		priceArea.append(String.valueOf(minPrice)).append("-").append(String.valueOf(maxPrice));

	}

	/**
	 * @date 2017年2月18日
	 * @Description: 生成主键
	 * @author 杨益桦
	 * @param tableName
	 * @return String
	 */
	private String addPrimaryId(String tableName) {
		String activityId = keyGenerate.getKeyGenerate(tableName);
		return activityId;
	}

	@Override
	public Map<String, Object> modifyGoods(ModifyGoodsVo modifyGoodsVo) {
		Date date = new Date();
		Goods goods = JSONUtil.trans(modifyGoodsVo, Goods.class);

		goods.setUpdate_user(modifyGoodsVo.getHjy_operator_id());
		goods.setUpdate_user_name(modifyGoodsVo.getHjy_operator_name());
		if (!StringUtils.isBlank(modifyGoodsVo.getAd_url())) {
			if (modifyGoodsVo.getAd_url().contains("data") || !modifyGoodsVo.getAd_url().contains("GOODS")) {
				// 添加占位图
				// addAdUrl(modifyGoodsVo, goods);
				goods.setAd_url(modifyGoodsVo.getAd_url());
			}
		}
		if (!StringUtils.isBlank(modifyGoodsVo.getShow_url()) && modifyGoodsVo.getShow_url().contains("data")
				|| !modifyGoodsVo.getShow_url().contains("GOODS")) {
			// 添加首页展示图
			// addShowUrl(modifyGoodsVo, goods);
			goods.setShow_url(modifyGoodsVo.getShow_url());
		}
		// 批量保存规格
		StringBuilder priceArea = new StringBuilder();
		StringBuilder standardIds = new StringBuilder();
		modifyStandardList(modifyGoodsVo, standardIds, date, priceArea);

		String minPrice = priceArea.toString().split("-")[0];
		goods.setMin_price(minPrice);

		// 批量保存详情
		StringBuilder detailIds = new StringBuilder();
		modifyDetailList(modifyGoodsVo, detailIds, date);

		// 批量保存banner
		StringBuilder bannerIds = new StringBuilder();
		modifyBannerList(modifyGoodsVo, bannerIds, date);

		// 批量保存简介
		StringBuilder infoIds = new StringBuilder();
		modifyInfoList(modifyGoodsVo, infoIds, date);

		// 更新商品
		updateGoods(goods, standardIds, detailIds, bannerIds, infoIds, date, priceArea.toString());
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 添加首页展示图
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param goods
	 *            void
	 */
	private void addShowUrl(ModifyGoodsVo modifyGoodsVo, Goods goods) {
		UploadPicture uploadPicture = new UploadPicture();
		uploadPicture.setImagecontext(modifyGoodsVo.getShow_url());
		uploadPicture.setUpload_type(UploadType.GOODS_SHOW.getVal());
		Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
		goods.setShow_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 添加占位图
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param goods
	 *            void
	 */
	private void addAdUrl(ModifyGoodsVo modifyGoodsVo, Goods goods) {
		UploadPicture uploadPicture = new UploadPicture();
		uploadPicture.setImagecontext(modifyGoodsVo.getAd_url());
		uploadPicture.setUpload_type(UploadType.GOODS_AD.getVal());
		Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
		goods.setAd_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 修改规格
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param standardIds
	 * @param date
	 * @param priceArea
	 *            void
	 */
	private void modifyStandardList(ModifyGoodsVo modifyGoodsVo, StringBuilder standardIds, Date date,
			StringBuilder priceArea) {
		List<Map<String, Object>> stanardList = modifyGoodsVo.getStandardList();
		double minPrice = 0;
		double maxPrice = 0;
		boolean first = true;
		if (stanardList != null && stanardList.size() > 0) {
			for (Map<String, Object> map : stanardList) {
				GoodsStandards goodsStandards = JSONUtil.trans(map, GoodsStandards.class);
				if (StringUtils.isBlank(goodsStandards.getStandard_id())) {
					// 当规格id为空的时候执行将路径保存
					goodsStandards.setStandard_id(addPrimaryId(MallFields.GOODS_STANDARD));
					goodsStandards.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
					goodsStandards.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
					goodsStandards.setStatus(BasicFields.ENABLE);
					Integer maxSaleNum = (Integer) map.get(MallFields.MAX_SALE_NUM);
					maxSaleNum = maxSaleNum <= MallFields.MAX_SALE_NUM_VALUE ? maxSaleNum
							: MallFields.MAX_SALE_NUM_VALUE;
					goodsStandards.setMax_sale_num(maxSaleNum);
					goodsStandards.initForClearNull();
					goodsStandardsDao.save(goodsStandards);
				} else {
					goodsStandards.setStandard_id(goodsStandards.getStandard_id());
					goodsStandards.setStatus(BasicFields.ENABLE);
					Integer maxSaleNum = (Integer) map.get(MallFields.MAX_SALE_NUM);
					maxSaleNum = maxSaleNum <= MallFields.MAX_SALE_NUM_VALUE ? maxSaleNum
							: MallFields.MAX_SALE_NUM_VALUE;
					goodsStandards.setMax_sale_num(maxSaleNum);
					goodsStandards.initForClearNull();
					goodsStandardsDao.update(goodsStandards);
				}
				
				standardIds.append(goodsStandards.getStandard_id()).append(",");

				// 找到最大与最小值
				double currentPrice = goodsStandards.getPrice().doubleValue();
				if (first) {
					first = false;
					minPrice = currentPrice;
					maxPrice = currentPrice;
				}
				if (maxPrice < currentPrice) {
					maxPrice = currentPrice;
				}
				if (minPrice > currentPrice) {
					minPrice = currentPrice;
				}
			}
		}
		priceArea.append(String.valueOf(minPrice)).append("-").append(String.valueOf(maxPrice));
		// 删除没有使用的规格
		List<String> bathDeleteIds = new ArrayList<>();
		
		Goods goods = goodsDao.get(modifyGoodsVo.getGoods_id());
		String standardIdsDb = null;
		if (null != goods) {
			standardIdsDb = goods.getStandard_ids();
		}
		if (null != standardIdsDb) {
			String[] split = standardIdsDb.split(",");
			for (String standardId : split) {
				if (!(standardIds.indexOf(standardId) > -1)) {
					bathDeleteIds.add(standardId);
				}
			}
		}
		
		if(null != bathDeleteIds && bathDeleteIds.size()>0){
			GoodsStandards bathDelete = new GoodsStandards();
			bathDelete.setIds(bathDeleteIds);
			goodsStandardsDao.bathDelete(bathDelete);
		}

	}

	/**
	 * @date 2017年2月20日
	 * @Description: 修改详情
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param detailIds
	 * @param date
	 *            void
	 */
	private void modifyDetailList(ModifyGoodsVo modifyGoodsVo, StringBuilder detailIds, Date date) {
		List<Map<String, Object>> detailList = modifyGoodsVo.getDetailList();
		for (Map<String, Object> map : detailList) {
			GoodsPic goodsPic = JSONUtil.trans(map, GoodsPic.class);
			if (StringUtils.isBlank(goodsPic.getPic_id())) {
				goodsPic.setPic_id(addPrimaryId(MallFields.GOODS_PIC));

				// UploadPicture uploadPicture = new UploadPicture();
				// uploadPicture.setImagecontext(goodsPic.getPic_url());
				// uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
				// Map<String, Object> uploadResult =
				// uploadService.uploadPic(uploadPicture);
				// goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

				goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsPic.setStatus(BasicFields.ENABLE);
				goodsPic.initForClearNull();
				goodsPicDao.save(goodsPic);
			} else {
				// if (goodsPic.getPic_url().contains("data")) {

				// UploadPicture uploadPicture = new UploadPicture();
				// uploadPicture.setImagecontext(goodsPic.getPic_url());
				// uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
				// Map<String, Object> uploadResult =
				// uploadService.uploadPic(uploadPicture);
				// goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
				// }

				goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsPic.setStatus(BasicFields.ENABLE);
				goodsPic.initForClearNull();
				goodsPicDao.update(goodsPic);
			}
			detailIds.append(goodsPic.getPic_id()).append(",");
		}
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 修改banner
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param bannerIds
	 * @param date
	 *            void
	 */
	private void modifyBannerList(ModifyGoodsVo modifyGoodsVo, StringBuilder bannerIds, Date date) {
		List<Map<String, Object>> bannerList = modifyGoodsVo.getBannerList();
		for (Map<String, Object> map : bannerList) {
			GoodsPic goodsPic = JSONUtil.trans(map, GoodsPic.class);
			if (StringUtils.isBlank(goodsPic.getPic_id())) {
				goodsPic.setPic_id(addPrimaryId(MallFields.GOODS_PIC));

				// UploadPicture uploadPicture = new UploadPicture();
				// uploadPicture.setImagecontext(goodsPic.getPic_url());
				// uploadPicture.setUpload_type(UploadType.GOODS_BANNER.getVal());
				// Map<String, Object> uploadResult =
				// uploadService.uploadPic(uploadPicture);
				// goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

				goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsPic.setStatus(BasicFields.ENABLE);
				goodsPic.initForClearNull();
				goodsPicDao.save(goodsPic);
			}
			bannerIds.append(goodsPic.getPic_id()).append(",");
		}
	}

	/**
	 * @date 2017年2月20日
	 * @Description: 修改简介
	 * @author 杨益桦
	 * @param modifyGoodsVo
	 * @param infoIds
	 * @param date
	 *            void
	 */
	private void modifyInfoList(ModifyGoodsVo modifyGoodsVo, StringBuilder infoIds, Date date) {
		List<Map<String, Object>> infoList = modifyGoodsVo.getInfoList();
		for (Map<String, Object> map : infoList) {
			GoodsInfo goodsInfo = JSONUtil.trans(map, GoodsInfo.class);
			if (StringUtils.isBlank(goodsInfo.getInfo_id())) {
				goodsInfo.setInfo_id(addPrimaryId(MallFields.GOODS_INFO));
				goodsInfo.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsInfo.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsInfo.setStatus(BasicFields.ENABLE);
				goodsInfo.initForClearNull();
				goodsInfoDao.save(goodsInfo);
			} else {
				goodsInfoDao.update(goodsInfo);
			}
			infoIds.append(goodsInfo.getInfo_id()).append(",");
		}
	}

	private void updateGoods(Goods goods, StringBuilder standardIds, StringBuilder detailIds, StringBuilder bannerIds,
			StringBuilder infoIds, Date date, String priceArea) {
		goods.setDetail_ids(detailIds.toString());
		goods.setInfo_ids(infoIds.toString());
		goods.setBanner_ids(bannerIds.toString());
		goods.setStandard_ids(standardIds.toString());
		goods.setStatus(BasicFields.ENABLE);
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setPrice_area(priceArea);
		update(goods);
	}

	@Override
	public Map<String, Object> undercarriageGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		Goods goods = new Goods();
		String goods_id = undercarriageGoodsVo.getGoods_id();
		goods.setGoods_id(goods_id);
		goods.setGoods_status(BasicFields.DISABLE);
		Date date = new Date();
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setUpdate_user(undercarriageGoodsVo.getHjy_operator_id());
		goods.setUpdate_user_name(undercarriageGoodsVo.getHjy_operator_name());
		update(goods);
		// 删除商品关联的活动 start
		activityGoodDao.batchDelBygoodId(Arrays.asList(goods_id.split(",")));
		// 删除商品关联的活动 end
		try {
			searchService.delete(goods.getGoods_id());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> queryJsonGoodsList(QueryJsonGoodsListVo queryJsonGoodsListVo) {
		Goods goods = new Goods();
		goods.setStatus(BasicFields.ENABLE);
		goods.setGoods_id(queryJsonGoodsListVo.getStart_id());
		goods.setGoods_status(BasicFields.ENABLE);
		goods.setCategory_id(queryJsonGoodsListVo.getCategory_id());

		// app 接口添加排序
		addSortMarker(goods, queryJsonGoodsListVo.getPage_size());

		List<Map<String, Object>> goodsList = goodsDao.queryJson(goods);

		List<Map<String, Object>> resultList = new ArrayList<>();
		// 给每个商品遍历添加类目名称
		ergodicGoods4Json(goodsList, resultList);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, goodsList);
		return result;
	}

	/**
	 * @date 2017年2月22日
	 * @Description: app 接口添加排序
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	private void addSortMarker(Goods goods, int pageSize) {
		SortMarker sortMarker = new SortMarker();
		sortMarker.setAsc(false);
		sortMarker.setField(MallFields.GOODS_ID);
		List<SortMarker> sortMarkers = new ArrayList<>();
		sortMarkers.add(sortMarker);
		goods.setSortMarkers(sortMarkers);
		Pagination pagination = new Pagination();
		pagination.setPage_size(pageSize);
		goods.setPage(pagination);
	}

	private void ergodicGoods4Json(List<Map<String, Object>> goodsList, List<Map<String, Object>> resultList) {
		for (Map<String, Object> singleGoods : goodsList) {
			String bannerIds = singleGoods.get(MallFields.BANNER_IDS).toString();
			String[] bannerIdArray = bannerIds.split(",");
			String picUrl = "";
			if (bannerIdArray != null && bannerIdArray.length > 0) {
				GoodsPic goodsPic = goodsPicDao.get(bannerIdArray[0]);
				if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
					picUrl = goodsPic.getPic_url();
				}
			}
			singleGoods.put(MallFields.FIRST_PIC, picUrl);
			singleGoods.remove(MallFields.BANNER_IDS);

			resultList.add(singleGoods);
		}
	}

	@Override
	public Map<String, Object> getJsonGoods(GetJsonGoodsVo getJsonGoodsVo) {
		Goods goods = goodsDao.get(getJsonGoodsVo.getGoods_id());
		String[] bannerArray = goods.getBanner_ids().split(",");
		List<Map<String, Object>> bannerList = new ArrayList<>();
		for (String id : bannerArray) {
			Map<String, Object> map = new HashMap<>();
			String picUrl = "";
			GoodsPic goodsPic = goodsPicDao.get(id);
			if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
				picUrl = goodsPic.getPic_url();
			}
			map.put(MallFields.BANNER_URL, picUrl);
			bannerList.add(map);
		}

		String[] detailArray = goods.getDetail_ids().split(",");
		List<Map<String, Object>> detailList = new ArrayList<>();
		for (String id : detailArray) {
			Map<String, Object> map = new HashMap<>();
			String picUrl = "";
			String picDesc = "";
			GoodsPic goodsPic = goodsPicDao.get(id);
			if (goodsPic != null && !StringUtils.isBlank(goodsPic.getPic_url())) {
				picUrl = goodsPic.getPic_url();
				picDesc = goodsPic.getPic_desc();
			}
			map.put(MallFields.DETAIL_URL, picUrl);
			map.put(MallFields.PIC_DESC, picDesc);
			detailList.add(map);
		}

		String[] infoArray = goods.getInfo_ids().split(",");
		List<Map<String, Object>> infoList = new ArrayList<>();
		Map<String, Object> brandMap = new HashMap<>();
		brandMap.put(MallFields.INFO_TITLE, MallFields.BRAND_NAME_CN);
		brandMap.put(MallFields.INFO_CONTENT, goods.getBrand_name());
		infoList.add(brandMap);
		for (String id : infoArray) {
			Map<String, Object> map = new HashMap<>();
			String infoContent = "";
			String infoTitle = "";
			GoodsInfo goodsInfo = goodsInfoDao.get(id);
			if (goodsInfo != null && !StringUtils.isBlank(goodsInfo.getInfo_title())) {
				infoTitle = goodsInfo.getInfo_title();
				infoContent = goodsInfo.getInfo_content();
			}
			map.put(MallFields.INFO_TITLE, infoTitle);
			map.put(MallFields.INFO_CONTENT, infoContent);
			infoList.add(map);
		}

		String[] standardArray = goods.getStandard_ids().split(",");
		List<Map<String, Object>> standardList = new ArrayList<>();
		boolean standardNotExist = false;
		for (String id : standardArray) {
			Map<String, Object> map = new HashMap<>();
			String standardMust = "";
			String optionalFirst = "";
			String optionalSecond = "";
			String price = "";
			String referencePrice = "0.00";
			// double saleNum = 0;
			double storeNum = 0;
			Integer maxSaleNum =null;
			GoodsStandards goodsStandards = goodsStandardsDao.get(id);
			if (goodsStandards != null && !StringUtils.isBlank(goodsStandards.getStandard_must())) {
				standardMust = goodsStandards.getStandard_must();
				optionalFirst = goodsStandards.getOptional_first();
				optionalSecond = goodsStandards.getOptional_second();
				optionalSecond = goodsStandards.getOptional_second();
				// saleNum = goodsStandards.getSale_num();
				storeNum = goodsStandards.getStore_num();
				price = goodsStandards.getPrice().setScale(2).toString();
				referencePrice = goodsStandards.getReference_price().setScale(2).toString();
				maxSaleNum = goodsStandards.getMax_sale_num();
			}
			map.put(MallFields.STANDARD_MUST, standardMust);
			map.put(MallFields.STANDARD_ID, id);
			map.put(MallFields.STORE_NUM, storeNum);
			// map.put(MallFields.SALE_NUM, saleNum);
			map.put(MallFields.OPTIONAL_FIRST, optionalFirst);
			map.put(MallFields.OPTIONAL_SECOND, optionalSecond);
			map.put(MallFields.PRICE, price);
			// 排除参考价为0的
			if(!"0.00".equals(referencePrice)){
				map.put(MallFields.REFERENCE_PRICE, referencePrice);
			}
			map.put(MallFields.UNIT_NAME, goods.getUnit_name());
			
			map.put(MallFields.MAX_SALE_NUM, maxSaleNum);
			if (StringUtils.isBlank(standardMust)) {
				standardNotExist = true;
			}
			standardList.add(map);
		}

		Map<String, Object> goodsMap = new HashMap<>();
		// goodsMap = JSONUtil.trans(goods, Map.class);
		goodsMap.put(MallFields.GOODS_NAME, goods.getGoods_name());
		goodsMap.put(MallFields.GOODS_STATUS, goods.getGoods_status());
		goodsMap.put(MallFields.SHOW_URL, goods.getShow_url());
		goodsMap.put(MallFields.ADAPT_ALL_MODELS, goods.getAdapt_all_models());
		goodsMap.put(MallFields.AD_URL, goods.getAd_url());
		goodsMap.put(MallFields.FIRST_CATEGORY_NAME, goods.getFirst_category_name());
		goodsMap.put(MallFields.FIRST_CATEGORY_ID, goods.getFirst_category_id());
		goodsMap.put(MallFields.SECOND_CATEGORY_NAME, goods.getSecond_category_name());
		goodsMap.put(MallFields.SECOND_CATEGORY_ID, goods.getSecond_category_id());
		goodsMap.put(MallFields.THIRD_CATEGORY_ID, goods.getThird_category_id());
		goodsMap.put(MallFields.THIRD_CATEGORY_NAME, goods.getThird_category_name());
		goodsMap.put(MallFields.BRAND_NAME, goods.getBrand_name());
		goodsMap.put(MallFields.BRAND_ID, goods.getBrand_id());
		goodsMap.put(MallFields.FONT_MONEY_RATE, goods.getFont_money_rate());
		goodsMap.put(MallFields.PAY_TYPE, goods.getPay_type());
		if (standardNotExist) {
			goodsMap.put(MallFields.STANDARD_LIST, new ArrayList<>());
		} else {
			goodsMap.put(MallFields.STANDARD_LIST, standardList);
		}

		goodsMap.put(MallFields.INFO_LIST, infoList);
		goodsMap.put(MallFields.DETAIL_LIST, detailList);
		goodsMap.put(MallFields.BANNER_LIST, bannerList);
		goodsMap.put(MallFields.UNIT_NAME, goods.getUnit_name());
		goodsMap.put(MallFields.GOODS_ID, goods.getGoods_id());
		goodsMap.put(MallFields.TEL, goods.getTel());

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.putAll(goodsMap);
		return result;
	}

	@Override
	public Map<String, Object> groundingGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		Goods goods = new Goods();
		goods.setGoods_id(undercarriageGoodsVo.getGoods_id());
		goods.setGoods_status(BasicFields.ENABLE);
		Date date = new Date();
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setUpdate_user(undercarriageGoodsVo.getHjy_operator_id());
		goods.setUpdate_user_name(undercarriageGoodsVo.getHjy_operator_name());
		update(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> delGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		Goods goods = new Goods();
		goods.setGoods_id(undercarriageGoodsVo.getGoods_id());
		goods.setStatus(BasicFields.DISABLE);
		Date date = new Date();
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setUpdate_user(undercarriageGoodsVo.getHjy_operator_id());
		goods.setUpdate_user_name(undercarriageGoodsVo.getHjy_operator_name());
		update(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryPageLimit(QueryPageLimitVo queryPageLimitVo) {

		Goods goods = new Goods();
		// 将queryGoodsVo转为goods
		transGoods(queryPageLimitVo, goods);

		goods.setStatus(BasicFields.ENABLE);
		goods.setGoods_status(BasicFields.ENABLE);

		Map<String, Object> goodsMap = JSONUtil.trans(goods, Map.class);

		int total = goodsDao.countQueryGoods(goodsMap);
		// 添加分页和排序参数
		addSrotAndPageParam(queryPageLimitVo, goods, total);
		goodsMap = JSONUtil.trans(goods, Map.class);
		List<Map<String, Object>> goodsList = goodsDao.queryPageLimit(goodsMap);

		List<Map<String, Object>> resultList = new ArrayList<>();
		// 给每个商品遍历添加类目名称
		ergodicGoods4Json(goodsList, resultList);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		result.put(BasicFields.TOTAL_NUM, total);
		return result;
	}

	private void transGoods(QueryPageLimitVo queryPageLimitVo, Goods goods) {
		try {
			BeanUtil.reflectionAttr(queryPageLimitVo, goods);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addSrotAndPageParam(QueryPageLimitVo queryPageLimitVo, Goods goods, int total) {
		Pagination pagination = createPage(total, queryPageLimitVo.getPage(), queryPageLimitVo.getLimit());
		goods.setPage(pagination);

		SortMarker sortMarker = createMarker(false, MallFields.GOODS_ID);
		SortMarker sort = createMarker(true, MallFields.SORT);
		List<SortMarker> list = new ArrayList<>();
		list.add(sort);
		list.add(sortMarker);
		goods.setSortMarkers(list);
	}

	@Override
	public Map<String, Object> chmodBath(ChmodBathVo chmodBathVo) {
		Goods goods = new Goods();
		List<String> ids = chmodBathVo.getIds();
		goods.setIds(ids);
		goods.setGoods_status(chmodBathVo.getGoods_status());
		Date date = new Date();
		goods.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		goods.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		goods.setUpdate_user(chmodBathVo.getHjy_operator_id());
		goods.setUpdate_user_name(chmodBathVo.getHjy_operator_name());
		goodsDao.chmodBatch(goods);
		// 删除商品关联的活动 start
		if (ids.size() > 0 && Status.DISENABLED.getVal().equals(chmodBathVo.getGoods_status())) {
			activityGoodDao.batchDelBygoodId(ids);
		}
		// 删除商品关联的活动 end
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> updateSolr(UpdateSolrVo updateSolrVo) {
		Map<String, Object> goods = goodsDao.getSolrEntity(updateSolrVo.getGoods_id());
		GoodsSorl goodsSorl = JSONUtil.trans(goods, GoodsSorl.class);
		goodsSorl.setId(updateSolrVo.getGoods_id());
		goodsSorl.setCar_brand_name(updateSolrVo.getBrand_names());
		goodsSorl.setUpdate_date(
				goods.get(MallFields.UPDATE_DATE).toString() + goods.get(MallFields.UPDATE_TIME).toString());
		goodsSorl.setCar_models_name(updateSolrVo.getCar_models_names());
		goodsSorl.setCar_type(updateSolrVo.getCar_types());
		// goodsSorl.setAdapt_all_models(updateSolrVo.getAdapt_all_models());
		try {
			searchService.save(goodsSorl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public void delSolr(UpdateSolrVo updateSolrVo) {
		try {
			searchService.delete(updateSolrVo.getGoods_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 设置factes
	 * @author 杨益桦
	 * @param solrQuery
	 * @param fileds
	 *            void
	 */
	private void setSolrFacets(SolrQuery solrQuery, String... fileds) {
		solrQuery.setFacet(true);
		solrQuery.addFacetField(fileds);
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 创建solrquery
	 * @author 杨益桦
	 * @param goodsSorl
	 * @param solrQuery
	 * @return SolrQuery
	 */
	private SolrQuery createSolrQuery(GoodsSorl goodsSorl, SolrQuery solrQuery) {
		try {
			solrQuery = searchService.creatAllFiledSolrQuery(goodsSorl, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return solrQuery;
	}

	/**
	 * @date 2017年3月18日
	 * @Description: 查询搜索引擎
	 * @author 杨益桦
	 * @param result
	 * @param solrQuery
	 * @param limit
	 * @param page
	 * @param solrSort
	 *            void
	 */
	private Map<String, Object> querySolr(SolrQuery solrQuery, PageQueryVO pageQueryVO, String solrSort) {
		Map<String, Object> result = VOUtil.genSuccessResult();
		Integer limit = pageQueryVO.getLimit();
		Integer page = pageQueryVO.getPage();
		page = page < 1 ? 1 : page;
		if (!StringUtils.isBlank(solrSort)) {
			String field = solrSort.split(",")[0];
			String order = solrSort.split(",")[1];
			solrQuery.setSort(field, ORDER.valueOf(order));
		}
		solrQuery.setRows(limit);
		solrQuery.setStart(limit * (page - 1));
		try {
			QueryResponse solrResponse = searchService.querybyQueryString(solrQuery);
			SolrDocumentList reslList = solrResponse.getResults();
			List<Map<String, Object>> resultList = (List<Map<String, Object>>) JSONObject.toJSON(reslList);
			for (Map<String, Object> map : resultList) {
				map.remove("update_date");
			}
			result.put(BasicFields.RESULT_LIST, resultList);
			List<FacetField> list = solrResponse.getFacetFields();
			List<Map<String, Object>> facetList = new ArrayList<>();
			for (FacetField facetField : list) {
				Map<String, Object> map = new HashMap<String, Object>();

				map.put(MallFields.FACET_FIELD, facetField.getName());
				List<Map<String, Object>> countList = new ArrayList<>();
				for (Count count : facetField.getValues()) {
					Map<String, Object> countMap = new HashMap<>();
					countMap.put(MallFields.NAME, count.getName());
					countMap.put(MallFields.COUNT, count.getCount());
					countList.add(countMap);
				}
				map.put(MallFields.FACET_FIELD_LIST, countList);
				facetList.add(map);
			}
			result.put(MallFields.FACET_FIELDS_LIST, facetList);
			result.put(BasicFields.PAGE_SIZE, limit);
			result.put(BasicFields.PAGE, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> querySolrByAll(QuerySolrVo querySolrVo) {
		GoodsSorl goodsSorl = JSONUtil.trans(querySolrVo, GoodsSorl.class);
		if (!StringUtils.isBlank(goodsSorl.getCar_models_name())) {
			goodsSorl.setCar_models_name(goodsSorl.getCar_models_name().replace(" ", ""));
			// 转义设置，解决机型查询出现的时候英文括号查询不到的问题 p.s. 目前的机制是避免出现英文括号，一下代码留着，做记录
			// goodsSorl.setCar_models_name(ClientUtils.escapeQueryChars(goodsSorl.getCar_models_name()));
		}
		SolrQuery solrQuery = null;
		// 创建solrquery
		solrQuery = createSolrQuery(goodsSorl, solrQuery);

		// 当设置成查询机型的时候，选择全部机型
		String query = solrQuery.getQuery();
		if (!StringUtils.isBlank(goodsSorl.getCar_models_name())) {
			query = query.replace("car_models_name", "(car_models_name");
			query += " OR adapt_all_models:1)";
			solrQuery.setQuery(query);
		}

		// 设置factes
		String[] facets = EnumUtil.valOf(querySolrVo.getSolrFactesFields(), SolrFactesFields.class).getDescription()
				.split(",");
		if (facets != null && facets.length > 0) {
			setSolrFacets(solrQuery, facets);
		}

		String solrSort = EnumUtil.valOf(querySolrVo.getSolrSortField(), SolrSortFields.class).getDescription();
		// 查询搜索引擎
		Map<String, Object> result = querySolr(solrQuery, querySolrVo, solrSort);

		return result;
	}

	@Override
	public Map<String, Object> bathUpdateSolr(List<Map<String, Object>> list) throws HJHBCSErrInfoException {
		List<GoodsSorl> goodsSorls = new ArrayList<>();
		for (Map<String, Object> map : list) {
			GoodsSorl goodsSorl = new GoodsSorl();
			goodsSorl = JSONUtil.trans(map, GoodsSorl.class);
			StringBuilder sb = new StringBuilder();
			sb.append(map.get(MallFields.UPDATE_DATE)).append(map.get(MallFields.UPDATE_TIME));
			goodsSorl.setUpdate_date(sb.toString());
			goodsSorls.add(goodsSorl);
		}
		try {
			searchService.batch(goodsSorls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> queryAllId(String startDate, String endDate) {
		Map<String, Object> map = new HashMap<>();
		map.put(MallFields.GOODS_STATUS, BasicFields.ENABLE);
		map.put(MallFields.STATUS, BasicFields.ENABLE);

		map.put(MallFields.START_DATE, startDate);
		map.put(MallFields.END_DATE, endDate);

		List<Map<String, Object>> allId = goodsDao.queryAllId(map);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, allId);
		return result;
	}

	@Override
	public void updateGoodsTimer(GoodsTimerVo goodsTimerVo) {
		Goods goods = JSONUtil.trans(goodsTimerVo, Goods.class);

		goodsDao.updateGoodsTimer(goods);
	}

	@Override
	public Map<String, Object> queryIdList(GoodsTimerVo goodsTimerVo) {
		Goods goods = JSONUtil.trans(goodsTimerVo, Goods.class);
		List<String> resultList = goodsDao.queryIdList(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@Override
	public Map<String, Object> querySolrEntityByids(List<String> ids) {
		Goods goods = new Goods();
		if (null == ids || ids.size() == 0) {// 可能传入的[]
			ids = null;
		}
		goods.setIds(ids);
		goods.setGoods_status(BasicFields.ENABLE);
		goods.setStatus(BasicFields.ENABLE);
		List<Map<String, Object>> resultList = goodsDao.querySolrEntityByids(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
		return result;
	}

	@Override
	public Map<String, Object> getProduct4Order(String productId, String standardId) {
		Goods goods = goodsDao.get(productId);
		// 1、判断商品是否存在 2、判断商品是否删除 3、判断商品是否上架 4、判断规格是否存在
		String errorCode = checkProduct(goods, goods.getStandard_ids());
		if (BasicFields.ENABLE != errorCode) {
			return VOUtil.genErrorResult(errorCode);
		}

		GoodsStandards goodsStandards = goodsStandardsDao.get(standardId);
		// 1、判断规格存在与否
		errorCode = checkStandard(goodsStandards);
		if (BasicFields.ENABLE != errorCode) {
			return VOUtil.genErrorResult(errorCode);
		}

		Product4Order product4Order = new Product4Order();
		product4Order.setBrandId(goods.getBrand_id());
		product4Order.setBrandName(goods.getBrand_name());
		product4Order.setCategoryId(goods.getThird_category_id());
		product4Order.setFontMoneyRate(goods.getFont_money_rate());
		product4Order.setProductId(goods.getGoods_id());
		product4Order.setProductName(goods.getGoods_name());
		product4Order.setShowUrl(goods.getShow_url());
		product4Order.setMaxSaleNum(goodsStandards.getMax_sale_num());
		product4Order.setOptional_first(goodsStandards.getOptional_first());
		product4Order.setOptional_second(goodsStandards.getOptional_second());
		product4Order.setPrice(goodsStandards.getPrice());
		product4Order.setStandard_must(goodsStandards.getStandard_must());
		product4Order.setStoreNum((int) goodsStandards.getStore_num());
		product4Order.setStandardId(standardId);
		product4Order.setPayType(goods.getPay_type());
		product4Order.setUnitName(goods.getUnit_name());
		product4Order.setCategoryName(goods.getThird_category_name());

		Map<String, Object> result = VOUtil.genSuccessExtResult();
		result.put(MallFields.PRODUCT4ORDER, product4Order);

		return result;
	}

	/**
	 * @date 2017年5月16日
	 * @Description: 1、判断规格存在与否
	 * @author 杨益桦
	 * @param goodsStandards
	 *            void
	 */
	private String checkStandard(GoodsStandards goodsStandards) {
		String errorCode = BasicFields.ENABLE;
		if (null == goodsStandards) {
			errorCode = MallErrorCode.GoodsErrorCode.STANDARD_NOT_EXIST;
		}
		return errorCode;

	}

	/**
	 * @date 2017年5月16日
	 * @Description: 1、判断商品是否存在 2、判断商品是否删除 3、判断商品是否上架 4、判断规格是否存在
	 * @author 杨益桦
	 * @param goods
	 *            void
	 */
	private String checkProduct(Goods goods, String standardId) {
		String errorCode = BasicFields.ENABLE;
		if (goods == null) {
			errorCode = MallErrorCode.GoodsErrorCode.GOODS_NOT_EXIST;
			return errorCode;
		}

		if (BasicFields.DISABLE.equals(goods.getStatus())) {
			errorCode = MallErrorCode.GoodsErrorCode.GOODS_LOGIC_DELETE;
			return errorCode;
		}

		if (BasicFields.DISABLE.equals(goods.getGoods_status())) {
			errorCode = MallErrorCode.GoodsErrorCode.GOODS_HAS_UNDER;
			return errorCode;
		}

		if (goods.getStandard_ids().indexOf(standardId) <= -1) {
			errorCode = MallErrorCode.GoodsErrorCode.GOODS_NO_THIS_STANDARD;
			return errorCode;
		}
		return errorCode;
	}
}
