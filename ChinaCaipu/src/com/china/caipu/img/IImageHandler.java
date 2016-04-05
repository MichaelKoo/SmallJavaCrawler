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
public interface IImageHandler {

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Cai> findAllCai() throws Exception;

	/**
	 * @param url
	 *            image path
	 * @return
	 * @throws Exception
	 */
	public InputStream downLoadImage(String url) throws Exception;

	/**
	 * @param input
	 *            image inputStream
	 * @return
	 * @throws Exception
	 */
	public String saveImage(InputStream input) throws Exception;

}// end
