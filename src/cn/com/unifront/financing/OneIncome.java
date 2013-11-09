package cn.com.unifront.financing;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.linko.R;

public class OneIncome extends Activity implements OnClickListener {
	private Button bt_oneincome_datepicker;
	private TextView tv_oneincome_entertime;
	private EditText et_oneincome_entermoney;
	private TextView et_oneincome_entertype;
	private TextView tv_oneincome_choose_account;
	private Button bt_oneincome_recordtype;
	private Button bt_oneincome_chooseaccount;
	private Button bt_oneincome_cancle;
	private EditText et_oneincome_remark;
	DatePickerDialog dialog;
	private Button bt_oneincome_ture;
	private int year1;
	private int month1;
	private int day1;
	private String month2;
	private String day2;
	private String tag = "OneIncomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_incom_one);
		findView();
		setDialogDate();
		setButtonClickListener();

	}

	/**
	 * 设置按钮点击事件
	 */
	private void setButtonClickListener() {
		bt_oneincome_datepicker.setOnClickListener(this);
		bt_oneincome_ture.setOnClickListener(this);
		bt_oneincome_recordtype.setOnClickListener(this);
		bt_oneincome_chooseaccount.setOnClickListener(this);
		bt_oneincome_cancle.setOnClickListener(this);
	}

	/**
	 * 设置设置选择时间时的初始化时间
	 */
	private void setDialogDate() {
		final Calendar calendar = Calendar.getInstance();
		year1 = calendar.get(Calendar.YEAR);
		month1 = calendar.get(Calendar.MONTH);
		day1 = calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 找到控件初始化
	 */
	private void findView() {
		et_oneincome_entermoney = (EditText) this
				.findViewById(R.id.et_oneincome_entermoney);
		et_oneincome_entertype = (TextView) this
				.findViewById(R.id.et_oneincome_entertype);
		tv_oneincome_entertime = (TextView) this
				.findViewById(R.id.tv_oneincome_entertime);
		bt_oneincome_recordtype = (Button) this
				.findViewById(R.id.bt_oneincome_recordtype);
		tv_oneincome_choose_account = (TextView) this
				.findViewById(R.id.tv_oneincome_choose_account);
		bt_oneincome_chooseaccount = (Button) this
				.findViewById(R.id.bt_oneincome_chooseacount);
		bt_oneincome_datepicker = (Button) this
				.findViewById(R.id.bt_oneincome_datepicker);
		bt_oneincome_ture = (Button) this.findViewById(R.id.bt_oneincome_ture);
		bt_oneincome_cancle = (Button) this
				.findViewById(R.id.bt_oneincome_cancle);
		et_oneincome_remark = (EditText) this
				.findViewById(R.id.et_oneincome_remark);

	}

	/**
	 * 按钮点击事件处理函数
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		// 选择类别按钮事件处理
		case R.id.bt_oneincome_recordtype:
			loadCategoryActivity();
			break;
			
		// 选择账户名按钮事件处理
		case R.id.bt_oneincome_chooseacount:
			loadAccountListActivity();
			break;
			
		// 选择时间的按钮事件处理
		case R.id.bt_oneincome_datepicker:
			creatDateDialog();
			break;
		// 确定添加按钮事件处理
		case R.id.bt_oneincome_ture:
			float money = Float.parseFloat(et_oneincome_entermoney.getText().toString().trim());					
			String type = et_oneincome_entertype.getText().toString().trim();
			String account = tv_oneincome_choose_account.getText().toString();
			String time = tv_oneincome_entertime.getText().toString().trim();			
			String remark = et_oneincome_remark.getText().toString().trim();
			
			SqlOperate operate = new SqlOperate(this);
			
			if ("0.0".equals(money) || "请输入记账类别".equals(type)
					|| "请选择时间".equals(time)) {
				Toast.makeText(OneIncome.this, "请输入完整的信息！！", Toast.LENGTH_SHORT)
						.show();

			} else {
				operate.insertPayIncomeDate(0,money, time, account, type, remark);
				Toast.makeText(OneIncome.this, "记账一笔成功！", Toast.LENGTH_LONG)
						.show();
				finish();
				overridePendingTransition(0, R.anim.translate_left_out);
			}
			break;

		case R.id.bt_oneincome_cancle:// 选择取消按钮事件处理
			finish();
			overridePendingTransition(0, R.anim.translate_left_out);
			break;
		}
	}

	private void loadAccountListActivity() {
		Intent intent = new Intent(OneIncome.this, AccountListActivity.class);
		intent.putExtra("WhatActivityLaunch", "OneIncomeActivity");
		startActivityForResult(intent, 0);
		

	}

	private void loadCategoryActivity() {
		Intent intent = new Intent(OneIncome.this,
				IncomeCategoryListActivity.class);
		intent.putExtra("WhatActivityLaunch", "OneIncomeActivity");
		startActivityForResult(intent, 0);

	}

	/**
	 * 弹出时间选择对话框
	 */
	private void creatDateDialog() {
		dialog = new DatePickerDialog(OneIncome.this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int day) {
				int month1 = month + 1;
				if (month < 10) {
					month2 = "0" + month1;
				}
				if (day < 10) {
					day2 = "0" + day;
				} else {
					day2 = day + "";
				}
				tv_oneincome_entertime
						.setText(year + "-" + month2 + "-" + day2);
			}
		}, year1, month1, day1);
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String name = data.getStringExtra("result");
		if (resultCode == 0) {// 收入分类列表返回的信息
			et_oneincome_entertype.setText(name);
		} else if (resultCode == 1) {// 账户列表返回的信息
			tv_oneincome_choose_account.setText(name);
		}

	}

}
