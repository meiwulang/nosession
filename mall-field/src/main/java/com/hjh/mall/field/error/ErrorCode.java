package com.hjh.mall.field.error;

/**
 * @Project: hjy-filed
 * @Description TODO
 * @author 王斌
 * @date 2016年7月19日
 * @version V1.0
 */
public interface ErrorCode {

	public final static String HJY_ERRORCODE_NAMESPACE = "400";

	public class VerifyErrorCode {

		public final static String VERIFY_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "00";

		/**
		 * 认证类型不匹配 40000001
		 */
		public final static String CHECK_NOT_MATCHING = VERIFY_NAMESPACE + "001";
		/**
		 * 用户已经被锁定
		 */
		public final static String USER_IS_DISENABLED = VERIFY_NAMESPACE + "002";

		/**
		 * 手机号码已被注册
		 */
		public final static String USER_ALREADY_REGIST = VERIFY_NAMESPACE + "003";

		/**
		 * 手机号不存在
		 */
		public final static String MOBLIE_TEL_IS_NOT_EXSIT = VERIFY_NAMESPACE + "004";

		/**
		 * 您获取验证码过于频繁，请您稍后获取验证码
		 */
		public final static String GET_VERIFY_TO_MUCH = VERIFY_NAMESPACE + "005";

		/**
		 * 验证失败次数过多请稍后在试
		 */
		public final static String FAIL_TOO_MUCH = VERIFY_NAMESPACE + "006";

		/**
		 * 用户名密码错误
		 */
		public final static String USER_IS_NULL = VERIFY_NAMESPACE + "007";

		/**
		 * 验证码已失效，请重新获取
		 */
		public final static String VERIFY_IS_DISENABLED = VERIFY_NAMESPACE + "008";

		/**
		 * 输入验证码不正确，请重新输入
		 */
		public final static String VERIFY_NOT_MATCHING = VERIFY_NAMESPACE + "009";

		/**
		 * 手机号未注册
		 */
		public final static String USER_NOT_REGIST = VERIFY_NAMESPACE + "008";

	}

	public class UserErrorCode {

		public final static String USER_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "02";

		/**
		 * 用户名密码错误
		 */
		public final static String USER_IS_NULL = USER_NAMESPACE + "001";
		/**
		 * 用户未激活
		 */
		public final static String USER_IS_DISENABLED = USER_NAMESPACE + "002";

		/**
		 * 用户未加入圈子
		 */
		public final static String USER_NOT_COMMUNITY = USER_NAMESPACE + "003";

		/**
		 * 非企业用户错误提示
		 */
		public final static String USER_NOT_AGENT = USER_NAMESPACE + "004";

		/**
		 * 用户已存在角色和加入角色不匹配
		 */
		public final static String ROLE_NOT_MATCHING = USER_NAMESPACE + "005";

		/**
		 * 用户已注册
		 */
		public final static String USER_ALREADY_REGIST = USER_NAMESPACE + "006";

		/**
		 * 验证码错误
		 */
		public final static String VERIFY_ERROR = USER_NAMESPACE + "007";

		/**
		 * 验证码类型不匹配
		 */
		public final static String VERIFY_TYPE_NOT_MATCHING = USER_NAMESPACE + "008";

		/**
		 * 密码不一致
		 */
		public final static String PASSWORD_NOT_MATCHING = USER_NAMESPACE + "009";

		/**
		 * position格式不正确
		 */
		public final static String POSITION_FORMAT_WRONG = USER_NAMESPACE + "010";
		/**
		 * 管理员登录信息超时
		 */
		public final static String INVALID_OPT_INTO = USER_NAMESPACE + "011";
		/**
		 * 账号已存在
		 */
		public final static String EXIST_ACCOUNT = USER_NAMESPACE + "012";

		/**
		 * 用户昵称已存在
		 */
		public final static String NICK_NAME_EXIST = USER_NAMESPACE + "015";

	}

	public class CommunityErrorCode {
		public final static String COMMUNITY = HJY_ERRORCODE_NAMESPACE + "03";
		/**
		 * 该圈子不存在
		 */
		public final static String COMMUNITY_IS_NOT_EXIST = COMMUNITY + "001";

		/**
		 * 该圈子尚无资讯内容
		 */
		public final static String COMMUNITY_IS_NOT_NEWS = COMMUNITY + "002";
	}

	public class WorkerOrderVerification {

		public final static String WORKER_ORDER = HJY_ERRORCODE_NAMESPACE + "08";
		/**
		 * 车辆位置不能为空
		 */
		public final static String VEHICLE_POSITION_IS_NULL = WORKER_ORDER + "001";
	}

