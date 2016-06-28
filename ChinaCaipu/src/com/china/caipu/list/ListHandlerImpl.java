package com.china.caipu.list;

import java.util.List;

import com.china.caipu.constant.Config;
import com.china.caipu.list.inter.IListHandler;
import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.db.DBCaiUtil;
import com.china.caipu.util.parser.CaipuListParser;
import com.china.caipu.vo.Cai;
import com.mk.log.LOG;
import com.mk.util.MKUtils;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
final class ListHandlerImpl implements IListHandler {

	/**
	 * http://www.chinacaipu.com/menu/guangdongecai/
	 * http://www.chinacaipu.com/menu/guangdongecai/index_2.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_3.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_4.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_5.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_6.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_7.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_8.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_9.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_10.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_11.html
	 * http://www.chinacaipu.com/menu/guangdongecai/index_12.html
	 * 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ListHandlerImpl impl = new ListHandlerImpl();
		for (int in = 0; in < 12; in++) {
			String url = impl.genUrl(in);

			if (in == 3) {
				// is OK
				String data = impl.getContent(url);
				List<Cai> list = impl.parseContent(data);
				for (Cai cai : list) {
					boolean result = impl.saveContent(cai);
					log(cai.mName + "<--->" + result);
				}
				break;
			}
		}

	}

	static void testSaveContent(ListHandlerImpl impl, Cai cai) throws Exception {
		boolean result = impl.saveContent(cai);
		log(cai.mName + "," + result);
	}

	static List<Cai> testParseContent(ListHandlerImpl impl, String data)
			throws Exception {
		// String data = getContentFromLocal(impl.getClass(), "sample1.html");
		List<Cai> list = impl.parseContent(data);
		// for (Cai cai : list) {
		// log(cai.toString());
		// }
		return list;
	}

	static String testGetContent(ListHandlerImpl impl, String url)
			throws Exception {
		// String content = impl.getContent(url);
		String content = MKUtils.getContentFromPackage(impl.getClass(),
				"sample1.html", Config.CHARSET);

		// log(content);
		return content;
	}

	static void testGetContentFromLocal() throws Exception {
		Class<?> cls = ListHandlerImpl.class;
		String data = MKUtils.getContentFromPackage(cls, "sample1.html",
				Config.CHARSET);
		log(data);
	}

	static void log(String msg) {
		LOG.D(msg);
	}

	/**
	 * is OK
	 * 
	 * @param index
	 * @return
	 */
	@Override
	public String genUrl(int index) {
		// TODO Auto-generated method stub
		// String first = "http://www.chinacaipu.com/menu/guangdongecai/";
		// String second =
		// "http://www.chinacaipu.com/menu/guangdongecai/index_2.html";
		// String third =
		// "http://www.chinacaipu.com/menu/guangdongecai/index_3.html";

		String pre = Config.URL_PRE;
		if (index == 0) {
			return pre;
		}

		pre += "index_" + (index + 1) + ".html";
		return pre;

	}

	/**
	 * is OK
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getContent(String url) throws Exception {
		// TODO Auto-generated method stub
		return RemoteUtil.getRemoteContent(url);
	}

	/**
	 * is OK
	 */
	@Override
	public List<Cai> parseContent(String data) throws Exception {
		// TODO Auto-generated method stub

		List<Cai> result = CaipuListParser.parseListHtml(data);

		return result;
	}

	/**
	 * is OK
	 * 
	 * save to DB==JDBC
	 * 
	 */
	@Override
	public boolean saveContent(Cai cai) throws Exception {
		// TODO Auto-generated method stub
		return DBCaiUtil.addCai(cai);
	}

}// end
