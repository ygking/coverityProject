package com.goertek.factorytest.memory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.goertek.factorytest.R;
import com.goertek.factorytest.ShellExe;
import com.goertek.factorytest.Utils;

import java.io.File;
import java.io.IOException;

import static com.goertek.factorytest.ShellExe.execCommand;

public class Memory extends Activity
  implements OnClickListener
{
    private String TAG = "yxyx_Memory";
  private TextView mBtFailed;
  private TextView mBtOk;
  private TextView mCommInfo;
  SharedPreferences mSp;

  private String getInfo(String paramString)
  {
      StringBuilder sb = new StringBuilder();
      long blockSize = 0;
      long blockCount = 0;
      long availCount = 0;
      long totalSize = 0;
      long availSize = 0;

     //StatFs rootsf = new StatFs(Environment.getRootDirectory().getPath()); 
     StatFs rootsf = new StatFs(Environment.getExternalStorageDirectory().getPath());
     //Environment.getDataDirectory().getPath()
      blockSize = rootsf.getBlockSize();  
      blockCount = rootsf.getBlockCount();  
      availCount = rootsf.getAvailableBlocks(); 
      totalSize = blockCount * blockSize / 1024L / 1024L;
      availSize = availCount * blockSize / 1024L / 1024L;
      sb.append(this.getString(R.string.internal_memory)).append("\n\n");
      sb.append(this.getString(R.string.sdcard_totalsize)).append(totalSize).append("MB").append("\n\n");
      sb.append(this.getString(R.string.sdcard_freesize)).append(availSize).append("MB").append("\n\n");
      
      return sb.toString();
  }


    private String getRamInfo(String cmd){
      String command = cmd;
      String str = "";

      try {
          int ret = ShellExe.execCommand(command);
          Log.d(TAG,"getRamInfo ret="+ret);
          if(ret == 0){
              str = ShellExe.getOutput();
          }
      }catch (IOException exception){
          exception.printStackTrace();
      }
      return str;
  }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        super.onKeyDown(keyCode,keyEvent);
        Log.d(TAG,"keyCode :"+keyCode);

        return true;
    }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    int j = paramView.getId();
      Log.d(TAG,"onClick viewId :"+j);
    int k = this.mBtOk.getId();
    int kk = this.mBtFailed.getId();
    if (j == k)
    {
       Utils.SetPreferences(this, localSharedPreferences, R.string.memory_name, "success");
       finish();
    }
    if (j == kk)
    {
    	Utils.SetPreferences(this, localSharedPreferences, R.string.memory_name, "failed");
    	 finish();
    }
   
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
      Log.d(TAG,"onCreate");
    setContentView(R.layout.memory);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    TextView localTextView1 = (TextView)findViewById(R.id.memory_bt_ok);
    this.mBtOk = localTextView1;
    this.mBtOk.setOnClickListener(this);
    TextView localTextView2 = (TextView)findViewById(R.id.memory_bt_failed);
    this.mBtFailed = localTextView2;
    this.mBtFailed.setOnClickListener(this);
    TextView localTextView3 = (TextView)findViewById(R.id.comm_info);
    this.mCommInfo = localTextView3;
    TextView localTextView4 = this.mCommInfo;
    String str = getInfo(" ");
    String ram2 = getRamInfo( "cat /proc/meminfo");
    String[] ram2List= ram2.split(" ");
    String ram="";
      if(ram2List.length>=9) {
         ram = "RAM大小：  " + ram2List[8]+" kB" ;
    }
    Log.d(TAG,"ram2 length="+ram2List.length);
    /*for(int i=0;i<ram2List.length;i++){
        Log.d(TAG,"i="+i+" "+ram2List[i]);
    }*/
    str = str + ram;
    localTextView4.setText(str);

  }
}