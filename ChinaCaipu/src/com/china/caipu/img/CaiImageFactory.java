package com.china.caipu.img;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
public final class CaiImageFactory {

	private IHandlerImg handler;
	private static CaiImageFactory instance;

	private CaiImageFactory() {
		handler = new IHandlerImgImpl();
	}

	public static CaiImageFactory getInstance() {
		if (instance == null) {
			instance = new CaiImageFactory();
		}
		return instance;
	}

	/**
	 * 
	 * @return all cai
	 * @throws Exception
	 */
	public List<Cai> findAllCai() throws Exception {
		if (handler != null) {
			return handler.findAllCai();
		}

		return null;
	}

	/**
	 * 
	 * @return image inputStream
	 * @throws Exception
	 */
	public InputStream downLoadImage(String url) throws Exception {
		if (handler != null) {
			return handler.downLoadImage(url);
		}
		return null;
	}

	/**
	 * 
	 * @return save path
	 * 
	 * @throws Exception
	 */
	public String saveImage(InputStream input) throws Exception {

		if (handler != null) {
			return handler.saveImage(input);
		}
		return null;
	}

}// end
