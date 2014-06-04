package com.murdock.book.jarviewer.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void test() {
		// classLoader -> SystemClassLoader -> ExtClassLoader ->
		// BootstrapClassLoader
		ClassLoader classLoader = new URLClassLoader(new URL[] {});
		// classLoader1 -> classLoader -> SystemClassLoader -> ExtClassLoader -> BootstrapClassLoader
		ClassLoader classLoader1 = new URLClassLoader(new URL[] {}, classLoader);

		printClassLoader(classLoader);
		System.out.println("=============");
		printClassLoader(classLoader1);
	}

	private void printClassLoader(ClassLoader current) {
		while (current != null) {
			System.out.println(current);
			current = current.getParent();
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void getResource() {
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub").toURI().toURL() });
			BufferedReader br = new BufferedReader(new InputStreamReader(classLoader.getResource("test/t.txt")
					.openStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void getResources() {
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub").toURI().toURL() });

			Enumeration<URL> urls = classLoader.getResources("META-INF/MANIFEST.MF");
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				System.out.println(url);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void loadClass_super_child() {
		// Bootstrap --> Ext --> System(AppClassLoader) --> Custom Class Loader
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub/super_child.jar").toURI().toURL() });
			Class<?> clazz = Class.forName("com.murdock.test.classload.Child", true, classLoader);
			System.out.println(clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void loadClass_super_child_classloader() {
		// Bootstrap --> Ext --> System(AppClassLoader) --> Custom Class Loader
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub/super_child.jar").toURI().toURL() });
			Class<?> clazz = classLoader.loadClass("com.murdock.test.classload.Child");
			System.out.println(clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void assert_method_not_on() {
		// Bootstrap --> Ext --> System(AppClassLoader) --> Custom Class Loader
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub/assert_method.jar").toURI().toURL() });
			Class<?> clazz = classLoader.loadClass("Assert");
			Object target = clazz.newInstance();
			Method assertI = clazz.getMethod("assertI", new Class<?>[] { int.class });
			assertI.invoke(target, new Object[] { 1 });
			assertI.invoke(target, new Object[] { -1 });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void assert_method_on() {
		// Bootstrap --> Ext --> System(AppClassLoader) --> Custom Class Loader
		try {
			ClassLoader classLoader = new URLClassLoader(new URL[] { new File(
					"/Users/weipeng2k/Documents/arena/jarviewer/stub/assert_method.jar").toURI().toURL() });
			classLoader.setClassAssertionStatus("Assert", true);
			Class<?> clazz = classLoader.loadClass("Assert");
			Object target = clazz.newInstance();
			Method assertI = clazz.getMethod("assertI", new Class<?>[] { int.class });
			assertI.invoke(target, new Object[] { 1 });
			assertI.invoke(target, new Object[] { -1 });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
