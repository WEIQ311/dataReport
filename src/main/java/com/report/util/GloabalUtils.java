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

    /**
     * 请求类型
     */
    public static final String X_REQUESTED_WIDTH = "X-Requested-With";

    /**
     * xml请求
     */
    public static final String XML_HTTP_REQUEST = "XMLHttpRequest";

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

    /**
     * 判断是否ajax请求.
     * 可以看到Ajax 请求多了个 x-requested-with ，可以利用它，
     * request.getHeader("x-requested-with"); 为 null，则为传统同步请求，为 XMLHttpRequest，则为Ajax 异步请求。
     *
     * @param request HttpServletRequest
     * @return 是否ajax请求.
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String xr = request.getHeader(X_REQUESTED_WIDTH);
        return (xr != null && XML_HTTP_REQUEST.equalsIgnoreCase(xr));
    }

}
