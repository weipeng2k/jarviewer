/**
 * 
 */
package com.murdock.book.jarviewer;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.murdock.book.jarviewer.stub.Test;

/**
 * @author weipeng2k 2013年12月31日 下午1:29:45
 */
public class URLClassLoaderTest {
	/**
	 * 测试加载到Test，这时委派给了SystemClassLoader也就是AppClassLoader加载
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void load() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		// 构造了URLClassLoader加载stub_test.jar，同时parent是AppClassLoader
		URLClassLoader classLoader = new URLClassLoader(new URL[] { repo });
		// 加载com.murdock.book.jarviewer.stub.Test，这里Class类型，AppClassLoader已经有了，因此不会加载Test类型
		Class<?> klass = classLoader
				.loadClass("com.murdock.book.jarviewer.stub.Test");
		Method method = klass.getMethod("m", new Class<?>[] {});
		Object obj = klass.newInstance();
		method.invoke(obj, new Object[] {});
		classLoader.close();
	}

	/**
	 * Class cast error.
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void error() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		// 构造了URLClassLoader加载stub_test.jar，修改了双亲委派，优先加载自己的
		URLClassLoader classLoader = new URLClassLoader(new URL[] { repo }) {

			@Override
			public Class<?> loadClass(String name)
					throws ClassNotFoundException {
				try {
					Class<?> clazz = null;
					clazz = findClass(name);
					return clazz;
				} catch (Exception ex) {

				}
				return super.loadClass(name);
			}

		};
		// 加载Test，这里Class类型，AppClassLoader已经有了，因此不会加载Test类型
		Class<?> klass = classLoader
				.loadClass("com.murdock.book.jarviewer.stub.Test");
		try {
			// AppClassLoader看到了Test，开始尝试向其中加载Test，然后进行转型
			// appclassloader.com.murdock.book.jarviewer.stub.Test !=
			// urlclassloader.com.murdock.book.jarviewer.stub.Test
			// 转型失败
			Test test = (Test) klass.newInstance();
			System.out.println(test.getClass().getClassLoader());
		} catch (Exception ex) {
			System.out.println("WTF:");
			ex.printStackTrace();
		}
		classLoader.close();
	}

	/**
	 * 多个ClassLoader加载
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void loadChain() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		List<URLClassLoader> loaderChain = new ArrayList<URLClassLoader>();
		for (int i = 0; i < 10; i++) {
			// 构造了URLClassLoader加载stub_test.jar，修改了双亲委派，优先加载自己的
			URLClassLoader classLoader = new URLClassLoader(new URL[] { repo }) {

				@Override
				public Class<?> loadClass(String name)
						throws ClassNotFoundException {
					try {
						Class<?> clazz = null;
						clazz = findClass(name);
						return clazz;
					} catch (Exception ex) {

					}
					return super.loadClass(name);
				}

			};
			loaderChain.add(classLoader);
		}

		for (URLClassLoader classLoader : loaderChain) {
			Class<?> klass = classLoader
					.loadClass("com.murdock.book.jarviewer.stub.Test");
			Method method = klass.getMethod("m", new Class<?>[] {});
			Object obj = klass.newInstance();
			method.invoke(obj, new Object[] {});
			classLoader.close();
		}
	}

	/**
	 * findLoadedClass0，从自身classloader中加载class
	 * 
	 * @throws Exception
	 */
	@org.junit.Test
	public void findLoadedClass0() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		// 构造了URLClassLoader加载stub_test.jar，修改了双亲委派，优先加载自己的
		URLClassLoader classLoader = new URLClassLoader(new URL[] { repo }) {

			@Override
			public Class<?> loadClass(String name)
					throws ClassNotFoundException {
				try {
					Class<?> clazz = null;
					clazz = findClass(name);
					return clazz;
				} catch (Exception ex) {

				}
				return super.loadClass(name);
			}

		};
		// 加载Test，这里Class类型，AppClassLoader已经有了，因此不会加载Test类型
		classLoader.loadClass("com.murdock.book.jarviewer.stub.Test");

		// 反射获取ClassLoader定义的findLoadedClass0这个私有native方法
		Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass0",
				new Class<?>[] { java.lang.String.class });
		method.setAccessible(true);

		// 调用AppClassLoader中的findLoadedClass0
		Class<?> clazz = (Class<?>) method.invoke(
				URLClassLoaderTest.class.getClassLoader(),
				new Object[] { "com.murdock.book.jarviewer.stub.Test" });
		System.out.println(URLClassLoaderTest.class.getClassLoader() + " find "
				+ clazz);
		clazz = (Class<?>) method.invoke(classLoader,
				new Object[] { "com.murdock.book.jarviewer.stub.Test" });
		System.out.println(classLoader + " find " + clazz);
		classLoader.close();
	}

	@org.junit.Test
	public void loadChainParent() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		List<URLClassLoader> loaderChain = new ArrayList<URLClassLoader>();
		for (int i = 0; i < 10; i++) {

			URLClassLoader classLoader = null;
			if (i == 0) {
				classLoader = new URLClassLoader(new URL[] { repo },
						URLClassLoaderTest.class.getClassLoader());

			} else {
				classLoader = new URLClassLoader(new URL[] { repo },
						loaderChain.get(i - 1));
			}
			loaderChain.add(classLoader);
		}

		for (URLClassLoader classLoader : loaderChain) {
			Class<?> klass = classLoader
					.loadClass("com.murdock.book.jarviewer.stub.Test");
			Method method = klass.getMethod("m", new Class<?>[] {});
			Object obj = klass.newInstance();
			method.invoke(obj, new Object[] {});
			classLoader.close();
		}
	}
	
	@org.junit.Test
	public void loadJavax() throws Exception {
		File file = new File(System.getProperty("user.dir"),
				"stub/stub_test.jar");
		URL repo = file.toURI().toURL();
		// 构造了URLClassLoader加载stub_test.jar，修改了双亲委派，优先加载自己的
		URLClassLoader classLoader = new URLClassLoader(new URL[] { repo }) {

			@Override
			public Class<?> loadClass(String name)
					throws ClassNotFoundException {
				try {
					Class<?> clazz = null;
					clazz = findClass(name);
					return clazz;
				} catch (Exception ex) {

				}
				return super.loadClass(name);
			}

		};
		
		Class<?> clazz = classLoader.loadClass("javax.swing.AbstractAction");
		System.out.println(clazz.getClassLoader());
		System.out.println(clazz.getClass().getClassLoader());
		System.out.println(List.class.getClassLoader());
		System.out.println(ClassLoader.getSystemClassLoader());
		classLoader.close();
	}
}
