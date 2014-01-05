
package com.murdock.book.jarviewer.stub;

public class Test {
	public void m() {
		System.out.println("m is called. class loader is " + this.getClass().getClassLoader());
		new B();
		System.out.println("New");
	}
	
	public static void main(String[] args) {
		new Test().m();
	}
}
