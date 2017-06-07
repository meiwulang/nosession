package com.hjh.mall.category.bizapi.bizserver.navigation;

import java.io.Serializable;
import java.util.Map;

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
import com.hjh.mall.common.core.annotation.BizService;
import com.hjh.mall.common.core.vo.HJYVO;

/**
 * @Project: hjh-basedata-api
 * @Description 基础导航服务
 * @author 王斌
 * @date 2016年10月11日
 * @version V1.0
 */
public interface BizNavigationService extends Serializable {
	@BizService(functionId = "900505", name = "创建导航", desc = "创建1-3级导航")
	public Map<String, Object> createNavigation(CreateNavigation vo);

	@BizService(functionId = "900506", name = "编辑导航", desc = "编辑1-3级导航")
	public Map<String, Object> updateNavigation(UpdateNavigation vo);

	@BizService(functionId = "900513", name = "批量编辑导航状态", desc = "批量编辑1-3级导航状态")
	public Map<String, Object> batchUpdateNavigationStatus(UpdateNavigationStatus vo);

	@BizService(functionId = "900507", name = "模糊查询1级导航", desc = "模糊查询1级导航")
	public Map<String, Object> queryFirstLevelNavigations(QueryFirstLevelNavigations vo);

	@BizService(functionId = "900508", name = "模糊查询2级导航", desc = "模糊查询2级导航")
	public Map<String, Object> querySecondLevelNavigations(QuerySecondLevelNavigations vo);

	@BizService(functionId = "900511", name = "模糊查询3级导航", desc = "模糊查询3级导航")
	public Map<String, Object> queryThirdLevelNavigations(QueryThirdLevelNavigations vo);

	@BizService(functionId = "900512", name = "查询app导航", desc = "查询app导航")
	public Map<String, Object> queryNavigationsForApp(QueryNavigationsForApp vo);

	@BizService(functionId = "900514", name = "按层级模糊查询类目", desc = "按层级模糊查询类目")
	public Map<String, Object> queryNavigationsByLike(QueryNavigationsByLike vo);

	@BizService(functionId = "900515", name = "按父级编号查询类目", desc = "按父级编号查询类目")
	public Map<String, Object> queryNavigationsByparentId(QueryNavigationsByparentId vo);

	@BizService(functionId = "900516", name = "查询二三级全部可用类目", desc = "查询二三级全部可用类目")
	public Map<String, Object> queryAllNavigationsForApp(HJYVO vo);

	@BizService(functionId = "900517", name = "修改单条导航状态", desc = "修改单条导航状态")
	public Map<String, Object> updateNavigationStatus(UpdatesingleNavigationStatus vo);

}
