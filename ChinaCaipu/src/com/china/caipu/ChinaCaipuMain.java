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
 * 1����ʼ����ϵ��
 * 
 * 2����ȡ��ϵ�����вˣ�
 * 
 * 3������˵���ϸ��
 * 
 * 4������˵�ͼƬ;
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
	 * ��ʼ�����в�ϵ
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
		LOG.D("��ϵ��ʼ�����");

		return true;
	}

	/**
	 * 1����ȡ��ϵ�б�
	 * 
	 * 2����ȡ��ϵ�����еĲ�
	 * 
	 * 3��������
	 * 
	 * 4�������
	 * 
	 * 5 ����ȡ��һҳ
	 * 
	 * @throws Exception
	 */
	static boolean handleCaiList() throws Exception {
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

				// 4������ò�ϵ�ڱ�ҳ�еĲ�
				listHandler.handleCaiList(caiXi.mCaiXiID, caiList.mCaiList);

				// ��ȡ��һҳ
				next = caiList.mNext;

				LOG.D("next page=" + next);

				if (IsUtil.isNull(caiList.mNext)) {
					LOG.D(caiXi.mCaiXiName + "  ��ϵ ���");
					break;
				}

				Thread.sleep(MKUtils.genSleep());

			}// ����ϵ���

		}// ���в�ϵ���

		LOG.D("��ȡ��ϵ�������б����");

		return true;
	}

	/**
	 * 1.��ȡ���вˣ�
	 * 
	 * 2.���ݲ˻�ȡ�˵���ϸ
	 * 
	 * 3.������ϸ��
	 * 
	 * 4.�洢��ϸ��
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

		LOG.D("����˵���ϸ���");

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
