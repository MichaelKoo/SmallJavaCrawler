package com.china.caipu;

import java.util.List;

import com.china.caipu.util.ChinaCaipu;
import com.china.caipu.util.LocalAbstractData;
import com.china.caipu.util.db.DBCaiDetailUtil;
import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.util.parser.CaipuDetailParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;
import com.mk.log.LOG;
import com.mk.util.MKUtils;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-3-31
 */
public class ChinaCaipuMain {

	public static void main(String[] args) throws Exception {
		new ChinaCaipuMain().elevenCaipuDetail();
	}

	/**
	 * �����б�ҳ
	 * 
	 * @throws Exception
	 */
	void getCaipuList() throws Exception {
		boolean isLocal = false;
		for (int in = 11; in < 12; in++) {
			// 1����ȡURL
			String url = ChinaCaipu.genUrl(in);

			// 2����ȡ��ҳ����
			String data = ChinaCaipu.getInstance(isLocal).getCaipuContent(url);

			// 3��������ҳ����
			List<Cai> datas = ChinaCaipu.getInstance(isLocal).parseList(data);

			// 4���洢����
			for (Cai cai : datas) {
				boolean result = ChinaCaipu.getInstance(isLocal)
						.saveCaipuContent(cai);
				LOG.D(cai.mName + "=over=" + result);
			}
			//
			Thread.sleep(genSleep());
		}

		LOG.D("over");
	}

	/**
	 * 1.�����ݿ��ȡ���ӣ�
	 * 
	 * 2.�������ӻ�ȡ���ݣ�
	 * 
	 * 3.�������ݽ����洢��
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	void getCaipuDetail() throws Exception {

		List<Cai> dataList = DBCaiListUtil.findAllCai();
		int count = dataList.size();

		boolean isLocal = false;

		for (int in = count - 1 - 100; in >= 0; in--) {
			Cai cai = dataList.get(in);

			handleCaipuDetail(cai, isLocal);

			//
			Thread.sleep(genSleep());

		}

		LOG.D("   task over  ");
	}

	void elevenCaipuDetail() throws Exception {
		String[] names = { "�����۵�����", "����Ｙ������", "����������", "�������������", "����ɽ�˲����ϼ�",
				"���ȼ���", "�彭��������", "����ʥŮ��������", "���ƹ�ҩ�л���", "�����ı���", "��������Ƭ",
				"����з������", "�´׺���ͷ", "ʲ�����㶡", "����Ϻ���˽ڹ�", "�罷�廨�������", "��֭���յ�����",
				"���ţ�й�", "��÷±���������", "��������㹽", "�����糴����", "������������", "�ɱ�з��ɽҩ��",
				"�������㣨���㣩", "������ζ���׵�����", "�������۵�����", "ĵ�õ�����", "��ʽ�������������",
				"��������������", "ʲ����ͨ�ķ۵�����" };
		
		
 
	}

	static void handleCaipuDetail(Cai cai, boolean isLocal) throws Exception {
		String data = ChinaCaipu.getInstance(isLocal).getCaipuRemoteContent(
				cai.mDetail);

		CaiDetail caipu = CaipuDetailParser.parseDetail(data);

		if (IsUtil.isNotNull(caipu)) {
			// LOG.D("" + caipu);
			boolean result = DBCaiDetailUtil.addCaiDetail(caipu);
			LOG.D("over : " + cai.mName + ":  -->" + result);

			StringBuilder sb = new StringBuilder();
			sb.append(cai.mName).append(",").append(result);

			ChinaCaipu.getInstance(isLocal).saveCaipuContent(data,
					"D://getCaipuDetailResult.csv", true);
		}
	}

	/**
	 * 
	 * @return
	 */
	static long genSleep() {
		long time = (long) (Math.random() * 9) + 6;
		time = time * 1000;
		return time;
	}
}// end
