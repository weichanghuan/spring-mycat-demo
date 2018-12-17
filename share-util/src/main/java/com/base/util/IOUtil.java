package com.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * IO工具类
 */
public final class IOUtil {
    /**
     * 调用指定对象的close方法，捕获所有异常
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            // Swallow the exception
        }
    }

    /**
     * 从源文件拷贝数据至目标文件
     *
     * @param src
     * @param target
     * @return
     * @throws IOException
     */
    public static long copy(File src, File target) throws IOException {
        return copy(new FileInputStream(src), target, true, true);
    }

    /**
     * 从源文件拷贝数据至目标文件
     *
     * @param src
     * @param target
     * @param createNewFile 是否自动创建目标文件
     * @return
     * @throws IOException
     */
    public static long copy(File src, File target, boolean createNewFile) throws IOException {
        return copy(new FileInputStream(src), target, createNewFile, true);
    }

    /**
     * 从输入流拷贝数据到指定文件, 自动创建目标文件上级目录, 自动关闭输入流
     *
     * @param inStream
     * @param target
     * @return
     * @throws IOException
     */
    public static long copy(InputStream inStream, File target) throws IOException {
        return copy(inStream, target, true, true);
    }

    /**
     * 从输入流拷贝数据到指定文件, 自动关闭输入流
     *
     * @param inStream
     * @param target
     * @param createNewFile
     * @return
     * @throws IOException
     */
    public static long copy(InputStream inStream, File target, boolean createNewFile) throws IOException {
        return copy(inStream, target, createNewFile, true);
    }

    /**
     * 从输入流拷贝数据到指定文件
     *
     * @param inStream
     * @param target
     * @param createNewFile
     * @param closeAfterComplete
     * @return
     * @throws IOException
     */
    public static long copy(InputStream inStream, File target, boolean createNewFile, boolean closeAfterComplete)
            throws IOException {
        if (createNewFile) {
            FileUtil.createNewFile(target);
        }

        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(target);
            return copy(inStream, outStream, false); // 由本方法关闭，避免关闭两次
        } finally {
            closeQuietly(outStream);
            if (closeAfterComplete) {
                closeQuietly(inStream);
            }
        }
    }

    /**
     * 从文件拷贝数据到指定输出流, 自动关闭输出流
     *
     * @param src
     * @param outStream
     * @return
     * @throws IOException
     */
    public static long copy(File src, OutputStream outStream) throws IOException {
        return copy(src, outStream, true);
    }

    /**
     * 从文件拷贝数据到指定输出流
     *
     * @param src
     * @param outStream
     * @param closeAfterComplete
     * @return
     * @throws IOException
     */
    public static long copy(File src, OutputStream outStream, boolean closeAfterComplete) throws IOException {
        InputStream inStream = null;
        try {
            inStream = new FileInputStream(src);
            return copy(new FileInputStream(src), outStream, false); // 由本方法关闭，避免关闭两次
        } finally {
            closeQuietly(inStream);
            if (closeAfterComplete) {
                closeQuietly(outStream);
            }
        }
    }

    /**
     * 从输入流读取数据写入数据输出流, 自动关闭输入输出流
     *
     * @param inStream
     * @param outStream
     * @return
     * @throws IOException
     */
    public static long copy(InputStream inStream, OutputStream outStream) throws IOException {
        return copy(inStream, outStream, true);
    }

    /**
     * 从输入流读取数据写入数据输出流
     *
     * @param inStream
     * @param outStream
     * @param closeAfterComplete 是否关闭输入输出流当处理完毕
     * @return
     * @throws IOException
     */
    public static long copy(InputStream inStream, OutputStream outStream, boolean closeAfterComplete)
            throws IOException {
        try {
            byte[] bytes = new byte[8196];

            long count = 0;
            int length;
            while ((length = inStream.read(bytes)) > 0) {
                outStream.write(bytes, 0, length);
                count += length;
            }

            return count;
        } finally {
            if (closeAfterComplete) {
                closeQuietly(inStream);
                closeQuietly(outStream);
            }
        }
    }

    /**
     * 从输入流中读取数据并转换为字符串，使用平台默认字符集
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String toString(InputStream in) throws IOException {
        return toString(in, null);
    }

    /**
     * 从输入流中读取数据并转换为字符串
     *
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public static String toString(InputStream in, String charset) throws IOException {
        if (in == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);

        return out.toString(charset != null ? charset : Charset.defaultCharset().name());
    }

    /**
     * 从输入流中读取数据并转换为字节数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);

        return out.toByteArray();
    }

    /**
     * 从输入流中读取数据，按行转换为字符串集合，使用平台默认字符集
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static List<String> readLines(InputStream in) throws IOException {
        return readLines(in, null);
    }

    /**
     * 从输入流中读取数据，按行转换为字符串集合
     *
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public static List<String> readLines(InputStream in, String charset) throws IOException {
        if (in == null) {
            return null;
        }

        if (charset == null) {
            charset = Charset.defaultCharset().name();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));
            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        } finally {
            closeQuietly(in);
        }
    }

    /**
     * 向输出流中写入数据，按行输出字符串集合
     *
     * @param lines
     * @param out
     * @throws IOException
     */
    public static void writeLines(List<String> lines, OutputStream out) throws IOException {
        writeLines(lines, null, out, null);
    }

    /**
     * 向输出流中写入数据，按行输出字符串集合
     *
     * @param lines
     * @param lineSeparator
     * @param out
     * @param charset
     * @throws IOException
     */
    public static void writeLines(List<String> lines, String lineSeparator, OutputStream out, String charset)
            throws IOException {
        if (CollectionUtil.isEmpty(lines) || out == null) {
            return;
        }

        if (charset == null) {
            charset = Charset.defaultCharset().name();
        }

        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, charset));
            int i = 0;
            for (; i < lines.size() - 1; i++) {
                writer.print(lines.get(i));
                if (lineSeparator != null) {
                    writer.print(lineSeparator);

                } else {
                    writer.println();
                }
            }

            writer.print(lines.get(i));
            writer.flush();
        } finally {
            closeQuietly(out);
        }
    }

    private IOUtil() {
    }
}
