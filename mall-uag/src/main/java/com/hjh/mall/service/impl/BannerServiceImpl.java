package com.hjh.mall.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.dao.base.DAOBase;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.model.Pagination;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.common.core.vo.HJYVO;
import com.hjh.mall.dao.BannerDao;
import com.hjh.mall.entity.Banner;
import com.hjh.mall.field.MallErrorCode;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.service.BannerService;
import com.hjh.mall.service.base.HJYServiceImplBase;
import com.hjh.mall.vo.banner.Addbanner;
import com.hjh.mall.vo.banner.GetBannerListVo;
import com.hjh.mall.vo.banner.UpdateBanner;
import com.hjh.mall.vo.banner.UpdateBannerStatus;

/**
 * @Project: hjh-blog-middle
 * @Description TODO
 * @author 曾繁林
 * @date 2016年12月14日
 * @version V1.0
 */
@Service
public class BannerServiceImpl extends HJYServiceImplBase<Banner, String> implements BannerService {

	@Resource
	private BannerDao bannerDao;

	@Resource
	private KeyGenerate keyGenerate;

	@Override
	public Map<String, Object> addBanner(Addbanner addbanner) {
		if ("1".equals(addbanner.getType())) {// 占位图
			Banner banner1 = new Banner();
			banner1.setType(addbanner.getType());
			banner1.setStatus(Status.ENABLED.getVal());
			List<Banner> list = bannerDao.query(banner1);
			if (list != null && list.size() > 0) {
				throw new HJHBCSErrInfoException(MallErrorCode.BannerErrorCode.BITMAP_EXIST);
			}
		}
		if ("0".equals(addbanner.getType())) {// 轮播图
			if (addbanner.getSort() != null && !addbanner.getSort().equals(0)) {
				Banner banner1 = new Banner();
				banner1.setSort(addbanner.getSort());
				banner1.setType(addbanner.getType());
				List<Banner> list = bannerDao.query(banner1);
				if (list != null && list.size() > 0) {
					throw new HJHBCSErrInfoException(MallErrorCode.BannerErrorCode.BANNER_SORT_EXIST);
				}
			}
		}
		Banner banner = JSONUtil.trans(addbanner, Banner.class);
		banner.setStatus(Status.ENABLED.getVal());
		String banner_id = addPrimaryId("banner");
		banner.setBanner_id(banner_id);
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		banner.setInit_date(date);
		banner.setInit_time(time);
		banner.setUpdate_date(date);
		banner.setUpdate_time(time);
		banner.initForClearNull();
		bannerDao.save(banner);
		return VOUtil.genSuccessResult();
	}

	// 获取主键ID
	private String addPrimaryId(String tableName) {
		String activityId = keyGenerate.getKeyGenerate(tableName);
		return activityId;
	}

	@Override
	public Map<String, Object> updateBanner(UpdateBanner updateBanner) {
		if ("1".equals(updateBanner.getType()) && Status.ENABLED.getVal().equals(updateBanner.getStatus())) {// 占位图并且启用
			Banner banner1 = new Banner();
			banner1.setType(updateBanner.getType());
			banner1.setStatus(Status.ENABLED.getVal());
			List<Banner> list = bannerDao.query(banner1);
			if (list != null && list.size() > 0) {
				for (Banner b : list) {
					if (!b.getBanner_id().equals(updateBanner.getBanner_id())) {
						throw new HJHBCSErrInfoException(MallErrorCode.BannerErrorCode.BITMAP_EXIST);
					}
				}
			}
		}
		if ("0".equals(updateBanner.getType())) {// 轮播图
			if (updateBanner.getSort() != null && !updateBanner.getSort().equals(0)) {
				Banner banner1 = new Banner();
				banner1.setSort(updateBanner.getSort());
				banner1.setType(updateBanner.getType());
				List<Banner> list = bannerDao.query(banner1);
				if (list != null && list.size() > 0) {
					for (Banner b : list) {
						if (!b.getBanner_id().equals(updateBanner.getBanner_id())) {
							throw new HJHBCSErrInfoException(MallErrorCode.BannerErrorCode.BANNER_SORT_EXIST);
						}
					}
				}
			}
		}

		Banner banner = JSONUtil.trans(updateBanner, Banner.class);
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		banner.setUpdate_date(date);
		banner.setUpdate_time(time);
		bannerDao.update(banner);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> getBannerList(GetBannerListVo getBannerListVo) {
		Banner banner = new Banner();
		if (getBannerListVo.getType() != null && !"".equals(getBannerListVo.getType())
				&& !"null".equals(getBannerListVo.getType())) {
			banner.setType(getBannerListVo.getType());
		}
		if (getBannerListVo.getStatus() != null && !"".equals(getBannerListVo.getStatus())
				&& !"null".equals(getBannerListVo.getStatus())) {
			banner.setStatus(getBannerListVo.getStatus());
		}
		int totalNum = bannerDao.count(banner);
		if (getBannerListVo.getLimit() != null && getBannerListVo.getPage() != null) {
			Pagination page = new Pagination();
			page.setPage_size(getBannerListVo.getLimit());
			page.setPage_no(getBannerListVo.getPage());
			page.setTotal_item_num(totalNum);
			page.calc();
			banner.setPage(page);
		}
		List<Map<String, Object>> list = bannerDao.getBannerList(banner);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		result.put(BasicFields.TOTAL_NUM, totalNum);
		return result;
	}

	@Override
	public Map<String, Object> updateBannerStatus(UpdateBannerStatus updateBannerStatus) {
		if ("1".equals(updateBannerStatus.getType())
				&& Status.ENABLED.getVal().equals(updateBannerStatus.getStatus())) {// 占位图并且启用
			Banner banner1 = new Banner();
			banner1.setType(updateBannerStatus.getType());
			banner1.setStatus(Status.ENABLED.getVal());
			List<Banner> list = bannerDao.query(banner1);
			if (list != null && list.size() > 0) {
				throw new HJHBCSErrInfoException(MallErrorCode.BannerErrorCode.BITMAP_EXIST);
			}
		}
		Banner banner = JSONUtil.trans(updateBannerStatus, Banner.class);
		String date = new SimpleDateFormat(DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK).format(new Date());// 日期
		String time = new SimpleDateFormat(DateTimeUtil.FORMAT_HHMMSS_NO_BREAK).format(new Date());// 时间
		banner.setUpdate_date(date);
		banner.setUpdate_time(time);
		bannerDao.update(banner);
		return VOUtil.genSuccessResult();
	}

	@Override
	public Map<String, Object> getBannerListByMobile(HJYVO hjyvo) {
		Banner banner = new Banner();
		banner.setStatus(Status.ENABLED.getVal());
		List<Banner> list = bannerDao.query(banner);
		Map<String, Object> result = VOUtil.genSuccessResult();
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}

	@Override
	protected DAOBase<Banner, String> getDAO() {
		return bannerDao;
	}

}
