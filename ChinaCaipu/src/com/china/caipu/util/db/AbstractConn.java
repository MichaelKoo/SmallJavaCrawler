package com.china.caipu.util.db;

import java.sql.Connection;

/**
 * 
 * @author {Michael Koo ,Email:MK520VIP@163.com}
 * 
 *         2016-3-26
 */
abstract class AbstractConn {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract Connection getConnection() throws Exception;

}// end
