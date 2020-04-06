package com.hengyue.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 * 
 * @author 章家宝
 *
 */
public class FileUtils {

	private FileUtils() {
	}

	public FileUtils build() {
		return new FileUtils();
	}

	/**
	 * 创建目录
	 * 
	 * @param name
	 * @return
	 */
	public static boolean createflies(String name) {
		boolean flag = false;
		File file = new File(name);
		// 创建目录
		if (file.mkdir() == true) {
			flag = true;
		} else {
			flag = false;

		}

		return flag;
	}

	/**
	 * 复制文件
	 * 
	 * @param sourse
	 * @param target
	 */
	public static void fileInputOutput(String sourse, String target) {
		try {
			File s = new File(sourse);
			File t = new File(target);

			FileInputStream fin = new FileInputStream(s);
			FileOutputStream fout = new FileOutputStream(t);

			byte[] a = new byte[1024 * 1024 * 4];
			int b = -1;

			// 边读边写
			while ((b = fin.read(a)) != -1) {
				fout.write(a, 0, b);
			}

			fout.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历文件夹并复制
	 * 
	 * @param sourse
	 * @param target
	 */
	public static void clone(String sourse, String target) {
		// 获取目录下所有文件
		File f = new File(sourse);
		File[] allf = f.listFiles();

		// 遍历所有文件
		for (File fi : allf) {
			try {
				// 拼接目标位置
				String url = target + "\\" + fi.getName();
				System.out.println("url:" + url);
				// 创建目录或文件
				if (fi.isDirectory()) {
					createflies(url);
				} else {
					fileInputOutput(fi.getAbsolutePath(), url);
				}
				// 递归调用
				if (fi.isDirectory()) {
					clone(fi.getAbsolutePath(), url);
				}
			} catch (Exception e) {
				System.out.println("error");
			}
		}
	}
	
	public static boolean isFile(String filepath) {
		File f = new File(filepath);
		return f.exists() && f.isFile();
	}

	public static boolean isDir(String dirPath) {
		File f = new File(dirPath);
		return f.exists() && f.isDirectory();
	}

	/**
	 * 创建多级目录
	 * @param path
	 */
	public static void makeDirs(String path) {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}else {
			System.out.println("创建目录失败："+path);
		}
	}

	public static void main(String[] args) {
		FileUtils.clone("D:\\Program Files\\eclipse-workspace2\\JXC\\src\\main\\webapp\\file\\会计" , "C:\\Users\\章家宝\\Desktop\\test"); 
	}

}
