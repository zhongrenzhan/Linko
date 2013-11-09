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
import cn.com.unifront.linko.R;

public class SplashActivity extends Activity {
	private RelativeLayout mFinancingSplas;
	private SharedPreferences sp;
	private Handler handler = new Handler() {

        private String tag = "防护状态";

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			sp = getSharedPreferences("config", Context.MODE_PRIVATE);
			if (getRegestState()) {// 判断是否已经注册过
				if (getProtectState()) {// 判断是否开启密码防护，如果开启则进入登陆界面
					Intent intent = new Intent(SplashActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				} else { // 判断是否开启密码防护，如果没开启则进入主界面
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}

			} else {// 如果没有进行注册过，则进入注册页面
				Intent intent = new Intent(SplashActivity.this,
						RegistActivity.class);
				startActivity(intent);
				finish();
			}
		}

		/**
		 * 获取密码防护的状态
		 * 
		 */
		private boolean getProtectState() {
			String state = sp.getString("protect_state", "");
			Log.i(tag, "" + state);
			if ("1".equals(state)) {
				return true;
			} else {
				return false;
			}

		}

		/**
		 * 获取是否进行过注册
		 * 
		 */
		private boolean getRegestState() {
			String state = sp.getString("regested", "");

			if (state != "") {
				return true;
			} else {
				return false;
			}
		}
	};
	private String tag="版本：";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
		setContentView(R.layout.activity_financing_splash);
		mFinancingSplas = (RelativeLayout) this.findViewById(R.id.financing_splash);

		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		mFinancingSplas.setAnimation(aa);

		dbInitTask task = new dbInitTask();
		new Thread(task).start();// 开启子线程去初始化数据库和延时跳转页面
		//createShortCut();
	}
	
private void createShortCut() {
		
		if(!isExist()){
			Intent intent = new Intent();
    		//指定动作名称
    		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
    		//指定快捷方式的图标
    		Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
    		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
    		//指定快捷方式的名称
    		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "家庭理财");
    		
    		//指定快捷图标激活哪个activity
    		Intent i = new Intent();
    		i.setAction(Intent.ACTION_MAIN);
    		i.addCategory(Intent.CATEGORY_LAUNCHER);
    		ComponentName component = new ComponentName(this, SplashActivity.class);
    		i.setComponent(component);
    		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
    		sendBroadcast(intent);
		}
		
	}

	private boolean isExist(){
    	boolean isExist = false;
    	int version = getSdkVersion();
    	Uri uri = null;
    	Log.i(tag, ""+version);
//    	if(version < 2.0){
//    		
//    	}else{
//    		uri = Uri.parse("content://com.android.launcher2.settings/favorites");
//    	}
    	uri = Uri.parse("content://com.android.launcher.settings/favorites");
    	String selection = " title = ?";
    	String[] selectionArgs = new String[]{"家庭理财"};
    	Cursor c = getContentResolver().query(uri, null, selection, selectionArgs, null);
    	if(c.getCount() > 0){
    		isExist = true;
    	}
    	c.close();
    	return isExist;
    	
    }

	/**
     * 得到当前系统sdk版本
     */
    private int getSdkVersion(){
    	return android.os.Build.VERSION.SDK_INT;
    }
    
    
	private class dbInitTask implements Runnable {

		@Override
		public void run() {
			SqlOperate operate = new SqlOperate(SplashActivity.this);

			sp = getSharedPreferences("config", Context.MODE_APPEND);
			String initstate = sp.getString("initialized", "3");
			if ("1".equals(initstate)) {// 数据库已经初始化过
				try {
					Thread.sleep(3000);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {// 数据库没有初始化过，则进行初始化

				operate.initdb();
				Editor editor = sp.edit();
				editor.putString("initialized", "1");
				editor.commit();

				try {
					Thread.sleep(3000);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
