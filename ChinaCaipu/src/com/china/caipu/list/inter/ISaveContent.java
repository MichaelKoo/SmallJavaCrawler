package com.china.caipu.list.inter;

import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public interface ISaveContent {

	/**
	 * 
	 * @param cai
	 *            {@link #parseContent(String)}
	 * @return
	 * @throws Exception
	 */
	public boolean saveContent(Cai cai) throws Exception;
	
}
