package com.china.caipu;

import java.util.List;

import com.china.caipu.img.CaiImageFactory;
import com.china.caipu.img.IImageHandler;
import com.china.caipu.list.IListHandler;
import com.china.caipu.list.ListHandlerFactory;
import com.china.caipu.util.RemoteUtil;
import com.china.caipu.util.Util;
import com.china.caipu.util.db.DBCaiDetailUtil;
import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.util.parser.CaipuDetailParser;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;
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

		handleCaipuList();

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

		IImageHandler iImageHandler = CaiImageFactory.getIHandlerImg();
		List<Cai> all = iImageHandler.findAllCai();

		for (Cai cai : all) {
			String imageName = Util.getImageName(cai.mImage);

			if (!Util.isExistsImage(imageName)) {
				// InputStream input = iImageHandler.downLoadImage(cai.mImage);
				// String path = iImageHandler.saveImage(imageName, input);
				//
				// LOG.D("<---->" + path);
				// if (input != null) {
				// input.close();
				// }
				// Thread.sleep(MKUtils.genSleep());
				LOG.D("" + cai.mName);
			}

		}

		LOG.D("task over");
	}

	/**
	 * 1.从数据库读取连接，
	 * 
	 * 2.根据连接获取内容；
	 * 
	 * 3.根据内容解析存储；
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	static void getCaipuDetail() throws Exception {

		List<Cai> dataList = DBCaiListUtil.findAllCai();
		int count = dataList.size();
		int time = 0;

		for (int in = count - 1; in >= 0; in--) {
			Cai cai = dataList.get(in);
			//
			if (!DBCaiDetailUtil.findIsExists(cai.mName)) {
				handleCaipuDetail(cai.mDetail);
				//
				Thread.sleep(MKUtils.genSleep());
			} else {
				time++;
				LOG.D(cai.mName + "<---> is exists->>" + time);
			}
		}

		LOG.D("   task over  ");
	}

	/**
	 * 
	 * @param detail
	 *            detail url
	 * @throws Exception
	 */
	static void handleCaipuDetail(String detail) throws Exception {

		String data = RemoteUtil.getRemoteContent(detail);

		CaiDetail caipu = CaipuDetailParser.parseDetail(data);

		if (IsUtil.isNotNull(caipu)) {
			boolean result = DBCaiDetailUtil.addCaiDetail(caipu);
			LOG.D("over : " + detail + ":  -->" + result);
		}
	}

}// end
