package com.china.caipu.util;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.log.LOG;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-5
 */
public final class Util {

	/**
	 * 
	 * @param packageName
	 * @param fileName
	 * @return
	 */
	public static String genPath(String packageName, String fileName) {

		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(packageName.replace(".", "/"));
		sb.append("/");
		sb.append(fileName);

		return sb.toString();
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
