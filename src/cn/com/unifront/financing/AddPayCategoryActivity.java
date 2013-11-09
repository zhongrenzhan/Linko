package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.linko.R;

public class AddPayCategoryActivity extends Activity implements OnClickListener {
	private EditText et_addcategory_entercategory;
	private TextView tv_addcategory_choose_account;
	private Button bt_addcategory_chooseacount;
	private EditText et_addcategory_remark;
	private Button bt_categorylist_add;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addpaycategory);
		findViews();
		setClickListener();

	}

	private void setClickListener() {
		bt_addcategory_chooseacount.setOnClickListener(this);
		bt_categorylist_add.setOnClickListener(this);

	}

	private void findViews() {
		et_addcategory_entercategory = (EditText) this
				.findViewById(R.id.et_addcategory_entercategory);
		tv_addcategory_choose_account = (TextView) this
				.findViewById(R.id.tv_addcategory_choose_account);
		bt_addcategory_chooseacount = (Button) this
				.findViewById(R.id.bt_addcategory_chooseacount);
		et_addcategory_remark = (EditText) this
				.findViewById(R.id.et_addcategory_remark);
		bt_categorylist_add = (Button) this
				.findViewById(R.id.bt_categorylist_add);

	}

	@Override
	public void onClick(View v) {
		String category = et_addcategory_entercategory.getText().toString()
				.trim();
		String account = tv_addcategory_choose_account.getText().toString();
		String remark = et_addcategory_remark.getText().toString().trim();
		switch (v.getId()) {

		case R.id.bt_addcategory_chooseacount:// 选择账户的按钮
			Intent intent = new Intent(AddPayCategoryActivity.this,
					AccountListActivity.class);
			intent.putExtra("requestcode", 1);
			startActivityForResult(intent, 0);
			break;

		case R.id.bt_categorylist_add:// 添加的按钮
			if ("".equals(category) || "请选择账户".equals(account)) {
				Toast.makeText(AddPayCategoryActivity.this, "请输完整的信息！",
						Toast.LENGTH_SHORT).show();
			} else {
				SqlOperate operate = new SqlOperate(AddPayCategoryActivity.this);
				operate.insertPayCategory(category, account, remark);
				Toast.makeText(AddPayCategoryActivity.this, "恭喜，添加成功！",
						Toast.LENGTH_SHORT).show();
				Intent intent1 = getIntent();
				setResult(3);
				finish();
			}

		}
	}

	/**
	 * 选择账户的TextView更新
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			String name = data.getStringExtra("result");
			tv_addcategory_choose_account.setText(name);
		}
	}
	

}
