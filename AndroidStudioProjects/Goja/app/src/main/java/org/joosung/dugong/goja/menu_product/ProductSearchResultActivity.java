package org.joosung.dugong.goja.menu_product;

/**
 * Created by Dugong on 4/3/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joosung.dugong.goja.DB_Manager.DataBaseHelper;
import org.joosung.dugong.goja.R;

import java.io.IOException;
import java.sql.SQLException;

public class ProductSearchResultActivity extends Activity {

    static final String     DB     = "umsun.db";
    //카테고리테이블
    static final String     CATEGORY = "CATEGORY";
    //상품테이블
    static final String     PRODUCT  = "PRODUCT";
    //원재료테이블
    static final String     MAININGREDIENT ="MAININGREDIENT";
    //태그테이블
    static final String     TAG = "TAG";
    //재료테이블
    static final String     INGREDIENT = "INGREDIENT";
    //재료상세 테이블
    static final String     DETAIL = "DETAIL";

    //public DBManager mDBManager = null;
    DataBaseHelper myDbHelper;
    private SQLiteDatabase db;
    ImageView product_pic = null;
    EditText editText = null;
    TextView Productname=null;
    TextView brand = null;
    TextView Totalcontent = null;
    TextView eachcontent = null;
    Button button = null;
    Intent intent=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //mDBManager = DBManager.getInstance(this);
        myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        product_pic = (ImageView) findViewById(R.id.product_pic);
        brand = (TextView)findViewById(R.id.textView2);
        Productname = (TextView)findViewById(R.id.textView);
        eachcontent = (TextView)findViewById(R.id.textView4);
        //button =(Button)findViewById(R.id.button);
        intent = getIntent();
        Log.d("mytag", "id"+String.valueOf(intent.hasExtra("_id")));
        Log.d("mytag", "barcode"+String.valueOf(intent.hasExtra("barcode")));



        //intent의 id가 무엇인지에 따라서 분기
        if(intent.hasExtra("_id")){

            Bundle bundle = intent.getExtras();

            //리스트뷰 클릭으로 온 아이디값

            int _id = bundle.getInt("_id");
            String id=Integer.toString(_id);



            //id를 이용하여 상품정보 받기

            String[] key ={id};
            //상품테이블의 컬럼 선언
            String[] Product_columns = {"_id", "name", "category", "brand", "barcode","image"};
            //원재료테이블의 컬럼 선언
            String[] Main_columns = {"_id","mainingredient","content","origin"};
            //상품테이블을 담는 커서 선언
            Cursor Product_c = myDbHelper.query(PRODUCT,Product_columns, "_id = ?", key, null, null, null);
            //원재료테이블을 담는 커서 선언
            Cursor Main_c = myDbHelper.query(MAININGREDIENT,Main_columns,"_id = ?", key, null, null, null);

            try {

                StringBuilder stringBuilder=new StringBuilder();

                //원재료에 담긴 정보를 꺼내오겠습니다
                if(Main_c!=null){
                    while(Main_c.moveToNext()){
                        String mainingredient = Main_c.getString(1);
                        float content = Main_c.getFloat(2);
                        String origin = Main_c.getString(3);

                        stringBuilder.append(mainingredient);
                        stringBuilder.append("\n");
                        stringBuilder.append(" (");
                        stringBuilder.append(origin);
                        stringBuilder.append(") ");
                        stringBuilder.append(content);
                        stringBuilder.append("% \n");
                    }
                    Main_c.close();
                    eachcontent.setText(stringBuilder.toString());


                }
                String pname = null;
                if(Product_c!=null){
                    while(Product_c.moveToNext()){
                        //int _ID = Product_c.getInt(0);
                        pname = Product_c.getString(1);
                        int category = Product_c.getInt(2);
                        String pbrand = Product_c.getString(3);
                        //String barcode = Product_c.getString(4);
                        byte[] image = Product_c.getBlob(5);

                        Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);

                        product_pic.setImageBitmap(img);
                        Productname.setText(pname);
                        brand.setText(pbrand);

                    }
                    Toast.makeText(ProductSearchResultActivity.this, pname+" 상세정보", Toast.LENGTH_SHORT).show();
                    Product_c.close();
                }

            }catch (Exception e){
                Log.d("mytag",e.getMessage());}

        }else if(intent.hasExtra("barcode")){

            Bundle bundle = intent.getExtras();

            //barcode를 이용하여 상품정보 받기

            //바코드 스캔으로 온 바코드값

            String barcode = bundle.getString("barcode");

            Log.d("mytag",barcode);


            String[] bkey ={barcode};
            //상품테이블의 컬럼 선언
            String[] bProduct_columns = {"_id", "name", "category", "brand", "barcode","image"};

            //상품테이블을 담는 커서 선언
            Cursor bProduct_c = myDbHelper.query(PRODUCT, bProduct_columns, "barcode = ?", bkey, null, null, null);

            try {

                int getID=0;
                String pname = "";
                if(bProduct_c!=null){
                    while(bProduct_c.moveToNext()){
                        getID = bProduct_c.getInt(0);
                        pname = bProduct_c.getString(1);
                        int category = bProduct_c.getInt(2);
                        String pbrand = bProduct_c.getString(3);
                        //long barcode = bProduct_c.getLong(4);
                        byte[] image = bProduct_c.getBlob(5);

                        Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);

                        product_pic.setImageBitmap(img);
                        Productname.setText(pname);
                        brand.setText(pbrand);

                    }
                    Toast.makeText(ProductSearchResultActivity.this, pname+" 상세정보", Toast.LENGTH_SHORT).show();
                    bProduct_c.close();
                }
                String _bid =String.valueOf(getID);
                String[] bkey2 ={_bid};
                //원재료테이블의 컬럼 선언
                String[] bMain_columns = {"_id","mainingredient","content","origin"};
                //원재료테이블을 담는 커서 선언
                Cursor bMain_c = myDbHelper.query(MAININGREDIENT,bMain_columns,"_id = ?", bkey2, null, null, null);

                StringBuilder stringBuilder=new StringBuilder();

                //원재료에 담긴 정보를 꺼내오겠습니다
                if(bMain_c!=null){
                    while(bMain_c.moveToNext()){
                        String mainingredient = bMain_c.getString(1);
                        float content = bMain_c.getFloat(2);
                        String origin = bMain_c.getString(3);

                        stringBuilder.append(mainingredient);
                        stringBuilder.append("\n");
                        stringBuilder.append(" (");
                        stringBuilder.append(origin);
                        stringBuilder.append(") ");
                        stringBuilder.append(content);
                        stringBuilder.append("% \n");
                    }
                    bMain_c.close();
                    eachcontent.setText(stringBuilder.toString());


                }


            }catch (Exception e){
                Log.d("mytag",e.getMessage());}

        }

    }




}