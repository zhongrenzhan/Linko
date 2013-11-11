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
import cn.com.unifront.linko.LinkoApplication;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.DESPlus;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText mLoginPwd;
	private Button mButtonSure;
	private TextView findPsw;
	private SharedPreferences sp;
	private String pswEncoded;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		LinkoApplication app = (LinkoApplication) getApplication();
        sp = app.getDefaultSP();
		findView();
		registerListener();

	}

	private void registerListener() {
		mButtonSure.setOnClickListener(this);
		findPsw.setOnClickListener(this);
	}

	/**
	 * 找到控件
	 */
	private void findView() {
		mLoginPwd = (EditText) this.findViewById(R.id.et_login_pwd);
		mButtonSure = (Button) this.findViewById(R.id.bt_login_sure);

		findPsw = (TextView) this.findViewById(R.id.tv_find_psw);
		
	}

	@Override
	public void onClick(View v) {
		String psw = mLoginPwd.getText().toString().trim();
		//String pswencoded = MD5Encode.md5Encoding(psw);
		String truepsw = sp.getString("USER_PSW", null);
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
			    String warmPswWrong = LoginActivity.this.getResources().getString(R.string.warm_psw_wrong_psw);
				Toast.makeText(LoginActivity.this, warmPswWrong, Toast.LENGTH_SHORT)
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

	private void loadFindPswActivity() {
		Intent intent = new Intent(this, FindPswActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
	}

	private void loadMainUi() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.alpha_ctivity_in,
				R.anim.alpha_ctivity_out);
		finish();
	}
}
