package com.china.caipu;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.detail.IHandlerDetail;
import com.china.caipu.detail.IHandlerDetailFactory;
import com.china.caipu.img.ImageFactory;
import com.china.caipu.img.IImageHandler;
import com.china.caipu.list.IListHandler;
import com.china.caipu.list.ListHandlerFactory;
import com.china.caipu.util.Util;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.log.LOG;
import com.mk.util.MKUtils;

/**
 * 1、get the food menu list；
 * 
 * 2、get the food detail；
 * 
 * 3、get the food list image；
 * 
 * 
 * 
 * @author {Mark Sir}
 * 
 *         2016-3-31
 */
public class ChinaCaipuMain {

	public static void main(String[] args) throws Exception {

		handleCaipuImage();
	}

	/**
	 * is OK 4-9
	 * 
	 * @throws Exception
	 */
	static void handleCaipuList() throws Exception {
		IListHandler listHandler = ListHandlerFactory.getIListHandler();

		for (int page = 7; page < 8; page++) {
			// 1
			String url = listHandler.genUrl(page);
			// 2
			String data = listHandler.getContent(url);
			// 3
			List<Cai> caiList = listHandler.parseContent(data);

			// 4
			for (Cai cai : caiList) {
				boolean result = listHandler.saveContent(cai);
				LOG.D(cai.mName + "<--->" + result);
			}
			LOG.D("<--page--->" + page + "  is over");

			Thread.sleep(MKUtils.genSleep());
		}
		LOG.D("handleCaipuList   -->task over");
	}

	/**
	 * 1.获取所有菜；
	 * 
	 * 2.根据菜获取详细；
	 * 
	 * 3.解析详细；
	 * 
	 * 4.存储详细；
	 * 
	 * @throws Exception
	 */
	static void handleDetail() throws Exception {
		IHandlerDetail handler = IHandlerDetailFactory.getIHandlerDetail();
		// 1
		List<Cai> cais = handler.getAllCai();
		for (Cai cai : cais) {
			// 2
			if (!handler.isExistsDetail(cai)) {
				// 3
				String data = handler.getCaiDetail(cai);
				// 4
				CaiDetail detail = handler.parseCaiDetail(data);
				// 5
				boolean result = handler.saveCaiDetail(detail);

				LOG.D(cai.mName + "<---->" + result);

				Thread.sleep(MKUtils.genSleep());
			}
		}
		LOG.D("task over");
	}

	static String[] testImage = {
			"http://img.ugirls.com/uploads/magazine/content/aee3f9eb0d5f003d607cc8130875a393_magazine_web_m.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/1f178a82b9014a90b559c9faae773912b31bee16.jpg",
			"http://e.hiphotos.baidu.com/image/pic/item/e7cd7b899e510fb34395d1c3de33c895d0430cd1.jpg",
			"http://f.hiphotos.baidu.com/image/pic/item/242dd42a2834349bbe78c852cdea15ce37d3beef.jpg",
			"http://h.hiphotos.baidu.com/image/pic/item/3812b31bb051f819f1bab90cdfb44aed2f73e7dd.jpg"
	//
	};

	/**
	 * 1、找到图片地址；
	 * 
	 * 2、下载图片；
	 * 
	 * 3、根据菜名存储图片地址；
	 * 
	 * @throws Exception
	 */
	static void handleCaipuImage() throws Exception {

		IImageHandler imageHandler = ImageFactory.getIHandlerImg();
		// 1
		List<Cai> all = imageHandler.findAllCai();

		for (Cai cai : all) {
			// 2
			String imageName = Util.getImageName(cai.mImage);
			// 3
			if (!imageHandler.isExistsImage(imageName)) {
				// 4
				InputStream input = imageHandler.downLoadImage(cai.mImage);
				// 5
				String path = imageHandler.saveImage(imageName, input);

				LOG.D("<---->" + path);
				if (input != null) {
					input.close();
				}
				Thread.sleep(MKUtils.genSleep());
				
			}

		}

		LOG.D("task over");
	}
}// end
