package org.joosung.dugong.goja.DB_Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dugong on 3/30/16.
 */
public class DBManager extends SQLiteOpenHelper {

    // DB 명, 테이블 명, DB 버전 정보를 정의한다.
    // ========================================================================
    //데이터베이스명은 "umsun.db"로함
    static final String DB = "goja.db";
    //카테고리테이블
    static final String CATEGORY = "CATEGORY";
    //상품테이블
    static final String PRODUCT = "PRODUCT";
    //원재료테이블
    static final String MAININGREDIENT = "MAININGREDIENT";
    //태그테이블
    static final String TAG = "TAG";
    //재료테이블
    static final String INGREDIENT = "INGREDIENT";
    //재료상세 테이블
    static final String DETAIL = "DETAIL";


    static final int DB_VERSION = 11;

    // ========================================================================
    Context mContext = null;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        context = mContext;
    }

    public static DBManager mDbManager = null;

    // DB 매니저 객체는 싱글톤(singleton)으로 구현하도록 한다.
    // ========================================================================
    public static DBManager getInstance(Context context) {
        if (mDbManager == null) {
            mDbManager = new DBManager(context,
                    DB,
                    null,
                    DB_VERSION);
        }

        return mDbManager;
    }
    // ========================================================================


    @Override
    public void onCreate(SQLiteDatabase db) {
/*
        // Enable foreign key constraints

        db.execSQL("PRAGMA foreign_keys=ON;");

        //CATEGORY 테이블을 만든다
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CATEGORY +
                "   (category INTEGER, detail TEXT NOT NULL, PRIMARY KEY(category));");

        //PRODUCT 테이블을 만든다
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PRODUCT +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " category INTEGER,name TEXT NOT NULL UNIQUE, brand TEXT, barcode INTEGER UNIQUE, " +
                "image BLOB NOT NULL, FOREIGN KEY(category) REFERENCES CATEGORY(category));");
        //mainingredient 테이블을 만든다
        db.execSQL("CREATE TABLE IF NOT EXISTS " + MAININGREDIENT +
                " (_id INTEGER, mainingredient TEXT, content TEXT NOT NULL, origin TEXT NOT NULL, " +
                "PRIMARY KEY(_id,mainingredient), FOREIGN KEY(_id) REFERENCES PRODUCT(_id));  ");
        //tag 테이블을 만든다.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TAG +
                " (_id INTEGER, tag TEXT, PRIMARY KEY(_id,tag), " +
                "FOREIGN KEY (_id) REFERENCES PRODUCT(_id));");
        //detail 테이블을 만든다.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DETAIL +
                " (ingredient TEXT, grade INTEGER NOT NULL DEFAULT 1, " +
                "info1 TEXT NOT NULL, info2 TEXT, info3 TEXT, PRIMARY KEY(ingredient));");
        //ingredient 테이블을 만든다.
        db.execSQL("CREATE TABLE IF NOT EXISTS " + INGREDIENT +
                " (_id INTEGER, ingredient TEXT, PRIMARY KEY(_id,ingredient), " +
                "FOREIGN KEY(_id) REFERENCES PRODUCT(_id), " +
                "FOREIGN KEY(ingredient) REFERENCES detail(ingredient));");





        *//*

        CATEGORY 1 장류 데이터

         *//*

        db.execSQL("insert into category values(1,'장류');");


        //1

        //상품테이블 등록(null(_id는 autoincreament되어 있으므로),category,name,brand,barcode)
        db.execSQL("insert into product values (null,1,'CJ태양초골드고추장 1KG','씨제이제일제당',8801007052496);");
        //원재료테이블 등록(_id,main,mainingredient,content,origin)
        db.execSQL("insert into mainingredient values (1,'고춧가루','2.0','국산');");
        db.execSQL("insert into mainingredient values (1,'고추양념-고춧가루','9.3','중국산');");
        //태그테이블 등록 (_id,tag)
        db.execSQL("insert into tag values (1,'고추장');");
        db.execSQL("insert into tag values (1,'CJ');");
        db.execSQL("insert into tag values (1,'씨제이');");
        db.execSQL("insert into tag values(1,'시제이');");
        db.execSQL("insert into tag values (1,'태양초');");
        db.execSQL("insert into tag values (1,'제일제당');");
        db.execSQL("insert into tag values (1,'고추');");
        db.execSQL("insert into tag values (1,'고추가루');");
        db.execSQL("insert into tag values (1,'고춧가루');");
        //재료상세테이블 등록 (ingredient,grade,info1,info2)


        //재료테이블 등록 (_id,ingredient)
        db.execSQL("insert into ingredient values (1,'물엿');");
        db.execSQL("insert into ingredient values (1,'고추양념');");
        db.execSQL("insert into ingredient values (1,'소맥분');");
        db.execSQL("insert into ingredient values (1,'정제수');");
        db.execSQL("insert into ingredient values (1,'밀쌀');");
        db.execSQL("insert into ingredient values (1,'정제소금');");
        db.execSQL("insert into ingredient values (1,'고춧가루');");
        db.execSQL("insert into ingredient values (1,'주정');");
        db.execSQL("insert into ingredient values (1,'찹쌀');");
        db.execSQL("insert into ingredient values (1,'메주가루');");
        db.execSQL("insert into ingredient values (1,'혼합미분');");
        db.execSQL("insert into ingredient values (1,'종국');");


        //2
        db.execSQL("insert into product values (null,1,'순창고추로 만든 100% 국산 고추장 1KG','청정원',8801052742588);");
        //3
        db.execSQL("insert into product values (null,1,'순창 궁 우리햅쌀고추장 500G','해표',8801075009996);");
        //4
        db.execSQL("insert into product values (null,1,'신송고추장 -17% 1KG','신송',8801161249541);");*/
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {
        /*if (oldVersion < newVersion) {
            // 기존 테이블을 삭제하고 새로운 테이블을 생성한다.
            // ================================================================
            db.execSQL("DROP TABLE IF EXISTS " + INGREDIENT);
            db.execSQL("DROP TABLE IF EXISTS " + DETAIL);
            db.execSQL("DROP TABLE IF EXISTS " + TAG);
            db.execSQL("DROP TABLE IF EXISTS " + MAININGREDIENT);
            db.execSQL("DROP TABLE IF EXISTS " + PRODUCT);
            db.execSQL("DROP TABLE IF EXISTS " + CATEGORY);
            onCreate(db);
            // ================================================================
        }*/
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return getReadableDatabase().query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    /*public ArrayList<ProductDTO> getAllInfo() {
        ArrayList<ProductDTO> info = new ArrayList<ProductDTO>();
        String[] columns = {"productID", "category", "brand", "barcode", "name"};
        Cursor c = mDbManager.query(TABLE_NAME, new String[] {ID, MEETING, TIME}, null, null, null, null, ID + " DESC");

        if (c.moveToFirst()) {
            final int indexId = c.getColumnIndex(ID);
            final int indexMeeting = c.getColumnIndex(MEETING);
            final int indexTime = c.getColumnIndex(TIME);

            do {
                int id = c.getInt(indexId);
                String meeting = c.getString(indexMeeting);
                String time = c.getString(indexTime);
                info.add(new ProductDTO(id, meeting, time));
            } while (c.moveToNext());
        }
        c.close();

        return info;
    }*/

}

