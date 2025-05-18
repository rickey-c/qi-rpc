package com.rickey.rpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @Description: 配置工具类，用于加载配置文件并将其转换为指定的配置对象
 * @Author: rickey-c
 * @Date: 2025/5/18 23:33
 */
public class ConfigUtils {

    /**
     * 加载配置对象
     *
     * @param tClass 配置对象的类类型
     * @param prefix 配置文件中属性的前缀
     * @param <T>    配置对象的类型
     * @return 加载并转换后的配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        // 调用重载方法，默认不区分环境
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass      配置对象的类类型
     * @param prefix      配置文件中属性的前缀
     * @param environment 环境标识（如 dev、prod 等）
     * @param <T>         配置对象的类型
     * @return 加载并转换后的配置对象
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        // 构建配置文件名，默认是 application.properties
        StringBuilder configFileBuilder = new StringBuilder("application");
        // 如果指定了环境，则拼接环境标识
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");

        // 加载配置文件
        Props props = new Props(configFileBuilder.toString());
        // 将配置文件内容转换为指定的配置对象
        return props.toBean(tClass, prefix);
    }
}