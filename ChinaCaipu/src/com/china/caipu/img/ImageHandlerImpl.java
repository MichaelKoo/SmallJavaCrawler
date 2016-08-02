package com.china.caipu.img;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.net.NetFactory;
import com.china.caipu.util.Util;
import com.china.caipu.util.db.CaiDbUtil;
import com.china.caipu.vo.Cai;
import com.mk.IsUtil;
import com.mk.util.MKUtils;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         IImageHandlerImpl.java
 */
public final class ImageHandlerImpl implements IImageHandler {

	public ImageHandlerImpl() {

	}

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
		return CaiDbUtil.findAllCai();
	}

	/**
	 * @see Util#getImageName(String)
	 */
	@Override
	public String getImageName(String url) throws Exception {
		// TODO Auto-generated method stub
		return Util.getImageName(url);
	}

	@Override
	public boolean isExistsImage(String name) throws Exception {
		// TODO Auto-generated method stub
		return Util.isExistsImage(name);
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
		if (IsUtil.isNull(url)) {
			throw new NullPointerException(" url is null");
		}

		return NetFactory.getINetHandler().doGet(url, null);
	}

	/*
	 * save the image from input,return real path
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.china.caipu.img.IHandlerImg#saveImage(java.io.InputStream)
	 */
	@Override
	public String saveImage(String name, String path, InputStream input)
			throws Exception {
		// TODO Auto-generated method stub
		return MKUtils.saveLocal(name, path, input);
	}

}// end
