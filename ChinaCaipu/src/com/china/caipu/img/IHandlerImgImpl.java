package com.china.caipu.img;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * real implement
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
final class IHandlerImgImpl implements IHandlerImg {

	/*
	 * find all cai from DB
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.china.caipu.img.IHandlerImg#findAllCai()
	 */
	@Override
	public List<Cai> findAllCai() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * download image for url
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.china.caipu.img.IHandlerImg#downLoadImage(java.lang.String)
	 */
	@Override
	public InputStream downLoadImage(String url) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * save the image from input,return real path
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.china.caipu.img.IHandlerImg#saveImage(java.io.InputStream)
	 */
	@Override
	public String saveImage(InputStream input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}// end
