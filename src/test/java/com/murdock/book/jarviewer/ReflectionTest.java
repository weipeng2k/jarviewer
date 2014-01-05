package com.murdock.book.jarviewer;

import org.junit.Test;

import sun.reflect.Reflection;

/**
 * <pre>
 * Reflection.getCallerClass()，调用这个方法，或者载入这个类的Class，那么这个Class的classloader一定是用来载入当前类的。
 * 
 * </pre>
 * 
 * @author weipeng2k 2014年1月1日 下午12:00:25
 */
public class ReflectionTest {

	@Test
	public void test() {
		// AppClassLoader
		System.out.println(Reflection.getCallerClass());
		Invoke invoke = new Invoke();
		// 载入RefectionTest的classloader用来载入Invoke
		invoke.invoke(1);
	}

}
