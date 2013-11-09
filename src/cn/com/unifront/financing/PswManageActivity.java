package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.com.unifront.linko.R;

public class PswManageActivity extends Activity implements OnClickListener {
	private Button bt_reset_psw;
	private Button bt_clear_psw;
	private Button bt_cancle;
	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_psw_manage);
		findView();
		registerListener();

	}

	/**
	 * 注册按钮点击事件
	 */
	private void registerListener() {
		bt_reset_psw.setOnClickListener(this);
		bt_clear_psw.setOnClickListener(this);
		bt_cancle.setOnClickListener(this);

	}

	/**
	 * 获取控件
	 */
	private void findView() {
		bt_reset_psw = (Button) this.findViewById(R.id.bt_reset_psw);
		bt_clear_psw = (Button) this.findViewById(R.id.bt_clear_psw);
		bt_cancle = (Button) this.findViewById(R.id.bt_cancle);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);

	}

	/**
	 * 点击事件的处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_reset_psw:
			loadRegistActivity();
			finish();
			break;
		case R.id.bt_clear_psw:
			Editor edit = sp.edit();
			edit.putString("regested", "");
			edit.commit();
			Toast.makeText(getApplicationContext(), "密码已经清空！",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.bt_cancle:
			finish();
			break;

		default:
			break;
		}
	}

	private void loadRegistActivity() {
		Intent intent = new Intent(PswManageActivity.this, RegistActivity.class);
		startActivity(intent);
	}
	
	

	

}
