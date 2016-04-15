package org.joosung.dugong.goja.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.joosung.dugong.goja.HandlerUtil.*;
import org.joosung.dugong.goja.bean.*;
import org.joosung.dugong.goja.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private Handler handler;

    static ArrayList<String> getMsg = new ArrayList<>();
    static String serverName;

    static String ip = "70.12.111.96";
    static int port = 10010;

    EditText idText,passText;

    TextView idfalse,passfalse;
    Button btnLogin,btnJoin;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private static memberServerModel ms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("좋은 선택");

        idText = (EditText)findViewById(R.id.MainEdit1);
        passText = (EditText)findViewById(R.id.MainEdit2);
        ms = new memberServerModel();
        handler = new Handler();


        btnLogin = (Button) findViewById(R.id.MainBtnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRun login = new LoginRun();
                login.start();
            }
        });

        btnJoin = (Button)findViewById(R.id.MainBtnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
                idText.setText("");
                passText.setText("");
            }
        });

        idfalse = (TextView)findViewById(R.id.MainIdfalse);
        idfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MemberCheckActivity.class);
                intent.putExtra("call","1");

                startActivity(intent);
                idText.setText("");
                passText.setText("");
            }
        });


        passfalse = (TextView)findViewById(R.id.MainPassfalse);
        passfalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MemberCheckActivity.class);
                intent.putExtra("call","2");

                startActivity(intent);
                idText.setText("");
                passText.setText("");
            }
        });

    }//onCreate Method Close;






    /**
     * 소켓 셋팅
     */
    public class LoginRun extends Thread{
        @Override
        public void run() {
            try{
                View[] views= {btnJoin,btnLogin,idText,idfalse,passfalse,passText};
                toggleActivity toggle = new toggleActivity(views);
                handler.post(toggle.getstopActivity());
                Socket socket = new Socket(ip,port);

                serverName = socket.getInetAddress().getHostName();

                output = new ObjectOutputStream(socket.getOutputStream());

                ms.setId(idText.getText().toString());
                ms.setPass(passText.getText().toString());
                ms.setFunctionState(1);

                output.writeObject(ms);

                input = new ObjectInputStream(socket.getInputStream());
                ms = (memberServerModel)input.readObject();
                String msg="";
                msgToast msgToast;
                switch (ms.getFunctionState()){
                    case 1:
                        msg = ms.getName()+"님 환영합니다!";
                        msgToast= new msgToast(msg,getApplicationContext());
                        handler.post(msgToast);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), org.joosung.dugong.goja.search.SearchActivity.class);
                                intent.putExtra("user",ms);
                                startActivity(intent);
                            }
                        });
                        break;
                    default:
                        msg = "로그인에 실패 하셧습니다";
                        msgToast = new msgToast(msg,getApplicationContext());
                        handler.post(msgToast);
                        break;
                }

                handler.post(toggle.getstartActivity());
                socket.close();


            }catch(Exception e){
                Log.i("MyTag","MyError : " +e.toString());
                for(StackTraceElement se:e.getStackTrace()){
                    Log.i("MyTag", "MyError : " +se.toString());
                }
            }
        }//run method close;
    }//LoginRun ThreadClass close;

}//class Bottom
