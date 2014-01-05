package com.murdock.book.jarviewer;

public class ClassForNameTest {

	public static void main(String[] args) {
		for (int repeat = 0; repeat < 3; ++repeat) {
			try {
				// "Real" name for X is outer class name+$+nested class name:
				Class.forName("com.murdock.book.jarviewer.ClassForNameTest$X");
			} catch (Throwable t) {
				System.out.println("load attempt #" + repeat + ":" + " and s_count is " + s_count);
				t.printStackTrace(System.out);
			}
		}

	}

	public static class X {
		static {
			// 如果s_count执行了一次，那么后续再次初始化，就没有问题了。
			if (++s_count == 1)
				throw new RuntimeException("failing static initializer...");
		}

	} // End of nested class

	private static int s_count;

}
