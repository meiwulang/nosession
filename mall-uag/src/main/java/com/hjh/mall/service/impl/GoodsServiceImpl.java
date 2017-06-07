package com.hjh.mall.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodscar.BizGoodsCarModelService;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.filed.UploadType;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.dao.CategoryDao;
import com.hjh.mall.dao.GoodsDao;
import com.hjh.mall.dao.GoodsInfoDao;
import com.hjh.mall.dao.GoodsPicDao;
import com.hjh.mall.dao.GoodsStandardsDao;
import com.hjh.mall.dao.OperatorDao;
import com.hjh.mall.entity.Category;
import com.hjh.mall.entity.Goods;
import com.hjh.mall.entity.GoodsInfo;
import com.hjh.mall.entity.GoodsPic;
import com.hjh.mall.entity.GoodsStandards;
import com.hjh.mall.entity.Operator;
import com.hjh.mall.field.MallFields;
import com.hjh.mall.goods.bizapi.bizserver.BizGoodsService;
import com.hjh.mall.goods.bizapi.bizserver.vo.updateTimer.GoodsTimerVo;
import com.hjh.mall.service.GoodsService;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.vo.client.UploadPicture;
import com.hjh.mall.vo.goods.AddGoodsVo;
import com.hjh.mall.vo.goods.GetJsonGoodsVo;
import com.hjh.mall.vo.goods.ModifyGoodsVo;
import com.hjh.mall.vo.goods.QueryGoodsVo;
import com.hjh.mall.vo.goods.QueryJsonGoodsListVo;
import com.hjh.mall.vo.goods.QueryPageLimitVo;
import com.hjh.mall.vo.goods.UndercarriageGoodsVo;

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
	@Reference(version = "1.0.0")
	private BizGoodsService bizGoodsService;
	@Reference(version = "1.0.0")
	private BizGoodsCarModelService bizGoodsCarModelService;

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

		int total = goodsDao.countQueryGoods(goodsMap);
		// 添加分页和排序参数
		addSrotAndPageParam(queryGoodsVo, goods, total);
		goodsMap = JSONUtil.trans(goods, Map.class);
		goodsMap.put(MallFields.START_DATE, queryGoodsVo.getStart_date());
		goodsMap.put(MallFields.END_DATE, queryGoodsVo.getEnd_date());
		List<Goods> goodsList = goodsDao.queryGoods(goodsMap);

		List<Map<String, Object>> resultList = new ArrayList<>();
		// 给每个商品遍历添加类目名称
		ergodicGoods(goodsList, resultList);

		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, resultList);
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
	private void ergodicGoods(List<Goods> goodsList, List<Map<String, Object>> resultList) {
		for (Goods singleGoods : goodsList) {
			Map<String, Object> map = JSONUtil.trans(singleGoods, Map.class);
			Category category = categoryDao.get(singleGoods.getCategory_id());
			map.put(MallFields.CATEGORY_NAME, category == null ? " " : category.getCategory_name());

			GoodsStandards goodsStandards = new GoodsStandards();
			goodsStandards.setIds(Arrays.asList(singleGoods.getStandard_ids().split(",")));
			goodsStandards.setStatus(BasicFields.ENABLE);
			List<Map<String, Object>> standardList = goodsStandardsDao.queryByIds(goodsStandards);
			map.put("standardList", standardList);

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

			Operator operator = operatorDao.get(singleGoods.getUpdate_user());

			map.put(MallFields.OPERATOR_NAME, operator == null ? "" : operator.getOperator_name());
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

		SortMarker sortMarker = createMarker(false, MallFields.GOODS_ID);
		List<SortMarker> list = new ArrayList<>();
		list.add(sortMarker);
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

		if (!StringUtils.isBlank(addGoodsVo.getAd_url())) {

			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(addGoodsVo.getAd_url());
			uploadPicture.setUpload_type(UploadType.GOODS_AD.getVal());
			Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
			goods.setAd_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
		}

		goods.setGoods_status(BasicFields.ENABLE);

		// 批量保存规格
		StringBuilder priceArea = new StringBuilder();
		StringBuilder standardIds = new StringBuilder();
		saveStandardList(addGoodsVo, standardIds, date, priceArea);

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
		return result;
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

			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(goodsPic.getPic_url());
			uploadPicture.setUpload_type(UploadType.GOODS_BANNER.getVal());
			Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
			goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

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

			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(goodsPic.getPic_url());
			uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
			Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
			goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

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
	private void saveStandardList(AddGoodsVo addGoodsVo, StringBuilder standardIds, Date date, StringBuilder priceArea) {
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
				goodsStandards.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsStandards.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsStandards.initForClearNull();
				goodsStandardsDao.save(goodsStandards);
				standardIds.append(goodsStandards.getStandard_id()).append(",");

				// 找到最大与最小值
				double currentPrice = Double.parseDouble(goodsStandards.getPrice());
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

		if (!StringUtils.isBlank(modifyGoodsVo.getAd_url()) && modifyGoodsVo.getAd_url().contains("data")) {
			UploadPicture uploadPicture = new UploadPicture();
			uploadPicture.setImagecontext(modifyGoodsVo.getAd_url());
			uploadPicture.setUpload_type(UploadType.GOODS_AD.getVal());
			Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
			goods.setAd_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
		}

		// 批量保存规格
		StringBuilder priceArea = new StringBuilder();
		StringBuilder standardIds = new StringBuilder();
		modifyStandardList(modifyGoodsVo, standardIds, date, priceArea);

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
					goodsStandards.initForClearNull();
					goodsStandardsDao.save(goodsStandards);
				} else {
					goodsStandards.setStandard_id(goodsStandards.getStandard_id());
					goodsStandards.setStatus(BasicFields.ENABLE);
					goodsStandards.initForClearNull();
					goodsStandardsDao.update(goodsStandards);
				}
				standardIds.append(goodsStandards.getStandard_id()).append(",");

				// 找到最大与最小值
				double currentPrice = Double.parseDouble(goodsStandards.getPrice());
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

				UploadPicture uploadPicture = new UploadPicture();
				uploadPicture.setImagecontext(goodsPic.getPic_url());
				uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
				Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
				goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

				goodsPic.setInit_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
				goodsPic.setInit_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
				goodsPic.setStatus(BasicFields.ENABLE);
				goodsPic.initForClearNull();
				goodsPicDao.save(goodsPic);
			} else {
				if (goodsPic.getPic_url().contains("data")) {

					UploadPicture uploadPicture = new UploadPicture();
					uploadPicture.setImagecontext(goodsPic.getPic_url());
					uploadPicture.setUpload_type(UploadType.GOODS_DETAIL.getVal());
					Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
					goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());
				}

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

				UploadPicture uploadPicture = new UploadPicture();
				uploadPicture.setImagecontext(goodsPic.getPic_url());
				uploadPicture.setUpload_type(UploadType.GOODS_BANNER.getVal());
				Map<String, Object> uploadResult = uploadService.uploadPic(uploadPicture);
				goodsPic.setPic_url(uploadResult.get(BasicFields.IMAGE_KEY).toString());

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
		goods.setGoods_id(undercarriageGoodsVo.getGoods_id());
		goods.setGoods_status(BasicFields.DISABLE);
		update(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	@Deprecated
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
		Map<String, Object> brandMap = new HashMap<>();
		List<Map<String, Object>> infoList = new ArrayList<>();
		brandMap.put(MallFields.INFO_TITLE, MallFields.BRAND);
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
			GoodsStandards goodsStandards = goodsStandardsDao.get(id);
			if (goodsStandards != null && !StringUtils.isBlank(goodsStandards.getStandard_must())) {
				standardMust = goodsStandards.getStandard_must();
				optionalFirst = goodsStandards.getOptional_first();
				optionalSecond = goodsStandards.getOptional_second();
				optionalSecond = goodsStandards.getOptional_second();
				price = goodsStandards.getPrice();
			}
			map.put(MallFields.STANDARD_MUST, standardMust);
			map.put(MallFields.STANDARD_ID, id);
			map.put(MallFields.OPTIONAL_FIRST, optionalFirst);
			map.put(MallFields.OPTIONAL_SECOND, optionalSecond);
			map.put(MallFields.PRICE, price);
			if (StringUtils.isBlank(standardMust)) {
				standardNotExist = true;
			}
			standardList.add(map);
		}

		Map<String, Object> goodsMap = new HashMap<>();
		goodsMap.put(MallFields.GOODS_NAME, goods.getGoods_name());
		goodsMap.put(MallFields.AD_URL, goods.getAd_url());
		if (standardNotExist) {
			goodsMap.put(MallFields.STANDARD_LIST, new ArrayList<>());
		} else {
			goodsMap.put(MallFields.STANDARD_LIST, standardList);
		}
		goodsMap.put(MallFields.INFO_LIST, infoList);
		goodsMap.put(MallFields.DETAIL_LIST, detailList);
		goodsMap.put(MallFields.BANNER_LIST, bannerList);
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
		update(goods);
		Map<String, Object> result = VOUtil.genSuccessResult();
		return result;
	}

	@Override
	public Map<String, Object> delGoods(UndercarriageGoodsVo undercarriageGoodsVo) {
		Goods goods = new Goods();
		goods.setGoods_id(undercarriageGoodsVo.getGoods_id());
		goods.setStatus(BasicFields.DISABLE);
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

	/**
	 * @date 2017年4月7日
	 * @Description: 更新商品solr信息
	 * @author：王斌
	 * @param updateModelsVo
	 *            void
	 */
	public void updateGood(GoodsTimerVo goodsTimerVo) {
		// 更新数据库
		bizGoodsService.updateGoodsTimer(goodsTimerVo);

		// 需要更新的商品id列表
		Map<String, Object> updateIdResult = bizGoodsService.queryIdList(goodsTimerVo);
		List<String> updateIdList = (List<String>) updateIdResult.get(BasicFields.RESULT_LIST);

		// 找到需要更新的solr实体
		Map<String, Object> allIdMap = bizGoodsService.querySolrEntityByids(updateIdList);
		List<Map<String, Object>> list = (List<Map<String, Object>>) allIdMap.get(BasicFields.RESULT_LIST);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (map.get(com.hjh.mall.field.constant.MallFields.ADAPT_ALL_MODELS).toString()
						.equals(BasicFields.DISABLE)) {
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

		// 更新solr
		Map<String, Object> result = bizGoodsService.bathUpdateSolr(list);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateGroundSolr() {
		// 找到需要更新的solr实体
		Map<String, Object> allIdMap = bizGoodsService.querySolrEntityByids(null);
		List<Map<String, Object>> list = (List<Map<String, Object>>) allIdMap.get(BasicFields.RESULT_LIST);
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				if (map.get(com.hjh.mall.field.constant.MallFields.ADAPT_ALL_MODELS).toString()
						.equals(BasicFields.DISABLE)) {
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

		// 更新solr
		Map<String, Object> result = bizGoodsService.bathUpdateSolr(list);
	}
}
