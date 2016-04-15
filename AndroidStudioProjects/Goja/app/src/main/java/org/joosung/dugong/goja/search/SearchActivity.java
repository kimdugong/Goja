package org.joosung.dugong.goja.search;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.joosung.dugong.goja.DB_Manager.DataBaseHelper;
import org.joosung.dugong.goja.R;
import org.joosung.dugong.goja.menu_product.ProductSearchResultActivity;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Dugong on 3/31/16.
 */
public class SearchActivity extends Activity {


    static final String DB = "umsun.db";
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

    //private DBManager mDBManager;
    private SQLiteDatabase sdb;
    DataBaseHelper myDbHelper;
    private SQLiteDatabase db;

    //private DBinsert dBinsert;
    ListView listView;
    Button search, barcode;
    EditText editText;
    SimpleCursorAdapter mAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //위젯 생성

        listView = (ListView) findViewById(R.id.listView);
        search = (Button) findViewById(R.id.search);
        barcode = (Button) findViewById(R.id.barcode);
        editText = (EditText) findViewById(R.id.editText);

        //다른 곳에서 오는 검색 쿼리가 있는지??

        //바코드객체선언 아직 부르지는마

        final IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(AnyOrientation.class);
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setPrompt("네모안에 바코드가 들어가게 위치해주세요.");

        //mDBManager =DBManager.getInstance(this);
        //dBinsert = new DBinsert(this);

        //DB객체생성

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

        /*try {
            dBinsert.installDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dBinsert.openDataBase();*/

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
                    return search.isPressed() == true;
                }

                return false;

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pname = editText.getText().toString();

                String[] columns = {"_id", "name", "category", "brand", "barcode", "image"};
                String[] name = {"%" + pname + "%"};
                //Cursor c = mDBManager.query(PRODUCT,columns, "name like ?", name, null, null, null);
                Cursor c = myDbHelper.query(PRODUCT, columns, "name like ?", name, null, null, null);
                startManagingCursor(c);

                String[] listcolumns = new String[]{"name", "brand", "image"};
                int[] to = new int[]{R.id.name_entry, R.id.brand_entry, R.id.imageView};

                mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.listitem, c, listcolumns, to);

                mAdapter.setViewBinder(new CustimViewBinder());

                listView.setAdapter(mAdapter);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pname = editText.getText().toString();

                String[] columns = {"_id", "name", "category", "brand", "barcode", "image"};
                String[] name = {"%" + pname + "%"};
                //Cursor c = mDBManager.query(PRODUCT,columns, "name like ?", name, null, null, null);
                Cursor c = myDbHelper.query(PRODUCT, columns, "name like ?", name, null, null, null);
                startManagingCursor(c);

                String[] listcolumns = new String[]{"name", "brand", "image"};
                int[] to = new int[]{R.id.name_entry, R.id.brand_entry, R.id.imageView};

                mAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.listitem, c, listcolumns, to);

                mAdapter.setViewBinder(new CustimViewBinder());


                listView.setAdapter(mAdapter);


            }
        });

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integrator.initiateScan();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) mAdapter.getItem(position);
                int _id = c.getInt(0);


                Bundle bundle = new Bundle();
                bundle.putInt("_id", _id);

                Intent intent = new Intent(getApplicationContext(), ProductSearchResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        myDbHelper.close();
    }

    //바코드 결과값 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
        // QR코드/바코드를 스캔한 결과 값을 가져옵니다.
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            // 결과값 출력
            String barno = result.getContents();

            //번들로 보내기
            if(barno!=null) {
                Bundle bundle = new Bundle();
                bundle.putString("barcode", barno);

                Intent intent = new Intent(getApplicationContext(), ProductSearchResultActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.d("mytag", e.getLocalizedMessage());
        }


    }

    //리스트뷰에 사진을 넣기위한 커스텀 뷰바인더

    public class CustimViewBinder implements SimpleCursorAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {

            try {
                if (view instanceof ImageView) {
                    ImageView iv = (ImageView) view;
                    byte[] result = cursor.getBlob(5);//my image is stored as blob in db at 3
                    Bitmap bmp = BitmapFactory.decodeByteArray(result, 0, result.length);
                    iv.setImageBitmap(bmp);
                    return true;
                }

            } catch (Exception e) {
                Log.d("mytag", e.getMessage());
            }
            return false;
        }

    }
}