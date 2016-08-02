package com.china.caipu.detail;

import java.util.List;

import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;

/**
 * 1.获取所有菜；
 * 
 * 2.是否已经存在；
 * 
 * 3.根据菜获取详细；
 * 
 * 4.解析详细；
 * 
 * 5.存储详细；
 * 
 * 
 * 
 * 先有再优化
 */
public interface IHandlerDetail {

	public List<Cai> getAllCai() throws Exception;

	public boolean isExistsDetail(Cai cai) throws Exception;

	public String getCaiDetail(Cai cai) throws Exception;

	public CaiDetail parseCaiDetail(String data) throws Exception;

	public boolean saveCaiDetail(Cai cai) throws Exception;

}// end
