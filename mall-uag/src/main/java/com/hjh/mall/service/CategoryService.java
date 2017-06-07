package com.hjh.mall.service;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.navigation.vo.CreateNavigation;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QuerySecondLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.QueryThirdLevelNavigations;
import com.hjh.mall.category.bizapi.bizserver.navigation.vo.UpdateNavigation;
import com.hjh.mall.common.core.service.base.ServiceBase;
import com.hjh.mall.entity.Category;
import com.hjh.mall.vo.AddCategory;

/**
 * @Project: hjy-middle
 * @Description 类目业务层
 * @author 陈士俊
 * @date 2017年02月21日
 * @version V1.0
 */
public interface CategoryService extends ServiceBase<Category, String> {
	public void addLabel(AddCategory paramsEntity);

	List<Map<String, String>> queryLabelsBylike(Map<String, Object> map);

	int countByLike(Map<String, Object> map);

	public List<Map<String, String>> queryCategoryIdNames();

	public Map<String, Object> createNavigation(CreateNavigation vo);

	public Map<String, Object> updateNavigation(UpdateNavigation vo);

	public Map<String, Object> exportSecondLevelNavigations(QuerySecondLevelNavigations vo, OutputStream opts);

	public Map<String, Object> exporThirdLevelNavigations(QueryThirdLevelNavigations vo, OutputStream opts);
}
