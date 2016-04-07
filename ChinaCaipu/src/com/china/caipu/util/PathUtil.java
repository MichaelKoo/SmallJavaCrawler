package com.china.caipu.util;

import java.net.URL;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-5
 */
public final class PathUtil {

	/**
	 * 
	 * @return 
	 */
	public static String getImagePath() {
		String projectPath = getProjectPath();
		projectPath += "image/";
		return projectPath;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL url = cl.getResource("");
		String path = url.getPath();
		int endIndex = path.lastIndexOf("bin");
		path = path.substring(0, endIndex);
		return path;
	}
}// end
