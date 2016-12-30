package com.kymjs.arty.module.upgrade;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kymjs.arty.api.Api;
import com.kymjs.arty.utils.GsonHttpCallback;
import com.kymjs.common.App;
import com.kymjs.common.SystemTool;
import com.kymjs.common.function.ThreadSwitch;
import com.kymjs.rxvolley.RxVolley;

/**
 * Created by ZhangTao on 12/29/16.
 */

public class UpgradeService extends Service {
    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        UpgradeService getService() {
            return UpgradeService.this;
        }
    }

    public void checkUpgrade() {
        RxVolley.get(Api.getURL(Api.UPGRADE), new GsonHttpCallback<Upgrade>() {
            @Override
            protected Class<Upgrade> getDataType() {
                return Upgrade.class;
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                
                
            }
        });

        ThreadSwitch.get().io(new ThreadSwitch.IO() {
            @Override
            public void run() {
                SystemTool.getAppVersionCode(App.INSTANCE);
            }
        }).ui(new ThreadSwitch.UI() {
            @Override
            public void run() {

            }
        });
    }
}
