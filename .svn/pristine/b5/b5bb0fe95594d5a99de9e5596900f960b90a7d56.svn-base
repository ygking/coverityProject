package com.goertek.factorytest.microphone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.view.Window;
import android.content.SharedPreferences;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class FiveMIC extends Activity{
    public static final int FORWARD=0;
    public static final int LEFT=3;
    public static final int BACK=2;
    public static final int RIGHT=1;
    private ImageView forwardMic,midMic,leftMic,rightMic,backMic;
    private Button resetBtn;
    private IntentFilter intentFilter;
    private FiveMicReceiver fiveMicReceiver;
    public final static String ACTION_5MIC_FROM_MAIN="com.yongyida.robot.5MIC";
    public final static String ACTION_5MIC="com.yongyida.robot.VOICE";
    private boolean sendBroadcastFlag=true;
    public static final String TAG="FiveMIC";
	public static final String MIC5_SUCCESS="com.yongyida.robot.MIC5_SUCCESS";
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("jlog","onCreate()");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mic5_activity);
        init_view();
        init_filterBoradcast();
        sendBroadcastToMain();
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetAll();
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FORWARD:
                    forwardMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    midMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case LEFT:
                    leftMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    midMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case RIGHT:
                    rightMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    midMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case BACK:
                    backMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    midMic.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
            }
        }
    };

    class FiveMicReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ACTION_5MIC_FROM_MAIN)){
                String jsonResult=intent.getStringExtra("json");
                WakeUpInfo wakeUp = JsonParserUtils.parseResult(jsonResult, WakeUpInfo.class);
                Log.d("jlog", "json:" + jsonResult);
                if (wakeUp == null) {
                    return;
                }else {
                    Message message=new Message();
                    message.what=wakeUp.getBeam();
                    handler.sendMessage(message);
                }
                Log.d("jlog", wakeUp.getBeam()+"");
            }
        }
    }
    private void init_view(){
        forwardMic=(ImageView) findViewById(R.id.forward_mic);
        backMic=(ImageView)findViewById(R.id.back_mic);
        leftMic=(ImageView)findViewById(R.id.left_mic);
        rightMic=(ImageView)findViewById(R.id.right_mic);
        midMic=(ImageView)findViewById(R.id.mid_lamp);
        resetBtn=(Button)findViewById(R.id.reset_btn);
    }
    private void resetAll(){
        forwardMic.setBackground(getResources().getDrawable(R.drawable.light_white));
        leftMic.setBackground(getResources().getDrawable(R.drawable.light_white));
        rightMic.setBackground(getResources().getDrawable(R.drawable.light_white));
        midMic.setBackground(getResources().getDrawable(R.drawable.light_white));
        backMic.setBackground(getResources().getDrawable(R.drawable.light_white));
    }

    private void sendBroadcastToMain(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("jlog","-------->");
                while (sendBroadcastFlag){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(ACTION_5MIC);
                    intent.putExtra("data","wakeup_off");
                    sendBroadcast(intent);
                }
            }
        }).start();
    }
	public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.five_mic, "success");
        sendBroadcastFlag=false;
        sendOnToMain();
        finish();
	}
    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.five_mic, "failed");
		sendBroadcastFlag=false;
        sendOnToMain();
        finish();
		
    }
	@Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcastFlag=false;
        unregisterReceiver(fiveMicReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendBroadcastFlag=false;
        sendOnToMain();
    }

    private void init_filterBoradcast(){
        intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_5MIC_FROM_MAIN);
        fiveMicReceiver=new FiveMicReceiver();
        registerReceiver(fiveMicReceiver,intentFilter);
    }
    private void sendOnToMain(){
        Intent intent=new Intent(ACTION_5MIC);
        intent.putExtra("data","wakeup_on");
        sendBroadcast(intent);
    }
}
