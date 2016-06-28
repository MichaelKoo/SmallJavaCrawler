package com.china.caipu.detail;

import java.util.List;

import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.db.DBCaiDetailUtil;
import com.china.caipu.util.db.DBCaiUtil;
import com.china.caipu.util.parser.CaipuDetailParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-9
 */
final class HandlerDetailImpl implements IHandlerDetail {

	@Override
	public List<Cai> getAllCai() throws Exception {
		// TODO Auto-generated method stub
		return DBCaiUtil.findAllCai();
	}

	/**
	 * 是否已经存在
	 */
	@Override
	public boolean isExistsDetail(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return DBCaiDetailUtil.findIsExists(cai.mName);
	}

	@Override
	public String getCaiDetail(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		if (IsUtil.isNull(cai) || IsUtil.isNull(cai.mDetail)) {
			return null;
		}
		return RemoteUtil.getRemoteContent(cai.mDetail);
	}

	@Override
	public CaiDetail parseCaiDetail(String data) throws Exception {
		// TODO Auto-generated method stub
		return CaipuDetailParser.parseDetail(data);
	}

	@Override
	public boolean saveCaiDetail(CaiDetail detail) throws Exception {
		// TODO Auto-generated method stub
		return DBCaiDetailUtil.addCaiDetail(detail);
	}

}// end
