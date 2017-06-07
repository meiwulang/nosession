package com.hjh.mall.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hjh.mall.cache.cache.helper.CacheHelper;
import com.hjh.mall.cache.cache.sequence.KeyGenerate;
import com.hjh.mall.category.bizapi.bizserver.goodsbrand.BizGoodsBrandService;
import com.hjh.mall.common.core.annotation.NoLogin;
import com.hjh.mall.common.core.constants.BasicFields;
import com.hjh.mall.common.core.exception.HJHBCSErrInfoException;
import com.hjh.mall.common.core.model.SortMarker;
import com.hjh.mall.common.core.util.BeanUtil;
import com.hjh.mall.common.core.util.DateTimeUtil;
import com.hjh.mall.common.core.util.JSONUtil;
import com.hjh.mall.common.core.util.StringUtil;
import com.hjh.mall.common.core.util.VOUtil;
import com.hjh.mall.context.HJYBizDataContext;
import com.hjh.mall.entity.AccountReceipt;
import com.hjh.mall.field.constant.MallFields;
import com.hjh.mall.field.type.Status;
import com.hjh.mall.service.AccountReceiptService;
import com.hjh.mall.service.OperatorService;
import com.hjh.mall.service.UploadService;
import com.hjh.mall.util.BusiSessionHelper;
import com.hjh.mall.vo.accountreceipt.AccountReceiptAddVo;
import com.hjh.mall.vo.accountreceipt.AccountReceiptAppDisplayVo;
import com.hjh.mall.vo.accountreceipt.AccountReceiptQueryAppVo;
import com.hjh.mall.vo.accountreceipt.AccountReceiptQueryVo;
import com.hjh.mall.vo.accountreceipt.AccountReceiptUpdateVo;

import io.swagger.annotations.ApiOperation;

/**
 * @Project: mall-web
 * @Description 收款账号管理
 * @author 李慧峰
 * @date 2017年5月8日
 * @version V1.0 
 */
