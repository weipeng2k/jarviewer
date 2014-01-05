package com.murdock.book.jarviewer;

import sun.reflect.Reflection;

public class Invoke {
	void invoke(int ii) {
		for (int i = 0; i < 1; i++) {
			System.out.println(Reflection.getCallerClass());
		}
	}
}
