package com.china.caipu.detail;

import java.util.List;

import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.db.CaiDetailDbUtil;
import com.china.caipu.util.db.CaiDbUtil;
import com.china.caipu.util.parser.CaiDetailParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         HandlerDetailImpl.java
 */
final class HandlerDetailImpl implements IHandlerDetail {

	/**
	 * 查找所有没有菜详细的菜
	 */
	@Override
	public List<Cai> getAllCai() throws Exception {
		// TODO Auto-generated method stub
		return CaiDbUtil.findAllNotDetail();
	}

	/**
	 * 是否已经存在
	 */
	@Override
	public boolean isExistsDetail(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return CaiDetailDbUtil.findIsExists(cai.mName);
	}

	@Override
	public String getCaiDetail(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		if (IsUtil.isNull(cai) || IsUtil.isNull(cai.mDetail)) {
			return null;
		}
		return RemoteUtil.getRemoteContent(cai.mDetail);
	}

	/**
	 * @see CaiDetailParser#parseDetail(String)
	 */
	@Override
	public CaiDetail parseCaiDetail(String data) throws Exception {
		// TODO Auto-generated method stub
		return CaiDetailParser.parseDetail(data);
	}

	@Override
	public boolean saveCaiDetail(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return CaiDbUtil.updateCaiDetail(cai);
	}

}// end
