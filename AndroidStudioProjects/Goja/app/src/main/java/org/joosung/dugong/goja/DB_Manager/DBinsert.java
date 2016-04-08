package org.joosung.dugong.goja.DB_Manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Dugong on 4/5/16.
 */
public class DBinsert extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/org.joosung.dugong.goja/databases/";
    private static String DB_NAME = "umsun.db";
    private SQLiteDatabase mydatabase;
    private final Context mContext;
    public static DBinsert dbinsert = null;

    public DBinsert(Context context) {
        super(context, DB_NAME, null, 1);
        mContext=context;
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void openDataBase(){
        String mypath = DB_PATH+DB_NAME;
        mydatabase =SQLiteDatabase.openDatabase(mypath,null,SQLiteDatabase.NO_LOCALIZED_COLLATORS);
    }

    public void installDB() throws IOException {

        SQLiteDatabase check = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            check = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);

        } catch (Exception e) {

        }
        if (check != null) {
            check.close();
        }
        boolean isExist = check != null ? true : false;
        if (isExist) {
        } else {
            this.getReadableDatabase();
            try {
                InputStream inputStream = mContext.getAssets().open(DB_NAME);
                String outPath = DB_PATH + DB_NAME;
                OutputStream outputStream = new FileOutputStream(outPath);
                byte[] buffer = new byte[2147483591];
                int length;

                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                throw new Error("에러남");
            }
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


}