	public class ScheduleErrorCode {
		public final static String SCHEDULE_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "05";
		/**
		 * 拜访时间不合理
		 */
		public final static String SCHEDULE_ERROR_VISIT_DATE = SCHEDULE_NAMESPACE + "001";
		/**
		 * 非拜访日，不可修改记录
		 */
		public final static String SCHEDULE_ILLEGAL_OPRATION = SCHEDULE_NAMESPACE + "002";
		/**
		 * 日程进入执行阶段，不可删除
		 */
		public final static String SCHEDULE_CANNOT_DELETE = SCHEDULE_NAMESPACE + "003";
		/**
		 * 客户名称和客户编号个数不一致
		 */
		public final static String ILLEGAL_LENGTH = SCHEDULE_NAMESPACE + "004";
		/**
		 * 无满足条件记录
		 */
		public final static String WITHOUT_RESULT = SCHEDULE_NAMESPACE + "005";
	}

	public class VerifyMsgCode {
		public final static String VERIFYMSG = HJY_ERRORCODE_NAMESPACE + "010";

		/**
		 * 认证类型不匹配
		 */
		public static final String REUQEST_VER_TYPE = VERIFYMSG + "01";
		/**
		 * 输入验证码不正确，请重新输入
		 */
		public static final String VALIDATA_FASLE = VERIFYMSG + "02";
		/**
		 * 验证码已失效，请重新获取
		 */
		public static final String VALIDATA_INVALID = VERIFYMSG + "03";
		/**
		 * 您获取验证码过于平凡，请您稍后获取验证码
		 */
		public static final String TOO_OFTEN = VERIFYMSG + "04";
		/**
		 * 验证失败次数太多，请稍后重试
		 */
		public static final String FAILED_MANY_TIMES = VERIFYMSG + "05";

		/**
		 * 失败次数太多，用户已经被锁定
		 */
		public static final String USER_LOCKED = VERIFYMSG + "06";
		/**
		 * 电话号与获得验证码电话号不相同
		 */
		public static final String MOBILE_TEL_IS_FALSE = VERIFYMSG + "07";

	}

	/**
	 * 
	 * @Project: hjy-filed
	 * @Description 领导模块专用
	 * @author 王斌
	 * @date 2016年7月15日
	 * @version V1.0
	 */

	public class ManagerCode {
		/**
		 * 领导错误码命名空间
		 */
		public final static String MANAGER_BASE = HJY_ERRORCODE_NAMESPACE + "060";
		/**
		 * 无查询权限
		 */
		public static final String ERROR_ROLE = MANAGER_BASE + "01";
	}

	/**
	 * @Project: hjy-filed
	 * @Description 图片错误码
	 * @author 王斌
	 * @date 2016年7月19日
	 * @version V1.0
	 */
	public class ImageCode {
		/**
		 * 图片错误码命名空间
		 */
		public final static String IMAGE_BASE = HJY_ERRORCODE_NAMESPACE + "070";
		/**
		 * 图片格式不正确
		 */
		public static final String ERROR_TYPE = IMAGE_BASE + "01";
		/**
		 * 保存路径不正确
		 */
		public static final String ERROR_PATH = IMAGE_BASE + "02";
	}

	/**
	 * @Project: hjy-filed
	 * @Description：字典类型
	 * @author 王斌
	 * @date 2016年7月19日
	 * @version V1.0
	 */
	public class DictionaryCode {
		/**
		 * 字典类型命名空间
		 */
		public final static String DICTIONARY_BASE = HJY_ERRORCODE_NAMESPACE + "090";
		/**
		 * 字典类型为空
		 */
		public static final String ERROR_TYPE = DICTIONARY_BASE + "01";
		/**
		 * 字典拓展类型不能为空
		 */
		public static final String ERROR_EXT = DICTIONARY_BASE + "02";

		/**
		 * 名称重复
		 */
		public static final String NAME_CONFILCI = DICTIONARY_BASE + "03";
	}

	public class ModelTypeCode {
		/**
		 * 修改名片时的错误
		 */
		public final static String TYPE_BASE = HJY_ERRORCODE_NAMESPACE + "100";

		/**
		 * 单位不能为空
		 */
		public final static String UNIT = TYPE_BASE + "01";
		/**
		 * 经营范围不能为空
		 */
		public final static String BUSSINS = TYPE_BASE + "02";
		/**
		 * 品牌不能为空
		 */
		public final static String BRAND = TYPE_BASE + "03";
		/**
		 * 设备不能为空
		 */
		public final static String DEVICE = TYPE_BASE + "04";
		/**
		 * 客户类型不能改变
		 */
		public final static String DICTIONARY_ID_FALSE = TYPE_BASE + "05";
		/**
		 * 载重格式不正确
		 */
		public final static String ERROR_TYPE = TYPE_BASE + "06";
		/**
		 * 载重长度不正确
		 */
		public final static String ERROR_LENGTH = TYPE_BASE + "07";
		/**
		 * 类别不能为空
		 */
		public final static String ERROR_BRAND_TYPE = TYPE_BASE + "08";

	}

