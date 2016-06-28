package com.china.caipu.vo;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
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
		StringBuffer sb = new StringBuffer();
		sb.append(mName).append(SEPARATOR);
		sb.append(mDescrip).append(SEPARATOR);
		sb.append(mDetail).append(SEPARATOR);
		sb.append(mImage).append(SEPARATOR);

		return sb.toString();

	}

}// end
