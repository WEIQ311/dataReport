package com.report.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Properties;

/**
 * @author weiQiang
 */
public class GloabalUtils {

    /**
     * 常量:未知
     */
    public static final String UNKNOWN = "unknown";

    /**
     * 问号
     */
    public static final String QUERY_MARK = "?";

    /**
     * linux
     */
    public static final String LINUX_NAME = "linux";

    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int TWELVE = 12;
    public static final String GLOABAL_DAY = "day";
    public static final String GLOABAL_HOUR = "hour";
    public static final String GLOABAL_MINUTE = "minute";
    public static final String GLOABAL_SECOND = "second";
    public static final String IS_ENABLE = "isEnable";

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 判断是否是linux系统
     *
     * @return
     */
    public static boolean isOsLinux() {
        Properties properties = System.getProperties();
        String os = properties.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf(LINUX_NAME) > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据系统转换为windows格式或者linux格式
     *
     * @param path 路径
     * @return
     */
    public static String winOrLinuxPath(String path) {
        if (!isOsLinux()) {
            path = path.replace("/", "\\");
        }
        return path;
    }

    /**
     * 创建目录
     *
     * @param path
     * @return
     */
    public static String createDir(String path) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path = winOrLinuxPath(path);
        File unZipFileDir = new File(path);
        if (!unZipFileDir.exists()) {
            unZipFileDir.mkdirs();
        }
        return path;
    }
}
