/**
 * 
 */
package com.murdock.book.jarviewer.loader;

import org.junit.Test;

/**
 * @author weipeng2k 2014年1月5日 上午12:05:04
 */
public class LoaderTest {
	@Test
	public void test() throws Exception {
		System.out.println(ResourceLoader
				.loadClass("com.murdock.book.jarviewer.loader.LoaderTest$T"));
	}

	public static class T {

	}
}
