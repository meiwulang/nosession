package com.hjh.mall.field.error;

/**
 * @Project: mall-field
 * @Description TODO
 * @author 曾繁林
 * @date 2017年3月15日
 * @version V1.0
 */
public interface MallErrorCode {

	public final static String HJY_ERRORCODE_NAMESPACE = "500";

	public class MetaDataErrorCode {

		public final static String METADATA_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "06";

		/**
		 * 元数据名称存在
		 */
		public final static String METADATA_EXIST = METADATA_NAMESPACE + "001";
		/**
		 * 元数据别名重复
		 */
		public final static String ALIAS_EXIST = METADATA_NAMESPACE + "002";
		/**
		 * 元数据排序重复
		 */
		public final static String SORT_EXIST = METADATA_NAMESPACE + "003";

	}

	public class NavigationErrorCode {

		private final static String NAVIGATION_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "08";

		/**
		 * 类目名称已存在
		 */
		public final static String NAVIGATION_EXIST = NAVIGATION_NAMESPACE + "001";
		/**
		 * 类目别名已存在
		 */
		public final static String NICK_EXIST = NAVIGATION_NAMESPACE + "002";
		/**
		 * APP排序号重复
		 */
		public final static String SORT_EXIST = NAVIGATION_NAMESPACE + "003";
		/**
		 * 请正确输入父级编号
		 */
		public final static String ERROR_FATHER = NAVIGATION_NAMESPACE + "004";
		/**
		 * 代码为{1}的类目存在为禁用的子类目
		 */
		public final static String HAS_ENABLE_CHILD = NAVIGATION_NAMESPACE + "005";
		/**
		 * APP热门排序号重复
		 */
		public final static String HOT_SORT_EXIST = NAVIGATION_NAMESPACE + "006";
		/**
		 * APP热门排序号不合法
		 */
		public final static String HOT_SORT_CANNOT_NULL = NAVIGATION_NAMESPACE + "007";
		/**
		 * APP热门图片不能为空
		 */
		public final static String HOT_IMG_CANNOT_NULL = NAVIGATION_NAMESPACE + "008";
		/**
		 * APP热门排序不能重复
		 */
		public final static String HOT_SORT_UNIQUE = NAVIGATION_NAMESPACE + "009";

	}

	public class CarModelErrorCode {
		private final static String CARMODEL_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "09";

		/**
		 * 型号已存在
		 */
		public final static String CAR_MODEL_EXIST = CARMODEL_NAMESPACE + "001";

	}

	public class GoodsErrorCode {

		private final static String BASE_GOODS = HJY_ERRORCODE_NAMESPACE + "10";
		/**
		 * 库存不够
		 */
		public final static String SOTRE_NOT_ENOUGH = BASE_GOODS + "001";

		/**
		 * 商品已下架
		 */
		public final static String GOODS_HAS_UNDER = BASE_GOODS + "002";

		/**
		 * 商品不存在
		 */
		public final static String GOODS_NOT_EXIST = BASE_GOODS + "003";

		/**
		 * 商品已被逻辑删除
		 */
		public final static String GOODS_LOGIC_DELETE = BASE_GOODS + "004";
		/**
		 * 商品没有这个规格
		 */
		public final static String GOODS_NO_THIS_STANDARD = BASE_GOODS + "005";

		/**
		 * 规格不存在
		 */
		public final static String STANDARD_NOT_EXIST = BASE_GOODS + "006";

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
		/**
		 * 订单重复提交
		 */
		public final static String DUPLICATE = ERROR_BASE + "009";
		/**
		 * 收获地址不存在
		 */
		public final static String ERROR_DEST_ADDR = ERROR_BASE + "010";
		/**
		 * 管理员登录信息超时
		 */
		public final static String INVALID_OPT_INTO = ERROR_BASE + "011";

	}

	public class Activity {
		private final static String ERROR_BASE = HJY_ERRORCODE_NAMESPACE + "04";
		/**
		 * 活动序号重复
		 */
		public final static String SORT_REPEAT = ERROR_BASE + "001";
		/**
		 * 活动不存在或已失效
		 */
		public final static String NOT_EXIST = ERROR_BASE + "002";
		/**
		 * 活动序号过大
		 */
		public final static String SORT_TOO_LAGER = ERROR_BASE + "003";

	}
}
