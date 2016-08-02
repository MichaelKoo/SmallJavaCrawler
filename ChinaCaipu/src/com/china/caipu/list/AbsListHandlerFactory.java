package com.china.caipu.list;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         AbsListHandlerFactory.java
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
