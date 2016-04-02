package com.china.caipu;

import java.util.List;

import com.china.caipu.util.ChinaCaipu;
import com.china.caipu.util.db.DBCaiDetailUtil;
import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.util.parser.CaipuDetailParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;
import com.mk.log.LOG;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-3-31
 */
public class ChinaCaipuMain {

	public static void main(String[] args) throws Exception {

	}

	/**
	 * �����б�ҳ
	 * 
	 * @throws Exception
	 */
	public void getCaipuList() throws Exception {

		for (int in = 0; in < 11; in++) {
			// 1����ȡURL
			String url = ChinaCaipu.genUrl(in);

			// 2����ȡ��ҳ����
			String data = ChinaCaipu.getContent(url);

			// 3��������ҳ����
			List<Cai> datas = ChinaCaipu.parseListHtml(data);

			// 4���洢����
			for (Cai cai : datas) {
				boolean result = ChinaCaipu.saveContent(cai);
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
	public void getCaipuDetail() throws Exception {

		List<Cai> dataList = DBCaiListUtil.findAllCai();
		int count = dataList.size();
		StringBuilder sb = new StringBuilder();

		for (int in = count - 1 - 100; in >= 0; in--) {
			Cai cai = dataList.get(in);

			String data = ChinaCaipu.getRemoteContent(cai.mDetail);

			CaiDetail caipu = CaipuDetailParser.parseDetail(data);

			if (IsUtil.isNotNull(caipu)) {
				// LOG.D("" + caipu);
				boolean result = DBCaiDetailUtil.addCaiDetail(caipu);
				LOG.D("over : " + cai.mName + ":  -->" + result);

				sb.append(cai.mName).append(",").append(result);

				ChinaCaipu.saveLocal(sb.toString(),
						"D://getCaipuDetailResult.csv", true);
			}

			//
			Thread.sleep(genSleep());

		}

		LOG.D("   task over  ");
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
