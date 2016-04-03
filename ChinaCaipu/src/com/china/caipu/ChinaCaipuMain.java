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
	 * 解析列表页
	 * 
	 * @throws Exception
	 */
	void getCaipuList() throws Exception {
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
		String[] names = { "炒蛤蜊的做法", "凤凰里脊的做法", "蛤蜊氽鲫鱼", "龙井鲍鱼的做法", "长白山人参炖老鸡",
				"生嗜鸡块", "清江鱼茄子煲", "蛋心圣女果的做法", "秘制国药中华鳖", "鲍鱼四宝羹", "蒜心生鱼片",
				"香辣蟹的做法", "陈醋海蛰头", "什锦炒鱼丁", "银鱼虾干焖节瓜", "剁椒五花肉的做法", "蜜汁叉烧的做法",
				"香煎牛仔骨", "西梅卤鸡翅的做法", "鸡汤蚝油香菇", "酱蒜苗炒鱿鱼", "甜辣松子鲱鱼", "干贝蟹黄山药羹",
				"香煎黑鲷鱼（鳕鱼）", "蒜香辣味海白的做法", "辣炒蛤蜊的做法", "牡蛎的做法", "韩式辣炒鱿鱼的做法",
				"兰豆鸡丁的做法", "什锦炒通心粉的做法" };
		
		
 
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
