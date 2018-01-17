package com.goertek.factorytest.signal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

public class Signal extends Activity implements OnClickListener {

	private Button mBtFailed;
	private Button mBtOk;
	public SharedPreferences mSp;

	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		if (paramView.getId() == this.mBtOk.getId()) {
			Utils.SetPreferences(this, localSharedPreferences, R.string.telephone_name, "success");
			finish();
		} else {
			Utils.SetPreferences(this, localSharedPreferences, R.string.telephone_name, "failed");
			finish();
		}
	}

	public void button001(View view) {
		Uri localUri = Uri.fromParts("tel", "10086", null);
		Intent localIntent = new Intent(Intent.ACTION_DIAL, localUri);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		startActivity(localIntent);
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.signal);
		this.mSp = getSharedPreferences("FactoryMode", 0);
		this.mBtOk = (Button) findViewById(R.id.signal_bt_ok);
		this.mBtOk.setOnClickListener(this);
		this.mBtFailed = (Button) findViewById(R.id.signal_bt_failed);
		this.mBtFailed.setOnClickListener(this);
	}
}
