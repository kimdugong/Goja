package org.joosung.dugong.goja.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.joosung.dugong.goja.HandlerUtil.*;
import org.joosung.dugong.goja.bean.*;
import org.joosung.dugong.goja.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by student on 2016-03-31.
 */
public class JoinActivity extends AppCompatActivity {
    static String ip = "52.79.124.134";
    static int port = 10010;
    ObjectOutputStream output;
    ObjectInputStream input;


    msgToast msgtoast;

    Button btnIDCheck,btnJoin,btnGoBack;

    EditText editid,editpass,editpass2,editname,editphone,editemail;

    RadioGroup radioGroup;

    DatePicker datePicker;



    toggleActivity toggleActivity;

    String id,pass,pass2,name,phone,email,birthDay;
    String sex="1";
    int idCall=-1;
    private static memberServerModel ms;

    Handler handler= new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("좋은 선택");
        viewSetting();
        ms = new memberServerModel();
        ms.setSex(sex);
        RadioButton rbmale = (RadioButton)findViewById(R.id.JoinRadiomale);
        rbmale.setChecked(true);

        birthDay = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();



        btnIDCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editid.getText().toString();
                if(id==null||id.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요",Toast.LENGTH_LONG).show();
                }else{
                    IDCheck idCheck = new IDCheck();
                    idCheck.start();
                }
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idCall!=1){
                    Toast.makeText(getApplicationContext(),"아이디 중복을 확인해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                id = editid.getText().toString();
                pass = editpass.getText().toString();
                pass2 = editpass2.getText().toString();
                name = editname.getText().toString();
                phone = editphone.getText().toString();
                email = editemail.getText().toString();
                birthDay = datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth();
                if(!columnCheck()){return;}

                ms.setFunctionState(3);
                ms.setId(id); ms.setPass(pass); ms.setName(name);
                ms.setPhone(phone); ms.setEmail(email); ms.setBirthdate(birthDay);
                ms.setSex(sex);
                memberJoin memberjoin = new memberJoin();
                memberjoin.start();

            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.JoinRadiomale:
                        sex = "1";
                        ms.setSex(sex);
                        break;
                    case R.id.JoinRadiofemale:
                        sex = "2";
                        ms.setSex(sex);
                        break;
                }
            }
        });

    }//onCreate Method Close;

    /**
     * id 중복 체크 Thread Method입니다.
     */
    class IDCheck extends Thread{
        @Override
        public void run() {
            try{View[] v= {btnIDCheck,btnJoin,btnGoBack,editid};
                toggleActivity = new toggleActivity(v);
                handler.post(toggleActivity.getstopActivity());
            String msg = "";
            Socket socket = new Socket(ip,port);
                Log.i("MyTag", socket.toString());
                ms.setId(id);
                ms.setFunctionState(2);
                Log.i("MyTag", ms.toString());

                if(ms.getId().length()<6){
                    msgtoast = new msgToast("ID는 6글자 이상이어야 합니다",getApplicationContext());
                    handler.post(msgtoast);
                    handler.post(toggleActivity.getstartActivity());
                    return;
                }

                output = new ObjectOutputStream(socket.getOutputStream());
                  input = new ObjectInputStream(socket.getInputStream());


                Log.i("MyTag", input.toString() + "/" + output.toString());
                output.writeObject(ms);


                ms = (memberServerModel)input.readObject();
                switch(ms.getFunctionState()){
                    case 1:
                        idCall=1;
                        msgtoast = new msgToast("사용 가능 한 아이디 입니다",getApplicationContext());
                        handler.post(msgtoast);
                        break;
                    default:
                        idCall=-1;
                        msgtoast = new msgToast("사용이 불가능 한 아이디 입니다",getApplicationContext());
                        handler.post(msgtoast);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                editid.setText("");
                            }
                        });
                        break;
                }
                socket.close();
                handler.post(toggleActivity.getstartActivity());
            }catch (Exception e){
                Log.i("MyTag", "MyError : " + e.toString());
                for(StackTraceElement se:e.getStackTrace()){
                    Log.i("MyTag", "MyError : " +se.toString());
                }
                handler.post(toggleActivity.getstartActivity());
            }


        }
    }


    /**
     * 회원가입 Thread Method입니다.
     */
    class memberJoin extends Thread{
        @Override
        public void run() {
            try {
                View[] v = {btnIDCheck,btnJoin,btnGoBack,editid,editpass,editpass,editphone,editemail,editname,editpass2,radioGroup,datePicker};
                toggleActivity = new toggleActivity(v);
                handler.post(toggleActivity.getstopActivity());
                String msg = "";
                Socket socket = new Socket(ip,port);
                Log.i("MyTag",socket.toString());

                Log.i("MyTag", ms.toString());


                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());


                Log.i("MyTag", input.toString() + "/" + output.toString());
                output.writeObject(ms);

                ms = (memberServerModel)input.readObject();

                switch (ms.getFunctionState()){
                    case 1:
                        handler.post(new msgToast("회원 가입에 성공하셧습니다",getApplicationContext()));
                        handler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             finish();
                                         }
                                     });
                        break;
                    default:
                        handler.post(new msgToast("회원 가입 실패, 다시 시도 해주세요",getApplicationContext()));

                        break;
                }

                socket.close();
                handler.post(toggleActivity.getstartActivity());
            }catch (Exception e){
                handler.post(new msgToast("회원 가입 실패, 다시 시도 해주세요",getApplicationContext()));
                handler.post(toggleActivity.getstartActivity());
                Log.i("MyTag", "MyError : " + e.toString());
                for(StackTraceElement se:e.getStackTrace()){
                    Log.i("MyTag", "MyError : " +se.toString());
                }
            }
        }
    }


    /**
     * 항목간 검사로직을 구현함
     */
    public boolean columnCheck(){
        String[] columns = {id,pass,pass2,name,phone,email,birthDay};
        for(String s:columns){
            if(s.isEmpty()||s==null){
                Toast.makeText(getApplicationContext(),"비어 있는 항목이 있습니다",Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if(pass.length()<6||id.length()<6){
            Toast.makeText(getApplicationContext(),"아이디/패스워드는 6자 이상이어야 합니다",Toast.LENGTH_LONG).show();
            return false;
        }

        if(!pass.equals(pass2)){
            Toast.makeText(getApplicationContext(),"패스워드가 일치 하지 않습니다.",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }//columnCheck close;

    /**
     * 이 액티비티의 모든 유저 조작 view를 셋팅한다.
     */
    public void viewSetting(){
        editid = (EditText)findViewById(R.id.JoinEditId);
        editpass = (EditText)findViewById(R.id.JoinEditPass);
        editpass2 = (EditText)findViewById(R.id.JoinEditPass2);
        editname = (EditText)findViewById(R.id.JoinEditName);
        editphone = (EditText)findViewById(R.id.JoinEditPhone);
        editemail = (EditText)findViewById(R.id.JoinEditEmail);

        radioGroup = (RadioGroup)findViewById(R.id.JoinRgmf);

        datePicker = (DatePicker)findViewById(R.id.JoinDateSelector);

        btnIDCheck = (Button)findViewById(R.id.JoinBtn1);
        btnJoin = (Button)findViewById(R.id.JoinBtn2);
        btnGoBack = (Button)findViewById(R.id.JoinBtn3);
    }




}//class close;
