package com.goertek.factorytest.microphone;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.SharedPreferences;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

/**
 * Created by sunyibin on 2016/6/28 0028.
 */
public class ProjectionMic extends Activity {
    private ImageView micView;
    private Button btnReset;
    private IntentFilter intentFilter;
    private MicReceiver micReceiver;
    public final static String ACTION_MIC_FROM_MAIN="com.yongyida.robot.5MIC";
    public final static String ACTION_MIC_TO_MAIN="com.yongyida.robot.VOICE";
    private boolean sendBroadcastFlag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projection_mic);
        micView=(ImageView)findViewById(R.id.projection_mic_image);
        btnReset=(Button)findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                micView.setBackground(getResources().getDrawable(R.drawable.light_white));
            }
        });
        initFilterBroadcast();
        sendBroadcastToMain();     //发送广播到主服务
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
                    Intent intent=new Intent(ACTION_MIC_TO_MAIN);
                    intent.putExtra("data","wakeup_off");
                    sendBroadcast(intent);
                }
            }
        }).start();
    }
    private void initFilterBroadcast(){
        intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION_MIC_FROM_MAIN);
        micReceiver=new MicReceiver();
        registerReceiver(micReceiver,intentFilter);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    micView.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case 1:
                    micView.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case 2:
                    micView.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
                case 3:
                    micView.setBackground(getResources().getDrawable(R.drawable.light_green));
                    break;
            }
        }
    };
    private class MicReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(ACTION_MIC_FROM_MAIN)){
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
    public void button009(View view){
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_mic, "success");
        sendBroadcastFlag=false;
        sendOnToMain();
        finish();
    }
    public void button010(View view){
		SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.projection_mic, "failed");
        sendBroadcastFlag=false;
        sendOnToMain();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendBroadcastFlag=false;
        unregisterReceiver(micReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sendBroadcastFlag=false;
        sendOnToMain();
    }
    private void sendOnToMain(){
        Intent intent=new Intent(ACTION_MIC_TO_MAIN);
        intent.putExtra("data","wakeup_on");
        sendBroadcast(intent);
    }
}
