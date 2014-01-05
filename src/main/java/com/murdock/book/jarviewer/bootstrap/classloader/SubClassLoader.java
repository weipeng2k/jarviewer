/**
 * 
 */
package com.murdock.book.jarviewer.bootstrap.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author weipeng2k 2014年1月3日 下午3:00:40
 */
public class SubClassLoader extends URLClassLoader {
	public SubClassLoader(URL[] urls) {
		super(urls);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class<?> result = null;
		try {
			result = findClass(name);
		} catch (Exception ex) {
		}

		if (result != null) {
			return result;
		}

		return super.loadClass(name);
	}

}
