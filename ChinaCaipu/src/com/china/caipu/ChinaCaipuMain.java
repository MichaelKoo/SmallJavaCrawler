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
 * 1��get the food menu list��
 * 
 * 2��get the food detail��
 * 
 * 3��get the food list image��
 * 
 * Ҫ���ݴ����
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
		// 1����ȡ��ϵ�б�
		List<CaiXi> caiXiList = listHandler.getCaiXi();

		for (CaiXi caiXi : caiXiList) {
			// 2����ȡ��ϵ�����еĲ�
			String html = listHandler.getContent(caiXi.mCaiXiSrc);
			String next = null;

			for (;;) {
				if (next != null) {// ��ȡ����һҳ�Ĳ�
					html = listHandler.getContent(next);
				}
				// 3��������
				CaiList caiList = listHandler.parseContent(html);
				// 4�������
				listHandler.handleCaiList(caiXi.mCaiXiID, caiList.mCaiList);

				// ��ȡ��һҳ
				next = caiList.mNext;

				LOG.D("next page=" + next);

				if (IsUtil.isNull(caiList.mNext)) {
					LOG.D(caiXi.mCaiXiName + " <--->���");
					break;
				}
				Thread.sleep(MKUtils.genSleep());
			}// end

			// ����һ����ϵ���
		}
		LOG.D("task over");
	}

	/**
	 * ��ʼ�����в�ϵ
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
	 * 1.��ȡ���вˣ�
	 * 
	 * 2.���ݲ˻�ȡ��ϸ��
	 * 
	 * 3.������ϸ��
	 * 
	 * 4.�洢��ϸ��
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
	 * 1���ҵ�ͼƬ��ַ��
	 * 
	 * 2������ͼƬ��
	 * 
	 * 3�����ݲ����洢ͼƬ��ַ��
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
