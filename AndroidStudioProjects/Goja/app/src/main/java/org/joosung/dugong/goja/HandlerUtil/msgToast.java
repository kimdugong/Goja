package org.joosung.dugong.goja.HandlerUtil;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by student on 2016-03-31.
 */
public class msgToast implements Runnable {
    Context mContext;
    String msg;
    public msgToast(String msg,Context context){
        this.mContext = context;
        this.msg = msg;
    }
    @Override
    public void run() {
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }
}
