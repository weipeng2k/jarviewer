package com.murdock.book.jarviewer.loader;

public interface IClassLoadStrategy
{
    ClassLoader getClassLoader (ClassLoadContext ctx);
} // End of interface
