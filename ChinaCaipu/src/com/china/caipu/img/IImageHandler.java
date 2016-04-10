package com.china.caipu.img;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 1���������в��ף�
 * 
 * 2����ȡͼƬ����
 * 
 * 3���ж��Ƿ���ڣ�
 * 
 * 4������ͼƬ��
 * 
 * 5���洢ͼƬ��
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
