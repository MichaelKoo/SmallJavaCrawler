package com.china.caipu.img;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

final class ImageInvocationHandler implements InvocationHandler {
	public ImageInvocationHandler() {

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		return method.invoke(getObj(), args);
	}

	private static Object getObj() {
		Class<?> cls = ImageHandlerImpl.class;
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
