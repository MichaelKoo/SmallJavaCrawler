package com.china.caipu.list;

import java.util.List;

import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.db.CaiDbUtil;
import com.china.caipu.util.db.CaiXiDbUtil;
import com.china.caipu.util.parser.CaipuListParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiList;
import com.china.caipu.vo.CaiXi;

/**
 * ����ʵ��
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         AbsListHandlerImpl.java
 */
public final class AbsListHandlerImpl extends AbsListHandler {
	/**
	 * �����ݿ��ҳ����в�ϵ
	 */
	@Override
	public List<CaiXi> getCaiXi() throws Exception {
		// TODO Auto-generated method stub
		return CaiXiDbUtil.findAllCai();
	}

	/**
	 * ��ȡ��������
	 */
	@Override
	public String getContent(String url) throws Exception {
		// TODO Auto-generated method stub
		return RemoteUtil.getRemoteContent(url);
	}

	/**
	 * �������вˣ�������һҳ
	 * 
	 * @param html
	 *            {@link #getContent(String)}
	 */
	@Override
	public CaiList parseContent(String html) throws Exception {
		// TODO Auto-generated method stub
		CaiList caiList = new CaiList();
		caiList.mCaiList = CaipuListParser.parseListHtml(html);

		caiList.mNext = CaipuListParser.parseNext(html);

		return caiList;
	}

	/**
	 * �����
	 */
	@Override
	public boolean saveContent(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return CaiDbUtil.addCai(cai);
	}

	/**
	 * �����ϵ�µ����в� {@link #saveContent(Cai)}
	 */
	@Override
	public boolean handleCaiList(String caiXiID, List<Cai> list)
			throws Exception {
		// TODO Auto-generated method stub

		for (Cai cai : list) {
			cai.mCaiXiID = caiXiID;
			saveContent(cai);
		}

		return false;
	}

}// end
