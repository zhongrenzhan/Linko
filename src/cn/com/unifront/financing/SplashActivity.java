package cn.com.unifront.financing;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.linko.LinkoApplication;
import cn.com.unifront.linko.R;

public class SplashActivity extends Activity {
	private RelativeLayout mFinancingSplas;
	private SharedPreferences sp;
	private static final String TAG = "SplashActivity";
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
            Log.i(TAG, "RegestState:"+getRegestState()+"=== ProtectState:"+getProtectState());
			if (getRegestState()) {//user had regested
				if (getProtectState()) {
					Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(SplashActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			} else {
				Intent intent = new Intent(SplashActivity.this,RegistActivity.class);
				startActivity(intent);
				finish();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkoApplication app = (LinkoApplication) getApplication();
		sp = app.getDefaultSP();
		setContentView(R.layout.activity_financing_splash);
		mFinancingSplas = (RelativeLayout) this.findViewById(R.id.financing_splash);

		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		mFinancingSplas.setAnimation(aa);

		DbInitTask task = new DbInitTask();
		// starting one thread for init the database and delay to display financing MainActivity
		new Thread(task).start();
	}
   
	private class DbInitTask implements Runnable {

		@Override
		public void run() {
			SqlOperate operate = new SqlOperate(SplashActivity.this);
			Boolean isInitstate = sp.getBoolean("DB_INITIALIZED", false);
			if (!isInitstate) {
				operate.initDb();
				sp.edit().putBoolean("DB_INITIALIZED", true).commit();
			}
			//delay to send message for show MainActivity
			try {
                Thread.sleep(3000);
                handler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
	}
	
	private boolean getProtectState() {
        return sp.getBoolean("PROTECT_STATE", false);
    }

    private boolean getRegestState() {
        return sp.getBoolean("REGEST_STATE", false);
        
    }

}
