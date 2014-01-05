/**
 * 
 */
package com.murdock.book.jarviewer.bootstrap.server;

import java.io.File;
import java.io.ObjectInputStream;

import org.junit.Test;

/**
 * @author weipeng2k 2014年1月4日 上午11:09:19
 */
public class SubTest {

	@Test
	public void test() throws Exception {
		File file = new File(System.getProperty("user.dir"), "stub");

		Sub sub = new Sub(file);

		System.out.println(sub);

		System.out.println(sub.getEntryClass().getClassLoader());
		sub.getInitMethod().invoke(sub.getEntryClass().newInstance(),
				new Object[] {});
	}
	
	@Test
	public void objectInputStream() throws Exception {
		System.out.println(ObjectInputStream.class.getClassLoader());
		System.out.println(sun.misc.VM.latestUserDefinedLoader());
	}
}
