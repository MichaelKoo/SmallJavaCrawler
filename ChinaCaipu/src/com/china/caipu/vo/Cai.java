package com.china.caipu.vo;

/**
 * 
 * @author {Michael Koo ,Email:MK520VIP@163.com}
 * 
 *         2016-3-9
 */
public class Cai {
	private static final String SEPARATOR = ",";

	public String mName;
	public String mDetail;
	public String mImage;
	public String mDescrip;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(mName).append(SEPARATOR);
		sb.append(mDescrip).append(SEPARATOR);
		sb.append(mDetail).append(SEPARATOR);
		sb.append(mImage);
		return sb.toString();

	}

}// end
