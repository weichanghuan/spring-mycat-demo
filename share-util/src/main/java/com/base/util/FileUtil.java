package com.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件工具类。
 */
public class FileUtil {
    /**
     * 临时文件文件名前缀
     */
    private static final String TEMP_FILE_PREFIX = "ti_tmp";

    /**
     * 打开的文件句柄计数器（用于单元测试检查资源释放情况）
     */
    public static volatile int openingCount = 0;

    public static class FileReadHandler {
        private LineNumberReader reader;

        private InputStream in;

        public LineNumberReader getReader() {
            return reader;
        }

        public InputStream getInputStream() {
            return in;
        }
    }

    public static class FileWriteHandler {
        private PrintWriter writer;

        private OutputStream out;

        public PrintWriter getWriter() {
            return writer;
        }

        public OutputStream getOutputStream() {
            return out;
        }
    }

    public static FileReadHandler openForRead(File file, String encoding) {
        FileInputStream fin = null;

        try {
            FileReadHandler handler = new FileReadHandler();
            InputStreamReader streamReader;
            fin = new FileInputStream(file);

            openingCount++;

            if (encoding == null) {
                encoding = System.getProperty("file.encoding");
            }

            if (encoding != null) {
                streamReader = new InputStreamReader(fin, encoding);
            } else {
                streamReader = new InputStreamReader(fin);
            }

            handler.reader = new LineNumberReader(streamReader);
            handler.in = fin;

            return handler;
        } catch (Throwable e) {
            if (fin != null) {
                try {
                    fin.close();
                } catch (Throwable e2) {
                }

                openingCount--;
            }

            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static FileReadHandler openForRead(InputStream in, String encoding) {
        try {
            FileReadHandler handler = new FileReadHandler();
            InputStreamReader streamReader;

            if (encoding == null) {
                encoding = System.getProperty("file.encoding");
            }

            if (encoding != null) {
                streamReader = new InputStreamReader(in, encoding);
            } else {
                streamReader = new InputStreamReader(in);
            }

            handler.reader = new LineNumberReader(streamReader);
            handler.in = in;

            return handler;
        } catch (Throwable e) {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable e2) {
                }
            }

            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static FileReadHandler openForRead(File file) {
        return openForRead(file, null);
    }

    public static String readLine(FileReadHandler handler) {
        try {
            return handler.reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<String> readLines(FileReadHandler handler) {
        List<String> lines = new ArrayList<String>();
        try {
            String line = null;
            while ((line = handler.reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void readBytes(FileReadHandler handler, byte[] buffer, int off, int len) {
        try {
            handler.in.read(buffer, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void close(FileReadHandler handler) {
        if (handler.reader != null || handler.in != null) {
            openingCount--;
        }

        IOUtil.closeQuietly(handler.reader);
        IOUtil.closeQuietly(handler.in);
    }

    public static String readLine(File file, String encoding) {
        FileReadHandler handler = openForRead(file, encoding);
        try {
            return readLine(handler);
        } finally {
            close(handler);
        }
    }

    public static String readLine(File file) {
        return readLine(file, null);
    }

    public static List<String> readLines(File file, String encoding) {
        FileReadHandler handler = openForRead(file, encoding);
        try {
            return readLines(handler);
        } finally {
            close(handler);
        }
    }

    public static List<String> readLines(File file) {
        return readLines(file, null);
    }

    public static FileWriteHandler openForWrite(File file, String encoding) {
        FileOutputStream fos = null;

        try {
            FileWriteHandler handler = new FileWriteHandler();
            OutputStreamWriter streamWriter;
            fos = new FileOutputStream(file);

            openingCount++;

            if (encoding == null) {
                encoding = System.getProperty("file.encoding");
            }

            if (encoding != null) {
                streamWriter = new OutputStreamWriter(fos, encoding);
            } else {
                streamWriter = new OutputStreamWriter(fos);
            }

            handler.writer = new PrintWriter(streamWriter);
            handler.out = fos;
            return handler;
        } catch (Throwable e) {
            if (fos != null) {
                openingCount--;

                try {
                    fos.close();
                } catch (Throwable e2) {
                }
            }

            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static FileWriteHandler openForWrite(File file) {
        return openForWrite(file, null);
    }

    public static void printf(FileWriteHandler handler, String format, Object... args) {
        handler.writer.printf(format, args);
    }

    public static void close(FileWriteHandler handler) {
        if (handler.writer != null || handler.out != null) {
            openingCount--;
        }

        IOUtil.closeQuietly(handler.writer);
        IOUtil.closeQuietly(handler.out);
    }

    public static void printf(File file, String format, Object... args) {
        FileWriteHandler handler = openForWrite(file);
        try {
            printf(handler, format, args);
        } finally {
            close(handler);
        }
    }

    public static void writeFile(File file, String str) {
        FileWriteHandler handler = openForWrite(file);
        try {
            handler.writer.write(str);
        } finally {
            close(handler);
        }
    }

    public static void writeFile(File file, byte[] data, int off, int len) {
        FileWriteHandler handler = openForWrite(file);
        try {
            handler.out.write(data, off, len);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            close(handler);
        }
    }

    public static void writeFile(File file, byte[] data) {
        writeFile(file, data, 0, data.length);
    }

    public static void appendFile(File file, String data) {
        try {
            appendFile(file, data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void appendFile(File file, byte[] data) {
        appendFile(file, data, 0, data.length);
    }

    public static void appendFile(File file, byte[] data, int off, int len) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(data, off, len);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            CloseUtil.close(out);
        }
    }

    public static void copyFile(File src, File target) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            inStream = new FileInputStream(src);
            outStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];

            int length;
            // copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }

            inStream.close();
            outStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void loadProperties(Properties props, File file) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            props.load(fin);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    public static void copyDir(File fromDir, File toDir) {
        if (fromDir.exists() == false) {
            throw new RuntimeException("Not found the fromDir=[" + fromDir + "].");
        }

        if (fromDir.isFile()) {
            if (toDir.isFile()) {
                copyFile(fromDir, toDir);
                return;
            } else {
                toDir.mkdirs();
                copyFile(fromDir, new File(toDir.getPath() + "/" + fromDir.getName()));

                return;
            }
        }

        toDir.mkdirs();
        File[] files = fromDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                File targetDir = new File(toDir.getPath() + "/" + file.getName());
                targetDir.mkdirs();

                copyDir(file, targetDir);
            } else {
                copyFile(file, new File(toDir.getPath() + "/" + file.getName()));
            }
        }
    }

    public static String getFileNameExtension(String fileName) {
        int idx = fileName.lastIndexOf(".");
        if (idx <= 0) {
            return StringUtil.EMPTY;
        }

        int idx2 = fileName.lastIndexOf("/");
        if (idx < idx2) {
            return StringUtil.EMPTY;
        }

        return fileName.substring(idx + 1);
    }

    public static String getFileName(String filePath) {
        if (filePath == null) {
            return filePath;
        }

        return new File(filePath).getName();
    }

    public static String changeFileNameExtension(String filename, String ext) {
        StringBuilder sb = new StringBuilder();

        if (StringUtil.isNotEmpty(getFileNameExtension(filename))) {
            sb.append(filename.substring(0, filename.lastIndexOf(".")));

        } else {
            sb.append(filename);
        }

        sb.append(".");
        sb.append(ext);

        return sb.toString();
    }

    /**
     * 在指定路径创建新文件，自动创建上级目录
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createNewFile(String path) {
        if (StringUtil.isEmpty(path)) {
            return null;
        }

        File file = new File(path);
        return (createNewFile(file) ? file : null);
    }

    /**
     * 创建新文件，自动创建上级目录
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean createNewFile(File file) {
        if (file == null) {
            return false;
        }

        if (file.exists()) {
            return true;
        }

        mkParentDirs(file);

        try {
            return file.createNewFile();
        } catch (IOException ie) {
            throw new RuntimeException("Create file error", ie);
        }
    }

    /**
     * 自动创建上级目录
     *
     * @param file
     * @return
     */
    public static boolean mkParentDirs(String path) {
        if (StringUtil.isEmpty(path)) {
            return false;
        }

        return mkParentDirs(new File(path));
    }

    /**
     * 自动创建上级目录
     *
     * @param file
     * @return
     */
    public static boolean mkParentDirs(File file) {
        if (file == null) {
            return false;
        }

        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }

        if (parentFile.exists() == false) {
            return parentFile.mkdirs();
        }

        return true;
    }

    public static byte[] readFile(String filename) {
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(filename, "r");
            byte[] data = new byte[(int) raf.length()];
            raf.read(data);
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            CloseUtil.close(raf);
        }
    }

    /**
     * Reads this input stream and returns contents as a byte[]
     */
    public static byte[] readAsByteArray(InputStream inStream) throws IOException {
        int size = 1024;
        byte[] ba = new byte[size];
        int readSoFar = 0;

        while (true) {
            int nRead = inStream.read(ba, readSoFar, size - readSoFar);
            if (nRead == -1) {
                break;
            }
            readSoFar += nRead;
            if (readSoFar == size) {
                int newSize = size * 2;
                byte[] newBa = new byte[newSize];
                System.arraycopy(ba, 0, newBa, 0, size);
                ba = newBa;
                size = newSize;
            }
        }

        byte[] newBa = new byte[readSoFar];
        System.arraycopy(ba, 0, newBa, 0, readSoFar);
        return newBa;
    }

    public static void writeFile(String filename, byte[] data) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(filename);
            out.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            CloseUtil.close(out);
        }
    }

    /**
     * 创建临时文件
     *
     * @return
     */
    public static File createTempFile() {
        return createTempFile(TEMP_FILE_PREFIX, null);
    }

    /**
     * 创建临时文件
     *
     * @param suffix 后缀
     * @return
     */
    public static File createTempFile(String suffix) {
        return createTempFile(TEMP_FILE_PREFIX, suffix);
    }

    /**
     * 创建临时文件，格式为prefix + randomNum + suffix
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return
     */
    public static File createTempFile(String prefix, String suffix) {
        try {
            return File.createTempFile(prefix, suffix);
        } catch (IOException ie) {
            throw new RuntimeException("Create temp file error", ie);
        }
    }

    /**
     * 创建临时目录
     *
     * @return
     */
    public static File createTempDir() {
        String tmpDirPath = getSystemTempDir() + "/" + UUID.randomUUID().toString();
        File tmpDir = new File(tmpDirPath);

        if (tmpDir.mkdirs() == false) {
            throw new RuntimeException("Create temp dir failed, path=" + tmpDirPath);
        }

        return tmpDir;
    }

    /**
     * 返回目录下文件名符合规则的文件数组
     *
     * @param file
     * @param regExp
     * @return
     */
    public static File[] listFiles(File file, String regExp) {
        final Pattern pattern = Pattern.compile(regExp);
        return file.listFiles(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                Matcher matcher = pattern.matcher(name);
                return matcher.find();
            }
        });
    }

    /**
     * 文件重命名
     *
     * @param file
     * @param newName
     * @return
     */
    public static File rename(File file, String newName) {
        if (file == null || file.exists() == false) {
            throw new IllegalArgumentException("File must be existed");

        } else if (StringUtil.isEmpty(newName)) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        String parent = file.getParent();
        if (parent == null) {
            parent = ".";
        }

        File newFile = new File(parent + "/" + newName);
        if (file.renameTo(newFile) == false) {
            throw new RuntimeException(String.format("Rename file failed, srcFile=[%s], tgtName=[%s]",
                    file.getAbsolutePath(), newName));
        }

        return newFile;
    }

    /**
     * 获取系统临时目录路径
     *
     * @return
     */
    public static String getSystemTempDir() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        if (tmpDir == null) {
            tmpDir = "/tmp";
        }

        return tmpDir;
    }
}
