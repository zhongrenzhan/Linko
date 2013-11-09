package cn.com.unifront.financing;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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

public class OneOtherActivity extends Activity implements OnClickListener {
	private EditText et_oneothter_entermoney;
	private EditText et_oneothter_entertype;
	private Button bt_oneothter_recordtype;
	private TextView tv_oneothter_choose_account;
	private Button bt_oneothter_chooseacount;
	private TextView tv_oneothter_entertime;
	private Button bt_oneothter_datepicker;
	private EditText et_oneothter_remark;
	private Button bt_oneothter_ture;
	private Button bt_oneothter_cancle;
	private int year1;
	private int month1;
	private int day1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_one);
		getview();
		setDialogDate();
		setButtonClickListener();
	}

	/**
	 * 设置按钮点击事件
	 */
	private void setButtonClickListener() {
		bt_oneothter_recordtype.setOnClickListener(this);
		bt_oneothter_chooseacount.setOnClickListener(this);
		bt_oneothter_datepicker.setOnClickListener(this);
		bt_oneothter_ture.setOnClickListener(this);
		bt_oneothter_cancle.setOnClickListener(this);
	}

	private void getview() {
		et_oneothter_entermoney = (EditText) this
				.findViewById(R.id.et_oneother_entermoney);
		et_oneothter_entertype = (EditText) this
				.findViewById(R.id.et_oneother_entertype);
		bt_oneothter_recordtype = (Button) this
				.findViewById(R.id.bt_oneother_recordtype);
		tv_oneothter_choose_account = (TextView) this
				.findViewById(R.id.tv_oneother_choose_account);
		bt_oneothter_chooseacount = (Button) this
				.findViewById(R.id.bt_oneother_chooseacount);
		tv_oneothter_entertime = (TextView) this
				.findViewById(R.id.tv_oneother_entertime);
		bt_oneothter_datepicker = (Button) this
				.findViewById(R.id.bt_oneother_datepicker);
		et_oneothter_remark = (EditText) this
				.findViewById(R.id.et_oneother_remark);
		bt_oneothter_ture = (Button) this.findViewById(R.id.bt_oneother_ture);
		bt_oneothter_cancle = (Button) this
				.findViewById(R.id.bt_oneother_cancle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_oneother_datepicker:// 选择时间的按钮事件处理
			creatDateDialog();
			break;

		case R.id.bt_oneother_ture:// 选择确定添加按钮事件处理
			float money = Float.parseFloat(et_oneothter_entermoney.getText().toString().trim());
			String time = tv_oneothter_entertime.getText().toString().trim();
			String account = tv_oneothter_choose_account.getText().toString();
			String type = bt_oneothter_recordtype.getText().toString().trim();
			String remark = et_oneothter_remark.getText().toString().trim();
			SqlOperate operate = new SqlOperate(this);
			operate.insertPayIncomeDate(2,money, time, account, type, remark);
			Toast.makeText(OneOtherActivity.this, "存到数据库中", Toast.LENGTH_SHORT)
					.show();
			finish();
			break;
		case R.id.bt_oneother_recordtype:// 选择类别按钮事件处理

			break;
		case R.id.bt_oneother_chooseacount:// 选择账户名按钮事件处理

			break;
		case R.id.bt_oneother_cancle:// 选择取消按钮事件处理
			finish();
			break;
		}
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
	 * 弹出时间选择对话框
	 */
	private void creatDateDialog() {
		DatePickerDialog dialog = new DatePickerDialog(OneOtherActivity.this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker arg0, int year, int month,
							int day) {
						int month1 = month + 1;
						tv_oneothter_entertime.setText(year + "-" + month1
								+ "-" + day);
					}
				}, year1, month1, day1);
		dialog.show();

	}

}
