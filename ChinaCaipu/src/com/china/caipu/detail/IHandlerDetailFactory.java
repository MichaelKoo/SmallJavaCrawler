package com.china.caipu.detail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         IHandlerDetailFactory.java
 */
public final class IHandlerDetailFactory {

	public static IHandlerDetail getIHandlerDetail() {
		IHandlerDetail handler = (IHandlerDetail) Proxy.newProxyInstance(
				ClassLoader.getSystemClassLoader(),
				new Class[] { IHandlerDetail.class },
				new IHandlerDetailInvocationHandler());

		return handler;
	}

	private static class IHandlerDetailInvocationHandler implements
			InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return method.invoke(getObj(), args);
		}

		private static Object getObj() throws InstantiationException,
				IllegalAccessException {
			Class<?> cls = HandlerDetailImpl.class;
			Object obj = cls.newInstance();
			return obj;
		}
	}
}// end
