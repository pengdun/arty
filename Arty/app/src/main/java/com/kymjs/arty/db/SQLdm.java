package com.kymjs.arty.db;

/**
 * Created by ZhangTao on 12/26/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kymjs.common.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据库工具类
 */
public class SQLdm {

    public static SQLiteDatabase openDatabase(Context context, String assetFilePath) {
        File opemDB = context.getDatabasePath(assetFilePath);
        if (!opemDB.exists()) {
            return null;
        }
        return SQLiteDatabase.openOrCreateDatabase(opemDB, null);
    }

    /**
     * 复制assets文件中的数据库到包内存储区databases下
     *
     * @param assetFilePath asset中的相对路径
     * @throws IOException 流操作异常
     */
    public static void copyAssetDB(Context context, String assetFilePath) throws IOException {
        InputStream poemInputStream = null;
        OutputStream poemOutputStream = null;
        try {
            File opemDB = context.getDatabasePath(assetFilePath);
            if (!opemDB.exists()) {
                opemDB.getParentFile().mkdirs();
                opemDB.createNewFile();
            }
            poemOutputStream = new FileOutputStream(opemDB);
            poemInputStream = context.getAssets().open(assetFilePath);
            byte[] buffer = new byte[1024];
            int length = poemInputStream.read(buffer);
            while (length > 0) {
                poemOutputStream.write(buffer, 0, length);
                length = poemInputStream.read(buffer);
            }
            poemOutputStream.flush();
        } finally {
            FileUtils.closeIO(poemInputStream, poemOutputStream);
        }
    }
}  