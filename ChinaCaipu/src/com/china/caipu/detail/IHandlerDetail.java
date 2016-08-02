package com.china.caipu.detail;

import java.util.List;

import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;

/**
 * 1.��ȡ���вˣ�
 * 
 * 2.�Ƿ��Ѿ����ڣ�
 * 
 * 3.���ݲ˻�ȡ��ϸ��
 * 
 * 4.������ϸ��
 * 
 * 5.�洢��ϸ��
 * 
 * 
 * 
 * �������Ż�
 */
public interface IHandlerDetail {

	public List<Cai> getAllCai() throws Exception;

	public boolean isExistsDetail(Cai cai) throws Exception;

	public String getCaiDetail(Cai cai) throws Exception;

	public CaiDetail parseCaiDetail(String data) throws Exception;

	public boolean saveCaiDetail(Cai cai) throws Exception;

}// end
