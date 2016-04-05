package com.china.caipu.net;

import java.lang.reflect.Proxy;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 * 
 * 
 *         doPost,doGet
 */
public final class NetFactory {
	
	public static INetHandler getINetHandler() {
		INetHandler handler = (INetHandler) Proxy.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class[] { INetHandler.class }, new NetHandler());

		return handler;
	}

}// end
