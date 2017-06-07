package com.hjh.mall.field;

/**
 * @Project: hjy-filed
 * @Description TODO
 * @author 王斌
 * @date 2016年7月19日
 * @version V1.0
 */
public interface MallErrorCode {

	public final static String HJY_ERRORCODE_NAMESPACE = "500";

	public class InviteCodeErrorCode {

		public final static String INVIT_CODE_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "01";

		/**
		 * 邀请码错误
		 */
		public final static String INVIT_CODE_IS_NULL = INVIT_CODE_NAMESPACE + "001";

	}

	public class BannerErrorCode {

		public final static String BANNER_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "02";

		/**
		 * 占位图已存在
		 */
		public final static String BITMAP_EXIST = BANNER_NAMESPACE + "001";
		public final static String BANNER_SORT_EXIST = BANNER_NAMESPACE + "002";

	}

	public class CategoryErrorCode {

		public final static String CATEGORY_CODE_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "05";

		/**
		 * 类目已存在
		 */
		public final static String CATEGORY_EXIT = CATEGORY_CODE_NAMESPACE + "001";

	}

	public class OrderErrorCode {

		private final static String ERROR_BASE = HJY_ERRORCODE_NAMESPACE + "03";

		/**
		 * 规格不能为空
		 */
		public final static String ERROR_SPEC = ERROR_BASE + "001";
		/**
		 * 价格不能为空
		 */
		public final static String ERROR_PRICE = ERROR_BASE + "002";
		/**
		 * 数量不正确
		 */
		public final static String ERROR_NUM = ERROR_BASE + "003";
		/**
		 * 必选规格不能为空
		 */
		public final static String ERROR_MUST_SPEC = ERROR_BASE + "004";
		/**
		 * 商品已下架
		 */
		public final static String GOODS_NOT_EXIST = ERROR_BASE + "005";
		/**
		 * 购买商品的价格信息错误
		 */
		public final static String GOODS_ERROR_PRICE = ERROR_BASE + "006";
		/**
		 * 购买商品的规格信息错误
		 */
		public final static String GOOGS_ERROR_STANDANDS = ERROR_BASE + "007";
		/**
		 * 商品规格编号不存在
		 */
		public final static String GOOGS_ERROR_STANDANDS_ID = ERROR_BASE + "008";

	}
}
