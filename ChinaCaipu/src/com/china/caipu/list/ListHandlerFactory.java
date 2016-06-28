package com.china.caipu.list;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.china.caipu.list.inter.IListHandler;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
public final class ListHandlerFactory {

	/**
	 * 可以使用代理模式
	 * 
	 * @return
	 */
	public static IListHandler getIListHandler() {
		IListHandler handler = (IListHandler) Proxy.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class[] { IListHandler.class },
				new ListHandlerInvocationHandler());
		return handler;
	}

	private static class ListHandlerInvocationHandler implements
			InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return method.invoke(getObj(), args);
		}

		private static Object getObj() throws Exception {
			Class<?> cls = ListHandlerImpl.class;
			Object obj = cls.newInstance();
			return obj;
		}
	}

}// end
