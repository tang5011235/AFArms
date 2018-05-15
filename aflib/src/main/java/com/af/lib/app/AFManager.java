package com.af.lib.app;

import com.af.lib.app.component.AppComponent;

/**
 * 作者：thf on 2018/5/15 0015 16:48
 * <p>
 * 邮箱：tang5011235@163.com
 */
public final class AFManager {

    public static Object getService(String serviceName) {
        return AppComponent.afService.get(serviceName);
    }


    public static Object getService(Class clazz) {
        return AppComponent.afService.get(clazz.getSimpleName());
    }

    public static Object putService(String name, Object service) {
        if (AppComponent.afService.get(name) != null) {
            throw new RuntimeException("AFArms is existe" + name + "service");
        }
        return AppComponent.afService.put(name, service);
    }

    public static Object putService(Class clazz, Object service) {
        if (AppComponent.afService.get(clazz.getSimpleName()) != null) {
            throw new RuntimeException("AFArms is existe" + clazz.getSimpleName() + "service");
        }
        return AppComponent.afService.put(clazz.getSimpleName(), service);
    }

}

