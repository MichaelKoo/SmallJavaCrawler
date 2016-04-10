package com.china.caipu.img;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 1、查找所有菜谱；
 * 
 * 2、获取图片名；
 * 
 * 3、判断是否存在；
 * 
 * 4、下载图片；
 * 
 * 5、存储图片；
 * 
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
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getImageName(String url) throws Exception;

	/**
	 * 
	 * @param name
	 *            {@link #getImageName()} image name
	 * @return
	 * @throws Exception
	 */
	public boolean isExistsImage(String name) throws Exception;

	/**
	 * @param url
	 *            image path
	 * @return
	 * @throws Exception
	 */
	public InputStream downLoadImage(String url) throws Exception;

	/**
	 * @param input
	 *            image InputStream
	 * @param path
	 *            file path
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public String saveImage(String name, String path, InputStream input)
			throws Exception;

}// end
