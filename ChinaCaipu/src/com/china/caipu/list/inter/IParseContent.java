package com.china.caipu.list.inter;

import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public interface IParseContent {
	/**
	 * 
	 * @param data
	 *            {@link #getContent(String)}
	 * @return
	 * @throws Exception
	 */
	public List<Cai> parseContent(String data) throws Exception;

}
