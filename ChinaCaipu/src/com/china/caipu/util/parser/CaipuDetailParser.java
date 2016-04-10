package com.china.caipu.util.parser;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;

/**
 * 
 * @author {Michael Koo ,Email:MK520VIP@163.com}
 * 
 *         2016-3-29
 */
public final class CaipuDetailParser {

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static CaiDetail parseDetail(String data) throws Exception {

		Document doc = Jsoup.parse(data);
		Elements eles = doc.getElementsByClass("w640");
		if (IsUtil.isNull(eles) || eles.size() == 0) {
			return null;
		}
		Elements childs = eles.get(0).children();
		if (IsUtil.isNull(childs) || childs.size() == 0) {
			return null;
		}

		CaiDetail caipuDetail = new CaiDetail();

		for (Element ele : childs) {

			// title
			if (ele.hasClass("contit") && "h1".equals(ele.nodeName())) {
				caipuDetail.mName = ele.text();

				// descrip
			} else if (ele.hasClass("clearfix") && "div".equals(ele.nodeName())) {
				Elements descripChilds = ele.getAllElements();
				for (Element descrip : descripChilds) {
					if (descrip.hasClass("infor_fonts")) {
						caipuDetail.mDescrip = descrip.text();
					}
				}

				// detail
			} else if (ele.hasClass("content") && "div".equals(ele.nodeName())) {

				ele = doFilter(ele);
				caipuDetail.mDetail = ele.html();
			}

		}

		return caipuDetail;
	}

	/**
	 * 
	 * @param ele
	 * @return
	 */
	static Element doFilter(Element ele) {
		List<Node> list = ele.childNodes();
		for (int in = 0; in < list.size(); in++) {

			Node node = list.get(in);

			node = clearLink(node);

			// remove ad
			if (isNeedRemoveAD(node)) {
				node.remove();
			}
		}

		return ele;
	}

	static final String[] adList = { "广告", "JQ-content favpic", "相关文章",
			"去中国菜谱网" };

	/**
	 * 
	 * @param node
	 * @return
	 */
	static boolean isNeedRemoveAD(Node node) {
		String outerHtml = node.outerHtml();
		for (String ad : adList) {
			if (outerHtml.contains(ad)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * clear link
	 * 
	 * @param node
	 * @return
	 */
	static Node clearLink(Node node) {
		List<Node> childList = node.childNodes();
		for (int index = 0; index < childList.size(); index++) {
			Node child = childList.get(index);

			if ("a".equals(child.nodeName()) && child.hasAttr("href")) {
				Element childEle = (Element) child;

				// link fail
				Attributes attrs = childEle.attributes();
				List<Attribute> attrList = attrs.asList();
				for (Attribute attr : attrList) {
					childEle.removeAttr(attr.getKey());
				}
			}
		}
		return node;
	}
}// end
