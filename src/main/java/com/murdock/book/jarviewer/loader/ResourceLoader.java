package com.murdock.book.jarviewer.loader;

import java.net.URL;

public abstract class ResourceLoader {
	/**
	 * @see java.lang.ClassLoader#loadClass(java.lang.String)
	 */
	public static Class<?> loadClass(final String name)
			throws ClassNotFoundException {
		final ClassLoader loader = ClassLoaderResolver.getClassLoader(1);

		return Class.forName(name, false, loader);
	}

	/**
	 * @see java.lang.ClassLoader#getResource(java.lang.String)
	 */
	public static URL getResource(final String name) {
		final ClassLoader loader = ClassLoaderResolver.getClassLoader(1);

		if (loader != null)
			return loader.getResource(name);
		else
			return ClassLoader.getSystemResource(name);
	}
} // End of class
