package com.china.caipu.vo;

import java.lang.reflect.Field;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         Cai.java
 */
public final class Cai {

	private static final String SEPARATOR = ",";
	public String mCaiID;
	public String mCaiXiID;

	public String mName;
	public String mImage;

	public String mDescrip;
	public String mDetail;
	public String mDetailDescrip;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Class<?> cls = getClass();
		Field[] fs = cls.getDeclaredFields();
		try {
			for (Field field : fs) {
				Object value = field.get(this);
				sb.append(value).append(SEPARATOR);
			}

		} catch (Exception e) {

		}

		return sb.toString();
	}

}// end
