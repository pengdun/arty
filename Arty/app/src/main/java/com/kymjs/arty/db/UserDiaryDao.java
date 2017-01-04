package com.kymjs.arty.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kymjs.arty.Constant;
import com.kymjs.arty.module.diary.Diary;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangTao on 1/4/17.
 */

public class UserDiaryDao extends SQLiteOpenHelper {

    private static final int DIARY_DB_VERSION = 1;
    private static final String USER_DIARY_NAME = " diary ";

    public static final String CREATE_DIARY_TABLE = " create table "
            + USER_DIARY_NAME
            + " (_id integer primary key autoincrement not null, user_id long, poet text,"
            + " title text, content text, create_time datetime not null)";

    public UserDiaryDao(Context context) {
        super(context, Constant.DIARY_DB, null, DIARY_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    /**
     * 增 
     *
     * @param data
     */
    public boolean insert(Diary data) {
        if (data == null) {
            return false;
        }
        try {
            String sql = "insert into " + UserDiaryDao.USER_DIARY_NAME +
                    "(user_id, poet, title, content, create_time)" +
                    "values(?, ?, ?, ?, ?)";

            if (data.getCreateTime() == null) {
                data.setCreateTime(new Timestamp(new Date().getTime()));
            }

            SQLiteDatabase sqlite = getWritableDatabase();
            sqlite.execSQL(sql, new String[]{
                    data.getUserId() + "", data.getPoet(), data.getTitle(),
                    data.getContent(), data.getCreateTime().toString()});
            sqlite.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存一条数据到本地(若已存在则直接覆盖)
     *
     * @param data
     */
    public boolean save(Diary data) {
        if (data == null) {
            return false;
        }
        if (data.getId() == 0) {
            return insert(data);
        }
        try {
            String sql = "replace into " + UserDiaryDao.USER_DIARY_NAME +
                    "(user_id, poet, title, content, create_time)" +
                    "values(?, ?, ?, ?, ?, ?, ?)";

            if (data.getCreateTime() == null) {
                data.setCreateTime(new Timestamp(new Date().getTime()));
            }

            SQLiteDatabase sqlite = getWritableDatabase();
            sqlite.execSQL(sql, new String[]{
                    data.getUserId() + "", data.getPoet(), data.getTitle(),
                    data.getContent(), data.getCreateTime().toString()});
            sqlite.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 改
     *
     * @param data
     */
    public boolean update(Diary data) {
        if (data == null) {
            return false;
        }
        try {
            SQLiteDatabase sqlite = getWritableDatabase();
            String sql = ("update " + USER_DIARY_NAME + " set user_id=?, poet=?, " +
                    "title=?, content=?, create_time=? where _id=?");

            if (data.getCreateTime() == null) {
                data.setCreateTime(new Timestamp(new Date().getTime()));
            }

            sqlite.execSQL(sql, new String[]{
                    data.getUserId() + "", data.getPoet(), data.getTitle(),
                    data.getContent(), data.getCreateTime().toString(), data.getId() + ""});
            sqlite.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update(String set) {
        SQLiteDatabase sqlite = getWritableDatabase();
        sqlite.execSQL("update " + USER_DIARY_NAME + set);
        sqlite.close();
    }

    /**
     * 查
     */
    public List<Diary> query(String where) {
        SQLiteDatabase sqlite = getReadableDatabase();
        Cursor cursor = sqlite.rawQuery("select * from " + USER_DIARY_NAME + where, null);
        List<Diary> datas = getListFromCursor(cursor);
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return datas;
    }

    private List<Diary> getListFromCursor(Cursor cursor) {
        ArrayList<Diary> datas = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Diary msg = new Diary();
            msg.setId(cursor.getLong(0));
            msg.setUserId(cursor.getLong(1));
            msg.setPoet(cursor.getString(2));
            msg.setTitle(cursor.getString(3));
            msg.setContent(cursor.getString(4));
            try {
                msg.setCreateTime(Timestamp.valueOf(cursor.getString(5)));
            } catch (Exception e) {
                msg.setCreateTime(new Timestamp(System.currentTimeMillis()));
            }
            datas.add(msg);
        }
        return datas;
    }
}
