package com.china.caipu.net;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         NetHandler.java
 */
final class NetHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(getObj(), args);
	}

	private static Object getObj() {
		Class<?> cls = NetHandlerImpl.class;
		try {
			Object obj = cls.newInstance();
			return obj;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}// end
