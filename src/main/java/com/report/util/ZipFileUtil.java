package com.report.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 压缩文件操作类
 *
 * @author weiQiang
 * @date 2018/5/21
 */
@Slf4j
public class ZipFileUtil {

    /**
     * 编码方式
     */
    private static final String ENCODE = System.getProperty("sun.jnu.encoding");

    /**
     * 文件后缀
     */
    private static final String ZIP = ".zip";
    /**
     * 缓冲区大小 1M
     */
    private static final int BUFFER_SIZE = 1024 * 1000;

    /**
     * 添加文件到压缩包中
     *
     * @param zipFile   压缩文件
     * @param file      添加文件
     * @param entryPath 压缩文件内文件目录
     * @throws IOException
     */
    public static void addFileToZip(File zipFile, File file, String entryPath) throws IOException {
        addFileToZip(zipFile, new File[]{file}, new String[]{entryPath});
    }

    /**
     * 添加文件到压缩包中
     *
     * @param zipFile    压缩文件
     * @param files      添加文件
     * @param entryPaths 压缩文件内文件目录
     * @throws IOException
     */
    public static void addFileToZip(File zipFile, File[] files, String[] entryPaths) throws IOException {
        File tempFile = File.createTempFile(zipFile.getName(), null);
        tempFile.delete();
        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            throw new RuntimeException("could not rename the file " + zipFile.getAbsolutePath() + " to "
                    + tempFile.getAbsolutePath());
        }
        addFileToZip(tempFile, zipFile, files, entryPaths);
    }

    /**
     * 添加文件到压缩包中
     *
     * @param srcZip     源压缩文件
     * @param destZip    目标生成文件
     * @param files      添加文件
     * @param entryPaths 压缩文件内文件目录
     * @throws IOException
     */
    public static void addFileToZip(File srcZip, File destZip, File[] files, String[] entryPaths) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        ZipFile zipFile = new ZipFile(srcZip, ENCODE);
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destZip))) {
            out.setEncoding(ENCODE);
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                String name = entry.getName();
                boolean notInFiles = true;
                String entryName = "";
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (entryPaths != null && i < entryPaths.length) {
                        entryName = entryPaths[i];
                    }
                    String filePathAndName = (StringUtils.isNotBlank(entryName) ? entryName + File.separator : "") + file.getName();
                    if (Objects.equals(filePathAndName, name)) {
                        notInFiles = false;
                        break;
                    }
                }
                if (notInFiles) {
                    out.putNextEntry(new ZipEntry(name));
                    InputStream is = zipFile.getInputStream(entry);
                    int length = 0;
                    while ((length = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
                        out.write(buffer, 0, length);
                    }
                    out.flush();
                    is.close();
                }
            }
            String entryName = "";
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (entryPaths != null && i < entryPaths.length) {
                    entryName = entryPaths[i];
                }
                if (file.isDirectory() && StringUtils.isNotBlank(entryName)) {
                    compress(out, file, entryName, "", null, false);
                } else {
                    String filePathAndName = (StringUtils.isNotBlank(entryName) ? entryName + File.separator : "") + file.getName();
                    compress(out, file, filePathAndName, "", null, false);
                }
            }
        } finally {
            zipFile.close();
        }
    }

    /**
     * 将一组文件或目录压缩到一个Zip输出流中
     *
     * @param out         zip输出流
     * @param srcFile     文件或目录
     * @param name        压缩包中的名称
     * @param comment     压缩包注释
     * @param filter      过滤文件(正则表达式)
     * @param ignoreError 忽略错误
     * @throws IOException
     */
    public static void compress(ZipOutputStream out, File srcFile, String name, String comment, List<String> filter,
                                boolean ignoreError) throws IOException {
        if (!srcFile.exists()) {
            throw new IOException(srcFile.getAbsolutePath() + "文件不存在!");
        }
        try {
            if (srcFile.isDirectory()) {
                File[] files = srcFile.listFiles();
                name = name.length() == 0 ? "" : name + File.separator;
                if (!isFilter(name, filter) && name.length() != 0) {
                    out.putNextEntry(new ZipEntry(name));
                }
                for (File file : files) {
                    compress(out, file, name + file.getName(), comment, filter, ignoreError);
                }
            } else {
                if (!isFilter(name, filter)) {
                    name = name.length() == 0 ? srcFile.getName() : name;
                    ZipEntry zipEntry = new ZipEntry(name);
                    zipEntry.setComment(comment);
                    out.putNextEntry(zipEntry);
                    try (FileInputStream in = new FileInputStream(srcFile)) {
                        int length = 0;
                        byte[] buffer = new byte[BUFFER_SIZE];
                        while ((length = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                            out.write(buffer, 0, length);
                        }
                        out.flush();
                    }
                }
            }
        } catch (IOException e) {
            if (ignoreError) {
                log.error("compress zip ignore error:{} ", e.getMessage());
            } else {
                throw e;
            }
        }
    }

    /**
     * 指定的名称是否被过滤
     *
     * @param base
     * @param filter 过滤列表(正则表达式)
     * @return
     */
    private static boolean isFilter(String base, List<String> filter) {
        if (filter != null && !filter.isEmpty()) {
            for (int i = 0; i < filter.size(); i++) {
                Pattern pat = Pattern.compile(filter.get(i));
                Matcher mat = pat.matcher(base);
                if (mat.find()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将一个zip文件解压到一个目录
     *
     * @param srcPath    压缩包路径
     * @param destDir    解压位置
     * @param deleteFile 是否删除
     * @throws IOException
     */
    public static void unCompress(String srcPath, String destDir, boolean deleteFile) throws IOException {
        unCompress(new File(srcPath), new File(destDir), deleteFile);
    }

    /**
     * 将一个zip文件解压到一个目录
     *
     * @param srcFile    压缩包文件
     * @param destDir    解压位置
     * @param deleteFile 是否删除
     * @throws IOException
     */
    public static void unCompress(File srcFile, File destDir, boolean deleteFile) throws IOException {
        if (!srcFile.exists()) {
            throw new IOException(srcFile.getAbsolutePath() + "文件不存在!");
        }
        if (destDir.isFile()) {
            throw new IOException("解压位置是文件夹");
        }
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcFile, ENCODE);
            Enumeration<?> e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(destDir, name);
                    f.mkdirs();
                } else {
                    File f = new File(destDir, zipEntry.getName());
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    InputStream is = null;
                    try (FileOutputStream fos = new FileOutputStream(f)) {
                        is = zipFile.getInputStream(zipEntry);
                        int length = 0;
                        byte[] buffer = new byte[BUFFER_SIZE];
                        while ((length = is.read(buffer, 0, BUFFER_SIZE)) != -1) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
            }
        } finally {
            if (null != zipFile) {
                zipFile.close();
            }
        }
        if (deleteFile) {
            srcFile.deleteOnExit();
        }
    }
}
