package com.goertek.factorytest.camera;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.view.WindowManager;

import com.goertek.factorytest.R;
import com.goertek.factorytest.Utils;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class InfraredCamera extends Activity implements SurfaceHolder.Callback{
	private static final String TAG="InfraredCamera";
    private Button btnSuccess;
    private Button btnFailed;
	private Button buttonTakePicture;
	private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mPreviewRunning = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT); 
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        setContentView(R.layout.infrared_camera);
		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		
        mSurfaceHolder = mSurfaceView.getHolder();   
        mSurfaceHolder.addCallback(this);            
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        btnSuccess=(Button)findViewById(R.id.buttonSuccess);
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
                Utils.SetPreferences(InfraredCamera.this, sharedPreferences, R.string.infrared_camera, "success");
                finish();
            }
        });
		
		buttonTakePicture=(Button)findViewById(R.id.buttonTakePicture);
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonTakePicture.setEnabled(false);
                mCamera.takePicture(null,null,mPictureCallback);
            }
        });

        btnFailed=(Button)findViewById(R.id.buttonFailed);
        btnFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("FactoryMode", 0);
                Utils.SetPreferences(InfraredCamera.this, sharedPreferences, R.string.infrared_camera, "failed");
                finish();
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open(1);
        } catch (RuntimeException e) {
            Log.d(TAG, "open()方法有问题:" + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (mPreviewRunning) {
            mCamera.stopPreview();
        }
        Camera.Parameters p = mCamera.getParameters();
        p.setPreviewSize(width, height);
        mCamera.setParameters(p);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        mPreviewRunning = true;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {	
		mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] imageData, Camera c) {
        }
    };
}