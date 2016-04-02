package com.china.caipu.util;

import java.util.List;

import com.china.caipu.util.parser.CaipuListParser;
import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
public abstract class AbstractData {
	/**
	 * �洢����
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public abstract <T> T saveContent(Cai cai, Object... args) throws Exception;

	/**
	 * �ӱ��ػ�ȡ���������ȡ����
	 * 
	 * @param obj
	 *            class or api
	 * @return
	 * @throws Exception
	 */
	public abstract String getContent(Object obj, Object... args)
			throws Exception;

	/**
	 * �������� {@link CaipuListParser#parseListHtml(String)}
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public List<Cai> parseListHtml(String data) throws Exception {
		// TODO Auto-generated method stub
		return CaipuListParser.parseListHtml(data);
	}
}// end
