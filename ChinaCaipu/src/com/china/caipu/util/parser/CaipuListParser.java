package com.china.caipu.util.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.Util;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;

/**
 * 
 * 
 * @author {Mark Sir}
 * 
 *         2016-3-31
 */
public final class CaipuListParser {

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	static CaiDetail parseCaiDetail(String url) throws Exception {
		String html = RemoteUtil.getRemoteContent(url);
		CaiDetail detail = CaipuDetailParser.parseDetail(html);
		if (detail != null) {
			return detail;
		}

		return null;
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String parseNext(String data) throws Exception {
		String result = null;
		if (IsUtil.isNull(data)) {
			return result;
		}

		Document doc = Jsoup.parse(data);
		Elements childs = doc.getAllElements();
		for (Element ele : childs) {
			if (isCorrect(ele)) {
				if (ele.hasAttr("href")) {
					result = "http://www.chinacaipu.com" + ele.attr("href");
				}
			}
		}

		return result;
	}

	private static boolean isCorrect(Element element) {
		if ("a".equals(element.nodeName()) && element.text().contains("ÏÂÒ»Ò³")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param data
	 *            html
	 * @return
	 * @throws Exception
	 */
	public static List<Cai> parseListHtml(String data) throws Exception {
		List<Cai> result = new ArrayList<Cai>();
		if (IsUtil.isNull(data)) {
			return result;
		}
		Document doc = Jsoup.parse(data);
		Elements uls = doc.getElementsByClass("c_conlist");
		if (uls != null && uls.size() > 0) {
			Elements eles = uls.get(0).children();
			int size = eles.size();
			for (int in = 0; in < size; in++) {
				Element li = eles.get(in);
				Elements liChilds = li.children();
				Cai cai = new Cai();
				cai.mCaiID = Util.genUUID();
				for (Element ele : liChilds) {// li

					if (ele.hasAttr("class") && "div".equals(ele.nodeName())) {
						//
						Elements divs = ele.getAllElements();
						for (Element divChild : divs) {
							if (divChild.hasAttr("href")
									&& "a".equals(divChild.nodeName())) {
								// href
								cai.mDetail = divChild.attr("href");

								// if (IsUtil.isNotNull(cai.mDetail)) {
								// CaiDetail cd = parseCaiDetail(cai.mDetail);
								// if (cd != null) {
								// cai.mDetail = cd.mDetail;
								// cai.mDetailDescrip = cd.mDescrip;
								// }
								// }

							}
							if (divChild.hasAttr("alt")
									&& divChild.hasAttr("src")
									&& "img".equals(divChild.nodeName())) {
								// image
								cai.mImage = divChild.attr("src");
								// name
								cai.mName = divChild.attr("alt");
							}
						}
					} else if (ele.hasAttr("style")
							&& "font".equals(ele.nodeName())) {
						// descrip
						String descrip = ele.text();
						descrip = descrip.replaceAll(" ", "");
						descrip = descrip.replace("......", "");
						descrip = descrip.trim();

						cai.mDescrip = descrip;
					}
					if (cai.mName == null && ele.hasAttr("class")
							&& "h3".equals(ele.nodeName())) {
						Element child = ele.child(0);
						cai.mName = child.text();
					}
				}

				result.add(cai);
			}// end for
		}

		return result;
	}
}
