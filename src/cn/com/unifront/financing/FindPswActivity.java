package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Context;
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

public class FindPswActivity extends Activity implements OnClickListener {
	private TextView tv_findpwd_qust;
	private EditText et_findpwd_answer;
	private Button bt_back;
	private Button bt_sure;
	private TextView tv_showpsw;
	private SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpsw);
		findView();
		registerListener();

	}

	/**
	 * 注册按钮点击事件
	 */
	private void registerListener() {
		bt_sure.setOnClickListener(this);
		bt_back.setOnClickListener(this);

	}

	/**
	 * 获取控件
	 */
	private void findView() {
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		tv_findpwd_qust = (TextView) this.findViewById(R.id.tv_findpwd_qust);
		bt_back = (Button) this.findViewById(R.id.bt_back);
		et_findpwd_answer = (EditText) this
				.findViewById(R.id.et_findpwd_answer);
		bt_sure = (Button) this.findViewById(R.id.bt_sure);
		tv_showpsw = (TextView) this.findViewById(R.id.tv_showpsw);
		String qustion = sp.getString("qustion", "");
		tv_findpwd_qust.setText(qustion);

	}

	/**
	 * 点击事件的处理
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_sure:
			String answer = et_findpwd_answer.getText().toString().trim();
			String setanswer = sp.getString("answer", "");

			if (answer.equals(setanswer)) {				
				try {
					DESPlus plus = new DESPlus();
					String pswdecoded = plus.decrypt((sp.getString("regested", "")));
					tv_showpsw.setText("密码："+pswdecoded);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} else {
				Toast.makeText(FindPswActivity.this, "答案错误,请重新输入！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.bt_back:
			finish();
			overridePendingTransition(R.anim.alpha_ctivity_in,
					R.anim.alpha_ctivity_out);
			break;
		default:
			break;
		}
	}

}
