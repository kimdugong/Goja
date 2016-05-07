package org.joosung.dugong.goja.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joosung.dugong.goja.HandlerUtil.*;
import org.joosung.dugong.goja.bean.*;
import org.joosung.dugong.goja.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by student on 2016-03-31.
 */
public class MemberCheckActivity extends AppCompatActivity {
    String value;

    Handler hand = new Handler();

    private Button btn1,btn2;

    private EditText email1,email2,id;

    LinearLayout resultView;

    memberServerModel ms = new memberServerModel();

    private TextView top,goback;

    private LinearLayout l1,l2;

    LinearLayout.LayoutParams params;

    static String ip = "192.168.0.41";
    static int port = 10010;

    private toggleActivity toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membercheck);
        setTitle("좋은 선택");

        Intent getCall = getIntent();
        value = getCall.getStringExtra("call");
        resultView = (LinearLayout)findViewById(R.id.McResultView);

        params = new LinearLayout.LayoutParams
                ((int) ViewGroup.LayoutParams.WRAP_CONTENT,(int) ViewGroup.LayoutParams.WRAP_CONTENT);


        top = (TextView)findViewById(R.id.McFrontText);
        l1 = (LinearLayout)findViewById(R.id.McDefaultView);
        l2 = (LinearLayout)findViewById(R.id.McNextView);

        LayoutToggle(value);

        id = (EditText)findViewById(R.id.McIdinput);
        email1 =(EditText)findViewById(R.id.McEmailInput1);
        email2 = (EditText)findViewById(R.id.McEmailInput2);



        btn1 = (Button)findViewById(R.id.McBtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.removeAllViewsInLayout();
                ms.setFunctionState(4);
                ms.setEmail(email1.getText().toString());
                ServerRun serverRun = new ServerRun(value);
                serverRun.start();

            }
        });


        btn2 = (Button)findViewById(R.id.McBtn1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultView.removeAllViewsInLayout();
                ms.setFunctionState(5);
                ms.setId(id.getText().toString().trim());
                ms.setEmail(email2.getText().toString());
                ServerRun serverRun = new ServerRun(value);
                serverRun.start();
            }
        });

        goback = (TextView)findViewById(R.id.McGoback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 레이아웃의 키워드로 기능 전환
     * String 1이 들어오면 ID 찾기 ,String 2가 들어오면 Pass찾기
     * @param keyword
     */
     private void LayoutToggle(String keyword){
        switch (keyword){
            case "1":
                top.setText("ID를 조회합니다.");
                l2.setVisibility(View.INVISIBLE);
                l1.setVisibility(View.VISIBLE);
                break;

            case "2":
                top.setText("Password를 조회합니다.");
                l2.setVisibility(View.VISIBLE);
                l1.setVisibility(View.INVISIBLE);
                break;
        };

    }

     private class ServerRun extends Thread{
         private String code;
         public ServerRun(String value){
             code = value;
         }
         @Override
         public void run(){
             try {
                 View[] v = {btn1,btn2,email1,email2,id};
                 toggle = new toggleActivity(v);
                 hand.post(toggle.getstopActivity());
                 Socket socket = new Socket(ip, port);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                 Log.i("MyTag","sokect 생성 : "+socket.toString());
                 oos.writeObject(ms);

                 Log.i("MyTag", "sokect 생성 : " + "연결중...");

                 final ArrayList<memberServerModel> result = (ArrayList<memberServerModel>)ois.readObject();
                 Log.i("MyTag", result.toString());
                for(memberServerModel a:result){
                    Log.i("MyTag",a.toString());
                }
                 switch (result.size()){
                     case 0:
                         hand.post(new msgToast("조회에 실패하였습니다",getApplicationContext()));
                         break;
                     default:
                         hand.post(new Runnable() {
                             @Override
                             public void run() {
                                 postRun pr = new postRun(result,getApplicationContext());
                                 pr.run();
                             }
                         });
                         break;
                     

                 }
                socket.close();
                hand.post(toggle.getstartActivity());
             }catch(Exception e) {
                 Log.i("MyTag",e.toString());
                 for(StackTraceElement a :e.getStackTrace()){
                     Log.i("MyTag",a.toString());
                 }
                 hand.post(toggle.getstartActivity());
             }
         }
     }

    class TvClick implements View.OnClickListener{
        String s;
        public TvClick(String s){
            this.s=s;
        }

        @Override
        public void onClick(View v) {
            LayoutToggle("2");
            id.setText(s);
            resultView.removeAllViewsInLayout();
        }

    }

    class postRun implements Runnable{
        ArrayList<memberServerModel> result;
        Context con;
        public postRun(ArrayList<memberServerModel> result,Context context){
            this.result = result;
            con=context;
        }

        @Override
        public void run() {
            TextView los = new TextView(con);
            los.setText("조회된 결과....");
            los.setTextSize(20);
            los.setTextColor(Color.BLACK);
            los.setLayoutParams(params);
            resultView.addView(los);

            if(result.get(0).getFunctionState()==1){
                los.setText(los.getText().toString()+"\nPass를 찾으시려면 검색된 ID를 클릭해주세요");
                for(memberServerModel instanceModel:result){
                    TextView tv = new TextView(con);
                    tv.setText(instanceModel.getId());
                    tv.setOnClickListener(new TvClick(tv.getText().toString()));
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLUE);
                    tv.setLayoutParams(params);
                    resultView.addView(tv);
                }
            }else{
                for(memberServerModel instanceModel:result){
                    Log.i("MyTag",instanceModel.getPass());
                    los.setText(los.getText().toString()+"\n결과를 누르시면 로그인 창으로 이동합니다.");
                    TextView tv = new TextView(con);
                    tv.setText(instanceModel.getPass());
                    tv.setTextSize(20);
                    tv.setTextColor(Color.BLUE);
                    tv.setLayoutParams(params);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    resultView.addView(tv);
                }
            }
        }
    }

}
