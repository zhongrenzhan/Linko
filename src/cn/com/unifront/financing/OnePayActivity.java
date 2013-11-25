
package cn.com.unifront.financing;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
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
import cn.com.unifront.domain.PayoutIncome;
import cn.com.unifront.linko.R;

public class OnePayActivity extends Activity implements OnClickListener {
    private Context context;
    private EditText et_onepay_entermoney;
    private EditText et_onepay_entertype;
    private Button bt_onepay_recordtype;
    private TextView tv_onepay_choose_account;
    private Button bt_onepay_chooseacount;
    private TextView tv_onepay_entertime;
    private Button bt_onepay_datepicker;
    private EditText et_onepay_remark;
    private Button bt_onepay_ture;
    private Button bt_onepay_cancle;
    private int year1;
    private int month1;
    private int day1;
    private String month2;
    private String day2;
    private static final String PAY_INCOME = "pay_income";
    private PayoutIncome mPayoutIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = OnePayActivity.this;
        setContentView(R.layout.activity_pay_one);
        getview();
        setDialogDate();
        setButtonClickListener();
    }

    /**
     * 设置按钮点击事件
     */
    private void setButtonClickListener() {
        bt_onepay_recordtype.setOnClickListener(this);
        bt_onepay_chooseacount.setOnClickListener(this);
        bt_onepay_datepicker.setOnClickListener(this);
        bt_onepay_ture.setOnClickListener(this);
        bt_onepay_cancle.setOnClickListener(this);
    }

    private void getview() {
        et_onepay_entermoney = (EditText) this.findViewById(R.id.et_onepay_entermoney);
        et_onepay_entertype = (EditText) this
                .findViewById(R.id.et_onepay_entertype);
        bt_onepay_recordtype = (Button) this
                .findViewById(R.id.bt_onepay_recordtype);
        tv_onepay_choose_account = (TextView) this
                .findViewById(R.id.tv_onepay_choose_account);
        bt_onepay_chooseacount = (Button) this
                .findViewById(R.id.bt_onepay_chooseacount);
        tv_onepay_entertime = (TextView) this
                .findViewById(R.id.tv_onepay_entertime);
        bt_onepay_datepicker = (Button) this
                .findViewById(R.id.bt_onepay_datepicker);
        et_onepay_remark = (EditText) this.findViewById(R.id.et_onepay_remark);
        bt_onepay_ture = (Button) this.findViewById(R.id.bt_onepay_ture);
        bt_onepay_cancle = (Button) this.findViewById(R.id.bt_onepay_cancle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_onepay_datepicker:// 选择时间的按钮事件处理
                creatDateDialog();
                break;

            case R.id.bt_onepay_ture:// 选择确定添加按钮事件处理
                String moneyCheck = et_onepay_entermoney.getText().toString().trim();
                if ("0.0".equals(moneyCheck) == false) {
                    mPayoutIncome = new PayoutIncome();
                    double money = Double.parseDouble(et_onepay_entermoney.getText().toString()
                            .trim());
                    String time = tv_onepay_entertime.getText().toString().trim();
                    String account = tv_onepay_choose_account.getText().toString();
                    String type = et_onepay_entertype.getText().toString().trim();
                    String remark = et_onepay_remark.getText().toString().trim();
                    mPayoutIncome.setMoney(money);
                    mPayoutIncome.setType(type);
                    mPayoutIncome.setAccount(account);
                    mPayoutIncome.setTime(time);
                    mPayoutIncome.setRemark(remark);
                    if ("请选择记账类别".equals(type) || "请选择时间".equals(time)) {
                        Toast.makeText(OnePayActivity.this, "请输入完整的信息！！", Toast.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    SqlOperate operate = new SqlOperate(this);
                    // operate.insertPayIncomeDate(1,money, time, account, type,
                    // remark);
                    operate.insert(PAY_INCOME, mPayoutIncome);
                    Toast.makeText(OnePayActivity.this, context.getString(R.string.record_success),
                            Toast.LENGTH_SHORT)
                            .show();
                    finish();
                } else {
                    Toast.makeText(OnePayActivity.this,
                            context.getString(R.string.pleass_enter_complete_information),
                            Toast.LENGTH_SHORT)
                            .show();
                }

                break;

            case R.id.bt_onepay_recordtype:// 选择类别按钮事件处理
                loadPayCategoryListActivity();
                break;

            case R.id.bt_onepay_chooseacount:// 选择账户名按钮事件处理
                loadAccountListActivity();
                break;

            case R.id.bt_onepay_cancle:// 选择取消按钮事件处理
                finish();
                break;
        }
    }

    private void loadAccountListActivity() {
        Intent intent = new Intent(OnePayActivity.this,
                AccountListActivity.class);
        intent.putExtra("WhatActivityLaunch", "OnePayActivity");
        startActivityForResult(intent, 0);

    }

    private void loadPayCategoryListActivity() {
        Intent intent = new Intent(OnePayActivity.this, PayCategoryListActivity.class);
        intent.putExtra("WhatActivityLaunch", "OnePayActivity");
        startActivityForResult(intent, 1);
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
        DatePickerDialog dialog = new DatePickerDialog(OnePayActivity.this,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month,
                            int day) {
                        int month1 = month + 1;
                        if (month1 < 10) {
                            month2 = "0" + month1;
                        }
                        if (day < 10) {
                            day2 = "0" + day;
                        } else {
                            day2 = day + "";
                        }
                        tv_onepay_entertime.setText(year + "-" + month2 + "-"
                                + day2);
                    }
                }, year1, month1, day1);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = data.getStringExtra("result");
        if (resultCode == 0) {
            et_onepay_entertype.setText(text);
        } else if (resultCode == 1) {
            tv_onepay_choose_account.setText(text);
        }
    }

}
