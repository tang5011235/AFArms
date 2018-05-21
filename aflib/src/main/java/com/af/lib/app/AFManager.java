package com.af.lib.app;

import android.support.annotation.NonNull;

/**
 * 作者：thf on 2018/5/15 0015 16:48
 * <p>
 * 邮箱：tang5011235@163.com
 */
public final class AFManager {

    /**
     * 系统服务
     */
    public static Object getService(String serviceName) {
        return AppDelegate.AF_SERVICE.get(serviceName);
    }


    public static <T> T getService(Class<T> clazz) {
        return (T) AppDelegate.AF_SERVICE.get(clazz.getSimpleName());
    }

    static void putService(String name, Object service) {
        if (AppDelegate.AF_SERVICE.get(name) != null) {
            throw new RuntimeException("AFArms is existe" + name + "service");
        }
        AppDelegate.AF_SERVICE.put(name, service);
    }

    static <T> void putService(Class<T> clazz, T service) {
        if (AppDelegate.AF_SERVICE.get(clazz.getSimpleName()) != null) {
            throw new RuntimeException("AFArms is existe" + clazz.getSimpleName() + "service");
        }
        AppDelegate.AF_SERVICE.put(clazz.getSimpleName(), service);
    }


    /**
     * 自定义的服务
     */
    public static Object getCustomService(String serviceName) {
        return AppDelegate.CUSTOM_SERVICE.get(serviceName);
    }

    public static <T> T getCustomService(@NonNull Class<T> clazz) {
        return (T) AppDelegate.CUSTOM_SERVICE.get(clazz.getSimpleName());
    }

    public static void putCustomService(String name, Object service) {
        if (AppDelegate.CUSTOM_SERVICE.get(name) != null) {
            throw new RuntimeException("AFArms is existe" + name + "service");
        }
        AppDelegate.CUSTOM_SERVICE.put(name, service);
    }

    public static <T> void putCustomService(Class<T> clazz, T service) {
        if (AppDelegate.CUSTOM_SERVICE.get(clazz.getSimpleName()) != null) {
            throw new RuntimeException("AFArms is existe" + clazz.getSimpleName() + "service");
        }
        AppDelegate.CUSTOM_SERVICE.put(clazz.getSimpleName(), service);
    }
}

