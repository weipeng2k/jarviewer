/**
 * 
 */
package com.murdock.book.jarviewer.bootstrap.server;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.murdock.book.jarviewer.bootstrap.classloader.SubClassLoader;
import com.murdock.book.jarviewer.bootstrap.constant.Bootstrap;

/**
 * @author weipeng2k 2014年1月3日 下午3:11:45
 */
public class Sub {

	private static int COUNT = 1;

	private int id;

	private File home;

	private SubClassLoader loader;

	private Properties bootstrap;

	private Class<?> entryClass;

	public Sub(File home) {
		if (home == null || !home.exists() || !home.isDirectory()) {
			throw new IllegalArgumentException("Sub home is directory.");
		}
		this.home = home;

		String[] matchedFiles = home.list(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith(Bootstrap.JAR)
						|| name.endsWith(Bootstrap.INIT_FILE)) {
					return true;
				}
				return false;
			}
		});
		if (matchedFiles == null || matchedFiles.length < 2) {
			throw new IllegalArgumentException("Sub system is wrong.");
		}

		id = COUNT++;

		List<URL> repos = new ArrayList<URL>();
		// 仍需要添加jar，否则只有目录，还是无法找到class
		// 目录下的文件资源可以找到
		for (String fileName : matchedFiles) {
			File file = new File(home, fileName);
			try {
				repos.add(file.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		try {
			repos.add(home.toURI().toURL());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		loader = new SubClassLoader(repos.toArray(new URL[] {}));

		bootstrap = new Properties();
		try {
			bootstrap.load(loader.getResourceAsStream(Bootstrap.INIT_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getHome() {
		return home;
	}

	public SubClassLoader getLoader() {
		return loader;
	}

	public synchronized Class<?> getEntryClass() {
		if (entryClass == null) {
			String className = bootstrap.get(Bootstrap.CLASS_NAME).toString()
					.trim();
			try {
				entryClass = loader.loadClass(className);
			} catch (ClassNotFoundException ex) {
				throw new RuntimeException(ex);
			}

		}
		return entryClass;
	}

	public synchronized Method getInitMethod() {
		Class<?> clazz = getEntryClass();
		try {
			return clazz.getMethod(bootstrap.get(Bootstrap.INIT_METHOD)
					.toString().trim(), new Class<?>[] {});

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Sub System:" + id).append("\n");
		sb.append("Base on " + home).append("\n");
		sb.append("Load is " + loader + " have:").append("\n");
		for (URL url : loader.getURLs()) {
			sb.append("  |-" + url).append("\n");
		}
		sb.append("EntryClass:" + getEntryClass()).append("\n");
		sb.append("InitMethod:" + getInitMethod());

		return sb.toString();
	}

}
