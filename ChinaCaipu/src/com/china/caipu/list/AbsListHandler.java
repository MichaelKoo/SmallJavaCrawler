package com.china.caipu.list;

import java.util.List;

import com.china.caipu.list.inter.IGetContent;
import com.china.caipu.list.inter.IHandleCaiXi;
import com.china.caipu.list.inter.ISaveContent;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiList;

/**
 * 1�� ��ȡ���в�ϵ{@link #getCaiXi()}
 * 
 * 2��������ڵ�ַ��ȡhtml,{@link #getContent(String)}
 * 
 * 3�����������б�,{@link #parseContent(String)}
 * 
 * 4��{@link #handleCaiList(List)}
 * 
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public abstract class AbsListHandler implements IGetContent, IHandleCaiXi,
		ISaveContent {

	//

	/**
	 * ����CaiList
	 * 
	 * @param html
	 * @return {@link CaiList}
	 * @throws Exception
	 */
	public abstract CaiList parseContent(String html) throws Exception;

	//
	/**
	 * ����CaiList
	 * 
	 * @param caiXiID
	 *            {@link #getCaiXi()}
	 * @param list
	 *            {@link #parseContent(String)}
	 * @return
	 * @throws Exception
	 */
	public abstract boolean handleCaiList(String caiXiID, List<Cai> caiList)
			throws Exception;

}// end
