package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.linko.R;

public class AddAccountActivity extends Activity implements OnClickListener {
	private EditText et_addaccount_enteraccount;
	private EditText et_addaccount_enterbalance;
	private EditText et_addaccount_warm;
	private TextView tv_addcurrence_choose_currence;
	private Button bt_addcurrence_choosecurrence;
	private Button bt_account_add;
	private String tag="AddaccountActivity";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addaccount);
		findViews();
		setClickListener();
	}

	private void setClickListener() {
		bt_addcurrence_choosecurrence.setOnClickListener(this);
		bt_account_add.setOnClickListener(this);

	}

	private void findViews() {
		et_addaccount_enteraccount = (EditText) this
				.findViewById(R.id.et_addaccount_enteraccount);
		et_addaccount_enterbalance = (EditText) this
				.findViewById(R.id.et_addaccount_enterbalance);
		et_addaccount_warm = (EditText) this
				.findViewById(R.id.et_addaccount_warm);
		tv_addcurrence_choose_currence = (TextView) this
				.findViewById(R.id.tv_addcurrence_choose_currence);
		bt_addcurrence_choosecurrence = (Button) this
				.findViewById(R.id.bt_addcurrence_choosecurrence);
		bt_account_add = (Button) this.findViewById(R.id.bt_account_add);
	}

	@Override
	public void onClick(View v) {
		String account = et_addaccount_enteraccount.getText().toString().trim();
		String balance = et_addaccount_enterbalance.getText().toString().trim();
		String warmmoney = et_addaccount_warm.getText().toString().trim();
		String currence = tv_addcurrence_choose_currence.getText().toString()
				.trim();
		if (v == bt_addcurrence_choosecurrence) {
			loadCuurenceActivity();
		} else if (v == bt_account_add) {
			if ("".equals(account) || "".equals(balance)
					|| "".equals(warmmoney) || "请选择货币类型".equals(currence)) {
				Toast.makeText(AddAccountActivity.this, "请输入完整的账户信息！",
						Toast.LENGTH_SHORT).show();
			} else {
				Log.i(tag, account+balance+warmmoney+currence);
				SqlOperate operate = new SqlOperate(AddAccountActivity.this);
				operate.insertaccount(account, balance, warmmoney, currence);
				Toast.makeText(AddAccountActivity.this, "添加成功！",
						Toast.LENGTH_SHORT).show();
				Intent intent = getIntent();
				setResult(3);
				finish();
			}
		}

	}

	private void loadCuurenceActivity() {
		Intent intent = new Intent(AddAccountActivity.this,
				CurrencyListActivity.class);
		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==3){
			String name = data.getStringExtra("result");
			tv_addcurrence_choose_currence.setText(name);
		}
	}

}
