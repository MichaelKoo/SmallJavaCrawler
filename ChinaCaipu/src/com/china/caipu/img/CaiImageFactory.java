package com.china.caipu.img;

import java.lang.reflect.Proxy;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
public final class CaiImageFactory {

	/**
	 * 
	 * @return real IHandlerImg instance
	 */
	public static IImageHandler getIHandlerImg() {
		
		IImageHandler handler = (IImageHandler) Proxy.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class[] { IImageHandler.class }, new CaiImageInvocationHandler());

		return handler;
	}

	private CaiImageFactory() {

	}

}// end
