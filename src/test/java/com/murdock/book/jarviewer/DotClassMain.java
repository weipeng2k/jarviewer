package com.murdock.book.jarviewer;


public class DotClassMain {
	public static void main(String[] args) throws Exception {
		// 打印String.class
		System.out.println("String class: " + String.class);
		// 获取class$0这个编译器生成的静态变量，它是package访问级别的，给它改一个值
		DotClassMain.class.getDeclaredField("class$0").set(null, int.class);
		System.out.println("String class: " + String.class);

		// if (class$0 == null) {
		// class$0 = class$("java.lang.String")
		// }
		// local ref = class$0
		// but truly class$0 is changed.
	}
}
