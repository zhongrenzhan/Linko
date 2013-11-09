package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.DESPlus;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_login_pwd;
	private Button bt_login_sure;
	private TextView tv_find_psw;
	private SharedPreferences sp;
	private String pswEncoded;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findView();
		registerListener();

	}

	/**
	 * 注册点击事件
	 */
	private void registerListener() {
		bt_login_sure.setOnClickListener(this);
		tv_find_psw.setOnClickListener(this);
	}

	/**
	 * 找到控件
	 */
	private void findView() {
		et_login_pwd = (EditText) this.findViewById(R.id.et_login_pwd);
		bt_login_sure = (Button) this.findViewById(R.id.bt_login_sure);

		tv_find_psw = (TextView) this.findViewById(R.id.tv_find_psw);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	/**
	 * 点击事件的相应处理
	 */
	@Override
	public void onClick(View v) {
		String psw = et_login_pwd.getText().toString().trim();// 获取输入的密码
		//String pswencoded = MD5Encode.md5Encoding(psw);
		String truepsw = sp.getString("regested", null);
		try {
			DESPlus plus = new DESPlus();
			 pswEncoded = plus.encrypt(psw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (v.getId()) {
		case R.id.bt_login_sure:
			if (pswEncoded.equals(truepsw)) {
				loadMainUi();

				overridePendingTransition(R.anim.zoomin, 0);
			} else {
				Toast.makeText(LoginActivity.this, "密码错误！", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.tv_find_psw:
			loadFindPswActivity();
			break;
		default:
			break;
		}
	}

	/**
	 * 进入找回密码的页面
	 */
	private void loadFindPswActivity() {
		Intent intent = new Intent(this, FindPswActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
	}

	/**
	 * 进入主页面
	 */
	private void loadMainUi() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.alpha_ctivity_in,
				R.anim.alpha_ctivity_out);
		finish();
	}

}
