package com.af.demo.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.af.demo.BuildConfig;
import com.af.lib.app.AFManager;
import com.af.lib.app.lifcycles.AppLifeCycleCallbacks;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class ApplifeCycleImp implements AppLifeCycleCallbacks {
    @Override
    public void attachBaseContext(@NonNull Context base) {
    }

    @Override
    public void onCreate(@NonNull Application application) {
        openStackEyes();
        initLeackCanary(application);
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    private void initLeackCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        RefWatcher refWatcher = LeakCanary.install(application);
        AFManager.putService(RefWatcher.class, refWatcher);
    }

    private void openStackEyes() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                // 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 建议在该回调处上传至我们的Crash监测服务器
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }
}
