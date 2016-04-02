package com.china.caipu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.china.caipu.constant.Config;
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

	static LocalAbstractData instance = new LocalAbstractData();

	/**
	 * @deprecated instead of {@link #saveContent(Cai, Object...)}
	 * 
	 * @param data
	 * @param pathName
	 *            D://test.html
	 * @throws Exception
	 */
	public static String saveLocal(String data, String pathName, boolean append)
			throws Exception {
		// File file = new File(pathName);
		// if (!file.exists()) {
		// file.createNewFile();
		// }
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
		// new FileOutputStream(file, append)));
		// bw.write(data);
		// bw.close();
		//
		// return pathName;
		return instance.saveContent(null, pathName, append, data);
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
	 * @deprecated instead of {@link #getContent(Object, Object...)}
	 * 
	 * @param cls
	 * @param packageName
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String getLocal(Class<?> cls, String packageName,
			String fileName) throws Exception {
		// InputStream input = cls.getResourceAsStream(genPath(packageName,
		// fileName));
		// BufferedReader reader = new BufferedReader(new
		// InputStreamReader(input,
		// "utf-8"), 16 * 1024);
		// StringBuffer sb = new StringBuffer();
		// String tmp = null;
		// while ((tmp = reader.readLine()) != null) {
		// sb.append(tmp);
		// }
		// input.close();
		// return sb.toString();

		return instance.getContent(cls, packageName, fileName);
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

	/**
	 * args[0]=pathName,args[1]=append,args[2]=data;
	 * 
	 * @param cai
	 *            not use
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T saveContent(Cai cai, Object... args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(args[0].toString());
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file,
						Boolean.valueOf(args[1].toString()))));
		bw.write(args[2].toString());
		bw.close();

		return (T) file.getAbsolutePath();
	}

	@Override
	public String getContent(Object obj, Object... args) throws Exception {
		// TODO Auto-generated method stub
		Class<?> cls = (Class<?>) obj;
		InputStream input = cls.getResourceAsStream(genPath(args[0].toString(),
				args[1].toString()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,
				Config.CHARSET), 16 * 1024);
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		while ((tmp = reader.readLine()) != null) {
			sb.append(tmp);
		}
		input.close();
		return sb.toString();
	}

}// end
