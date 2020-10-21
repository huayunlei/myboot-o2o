package com.ihomefnt.o2o.intf.manager.util.common.secure;

import com.ihomefnt.o2o.intf.manager.exception.IhomeSecureException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;


public class SoapUtil {

	/**
	 * 获取参与的签名数据
	 * @param obj
	 * @return
	 */
	public static String getSignData(Object obj)  throws  IhomeSecureException {
		StringBuilder sb = new StringBuilder();
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (Class<?> cls = obj.getClass(); cls != null; cls = cls.getSuperclass()) {
				try {
					Field[] fields = cls.getDeclaredFields();

					for (Field f : fields) {
						if (f.isAnnotationPresent(ReqField.class) && f.getAnnotation(ReqField.class).joinSign()) {
							String propertyName = f.getName();
							String propertyValue = getFieldValue(f, obj);
							if (propertyValue == null || "".equals(propertyValue)) {
								continue;
							}
							map.put(propertyName, propertyValue);
						}
					}
				} catch (Throwable ignored) {
					throw  new IhomeSecureException( "获取签名数据异常 "  ,ignored)  ;
				}
			}

			List<String> keys = new ArrayList<String>(map.keySet());
			// 字段排序
			Collections.sort(keys);

			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				String value = map.get(key);
				if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
					sb.append(key).append("=").append(value);
				} else {
					sb.append(key).append("=").append(value).append("&");
				}

			}
		} catch (Exception e) {
			throw  new IhomeSecureException( "获取签名数据异常 "  ,e)  ;
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getFieldValue(Field f, Object obj) throws  Exception {
		Class clazz = f.getType();
		f.setAccessible(true);
		if (clazz == String.class) {
			return (String) f.get(obj);
		}
		if (clazz == Long.class) {
			return String.valueOf(f.getLong(obj));
		} else if (clazz == int.class) {
			return String.valueOf(f.getInt(obj));
		} else if (clazz == BigDecimal.class) {
			Object o = f.get(obj);
			return o.toString();
		} else if (clazz == List.class) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			List<Object> lists = (List<Object>) f.get(obj);
			if (lists != null) {
				for (int i = 0; i < lists.size(); i++) {
					Object o = lists.get(i);
					sb.append("{" + toJsonString(o) + "}");
					if (i != lists.size() - 1) {
						sb.append(",");
					}
				}
			}
			sb.append("]");
			return sb.toString();
		} else {
			Object o = f.get(obj);
			System.out.println(o);
			return toJsonString(f.get(obj));
		}
	}

	public static String toJsonString(Object obj)  throws  Exception {
		JSONObject json = new JSONObject();
			Field[] fields = obj.getClass().getFields();
			Map<String, String> map = new HashMap<String, String>();
			for (Field f : fields) {
				if (f.isAnnotationPresent(ReqField.class)) {
					String propertyName = f.getName();
					String propertyValue = null;
					propertyValue = SoapUtil.getFieldValue(f, obj);
					if (propertyValue == null || "".equals(propertyValue)) {
						continue;
					}
					map.put(propertyName, propertyValue);
				}
			}
			if (!map.isEmpty()) {
				Set<Map.Entry<String, String>> entries = map.entrySet();
				for (Iterator<Map.Entry<String, String>> itor = entries.iterator(); itor.hasNext();) {
					Map.Entry<String, String> entry = itor.next();
					json.put(entry.getKey(), entry.getValue());
				}
			}
		   return json.toString();
	}

}
