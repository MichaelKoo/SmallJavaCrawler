package com.china.caipu.img;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import com.china.caipu.net.NetFactory;
import com.china.caipu.util.PathUtil;
import com.china.caipu.util.Util;
import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.vo.Cai;
import com.mk.IsUtil;

/**
 * real implement
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
public final class IImageHandlerImpl implements IImageHandler {

	public IImageHandlerImpl() {

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
		return DBCaiListUtil.findAllCai();
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
	public String saveImage(String name, InputStream input) throws Exception {
		// TODO Auto-generated method stub
		return saveLocal(name, input);
	}

	/**
	 * 
	 * @param name
	 *            contact prefix
	 * @param input
	 * @return
	 * @throws Exception
	 */
	static String saveLocal(String name, InputStream input) throws Exception {
		String path = PathUtil.getImagePath();
		File file = new File(path + name);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		// int tmp = -1;
		// while ((tmp = input.read()) != -1) {
		// fos.write(tmp);
		// }
		byte[] buff = new byte[512];
		int tmp = -1;
		while ((tmp = input.read(buff)) != -1) {
			fos.write(buff, 0, tmp);
		}
		input.close();
		fos.close();
		//

		return file.getAbsolutePath();
	}

}// end
