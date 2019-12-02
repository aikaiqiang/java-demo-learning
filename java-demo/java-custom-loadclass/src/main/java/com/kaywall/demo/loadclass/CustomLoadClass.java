package com.kaywall.demo.loadclass;

import java.io.FileInputStream;

/**
 *  自定义类加载器
 * @author aikaiqiang
 * @date 2019年11月29日 9:21
 */
public class CustomLoadClass extends ClassLoader {

	private String classPath;

	public CustomLoadClass(String classPath) {
		this.classPath = classPath;
	}

	private byte[] loadByte(String name) throws Exception {
		name = name.replaceAll("\\.", "/");
		FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
		int len = fis.available();
		byte[] data = new byte[len];
		fis.read(data);
		fis.close();
		return data;

	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			byte[] data = loadByte(name);
			return defineClass(name, data, 0, data.length);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}
}
