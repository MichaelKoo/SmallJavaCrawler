package com.china.caipu.vo;

/**
 * 
 * @author {Michael Koo ,Email:MK520VIP@163.com}
 * 
 *         2016-3-29
 */
public class CaiDetail {
	
	private static final String SEPARATOR = ",";
	public String mName;
	public String mDescrip;
	public String mDetail;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mName);
		sb.append(SEPARATOR);
		sb.append(mDescrip);
		sb.append(SEPARATOR);
		sb.append(mDetail);

		return sb.toString();
	}
}
