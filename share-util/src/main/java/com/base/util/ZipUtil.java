package com.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

/**
 * 压缩工具类。
 * 
 *
 */
public class ZipUtil {
	public static byte[] zipBytes(byte[] data) {
		return zipBytes(data, 0, data.length);
	}
	
	public static byte[] zipBytes(byte[] data, int offset, int len) {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DeflaterOutputStream dout = new DeflaterOutputStream(bout);
			dout.write(data, offset, len);
			dout.finish();
			dout.close();
			return bout.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static byte[] unzipBytes(byte[] data, int offset, int len) {
		try {
			List<byte[]> udataList = new ArrayList<byte[]>();
			List<Integer> udataLenList = new ArrayList<Integer>();
			int udataLen = 0;

			ByteArrayInputStream bin = new ByteArrayInputStream(data, offset, len);
			InflaterInputStream iin = new InflaterInputStream(bin);

			while (iin.available() == 1) {
				byte[] udata = new byte[1024];
				int i = iin.read(udata);
				if (i <= 0) {
					break;
				}

				udataList.add(udata);
				udataLenList.add(i);
				udataLen += i;
			}

			byte[] udata = new byte[udataLen];
			int idx = 0;
			Iterator<Integer> it = udataLenList.iterator();
			for (byte[] ud : udataList) {
				int size = it.next();
				System.arraycopy(ud, 0, udata, idx, size);
				idx += size;
				udataLen -= size;
			}

			return udata;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static byte[] unzipBytes(byte[] data) {
		return unzipBytes(data, 0, data.length);
	}

	public static void zipFile(File tgtFile, String dir, String[] files) {
		StringBuffer sb = new StringBuffer();
		sb.append("jar cfM ");
		sb.append(tgtFile.getPath());
		for (String file : files) {
			sb.append(" -C ");
			sb.append(dir != null ? dir : ".");
			sb.append(" ");
			sb.append(file);
			sb.append(" ");
		}

		int exitCode;
		try {
			Process proc = Runtime.getRuntime().exec(sb.toString());
			exitCode = proc.waitFor();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		if (exitCode != 0) {
			throw new RuntimeException("Zip file meet error, exitCode=[" + exitCode + "].");
		}
	}

	public static File zipFile(File file) {
		File tgtFile = new File(FileUtil.changeFileNameExtension(file.getPath(), "zip"));
		zipFile(tgtFile, file.getParent(), new String[] { file.getName() });

		return tgtFile;
	}

	public static File[] unzipFile(File zipFile, String dir, String[] files) {
		File fDir = new File(dir != null ? dir : ".");
		if (fDir.exists() == false) {
			fDir.mkdirs();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("jar xf ");
		sb.append(zipFile.getPath());
		sb.append(" ");
		for (String file : files) {
			sb.append(file);
			sb.append(" ");
		}

		int exitCode;
		try {
			Process proc = Runtime.getRuntime().exec(sb.toString(), null, fDir);
			exitCode = proc.waitFor();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		if (exitCode != 0) {
			throw new RuntimeException("Unzip file meet error, exitCode=[" + exitCode + "].");
		}

		File[] fs = new File[files.length];
		int i = 0;
		for (String file : files) {
			fs[i] = new File(dir + "/" + file);
			if (fs[i].exists() == false) {
				throw new RuntimeException("Not found the file in zip, file=[" + file + "].");
			}

			i++;
		}

		return fs;
	}

	public static File[] unzipFile(File zipFile, String[] files) {
		return unzipFile(zipFile, zipFile.getParent(), files);
	}
}
