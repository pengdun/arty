package com.kymjs.arty.db;

/**
 * Created by ZhangTao on 12/26/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kymjs.arty.Constant;
import com.kymjs.arty.bean.LocalPoem;
import com.kymjs.common.App;
import com.kymjs.common.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库工具类
 */
public class SQLdm {

    public static Map<String, LocalPoem> sLocalPoemMap = new HashMap<>();

    public static SQLiteDatabase openDatabase(Context context, String filePath) {
        File opemDB = context.getDatabasePath(filePath);
        if (!opemDB.exists()) {
            return null;
        }
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

    public static void initLocalPoemMap() {
        SQLiteDatabase database = SQLdm.openDatabase(App.INSTANCE, Constant.POEM_DB);
        if (database != null) {
            Cursor cursor = database.rawQuery(Constant.SQL_FINDPOEM,
                    new String[]{});
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                LocalPoem localPoem = new LocalPoem();
                try {
                    int id = cursor.getInt(0);
                    localPoem.setId(id);
                    localPoem.setName(cursor.getString(1));
                    localPoem.setPoet(cursor.getString(2));
                    localPoem.setVideo(cursor.getString(3));
                    localPoem.setDynasty(cursor.getString(4));
                    localPoem.setCountry(cursor.getString(5));
                    localPoem.setColumn(cursor.getString(6));
                    localPoem.setStage(cursor.getString(7));
                    localPoem.setTextbook(cursor.getString(8));
                    localPoem.setBook(cursor.getString(9));
                    localPoem.setFrom(cursor.getString(10));
                    localPoem.setDescription(cursor.getString(11));
                    localPoem.setOriginal(cursor.getString(12));
                    localPoem.setVoice(cursor.getString(13));
                    sLocalPoemMap.put(String.valueOf(id), localPoem);
                } catch (Exception e) {
                    //
                }
            }
            cursor.close();
        }
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