@Controller
public class AccountReceiptController {
	@Resource
	private AccountReceiptService accountReceiptService;
	@Resource
	private UploadService uploadService;
	@Resource
	private CacheHelper cacheHelper;
	@Reference(version = "1.0.0")
	private BizGoodsBrandService bizGoodsBrandService;
	@Resource
	private BusiSessionHelper busiSessionHelper;
	@Resource
	private OperatorService operatorService;
	@Resource
	private KeyGenerate keyGenerate;
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountReceiptController.class);
	
	/** 
	 * @date 2017年5月18日
	 * @Description: 查询收款账号 后台使用
	 * @param AccountReceiptQueryVo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "查询收款账号", notes = "查询收款账号")
	@RequestMapping(value="/accountreceipt/query", method = RequestMethod.POST)
	public Map<String, Object> query(@RequestBody AccountReceiptQueryVo hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		AccountReceipt entity = new AccountReceipt();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			LOGGER.error("转换错误");
		}
		entity.addSortMarker(new SortMarker(MallFields.UPDATE_DATE,Boolean.FALSE));
		entity.addSortMarker(new SortMarker(MallFields.UPDATE_TIME,Boolean.FALSE));
		List<AccountReceipt> list = accountReceiptService.query(entity);
		Integer total_num=accountReceiptService.count(entity);
		result.put(BasicFields.TOTAL_NUM, total_num);
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	@NoLogin
	@ResponseBody
	@ApiOperation(value = "通过id查询收款账号", notes = "通过id查找收款账号")
	@RequestMapping(value="/accountreceipt/{id}", method = RequestMethod.POST)
	public Map<String, Object> queryById(@PathVariable String id) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		AccountReceipt entity=accountReceiptService.get(id);
		result.put(BasicFields.RESULT_LIST, entity);
		return result;
	}

	/** 
	 * @date 2017年5月18日
	 * @Description: 创建收款账号
	 * @param AccountReceiptAddVo hjyvo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	//@RequestMapping("/add")
	@ApiOperation(value = "创建收款账号", notes = "创建收款账号")
	//@ApiImplicitParam(name = "brand_id", value = "品牌信息介绍实体BrandInfo", required = true, dataType = "hjyvo")
    @RequestMapping(value = "/accountreceipt/add", method = RequestMethod.POST)  
	public Map<String, Object> saveBrandInfo(@RequestBody AccountReceiptAddVo hjyvo) {
		if(!StringUtil.isBlank(hjyvo.getAccount_logo())){
			hjyvo.setAccount_logo(uploadService.uploadImg(MallFields.ACCOUNT_RECEIPT, hjyvo.getAccount_logo()));
		}
		
		Map<String,Object> result=VOUtil.genSuccessResult();
		AccountReceipt entity=JSONUtil.trans(hjyvo, AccountReceipt.class);
		isExistBankName(entity);
		entity.setAccount_id(keyGenerate.getKeyGenerate(MallFields.ACCOUNT_RECEIPT));
		this.initAddDate(entity);
		accountReceiptService.save(entity);
		return result;
	}
	/** 
	 * @date 2017年5月18日
	 * @Description: 修改收款账号
	 * @param AccountReceiptUpdateVo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "修改收款账号", notes = "修改收款账号")
	@RequestMapping(value="/accountreceipt/update",method = RequestMethod.POST)
	public Map<String, Object> updateBrandInfo(@RequestBody AccountReceiptUpdateVo hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		if(!StringUtil.isBlank(hjyvo.getAccount_logo())){
			hjyvo.setAccount_logo(uploadService.uploadImg(MallFields.ACCOUNT_RECEIPT, hjyvo.getAccount_logo()));
		}
		AccountReceipt entity=JSONUtil.trans(hjyvo, AccountReceipt.class);
		isExistBankName(entity);
		this.initUpdateDate(entity);
		accountReceiptService.update(entity);
		return result;
	}
	
	/** 
	 * @date 2017年5月18日
	 * @Description: 启用禁用收款账号
	 * @param AccountReceiptAppDisplayVo
	 * @return
	 * Map<String,Object>
	 */
	@ResponseBody
	@ApiOperation(value = "启用禁用收款账号", notes = "启用禁用收款账号")
	@RequestMapping(value="/accountreceipt/updateappdisplay",method = RequestMethod.POST)
	public Map<String, Object> updateBrandInfoAppDisplay(@RequestBody AccountReceiptAppDisplayVo hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		AccountReceipt entity=JSONUtil.trans(hjyvo, AccountReceipt.class);
		this.initUpdateDate(entity);
		accountReceiptService.update(entity);
	
		return result;
	}
	/** 
	 * @date 2017年5月18日
	 * @Description: app列表
	 * @param AccountReceiptQueryVo
	 * @return
	 * Map<String,Object>
	 */
	@NoLogin
	@ResponseBody
	@ApiOperation(value = "app获取收款账号列表", notes = "app查询，参数暂时用空{}")
	@RequestMapping(value="/json/accountreceipt/queryapp", method = RequestMethod.POST)
	public Map<String, Object> queryApp(@RequestBody AccountReceiptQueryAppVo hjyvo) {
		Map<String,Object> result=VOUtil.genSuccessResult();
		AccountReceipt entity = new AccountReceipt();
		try {
			BeanUtil.reflectionAttr(hjyvo, entity);
		} catch (Exception e) {
			LOGGER.error("error"+hjyvo.toString());
			e.printStackTrace();
		}
		entity.setApp_display(Status.ENABLED.getVal());
		entity.addSortMarker(new SortMarker(MallFields.SORT,Boolean.TRUE));
		List<AccountReceipt> list = accountReceiptService.query(entity);
		Integer total_num = accountReceiptService.count(entity);
		result.put(BasicFields.TOTAL_NUM, total_num);
		result.put(BasicFields.RESULT_LIST, list);
		return result;
	}
	
	private void initAddDate(AccountReceipt entity){
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		entity.setStatus(Status.ENABLED.getVal());
		Date date=new Date();
		entity.setCreate_user(operator_id);
		entity.setCreate_user_name(operator_name);
		entity.setUpdate_user(operator_id);
		entity.setUpdate_user_name(operator_name);
		entity.setCreate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setCreate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
		entity.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		entity.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}
	
	private void initUpdateDate(AccountReceipt vo){
		String operator_id = HJYBizDataContext.getSessionIdentity().getClientId();
		String operator_name = HJYBizDataContext.getSessionIdentity().getOperatorName();
		vo.setUpdate_user(operator_id);
		vo.setUpdate_user_name(operator_name);
		Date date=new Date();
		vo.setUpdate_date(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_YYYYMMDD_NO_BREAK));
		vo.setUpdate_time(DateTimeUtil.toString(date, DateTimeUtil.FORMAT_HHMMSS_NO_BREAK));
	}
	
	/**
	 * @date 2017年5月22日
	 * @Description: 是否存在重复收款账号(银行名称重复)
	 * @param entity
	 *  void
	 */
	private void isExistBankName(AccountReceipt entity) {
		AccountReceipt cd = new AccountReceipt();
		if (!StringUtil.isBlank(entity.getAccount_id())){
			cd.setAccount_id(entity.getAccount_id());
		}
		cd.setBank_name(entity.getBank_name());
		List<AccountReceipt> list = accountReceiptService.isExist(cd);
		if (list.size() > 0) {
			throw new HJHBCSErrInfoException("40014011");
		}
	}
}
