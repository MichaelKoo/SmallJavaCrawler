package com.china.caipu.util;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mk.log.LOG;
import com.mk.util.PathUtil;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-5
 */
public final class Util {
	
	public static boolean isNull(Object obj) {
		if (obj == null || "".equals(obj) || "null".equals(obj)
				|| " ".equals(obj)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param rs
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Object resultSetParseVO(ResultSet rs, Class<?> cls)
			throws Exception {
		Object obj = cls.newInstance();
		Field[] fs = cls.getDeclaredFields();
		for (Field field : fs) {
			String name = field.getName();
			String value = rs.getString(rs.findColumn(name));
			field.set(obj, value);
		}
		return obj;
	}

	/**
	 * 
	 * @return
	 */
	public static String genUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * is exists image
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isExistsImage(String name) {
		File file = new File(PathUtil.getImagePath() + name);
		return file.exists();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getImageName(String url) {
		int beginIndex = url.lastIndexOf("/");
		String name = url.substring(beginIndex, url.length());
		return name;
	}

	/**
	 * 
	 * @param map
	 */
	public static void printMap(Map<String, List<String>> map) {

		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<String> value = map.get(key);
			for (String data : value) {
				LOG.D(data);
			}
		}
	}
}// end
