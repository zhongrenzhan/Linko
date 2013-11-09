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
import android.widget.EditText;
import android.widget.Toast;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.DESPlus;

public class RegistActivity extends Activity implements OnClickListener {
	private static final String TAG = "RegestActivity";
	private EditText et_regist_pwd;
	private EditText et_regest_conf;
	private Button bt_regest_sure;
	private Button bt_regest_conf;
	private EditText et_regest_qustion;
	private EditText et_regest_answer;
	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		findView();
		registerListener();
	}

	/**
	 * 获取控件
	 */
	public void findView() {

		et_regist_pwd = (EditText) this.findViewById(R.id.et_regist_pwd);
		et_regest_conf = (EditText) this.findViewById(R.id.et_regist_conf);
		bt_regest_sure = (Button) this.findViewById(R.id.bt_regest_sure);
		bt_regest_conf = (Button) this.findViewById(R.id.bt_regest_cancle);
		et_regest_qustion = (EditText) this
				.findViewById(R.id.et_regest_question);
		et_regest_answer = (EditText) this.findViewById(R.id.et_regest_answer);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	/**
	 * 注册按钮点击事件
	 */
	public void registerListener() {
		bt_regest_sure.setOnClickListener(this);
		bt_regest_conf.setOnClickListener(this);
	}

	/**
	 * 点击事件的处理
	 */
	@Override
	public void onClick(View v) {
		String psw = et_regist_pwd.getText().toString().trim();// 获取输入的密码
		String psw1 = et_regest_conf.getText().toString().trim();// 获取确认的密码
		String qustion = et_regest_qustion.getText().toString().trim();// 获取输入的问题
		String answer = et_regest_answer.getText().toString().trim();// 获取输入的回答

		switch (v.getId()) {
		case R.id.bt_regest_sure:

			// 判断输入是否有空的
			if ("".equals(psw) || "".equals(psw1) || "".equals(qustion)
					|| "".equals(answer)) {
				Toast.makeText(RegistActivity.this, "请填写完整的注册信息",
						Toast.LENGTH_SHORT).show();
			} else {
				if (psw.equals(psw1)) { // 如果没有空，并且两次密码一样
					//String pwdencoded = MD5Encode.md5Encoding(psw1);
					DESPlus plus;
					try {
						plus = new DESPlus();
						String pwdencoded = plus.encrypt(psw1);
						
						Editor editor = sp.edit();
						editor.putString("regested", pwdencoded);// 记录密码
						editor.putString("qustion", qustion); // 记录问题
						editor.putString("answer", answer); // 记录回答的问题
						editor.commit();
						
						Toast.makeText(RegistActivity.this, "设置成功！",
								Toast.LENGTH_LONG).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					finish();
					loadMainUi(); // 保存好后进入主页面
				} else {// 如果两次密码不等
					Toast.makeText(RegistActivity.this, "两次密码不一致",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.bt_regest_cancle:
			finish();
			break;
		}
	}

	/**
	 * 进入主界面
	 */
	private void loadMainUi() {
		Intent intent = new Intent(RegistActivity.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.alpha_ctivity_in,
				R.anim.alpha_ctivity_out);
	}

}
