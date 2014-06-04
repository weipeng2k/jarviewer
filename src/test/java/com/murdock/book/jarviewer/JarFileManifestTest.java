/**
 * 
 */
package com.murdock.book.jarviewer;

import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.junit.Test;

/**
 * @author weipeng2k 2013年12月27日 上午10:01:55
 */
public class JarFileManifestTest {
	static String file = "/Library/Java/JavaVirtualMachines/jdk1.7.0_55.jdk/Contents/Home/jre/lib/rt.jar";
	
	@Test
	public void manifest() throws Exception {
		JarFile jar = new JarFile(file);
		Manifest manifest = jar.getManifest();
		System.out.println("===========Main Attributes=========");
		Attributes mainAttri = manifest.getMainAttributes();
		System.out.println(toString(mainAttri));
		
		System.out.println("=============Entry=============");
		for (Map.Entry<String,Attributes> entry : manifest.getEntries().entrySet()) {
			Attributes attributes = entry.getValue();
			System.out.println("key:" + entry.getKey() + ",values:(" + toString(attributes));
		}
		jar.close();
	}
	
	public static String toString(Attributes attris) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Map.Entry<Object,Object> entry : attris.entrySet()) {
			sb.append("(key:" + entry.getKey() + ", value:" + entry.getValue() + ")");
		}
		sb.append("]");
		
		return sb.toString();
	}
}
