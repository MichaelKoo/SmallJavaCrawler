package com.china.caipu.list;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
public final class ListHandlerFactory {
	
	/**
	 * ����ʹ�ô���ģʽ
	 * 
	 * @return
	 */
	public static IListHandler getIListHandler() {
		return new ListHandlerImpl();
	}
	
}//end
