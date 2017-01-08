package com.kymjs.arty.module.upgrade;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kymjs.arty.api.Api;
import com.kymjs.arty.bean.UpGradeMessage;
import com.kymjs.arty.utils.GsonHttpCallback;
import com.kymjs.common.App;
import com.kymjs.common.SystemTool;
import com.kymjs.common.function.ThreadSwitch;
import com.kymjs.rxvolley.RxVolley;

import org.greenrobot.eventbus.EventBus;

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

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        checkUpgrade();
        return 2;
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
                checkRemoteVersion(data.getData());

            }
        });
    }

    /**
     * 版本比对
     *
     * @param data
     */
    private void checkRemoteVersion(Upgrade.DataBean data) {
        int remoteVersionCode = 0;
        try {
            remoteVersionCode = Integer.parseInt(data.getVersionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (remoteVersionCode < SystemTool.getAppVersionCode(this)) ;
        EventBus.getDefault().post(new UpGradeMessage(data));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
