package com.murdock.book.jarviewer.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import junit.framework.TestCase;

import org.junit.Test;

public class SystemClassLoaderTest {

	@Test
	public void test() {
		ClassLoader currentLoader = ClassLoader.getSystemClassLoader();
		TestCase.assertEquals(SystemClassLoaderTest.class.getClassLoader(), currentLoader);
		while (currentLoader != null) {
			System.out.println(currentLoader);
			currentLoader = currentLoader.getParent();
		}
	}

	@Test
	public void getSystemResource() {
		System.out.println(ClassLoader.getSystemResource("java/lang/Object.class"));
		System.out.println(ClassLoader.getSystemResource("com/murdock/book/jarviewer/Invoke.class"));
		System.out.println(ClassLoader.getSystemResource("META-INF/MANIFEST.MF"));
	}

	@Test
	public void getSystemResourceAsStream() {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				ClassLoader.getSystemResourceAsStream("test.properties")));
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException ex) {
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}

	@Test
	public void getResources() {
		try {
			Enumeration<URL> enumeration = ClassLoader.getSystemResources("META-INF/MANIFEST.MF");
			while (enumeration.hasMoreElements()) {
				System.out.println(enumeration.nextElement());
			}
		} catch (IOException ex) {
		}
	}
}
