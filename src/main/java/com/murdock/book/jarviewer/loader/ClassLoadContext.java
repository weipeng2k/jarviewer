package com.murdock.book.jarviewer.loader;

public class ClassLoadContext {
	public final Class<?> getCallerClass() {
		return m_caller;
	}

	ClassLoadContext(final Class<?> caller) {
		m_caller = caller;
	}

	private final Class<?> m_caller;
} // End of class