package cn.com.unifront.financing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.AccountName;
import cn.com.unifront.linko.R;

public class AccountSeach extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private RadioGroup group;
	private RadioButton rb_search_pay;
	private RadioButton rb_search_income;
	private Spinner spinner_search_choose_account;
	private EditText et_start_time;
	private EditText et_end_time;
	private EditText et_minimum_amount;
	private EditText et_maximum_amount;

	private Button bt_search;
	private Button bt_back;

	private int year0;
	private int month0;
	private int day0;
	private String month2;
	private String day2;
	private DatePickerDialog dialog;

	private String month1;
	private String day1;

	// 查询时的状态数据
	private int selected = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_search);
		findViews();
		initEditState();
		setListener();
	}

	/**
	 * 初始化时间的值，填充时间的EditText
	 */
	private void initEditState() {
		setStartTime();
		showSpinnerData();
		if (month0 < 10) {
			month1 = "0" + month0;
		}else {
			month1 = month0+"";
		}
		if (day0 < 10) {
			day1 = "0" + day0;
		}else{
			day1 = "" + day0;
		}
		et_start_time.setText(year0 + "-" + month1 + "-" + day1);
		et_end_time.setText(year0 + "-" + month1 + "-" + day1);

	}

	/**
	 * 注册点击事件
	 */
	private void setListener() {
		group.setOnCheckedChangeListener(this);
		et_start_time.setOnClickListener(this);
		et_end_time.setOnClickListener(this);
		bt_search.setOnClickListener(this);
		bt_back.setOnClickListener(this);
	}

	/**
	 * 获取控件
	 */
	private void findViews() {
		group = (RadioGroup) this.findViewById(R.id.rg_search_pay_income_type);
		rb_search_income = (RadioButton) this
				.findViewById(R.id.rb_search_income);
		rb_search_pay = (RadioButton) this.findViewById(R.id.rb_search_pay);
		spinner_search_choose_account = (Spinner) this
				.findViewById(R.id.spinner_search_choose_account);
		et_start_time = (EditText) this.findViewById(R.id.et_search_start_time);
		et_end_time = (EditText) this.findViewById(R.id.et_search_end_time);
		et_minimum_amount = (EditText) this
				.findViewById(R.id.et_search_minimum_amount);
		et_maximum_amount = (EditText) this
				.findViewById(R.id.et_search_maximum_amount);

		bt_search = (Button) this.findViewById(R.id.bt_search_search);
		bt_back = (Button) this.findViewById(R.id.bt_search_back);

	}

	/**
	 * 条件选择的点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.et_search_start_time:
			creatDateDialog(1);
			break;
		case R.id.et_search_end_time:
			creatDateDialog(2);
			break;

		case R.id.bt_search_search:
			getConditionAndSearch();

			break;
		case R.id.bt_search_back:
			finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 获取输入的条件进行查询
	 */
	private void getConditionAndSearch() {

		String account = spinner_search_choose_account.getSelectedItem()
				.toString();

		String startTime = et_start_time.getText().toString();
		String endTime = et_end_time.getText().toString();
		String mininum = et_minimum_amount.getText().toString().trim();
		String maximum = et_maximum_amount.getText().toString().trim();
		
		Intent intent = new Intent(AccountSeach.this,PayIncomeInfoList.class);
		String [] condiction = new String[]{selected+"",account,startTime,endTime,mininum,maximum};
		intent.putExtra("SEARCH_CONDICTION", condiction);
		startActivity(intent);
		
	}

	/**
	 * 弹出时间选择对话框
	 */
	private void showSpinnerData() {
		SqlOperate sqloprate = new SqlOperate(AccountSeach.this);
		List<AccountName> accountNames = sqloprate.getAllAccount();
		List<String> names = new ArrayList<String>();
		int size = accountNames.size();
		for (int i = 0; i < size; i++) {
			names.add(accountNames.get(i).getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				AccountSeach.this, android.R.layout.simple_spinner_item, names);
		spinner_search_choose_account.setAdapter(adapter);
	}

	/**
	 * 时间的初始化
	 */
	private void setStartTime() {
		final Calendar calendar = Calendar.getInstance();
		year0 = calendar.get(Calendar.YEAR);
		month0 = calendar.get(Calendar.MONTH) + 1;
		day0 = calendar.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 弹出时间选择对话框
	 */
	private void creatDateDialog(int i) {
		final int j = i;
		dialog = new DatePickerDialog(AccountSeach.this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int year, int month,
							int day) {
						int month1 = month + 1;

						if (month < 10) {
							month2 = "0" + month1;
						} else {
							month2 = "" + month1;
						}

						if (day < 10) {
							day2 = "0" + day;
						} else {
							day2 = day + "";
						}

						if (j == 1) {
							et_start_time.setText(year + "-" + month2 + "-"
									+ day2);
						} else {
							et_end_time.setText(year + "-" + month2 + "-"
									+ day2);
						}						

					}
				}, year0, month0 - 1, day0);
		dialog.show();

	}

	/**
	 * RadioButton的点击事件
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_search_income:
			selected = 0;
			break;
		case R.id.rb_search_pay:
			selected = 1;
			break;
		default:
			break;
		}

	}

}
