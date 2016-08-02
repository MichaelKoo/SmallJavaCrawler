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
import com.china.caipu.util.db.CaiDbUtil;
import com.china.caipu.util.db.CaiXiDbUtil;
import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.china.caipu.vo.CaiList;
import com.china.caipu.vo.CaiXi;
import com.mk.IsUtil;
import com.mk.log.LOG;
import com.mk.util.MKUtils;
import com.mk.util.PathUtil;

/**
 * 1、初始化菜系；
 * 
 * 2、获取菜系的所有菜；
 * 
 * 3、处理菜的详细；
 * 
 * 4、处理菜的图片;
 * 
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         ChinaCaipuMain.java
 */
public class ChinaCaipuMain {

	public static void main(String[] args) throws Exception {

		if (initCaiXi()) {
			if (handleCaiList()) {
				if (handleCaiDetail()) {
					handleCaiImage();
				}
			}
		}
	}

	/**
	 * 初始化所有菜系
	 * 
	 * @throws Exception
	 */
	static boolean initCaiXi() throws Exception {
		String[] names = Config.CaiXiSrc.CAIXI_NAMES;
		String[] urls = Config.CaiXiSrc.CAIXI_URLS;

		for (int in = 0; in < names.length; in++) {
			CaiXi cx = new CaiXi();
			cx.mCaiXiID = Util.genUUID();
			cx.mCaiXiName = names[in];
			cx.mCaiXiSrc = urls[in];

			CaiXiDbUtil.addCai(cx);
		}
		LOG.D("菜系初始化完成");

		return true;
	}

	/**
	 * 1、获取菜系列表
	 * 
	 * 2、获取菜系下所有的菜
	 * 
	 * 3、解析菜
	 * 
	 * 4、保存菜
	 * 
	 * 5 、获取下一页
	 * 
	 * @throws Exception
	 */
	static boolean handleCaiList() throws Exception {
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

				// 4、保存该菜系在本页中的菜
				listHandler.handleCaiList(caiXi.mCaiXiID, caiList.mCaiList);

				// 获取下一页
				next = caiList.mNext;

				LOG.D("next page=" + next);

				if (IsUtil.isNull(caiList.mNext)) {
					LOG.D(caiXi.mCaiXiName + "  菜系 完成");
					break;
				}

				Thread.sleep(MKUtils.genSleep());

			}// 本菜系完成

		}// 所有菜系完成

		LOG.D("获取菜系下属菜列表完成");

		return true;
	}

	/**
	 * 1.获取所有菜；
	 * 
	 * 2.根据菜获取菜的详细
	 * 
	 * 3.解析详细；
	 * 
	 * 4.存储详细；
	 * 
	 * @throws Exception
	 */
	static boolean handleCaiDetail() throws Exception {
		IHandlerDetail handler = IHandlerDetailFactory.getIHandlerDetail();
		// 1
		List<Cai> cais = handler.getAllCai();
		for (Cai cai : cais) {

			// 2
			String data = handler.getCaiDetail(cai);
			if (IsUtil.isNull(data)) {
				continue;
			}
			// 3
			CaiDetail detail = handler.parseCaiDetail(data);
			if (IsUtil.isNull(detail) || IsUtil.isNull(detail.mDetail)) {
				continue;
			}
			cai.mDetail = detail.mDetail;
			cai.mDetailDescrip = detail.mDescrip;
			// 4
			boolean result = handler.saveCaiDetail(cai);

			LOG.D(cai.mName + "<---->" + result);

			Thread.sleep(MKUtils.genSleep());
		}

		LOG.D("处理菜的详细完成");

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
	static void handleCaiImage() throws Exception {

		IImageHandler imageHandler = ImageFactory.getIHandlerImg();
		// 1
		List<Cai> all = imageHandler.findAllCai();
		for (Cai cai : all) {
			// 2,
			LOG.D("" + cai.mImage);
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

			}
		}

	}
}// end
