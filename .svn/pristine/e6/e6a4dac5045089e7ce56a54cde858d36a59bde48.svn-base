package com.goertek.factorytest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.view.WindowManager;

public class KeyCode extends Activity
  implements OnClickListener
{
  final int[] imgString;
  Button mBtFailed;
  Button mBtOk;
  private GridView mGrid;
  TextView mInfo;

  private List mListData;
  private SharedPreferences mSp;

  public KeyCode()
  {
    int[] arrayOfInt = { R.drawable.home,  R.drawable.menu,  R.drawable.vldown,  R.drawable.vlup,  
			R.drawable.back,  R.drawable.search,  R.drawable.camera,  R.drawable.sos, 
			R.drawable.call, R.drawable.unknown, R.drawable.unknown };
   this.imgString = arrayOfInt;
}

  public void onAttachedToWindow()
  {
    getWindow().setType(2009);
    super.onAttachedToWindow();
  }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    int i = R.string.KeyCode_name;
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, i, "success");
        finish();
    }else{
        Utils.SetPreferences(this, localSharedPreferences, i, "failed");
        finish();
    }
   
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.keycode);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    onAttachedToWindow();
    TextView localTextView = (TextView)findViewById(R.id.keycode_info);
    this.mInfo = localTextView;
    Button localButton1 = (Button)findViewById(R.id.keycode_bt_ok);
    this.mBtOk = localButton1;
    this.mBtOk.setOnClickListener(this);
    Button localButton2 = (Button)findViewById(R.id.keycode_bt_failed);
    this.mBtFailed = localButton2;
    this.mBtFailed.setOnClickListener(this);
    ArrayList localArrayList = new ArrayList();
    this.mListData = localArrayList;
    GridView localGridView = (GridView)findViewById(R.id.keycode_grid);
    this.mGrid = localGridView;

    Window window = getWindow(); 
//    window.addFlags(WindowManager.LayoutParams.FLAG_HOMEKEY_DISPATCHED);   FIXME
  }

  public boolean onKeyDown(int keycode, KeyEvent keyevent)
  {
	int i = 10;
	int a = 0;
	Integer localInteger = null;
	MyAdapter localMyAdapter = new MyAdapter(this);
	mGrid.setAdapter(localMyAdapter);

	switch (keycode) {
	case KeyEvent.KEYCODE_CAMERA:
		i = 6;
	break;
	case KeyEvent.KEYCODE_HOME:
		i = 0;
	break;
	case KeyEvent.KEYCODE_VOLUME_DOWN:
		i = 2;
	break;
	case KeyEvent.KEYCODE_VOLUME_UP:
		i = 3;
	break;
	case KeyEvent.KEYCODE_BACK:
		i = 4;
	break;
	case KeyEvent.KEYCODE_MENU:
		i = 1;
	break;
	case KeyEvent.KEYCODE_SEARCH:
		i = 5;
	break;
	case KeyEvent.KEYCODE_SYM:
		i = 7;
	break;
	case KeyEvent.KEYCODE_CALL: 
		i = 8;
	break;
	case 0://ppt
		i = 9;
	break;
	default:
		i = 10;
		break;
	}
	if (i != 10 && localMyAdapter.getCount() < 30) {
		localInteger =  Integer.valueOf(this.imgString[i]);
		mListData.add(localInteger);
	}
    return true;
  }

  public class MyAdapter extends BaseAdapter
  {
    private LayoutInflater mInflater;

    public MyAdapter(Context arg2)
    {
      //Context localContext;
      LayoutInflater localLayoutInflater = LayoutInflater.from(arg2);
      this.mInflater = localLayoutInflater;
    }

    public MyAdapter(FactoryMode paramInt, int arg3)
    {
    }

    public int getCount()
    {
    	int count = KeyCode.this.mListData.size();
      /*Object localObject = KeyCode.this.mListData;
      if (localObject == null);
      for (localObject = null; ; localObject = KeyCode.this.mListData.size())*/
        return count;
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = this.mInflater.inflate(R.layout.keycode_grid, null);
      ImageView localImageView = (ImageView)localView.findViewById(R.id.imgview);
      int i = ((Integer)KeyCode.this.mListData.get(paramInt)).intValue();
      localImageView.setBackgroundResource(i);
      return localView;
    }
  }
}
