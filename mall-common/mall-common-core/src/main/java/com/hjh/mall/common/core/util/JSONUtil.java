package com.hjh.mall.common.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author chengjia
 *
 */
public abstract class JSONUtil {

	public static final String EMPTY_JSON_OBJECT_STRING = "{}";

	public static JSONObject merge(JSONObject... jsonObjects) {
		int length = 0;
		if (null == jsonObjects || 0 == (length = jsonObjects.length)) {
			return new JSONObject();
		}
		JSONObject base = jsonObjects[0];
		for (int i = 1; i < length; i++) {
			JSONObject other = jsonObjects[i];
			if (null == other) {
				continue;
			}
			for (Entry<String, Object> entry : other.entrySet()) {
				base.put(entry.getKey(), entry.getValue());
			}
		}
		return base;
	}

	public static JSONObject merge(JSONObject base, String key, Object value) {
		if (null == base) {
			base = new JSONObject();
		}
		if (null != key) {
			base.put(key, value);
		}
		return base;
	}

	public static JSONObject merge(JSONObject base, Map<String, Object> map) {
		if (null == base) {
			base = new JSONObject();
		}
		if (null != map && !map.isEmpty()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				base.put(entry.getKey(), entry.getValue());
			}
		}
		return base;
	}

	public static JSONObject merge(JSONObject base, Properties prop) {
		if (null == base) {
			base = new JSONObject();
		}
		if (null != prop && !prop.isEmpty()) {
			for (Entry<Object, Object> entry : prop.entrySet()) {
				base.put((String) entry.getKey(), entry.getValue());
			}
		}
		return base;
	}

	public static boolean equals(JSONObject o, JSONObject ao) {
		if (null == o) {
			return null == ao;
		}
		return o.equals(ao);
	}

	public static boolean equals(JSONArray o, JSONArray ao) {
		if (null == o) {
			return null == ao;
		}
		return o.equals(ao);
	}

	public static boolean equals(JSONObject o, JSONObject ao, String[] keys) {
		if (null == o) {
			return null == ao;
		}
		if (o.equals(ao)) {
			return true;
		}
		if (null == keys || 0 == keys.length) {
			return true;
		}
		for (String key : keys) {
			Object ov = o.get(key);
			Object aov = ao.get(key);
			if (null == ov) {
				if (null != aov) {
					return false;
				}
			} else {
				if (null == aov) {
					return false;
				} else {
					if (!ov.equals(aov)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static JSONObject clone(JSONObject o) {
		if (null == o) {
			return null;
		}
		return (JSONObject) o.clone();
	}

	public static JSONArray clone(JSONArray o) {
		if (null == o) {
			return null;
		}
		return (JSONArray) o.clone();
	}
	/**
	 * 
	 * @date 2016年7月16日
	 * @Description: 将from入参的属性赋值到 toClass对象
	 * @param from
	 * @param toClass
	 * @return T
	 */
	public static <F, T> T trans(F from, Class<T> toClass) {
		String jsonString = JSON.toJSONString(from);
		return trans(jsonString, toClass);
	}

	/**
	 * JSON字符串转实体对象
	 * 
	 * @param jsonString
	 * @param toClass
	 * @return
	 */
	public static <T> T trans(String jsonString, Class<T> toClass) {
		return JSON.parseObject(jsonString, toClass);
	}

	/**
	 * JSON字符串转List
	 * 
	 * @param jsonList
	 * @param toClass
	 * @return
	 */
	public static <T> List<T> trans2List(String jsonList, Class<T> toClass) {
		return JSON.parseArray(jsonList, toClass);
	}

	/**
	 * JSON字符串赚Map
	 * 
	 * @param jsonMap
	 * @param toClass
	 * @return
	 */
	public static <T> Map<String, T> trans2Map(String jsonMap,
			Class<T> toClass) {
		return (Map<String, T>) JSON.parse(jsonMap);
	}

	public static <T> T trans(Map<String, Object> from, Class<T> toClass) {
		JSONObject json = new JSONObject(from);
		return JSON.toJavaObject(json, toClass);
	}

	public static <T> List<T> trans(List<Map<String, Object>> fromList,
			Class<T> toElementClass) {
		int size = fromList.size();
		List<T> resultList = new ArrayList<T>(size);
		for (int i = 0; i < size; i++) {
			Map<String, Object> from = fromList.get(i);
			T element = null;
			if (null != from) {
				element = trans(from, toElementClass);
			}
			resultList.add(element);
		}
		return resultList;
	}

	/**
	 * @date 2016年7月13日
	 * @Description: 遍历修改里面的类型
	 * @param fromList
	 * @param toElementClass
	 * @return List<T>
	 */
	public static <T> List<T> transInSide(List<?> fromList,
			Class<T> toElementClass) {
		int size = fromList.size();
		List<T> resultList = new ArrayList<T>(size);
		for (int i = 0; i < size; i++) {
			T element = null;
			if (null != fromList.get(i)) {
				if (fromList.get(i).getClass() == String.class) {
					element = trans(fromList.get(i).toString(), toElementClass);
				} else {
					element = trans(fromList.get(i), toElementClass);
				}
			}
			resultList.add(element);
		}
		return resultList;
	}

}
