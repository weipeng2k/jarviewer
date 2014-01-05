package com.murdock.book.jarviewer.stub;

import java.util.*;
import java.security.*;

public class Test {
	static int MAX = 10;

	static int CURRENT = 1;

	public void m() {
		System.out.println("m is called. class loader is " + this.getClass().getClassLoader());
		new B();
		System.out.println("sun.misc.VM.latest is " + sun.misc.VM.latestUserDefinedLoader());
		System.out.println("currentThreadLoader + " + Thread.currentThread().getContextClassLoader());
		if (CURRENT++ < MAX) {
			Thread thread = new Thread() {
				public void run() {
					new Test().m();
				}
			};
			System.out.println(thread.getClass() + "thread.getClass().getClassLoader() is " + thread.getClass().getClassLoader());
			thread.start();
		}
		System.out.println("getContextClassLoader() threadContextLoader" + getContextClassLoader());
	    
	}
	
    private ClassLoader getContextClassLoader() {
           return (ClassLoader)
                   AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                   ClassLoader cl = null;
                   try {
                   cl = Thread.currentThread().getContextClassLoader();
			   		} catch (SecurityException ex) { ex.printStackTrace();}

                   if (cl == null)
                       cl = ClassLoader.getSystemClassLoader();

                   return cl;
               }
           });
    }
	
	public static void main(String[] args) {
		new Test().m();
	}
}
