package com.china.caipu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import com.china.caipu.vo.Cai;
import com.mk.util.MKUtils;

/**
 * getLocal,saveLocal
 * 
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
final class LocalAbstractData extends AbstractData {

	/**
	 * 
	 * 
	 * @param data
	 * @param pathName
	 *            D://test.html
	 * @throws Exception
	 */
	public static String saveLocal(String data, String pathName, boolean append)
			throws Exception {
		File file = new File(pathName);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, append)));
		bw.write(data);
		bw.close();

		return pathName;
	}

	/**
	 * it is test data for index_2.htm
	 * 
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static String getLocal(Class<?> cls) throws Exception {

		return getLocal(cls, MKUtils.getPackageName(cls), "index_2.htm");
	}

	/**
	 * 
	 * @param cls
	 * @param packageName
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getLocal(Class<?> cls, String packageName,
			String fileName) throws Exception {
		InputStream input = cls.getResourceAsStream(genPath(packageName,
				fileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,
				"utf-8"), 16 * 1024);
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		while ((tmp = reader.readLine()) != null) {
			sb.append(tmp);
		}
		input.close();
		return sb.toString();
	}

	/**
	 * 
	 * @param packageName
	 * @param fileName
	 * @return
	 */
	private static String genPath(String packageName, String fileName) {

		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(packageName.replace(".", "/"));
		sb.append("/");
		sb.append(fileName);

		return sb.toString();
	}

	@Override
	public boolean saveContent(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getContent(Object obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContent(Class<?> obj) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContent(Class<?> cls, String packageName, String fileName)
			throws Exception {
		return null;
	}

	@Override
	public List<Cai> parseListHtml(String data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}// end
