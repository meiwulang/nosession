package com.hjh.mall.common.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chengjia
 *
 */
public abstract class StringUtil {

	public static final String EMPTY_STRING = "";

	public static final String[] EMPTY_STRING_ARR = new String[0];

	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

	private static Pattern blank_pattern = Pattern.compile("\\s");

	private static Pattern upper_case_pattern = Pattern.compile("([A-Z])");

	private static Method propertiesSaveConvertMethod;
	private static Properties dummyProperties;

	static {
		init();
	}

	private static void init() {
		try {
			propertiesSaveConvertMethod = Properties.class.getDeclaredMethod("saveConvert",
					new Class[] { String.class, Boolean.TYPE, Boolean.TYPE });
			propertiesSaveConvertMethod.setAccessible(true);
			dummyProperties = new Properties();
		} catch (Exception e) {
			LOGGER.error("init propertiesSaveConvertMethod failed", e);
		}
	}

	public static boolean isEmpty(String str) {
		return null == str || 0 == str.length();
	}

	public static boolean isBlank(String str) {
		if (null == str || 0 == str.length()) {
			return true;
		}
		int len = str.length();
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String nullSafeTrim(String str) {
		return null == str ? null : str.trim();
	}

	public static String emptyToNull(Object value) {
		if (null == value) {
			return null;
		} else {
			String str = String.valueOf(value);
			if (str.isEmpty()) {
				return null;
			} else {
				return str;
			}
		}
	}

	public static String nullToEmpty(Object value) {
		return null == value ? EMPTY_STRING : String.valueOf(value);
	}

	public static String[] splitUsingIndex(String source, String sep) {
		return splitUsingIndex(source, sep, false);
	}

	public static String[] splitUsingIndex(String source, String sep, boolean keepLastEmpty) {
		if (null == source || null == sep || source.isEmpty()) {
			return EMPTY_STRING_ARR;
		}
		List<String> list = new ArrayList<String>();
		int sourceLen = source.length();
		int sepLen = sep.length();
		int fromIdx = 0;
		int idx = 0;
		while (-1 != (idx = source.indexOf(sep, fromIdx))) {
			list.add(source.substring(fromIdx, idx));
			fromIdx = idx + sepLen;
			if (fromIdx == sourceLen) {
				break;
			}
		}
		if (fromIdx == sourceLen) {
			if (keepLastEmpty) {
				list.add(EMPTY_STRING);
			}
		} else {
			list.add(source.substring(fromIdx));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] splitUsingIndex(String source, char sep) {
		return splitUsingIndex(source, sep, false);
	}

	public static String[] splitUsingIndex(String source, char sep, boolean keepLastEmpty) {
		if (null == source) {
			return EMPTY_STRING_ARR;
		} else if (source.isEmpty()) {
			return new String[] { EMPTY_STRING };
		}
		List<String> list = new ArrayList<String>();
		int sourceLen = source.length();
		int sepLen = 1;
		int fromIdx = 0;
		int idx = 0;
		while (-1 != (idx = source.indexOf(sep, fromIdx))) {
			list.add(source.substring(fromIdx, idx));
			fromIdx = idx + sepLen;
			if (fromIdx == sourceLen) {
				break;
			}
		}
		if (fromIdx == sourceLen) {
			if (keepLastEmpty) {
				list.add(EMPTY_STRING);
			}
		} else {
			list.add(source.substring(fromIdx));
		}
		return list.toArray(new String[list.size()]);
	}

	public static Integer[] splitToInteger(String source, String splitRegex) {
		String[] strArr = source.split(splitRegex);
		int arrLen = strArr.length;
		Integer[] intArr = new Integer[arrLen];
		for (int i = 0; i < arrLen; i++) {
			intArr[i] = Integer.valueOf(strArr[i].trim());
		}
		return intArr;
	}

	public static int[] splitToInt(String source, String splitRegex) {
		String[] strArr = source.split(splitRegex);
		int arrLen = strArr.length;
		int[] intArr = new int[arrLen];
		for (int i = 0; i < arrLen; i++) {
			intArr[i] = Integer.parseInt(strArr[i]);
		}
		return intArr;
	}

	public static Long[] splitToLong(String source, String splitRegex) {
		String[] strArr = source.split(splitRegex);
		int arrLen = strArr.length;
		Long[] longArr = new Long[arrLen];
		for (int i = 0; i < arrLen; i++) {
			longArr[i] = Long.valueOf(strArr[i]);
		}
		return longArr;
	}

	public static long[] splitToLon(String source, String splitRegex) {
		String[] strArr = source.split(splitRegex);
		int arrLen = strArr.length;
		long[] longArr = new long[arrLen];
		for (int i = 0; i < arrLen; i++) {
			longArr[i] = Long.parseLong(strArr[i]);
		}
		return longArr;
	}

	public static String toPropertiesValue(String rawString) {
		if (null != propertiesSaveConvertMethod) {
			try {
				return (String) propertiesSaveConvertMethod.invoke(dummyProperties, rawString, false, true);
			} catch (Exception e) {
				LOGGER.error("call propertiesSaveConvertMethod failed", e);
			}
		}
		return simpleToPropertiesValue(rawString);
	}

	private static String simpleToPropertiesValue(String rawString) {
		if (null != rawString && !rawString.isEmpty()) {
			int len = rawString.length();
			StringBuilder sBuilder = new StringBuilder(len * 2);
			for (int i = 0; i < len; i++) {
				char c = rawString.charAt(i);
				if (0x007e < c) {
					sBuilder.append('\\').append('u').append(Integer.toHexString(c));
				} else {
					sBuilder.append(c);
				}
			}
			return sBuilder.toString();
		} else {
			return rawString;
		}
	}

	public static String camelToUnderlineCase(String source, boolean toUpper) {
		if (null == source) {
			return null;
		}
		String result = camelToSeparateCase(source, '_');
		if (toUpper) {
			result = result.toUpperCase();
		}
		return result;
	}

	public static String camelToUnderlineCase(String source) {
		return camelToUnderlineCase(source, false);
	}

	public static String underlineToCamelCase(String source) {
		return toCamelCase(source, '_');
	}

	public static String toCamelCase(String s, char separator) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		int len = s.length();
		StringBuilder sb = new StringBuilder(len);
		boolean upperCase = false;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c == separator) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String underlineToDot(String s) {
		return s.replaceAll("_", ".");
	}

	public static String camelToSeparateCase(String source, char separator) {
		if (null == source) {
			return null;
		}
		Matcher matcher = upper_case_pattern.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String g = matcher.group();
			matcher.appendReplacement(sb, separator + g.toLowerCase());
		}
		matcher.appendTail(sb);
		if (sb.charAt(0) == separator) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	public static String stripAllBlank(String str) {
		return null == str ? null : blank_pattern.matcher(str).replaceAll("");
	}

	public static List<String> tokenize(String source, String delimiters, boolean trim, boolean ignoreEmpty) {
		List<String> result = new ArrayList<String>();
		if (null == source) {
			return result;
		}
		StringTokenizer sTokenizer = null;
		if (null == delimiters) {
			sTokenizer = new StringTokenizer(source);
		} else {
			sTokenizer = new StringTokenizer(source, delimiters);
		}
		while (sTokenizer.hasMoreElements()) {
			String token = sTokenizer.nextToken();
			if (trim) {
				token = token.trim();
			}
			if (ignoreEmpty && token.isEmpty()) {
				continue;
			}
			result.add(token);
		}
		return result;
	}

	/**
	 * form形式的字符串解析为map，始终返回映射为多值的形式，即无论相同的key出现一次还是多次，映射的value都是ListList&lt;
	 * String&gt;
	 * 
	 * @param formString
	 *            form形式的字符串
	 * @param decodeCharset
	 *            解码字符集，如果{@code formString}
	 *            需要被URL解码，则指定解码时使用的字符集，传入null则不执行URL解码
	 * @return 解析后的map，如果formString为null或空白，则返回空的map
	 */
	public static Map<String, List<String>> formStringToMap(String formString, String decodeCharset) {
		if (StringUtils.isBlank(formString)) {
			return new HashMap<String, List<String>>();
		}
		// 先分解后解码
		String[] nvPairs = formString.split("&");
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>(nvPairs.length);
		for (String nvPairString : nvPairs) {
			String[] nvPair = nvPairString.split("=", -1);
			if (nvPair.length == 2) {
				String key = nvPair[0];

				String value = nvPair[1];
				// 如果decodeCharset为null则不解码
				if (null != decodeCharset) {
					try {
						value = URLDecoder.decode(value, decodeCharset);
					} catch (UnsupportedEncodingException e) {
						throw new IllegalArgumentException("unsupported encoding " + decodeCharset, e);
					}
				}

				// 如果对应的值列表还不存在就创建
				List<String> valList = map.get(key);
				if (null == valList) {
					valList = new ArrayList<String>();
					map.put(key, valList);
				}
				valList.add(value);
			}
		}
		return map;
	}

	/**
	 * form形式的字符串解析为map，返回映射为单值或多值的形式，即相同的key如果只出现一次，映射的value是String，如果出现多次，
	 * 映射的value是List&lt;String&gt;
	 * 
	 * @param formString
	 *            form形式的字符串
	 * @param decodeCharset
	 *            解码字符集，如果{@code formString}
	 *            需要被URL解码，则指定解码时使用的字符集，传入null则不执行URL解码
	 * @return 解析后的map，如果formString为null或空白，则返回空的map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> formStringToMapSingleValueNoArray(String formString, String decodeCharset) {
		if (StringUtils.isBlank(formString)) {
			return new HashMap<String, Object>();
		}
		// 先分解后解码
		String[] nvPairs = formString.split("&");
		Map<String, Object> map = new LinkedHashMap<String, Object>(nvPairs.length);
		for (String nvPairString : nvPairs) {
			String[] nvPair = nvPairString.split("=", -1);
			if (nvPair.length == 2) {
				String key = nvPair[0];

				String value = nvPair[1];
				// 如果decodeCharset为null则不解码
				if (null != decodeCharset) {
					try {
						value = URLDecoder.decode(value, decodeCharset);
					} catch (UnsupportedEncodingException e) {
						throw new IllegalArgumentException("unsupported encoding " + decodeCharset, e);
					}
				}

				// 获取map中对应的值
				Object vals = map.get(key);
				if (null == vals) {
					// map中不存在则是第一个，直接存入String
					map.put(key, value);
				} else if (vals instanceof String) {
					// map中已有一个String，则需要变成List
					List<String> valList = new ArrayList<String>();
					valList.add((String) vals);
					valList.add(value);
					map.put(key, valList);
				} else if (vals instanceof List) {
					// map中已是List，则添加
					((List) vals).add(value);
				}
			}
		}
		return map;
	}

	/**
	 * form形式的字符串解析为map，始终返回映射为单值的形式，即无论相同的key出现一次还是多次，映射的value都是String，
	 * 相同的key只取第一个value
	 * 
	 * @param formString
	 *            form形式的字符串
	 * @param decodeCharset
	 *            解码字符集，如果{@code formString}
	 *            需要被URL解码，则指定解码时使用的字符集，传入null则不执行URL解码
	 * @return 解析后的map，如果formString为null或空白，则返回空的map
	 */
	public static Map<String, String> formStringToMapTakeFirstValue(String formString, String decodeCharset) {
		if (StringUtils.isBlank(formString)) {
			return new HashMap<String, String>();
		}
		// 先分解后解码
		String[] nvPairs = formString.split("&");
		Map<String, String> map = new LinkedHashMap<String, String>(nvPairs.length);
		for (String nvPairString : nvPairs) {
			String[] nvPair = nvPairString.split("=", -1);
			if (nvPair.length == 2) {
				String key = nvPair[0];
				// 取第一个值，已经有了就跳过
				if (!map.containsKey(key)) {
					String value = nvPair[1];
					// 如果decodeCharset为null则不解码
					if (null != decodeCharset) {
						try {
							value = URLDecoder.decode(value, decodeCharset);
						} catch (UnsupportedEncodingException e) {
							throw new IllegalArgumentException("unsupported encoding " + decodeCharset, e);
						}
					}
					map.put(key, value);
				}
			}
		}
		return map;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * @date 2017年3月15日
	 * @Description: 生成指定时间格式
	 * @author：王斌
	 * @param dateString
	 * @param timeString
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String generateDate(String dateString, String timeString) {
		String tempdate = "";
		if (dateString.length() == 8) {
			tempdate = dateString.format("%s-%s-%s", dateString.substring(0, 4), dateString.substring(4, 6),
					dateString.substring(6, 8));
		}
		if (timeString.length() == 6) {
			tempdate += timeString.format(" %s:%s:%s", timeString.substring(0, 2), timeString.substring(2, 4),
					timeString.substring(4, 6));
		}
		return tempdate;
	}

	/**
	 * @date 2017年3月16日
	 * @Description: 生成状态
	 * @author：王斌
	 * @param status
	 * @return String
	 */
	public static String generateStatus(String status) {
		return status.equals("0") ? "禁用" : status.equals("1") ? "启用" : "";
	}
}
