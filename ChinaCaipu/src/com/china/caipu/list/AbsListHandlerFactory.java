package com.china.caipu.list;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public final class AbsListHandlerFactory {
	
	/**
	 * @see AbsListHandlerImpl
	 * @return
	 */
	public static AbsListHandler getHandler() {
		return new AbsListHandlerImpl();
	}
}// end
