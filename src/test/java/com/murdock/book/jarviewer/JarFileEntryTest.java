/**
 * 
 */
package com.murdock.book.jarviewer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

/**
 * @author weipeng2k 2013年12月27日 上午10:01:55
 */
public class JarFileEntryTest {
	static String file = "/Users/weipeng2k/Documents/arena/jarviewer/target/jarviewer-1.0-SNAPSHOT.jar";

	@Test
	public void entries() throws Exception {
		JarFile jar = new JarFile(file);
		Enumeration<JarEntry> enums = jar.entries();
		while (enums.hasMoreElements()) {
			JarEntry entry = enums.nextElement();
			System.out.print("Attributes:" + toString(entry.getAttributes()));
			System.out.println();
		}
		jar.close();
	}

	@Test
	public void getJarEntry() throws Exception {
		JarFile jar = new JarFile(file);

		JarEntry entry = jar.getJarEntry("com/murdock/book/jarviewer/Stub.class");
		System.out.println(entry);
		System.out.println("Comment:" + entry.getComment());
		System.out.println("CompressedSize:" + entry.getCompressedSize());
		System.out.println("Size:" + entry.getSize());
		System.out.println("Crc:" + entry.getCrc());
		System.out.println("Name:" + entry.getName());
		System.out.println("Extra:" + entry.getExtra());
		System.out.println("Attributes:" + toString(entry.getAttributes()));

		jar.close();
	}

	@Test
	public void getInputStream() throws Exception {
		JarFile jar = new JarFile(file);

		JarEntry entry = jar.getJarEntry("com/murdock/book/jarviewer/Stub.class");
		InputStream inputStream = jar.getInputStream(entry);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = null;
		PrintWriter out = new PrintWriter("Stub.class");
		while ((line = br.readLine()) != null) {
			out.println(line);
			System.out.println(line);
		}
		out.close();
		jar.close();
	}

	public static String toString(Attributes attris) {
		if (attris != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (Map.Entry<Object, Object> entry : attris.entrySet()) {
				sb.append("(key:" + entry.getKey() + ", value:"
						+ entry.getValue() + ")");
			}
			sb.append("]");

			return sb.toString();
		} else {
			return "";
		}
	}
}
