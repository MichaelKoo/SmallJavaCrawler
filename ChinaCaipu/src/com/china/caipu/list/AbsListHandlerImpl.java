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
 * 具体实现
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         AbsListHandlerImpl.java
 */
public final class AbsListHandlerImpl extends AbsListHandler {
	/**
	 * 从数据库找出所有菜系
	 */
	@Override
	public List<CaiXi> getCaiXi() throws Exception {
		// TODO Auto-generated method stub
		return CaiXiDbUtil.findAllCai();
	}

	/**
	 * 获取网络数据
	 */
	@Override
	public String getContent(String url) throws Exception {
		// TODO Auto-generated method stub
		return RemoteUtil.getRemoteContent(url);
	}

	/**
	 * 解析所有菜，解析下一页
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
	 * 保存菜
	 */
	@Override
	public boolean saveContent(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return CaiDbUtil.addCai(cai);
	}

	/**
	 * 保存菜系下的所有菜 {@link #saveContent(Cai)}
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
