package com.goertek.factorytest.otg;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StatFs;
import android.text.format.Formatter;
import android.widget.TextView;
import android.widget.ProgressBar;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class OTG extends Activity{
    private TextView textView;
    private File sdcardPath;
    private ProgressBar progressBar;
    private static  final int SCANNER_START=0;
    private static  final int SCANNER_FINISHI=1;
	
	private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCANNER_START:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case SCANNER_FINISHI:
                    progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otg);
		textView=(TextView)findViewById(R.id.txtView);
		
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_MEDIA_SCANNER_FINISHED);
        intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
        intentFilter.addDataScheme("file");
        registerReceiver(broadcastReceiver,intentFilter);
    }
	
	private final BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long availableBlocks=0;
            long blockSize=0;
            long totalBlocks=0;
            String action = intent.getAction();
            StringBuilder sb = new StringBuilder();
            if(action.equals(Intent.ACTION_MEDIA_SCANNER_FINISHED)){
                Message finshiMessage=new Message();
                finshiMessage.what=SCANNER_FINISHI;
                mHandler.sendMessage(finshiMessage);
                try{
                    sdcardPath= new File("/storage/usbotg/usbotg-sda1/");
                    StatFs stat = new StatFs(sdcardPath.getPath());
                    blockSize = stat.getBlockSize();
                    totalBlocks = stat.getBlockCount();
                    availableBlocks = stat.getAvailableBlocks();
                    String totalSize= Formatter.formatFileSize(context, blockSize * totalBlocks);
                    String availableSize=Formatter.formatFileSize(context, blockSize * availableBlocks);
					if(totalSize.equals("469 MB")||availableSize.equals("469MB")){
                        textView.setText("");
                    }else{
						sb.append("OTG外接U盘存储空间").append("\n\n");
						sb.append("总容量:").append(totalSize).append("\n\n");
						sb.append("剩余容量:").append(availableSize).append("\n\n");
						String str=sb.toString();
						textView.setText(str);
					}
                    
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(action.equals(Intent.ACTION_MEDIA_SCANNER_STARTED)){
                Message startMessage=new Message();
                startMessage.what=SCANNER_START;
                mHandler.sendMessage(startMessage);
            }
        }
    };
	
	    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver!=null){
            unregisterReceiver(broadcastReceiver);
        }
    }
	
	
    public void button001(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.otg, "success");
        finish();
    }

    public void button002(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
        Utils.SetPreferences(this, sharedPreferences, R.string.otg, "failed");
        finish();
    }
}
