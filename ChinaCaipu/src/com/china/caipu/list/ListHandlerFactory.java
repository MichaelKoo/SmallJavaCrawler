package com.china.caipu.list;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
public final class ListHandlerFactory {
	
	/**
	 * 可以使用代理模式
	 * 
	 * @return
	 */
	public static IListHandler getIListHandler() {
		return new ListHandlerImpl();
	}
	
}//end