	public class NewsContentCode {
		public final static String TYPE_BASE = HJY_ERRORCODE_NAMESPACE + "11";

		/**
		 * 标题重复
		 */
		public final static String TITLE_CONFILCI = TYPE_BASE + "001";
	}

	/**
	 * @Project: hjh-filed
	 * @Description 商家错误信息
	 * @author 王斌
	 * @date 2016年9月27日
	 * @version V1.0
	 */
	public class MerchantErrorCode {
		public final static String MERCHANT_NAMESPACE = HJY_ERRORCODE_NAMESPACE + "12";
		/**
		 * 单位不能为空
		 */
		public final static String ENTERPRISE_ERROR = MERCHANT_NAMESPACE + "001";
		/**
		 * 经营范围不能为空
		 */
		public static final String BUSSINESS_SCOPE_ERROR = MERCHANT_NAMESPACE + "002";
		/**
		 * 经营品牌不能为空
		 */
		public static final String BRAND_ERROR = MERCHANT_NAMESPACE + "003";
		/**
		 * 设备品牌不能为空
		 */
		public static final String DEVICE_BRAND_ERROR = MERCHANT_NAMESPACE + "004";
		/**
		 * 设备类别不能为空
		 */
		public static final String DEVICE_TYPE_ERROR = MERCHANT_NAMESPACE + "005";
		/**
		 * 设备数量不能为空
		 */
		public static final String DEVICE_COUNT_ERROR = MERCHANT_NAMESPACE + "006";
		/**
		 * 设备载重不能为空
		 */
		public static final String DEVICE_CAPABILITY_ERROR = MERCHANT_NAMESPACE + "007";
		/**
		 * 店铺照片不能为空
		 */
		public static final String STORE_PIC_ERROR = MERCHANT_NAMESPACE + "008";
		/**
		 * 店铺缩略照片不能为空
		 */
		public static final String STORE_ZIP_PIC_ERROR = MERCHANT_NAMESPACE + "009";
		/**
		 * 图片格式不正确
		 */
		public static final String STREET_SCOPE_PIC_ERROR = MERCHANT_NAMESPACE + "010";
		/**
		 * 实景图过多
		 */
		public static final String TOO_MANY_PIC = MERCHANT_NAMESPACE + "011";
		/**
		 * 名片不存在
		 */
		public static final String CUSTOMER_ISNOTEXITS = MERCHANT_NAMESPACE + "012";
		/**
		 * 实景图数量不合格
		 */
		public static final String ERROR_PIC_COUNT = MERCHANT_NAMESPACE + "013";

	}

	public class ChannelContentCode {
		public final static String TYPE_BASE = HJY_ERRORCODE_NAMESPACE + "13";

		/**
		 * 频道重复
		 */
		public final static String CHANNEL_TITLE_CONFILCI = TYPE_BASE + "001";
	}

	public class CardErrorCode {
		public final static String TYPE_BASE = HJY_ERRORCODE_NAMESPACE + "14";

		/**
		 * pic名片找不到
		 */
		public final static String NO_CARD = TYPE_BASE + "001";
	}

	public class CustomerErrorCode {
		public final static String TYPE_BASE = HJY_ERRORCODE_NAMESPACE + "15";

		/**
		 * 客户已存在
		 */
		public final static String CUSTOMER_EXIST = TYPE_BASE + "001";

		/**
		 * 导入的品牌 不存在
		 */
		public final static String CUSTOMER_BRAND_NOT_EXIST = TYPE_BASE + "002";
	}

	public class TopicErrorCode {
		private final static String BASE = HJY_ERRORCODE_NAMESPACE + "18";

		/**
		 * 置顶话题已经达到上限
		 */
		public static final String BANNER_NUM_MAX = BASE + "001";
	}

	public class Claim {
		private final static String BASE = HJY_ERRORCODE_NAMESPACE + "12";
		/**
		 * 名片已认领
		 */
		public static final String CARD_CLAIMED = BASE + "101";
	}

	public class Category {
		private final static String BASE = HJY_ERRORCODE_NAMESPACE + "11";
		/**
		 * 类目已经存在
		 */
		public static final String CATEGORY_EXIT = BASE + "401";
	}
}
