package com.hjh.mall.common.core.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;



public class EnvironmentUtils {
    
    private static final String RUNTIME_CONFIG_ROOT_ENV_KEY = "RUNTIME_CONFIG_ROOT";
    
    private static final String RUNTIME_CONFIG_ROOT_DIR_NAME = "runtime_config_root";
    
    private static final String CLASSPATH_PREFIX = "classpath:";
    
    /**
     * 获取系统运行期配置文件路径
     * @return
     */
    public static String getRuntimeConfigPath() {
        String path = System.getenv().get(RUNTIME_CONFIG_ROOT_ENV_KEY);
        if (StringUtils.isBlank(path)) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("win") >= 0) {
                path = "C:" + File.separator + RUNTIME_CONFIG_ROOT_DIR_NAME;
            } else {
                path = '/' + RUNTIME_CONFIG_ROOT_DIR_NAME;
            }
        }
        return path;
    }
    
    public static String findFileInRuntimeConfigDirOrClasspath(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        boolean inClasspath = false;
        if (fileName.startsWith(CLASSPATH_PREFIX)) {
            inClasspath = true;
            fileName = fileName.substring(CLASSPATH_PREFIX.length());
        }
        if (fileName.startsWith("/")) {
            fileName = fileName.substring(1);
        }
        if (!inClasspath) {
            String filePath = getRuntimeConfigPath() + File.separator + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                return filePath;
            }
        }
        String filePath = getClasspathRoot() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;
    }
    
    /**
     * 获取Classpath根目录地址
     * @return
     */
    public static String getClasspathRoot() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
    
    /**
     * 获取Web部署实际根目录
     * @return
     */
    public static String getWebRootPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
    
}
