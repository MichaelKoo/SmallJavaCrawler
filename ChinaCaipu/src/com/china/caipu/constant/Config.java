package com.china.caipu.constant;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
public final class Config {
	/** for DB table query all*/
	public static final String ALL = "*";

	public static final int TIME_OUT = 15 * 1000;

	public static final String URL_PRE = "http://www.chinacaipu.com/menu/guangdongecai/";

	public static final String CHARSET = "UTF-8";

	public static final String[] ACCEPT = {
			"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"//
			, "*/*"//
			, "text/html,application/html"//
			, "text/html"//
	};

	public static final String[] USER_AGENT = {
			"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0",//
			"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; en) Opera 9.51",//
			" Mozilla/5.0 (Windows NT 6.0; U; en; rv:1.8.1) Gecko/20061208 Firefox/2.0.0 Opera 9.51",//
			"Opera/9.51 (Windows NT 5.1; U; en)",//
			"Mozilla/5.0 (compatible; Konqueror/3.2; FreeBSD) (KHTML, like Gecko)",//
			"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13"
	//
	};
}// end
