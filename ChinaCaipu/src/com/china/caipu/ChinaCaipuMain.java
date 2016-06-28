package com.china.caipu;

import java.io.InputStream;
import java.util.List;

import com.china.caipu.constant.Config;
import com.china.caipu.detail.IHandlerDetail;
import com.china.caipu.detail.IHandlerDetailFactory;
import com.china.caipu.img.IImageHandler;
import com.china.caipu.img.ImageFactory;
import com.china.caipu.list.AbsListHandler;
import com.china.caipu.list.AbsListHandlerFactory;
import com.china.caipu.util.Util;
import com.china.caipu.util.db.DBCaiXiUtil;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.china.caipu.vo.CaiList;
import com.china.caipu.vo.CaiXi;
import com.mk.IsUtil;
import com.mk.log.LOG;
import com.mk.util.MKUtils;
import com.mk.util.PathUtil;

/**
 * 1、get the food menu list；
 * 
 * 2、get the food detail；
 * 
 * 3、get the food list image；
 * 
 * 要有容错机制
 * 
 * @author {Mark Sir}
 * 
 *         2016-3-31
 */
public class ChinaCaipuMain {

	public static void main(String[] args) throws Exception {
			
	}	

	/**
	 * 
	 * @throws Exception
	 */
	static void handleCaiList() throws Exception {
		AbsListHandler listHandler = AbsListHandlerFactory.getHandler();
		// 1、获取菜系列表
		List<CaiXi> caiXiList = listHandler.getCaiXi();

		for (CaiXi caiXi : caiXiList) {
			// 2、获取菜系下所有的菜
			String html = listHandler.getContent(caiXi.mCaiXiSrc);
			String next = null;

			for (;;) {
				if (next != null) {// 获取其中一页的菜
					html = listHandler.getContent(next);
				}
				// 3、解析菜
				CaiList caiList = listHandler.parseContent(html);
				// 4、保存菜
				listHandler.handleCaiList(caiXi.mCaiXiID, caiList.mCaiList);

				// 获取下一页
				next = caiList.mNext;

				LOG.D("next page=" + next);

				if (IsUtil.isNull(caiList.mNext)) {
					LOG.D(caiXi.mCaiXiName + " <--->完成");
					break;
				}
				Thread.sleep(MKUtils.genSleep());
			}// end

			// 其中一个菜系完成
		}
		LOG.D("task over");
	}

	/**
	 * 初始化所有菜系
	 * 
	 * @throws Exception
	 */
	static void handleCaiXi() throws Exception {
		String[] names = Config.CaiXiSrc.CAIXI_NAMES;
		String[] urls = Config.CaiXiSrc.CAIXI_URLS;

		for (int in = 0; in < names.length; in++) {
			CaiXi cx = new CaiXi();
			cx.mCaiXiID = Util.genUUID();
			cx.mCaiXiName = names[in];
			cx.mCaiXiSrc = urls[in];

			DBCaiXiUtil.addCai(cx);
		}
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
	static boolean handleDetail() throws Exception {
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
		return true;
	}

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
		int count = 0;
		for (Cai cai : all) {
			// 2,
			String imageName = imageHandler.getImageName(cai.mImage);
			// 3
			if (!imageHandler.isExistsImage(imageName)) {
				try {
					// 4
					InputStream input = imageHandler.downLoadImage(cai.mImage);
					// 5
					String path = imageHandler.saveImage(imageName,
							PathUtil.getImagePath(), input);

					LOG.D("<---->" + path);
					if (input != null) {
						input.close();
					}
					Thread.sleep(MKUtils.genSleep());
				} catch (Exception e) {
					LOG.D("" + e.getMessage());
				}

			} else {
				count++;
			}
		}

		LOG.D("task over-->" + count);
	}
}// end
