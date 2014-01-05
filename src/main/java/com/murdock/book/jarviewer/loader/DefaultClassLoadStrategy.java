package com.murdock.book.jarviewer.loader;

public class DefaultClassLoadStrategy implements IClassLoadStrategy {
	public ClassLoader getClassLoader(final ClassLoadContext ctx) {
		final ClassLoader callerLoader = ctx.getCallerClass().getClassLoader();
		final ClassLoader contextLoader = Thread.currentThread()
				.getContextClassLoader();

		ClassLoader result;
		if (isChild(contextLoader, callerLoader)) {
			result = callerLoader;
		} else {
			result = contextLoader;
		}

		final ClassLoader systemLoader = ClassLoader.getSystemClassLoader();

		// Precaution for when deployed as a bootstrap or extension class:
		if (isChild(result, systemLoader))
			result = systemLoader;

		return result;
	}

	/**
	 * 判定ClassLoader的层级关系
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	private static boolean isChild(ClassLoader parent, ClassLoader child) {
		if (parent == null || child == null) {
			return false;
		}

		if (child == parent) {
			return true;
		}

		ClassLoader cl = null;
		while ((cl = child.getParent()) != null) {
			if (cl == parent) {
				return true;
			}
		}

		return false;
	}

} // End of class
