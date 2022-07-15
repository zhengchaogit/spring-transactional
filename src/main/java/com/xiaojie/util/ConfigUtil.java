package com.xiaojie.util;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;


/**
 * @Description: 
 * 配置文件读取类 缓存|无缓存
 * 
 * @Author douxy
 * @Date 2014年9月28日 下午1:39:13 
 */
public class ConfigUtil {

    private String file = "config";

    private static final ConfigUtil instance = new ConfigUtil();


    public static class NocacheControl extends ResourceBundle.Control {
        public long getTimeToLive(String arg0, Locale arg1) {
            return 0;
        }

        @Override
        public boolean needsReload(String baseName, Locale locale, String format,
                                   ClassLoader loader, ResourceBundle bundle, long loadTime) {
            return true;
        }
    }

    /**
     * 构造函数
     * 
     * @return
     */
    public static ConfigUtil getInstance() {
        return instance;
    }

    /**
     * load文件
     * 
     * @param file
     * @return ResourceBundle
     */
    public ResourceBundle loadFile(String file) {
        try {
            return ResourceBundle.getBundle(file, Locale.ENGLISH);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * load文件
     * 
     * @param file
     * @return ResourceBundle
     */
    public ResourceBundle loadFileNoCache(String file) {
        try {
            return ResourceBundle.getBundle(file, Locale.ENGLISH, new NocacheControl());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 读取配置信息
     * 
     * @param key
     * @return String
     */
    public String getString(String key) {
        try {
            String value = loadFile(file).getString(key);
            return value;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理配置文件流
     * 
     * @param reader
     * @return
     */
    public Map<String, Map<String, String>> proReader(ResourceBundle reader) {
        Map<String, Map<String, String>> tmp = new HashMap<String, Map<String, String>>();
        Map<String, String> u;
        Set<String> keys = reader.keySet();
        String name = "", field = "";
        for (String key : keys) {
            name = key.substring(0, key.lastIndexOf("."));
            field = key.substring(key.lastIndexOf(".") + 1);
            // System.out.println(name + "---" + field);
            u = tmp.get(name);
            if (u == null) {
                u = new HashMap<String, String>();
            }
            u.put(field, reader.getString(key));
            tmp.put(name, u);
        }
        return tmp;
    }
}
