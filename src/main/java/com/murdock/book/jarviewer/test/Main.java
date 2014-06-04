package com.murdock.book.jarviewer.test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 接口方法签名改变，或者接口新增方法。
 * 
 * 如果旧有实现的方法没有实现变更后的方法：
 * 如果没有被调用到，表现正常，否则AbstractMethodError.
 * 
 * 
 * @author weipeng2k 2014年6月4日 上午9:20:15
 */
public class Main {

	public static void main(String[] args) throws Exception {
		ClassLoader cl = new URLClassLoader(new URL[] { new File(
				"/Users/weipeng2k/Documents/arena/jarviewer/target/jarviewer-1.0-SNAPSHOT.jar").toURI().toURL() }) {

					@Override
					protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
						if (name.equals("com.murdock.book.jarviewer.test.Implementation")) {
							Class<?> result = findClass("com.murdock.book.jarviewer.test.Implementation");
							if (resolve) {
								resolveClass(result);
							}
							
							return result;
						}
						
						return super.loadClass(name, resolve);
					}
			
		};
		
		Interface t = new Implementation();
		System.out.println("method t:");
		for (Method m : t.getClass().getMethods()) {
			System.out.println(m);
		}
		
		t.m();
		Interface t1 = (Interface) cl.loadClass("com.murdock.book.jarviewer.test.Implementation").newInstance();
		System.out.println("method t1:");
		
		for (Method m : t1.getClass().getMethods()) {
			System.out.println(m);
		}
		
		
		t1.m();
	}

}
