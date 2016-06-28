package com.china.caipu.list;

import java.util.List;

import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.db.DBCaiUtil;
import com.china.caipu.util.db.DBCaiXiUtil;
import com.china.caipu.util.parser.CaipuListParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiList;
import com.china.caipu.vo.CaiXi;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public final class AbsListHandlerImpl extends AbsListHandler {
	@Override
	public List<CaiXi> getCaiXi() throws Exception {
		// TODO Auto-generated method stub
		return DBCaiXiUtil.findAllCai();
	}

	@Override
	public String getContent(String url) throws Exception {
		// TODO Auto-generated method stub
		return RemoteUtil.getRemoteContent(url);
	}

	/**
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

	@Override
	public boolean saveContent(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return DBCaiUtil.addCai(cai);
	}

	/**
	 * return ignore
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
