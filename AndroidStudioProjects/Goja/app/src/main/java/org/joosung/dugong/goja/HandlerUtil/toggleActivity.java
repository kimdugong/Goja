package org.joosung.dugong.goja.HandlerUtil;

import android.view.View;

/**
 * Created by student on 2016-04-07.
 */
public class toggleActivity{
    private View[] toggleActivitys;

    public toggleActivity(View[] v){
        toggleActivitys = v;
    }

    public void setView(View[] v){
        toggleActivitys = v;
    }


    public stopActivity getstopActivity(){
        return new stopActivity(toggleActivitys);
    }
    public startActivity getstartActivity(){
        return new startActivity(toggleActivitys);
    }


    private class stopActivity implements Runnable {
        View[] toggleView;
        public stopActivity(View view[]){
            this.toggleView = view;
        }
        @Override
        public void run() {
            for(View v:toggleView){
                try{
                    v.setClickable(false);
                    v.setEnabled(false);
                }catch(Exception e){}
            }
        }
    }

    private class startActivity implements Runnable {
        View[] toggleView;
        public startActivity(View[] view){
            this.toggleView = view;
        }
        @Override
        public void run() {
            for(View v:toggleView){
                try{
                v.setClickable(true);
                v.setEnabled(true);
                }catch(Exception e){}
            }
        }
    }

}
