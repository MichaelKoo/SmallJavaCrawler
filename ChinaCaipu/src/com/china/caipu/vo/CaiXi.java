package com.china.caipu.vo;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public final class CaiXi {

	private static final String SEPARATOR = ",";

	public String mCaiXiID;
	public String mCaiXiName;
	public String mCaiXiSrc;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(mCaiXiID).append(SEPARATOR);
		sb.append(mCaiXiName).append(SEPARATOR);
		sb.append(mCaiXiSrc);
		
		return sb.toString();
	}

}// end
