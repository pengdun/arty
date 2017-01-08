package com.kymjs.arty.module.upgrade;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kymjs.arty.R;
import com.kymjs.arty.api.Api;
import com.kymjs.arty.bean.UpGradeMessage;
import com.kymjs.arty.utils.GsonHttpCallback;
import com.kymjs.common.App;
import com.kymjs.common.SystemTool;
import com.kymjs.common.function.ThreadSwitch;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;

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
     * 版本比对,下载
     *
     * @param data
     */
    private void checkRemoteVersion(final Upgrade.DataBean data) {
        int remoteVersionCode = 0;
        try {
            remoteVersionCode = Integer.parseInt(data.getVersionCode());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (remoteVersionCode < SystemTool.getAppVersionCode(this) && SystemTool.isWiFi(this)) {
            if (FileUtils.getExternalCacheDir(getString(R.string.down_app_name)).exists() && FileUtils.getExternalCacheDir(getString(R.string.down_app_name)).isFile()) {
                FileUtils.getExternalCacheDir(getString(R.string.down_app_name)).delete();
            }
            RxVolley.download(FileUtils.getExternalCacheDir(getString(R.string.down_app_name)).getAbsolutePath(), data.getUrl(), new ProgressListener() {
                @Override
                public void onProgress(long transferredBytes, long totalSize) {
                    Log.d("tag", transferredBytes + "");
                }
            }, new HttpCallback() {
                @Override
                public void onSuccess(String t) {
                    EventBus.getDefault().post(new UpGradeMessage("up"));
                    Log.e("tag", "下载完成");
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
