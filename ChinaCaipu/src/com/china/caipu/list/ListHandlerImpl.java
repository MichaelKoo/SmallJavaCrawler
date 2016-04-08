package com.china.caipu.list;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.china.caipu.constant.Config;
import com.china.caipu.util.db.DBCaiListUtil;
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
		return getContentFromRemote(url);
	}

	static String getContentFromRemote(String url) throws Exception {
		String result = null;
		URL mURL = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();

		conn.connect();

		InputStream input = conn.getInputStream();
		// 需要有InputStream 转为String的方法
		result = MKUtils.stream2String(input, Config.CHARSET);

		input.close();
		conn.disconnect();
		conn = null;

		return result;
	}

	/**
	 * is OK
	 */
	@Override
	public List<Cai> parseContent(String data) throws Exception {
		// TODO Auto-generated method stub
		Document doc = Jsoup.parse(data);
		Elements w660Eles = doc.getElementsByClass("w660");
		Element w660Child = w660Eles.first();
		Elements childs = w660Child.children();
		Element c_con3Ele = childs.get(1);
		Elements c_con3Childs = c_con3Ele.children();

		//
		List<Cai> result = new ArrayList<Cai>();
		for (Element cCon3 : c_con3Childs) {
			if (cCon3.hasClass("c_conlist")) {
				Elements c_conlist = cCon3.children();
				for (Element cConListChild : c_conlist) {
					// li
					Cai cai = new Cai();
					Elements liChilds = cConListChild.children();
					for (Element liChildren : liChilds) {

						// descrip
						if (liChildren.nodeName().equals("font")
								&& liChildren.hasAttr("style")) {
							String descrip = liChildren.text();
							descrip = descrip.replace("......", "");
							// log(descrip);
							cai.mDescrip = descrip;
						}

						// image
						if (liChildren.nodeName().equals("div")
								&& liChildren.hasClass("pic")) {
							String image = liChildren.child(0).child(0)
									.attr("src");
							// log(image);
							cai.mImage = image;
						}
						// name
						if (liChildren.nodeName().equals("h3")
								&& liChildren.hasClass("htitle")) {
							Element img = liChildren.child(0);
							String name = img.text();
							// log("" + name);
							String detail = img.attr("href");
							// log(detail);
							cai.mName = name;
							cai.mDetail = detail;
						}

					}
					result.add(cai);
				}//
			}
		}

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
		return DBCaiListUtil.addCai(cai);
	}

}// end
