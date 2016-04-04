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
	 * 解析列表页
	 * 
	 * @throws Exception
	 */
	static void getCaipuList() throws Exception {
		boolean isLocal = false;
		for (int in = 11; in < 12; in++) {
			// 1、获取URL
			String url = ChinaCaipu.genUrl(in);

			// 2、获取网页内容
			String data = ChinaCaipu.getInstance(isLocal).getCaipuContent(url);

			// 3、解析网页内容
			List<Cai> datas = ChinaCaipu.getInstance(isLocal).parseList(data);

			// 4、存储内容
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
	 * 1.从数据库读取连接，
	 * 
	 * 2.根据连接获取内容；
	 * 
	 * 3.根据内容解析存储；
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	static void getCaipuDetail() throws Exception {

		List<Cai> dataList = DBCaiListUtil.findAllCai();
		int count = dataList.size();

		boolean isLocal = false;

		for (int in = count - 1 - 100; in >= 0; in--) {
			Cai cai = dataList.get(in);

			handleCaipuDetail(cai.mDetail, isLocal);

			//
			Thread.sleep(genSleep());

		}

		LOG.D("   task over  ");
	}

	/**
	 * 
	 * @param detail
	 * @param isLocal
	 * @throws Exception
	 */
	static void handleCaipuDetail(String detail, boolean isLocal)
			throws Exception {

		String data = ChinaCaipu.getInstance(isLocal).getCaipuRemoteContent(
				detail);

		CaiDetail caipu = CaipuDetailParser.parseDetail(data);

		if (IsUtil.isNotNull(caipu)) {

			boolean result = DBCaiDetailUtil.addCaiDetail(caipu);
			LOG.D("over : " + detail + ":  -->" + result);

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
