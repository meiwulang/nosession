package com.hjh.mall.category.bizapi.bizserver.metadata;

import java.util.Map;

import com.hjh.mall.category.bizapi.bizserver.metadata.vo.AddMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByBrand;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataById;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByName;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataByType;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.GetMetadataList;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadata;
import com.hjh.mall.category.bizapi.bizserver.metadata.vo.UpdateMetadataStatus;
import com.hjh.mall.common.core.annotation.BizService;

/**
 * @Project: mall-category-api
 * @Description 元数据相关接口
 * @author 曾繁林
 * @date 2017年3月14日
 * @version V1.0
 */
public interface BizMetadataService {

	@BizService(functionId = "900809", name = "创建元数据", desc = "创建元数据")
	public Map<String, Object> addMetadata(AddMetadata addMetadata);

	@BizService(functionId = "900802", name = "编辑元数据", desc = "编辑元数据")
	public Map<String, Object> updateMetadata(UpdateMetadata updateMetadata);

	@BizService(functionId = "900803", name = "修改元数据状态", desc = "修改元数据状态")
	public Map<String, Object> updateMetadataStatus(UpdateMetadataStatus updateMetadataStatus);

	@BizService(functionId = "900804", name = "获取元数据", desc = "获取元数据")
	public Map<String, Object> getMetadataList(GetMetadataList getMetadataList);

	@BizService(functionId = "900805", name = "根据类型获取元数据", desc = "根据类型获取元数据")
	public Map<String, Object> getMetadataListByType(GetMetadataByType getMetadataByType);

	@BizService(functionId = "900806", name = "加载元数据到缓存", desc = "加载元数据到缓存")
	public Map<String, Object> reloadAllMetadata();

	@BizService(functionId = "900807", name = "根据名称匹配元数据", desc = "根据名称匹配元数据")
	public Map<String, Object> getMetadataByName(GetMetadataByName getMetadataByName);

	@BizService(functionId = "900808", name = "根据Id元数据名称", desc = "根据Id元数据名称")
	public Map<String, Object> getMetadataById(GetMetadataById getMetadataById);

	@BizService(functionId = "900801", name = "根据Ids获取元数据名称", desc = "根据Ids获取元数据名称")
	public Map<String, Object> getMetadataBybrand(GetMetadataByBrand GetMetadataByBrand);

	@BizService(functionId = "900810", name = "检测是否修改元数据名称", desc = "检测是否修改元数据名称")
	public boolean checkMetadataName(UpdateMetadata updateMetadata);

}
