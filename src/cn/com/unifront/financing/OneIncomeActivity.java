
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

public class OneIncomeActivity extends Activity implements OnClickListener {
    private Button mDatepicker;
    private TextView mTime;
    private EditText mMoney;
    private TextView mType;
    private TextView mAccount;
    private Button btType;
    private Button btAccount;
    private Button btCancle;
    private EditText etRemark;
    DatePickerDialog dialog;
    private Button btTure;
    private int initYear;
    private int initMonth;
    private int initDay;
    private String month;
    private String day;

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
        mDatepicker.setOnClickListener(this);
        btTure.setOnClickListener(this);
        btType.setOnClickListener(this);
        btAccount.setOnClickListener(this);
        btCancle.setOnClickListener(this);
    }

    private void setDialogDate() {
        final Calendar calendar = Calendar.getInstance();
        initYear = calendar.get(Calendar.YEAR);
        initMonth = calendar.get(Calendar.MONTH);
        initDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 找到控件初始化
     */
    private void findView() {
        mMoney = (EditText) this
                .findViewById(R.id.et_oneincome_entermoney);
        mType = (TextView) this
                .findViewById(R.id.et_oneincome_entertype);
        mTime = (TextView) this
                .findViewById(R.id.tv_oneincome_entertime);
        btType = (Button) this
                .findViewById(R.id.bt_oneincome_recordtype);
        mAccount = (TextView) this
                .findViewById(R.id.tv_oneincome_choose_account);
        btAccount = (Button) this
                .findViewById(R.id.bt_oneincome_chooseacount);
        mDatepicker = (Button) this
                .findViewById(R.id.bt_oneincome_datepicker);
        btTure = (Button) this.findViewById(R.id.bt_oneincome_ture);
        btCancle = (Button) this
                .findViewById(R.id.bt_oneincome_cancle);
        etRemark = (EditText) this
                .findViewById(R.id.et_oneincome_remark);

    }

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
                float money = Float.parseFloat(mMoney.getText().toString().trim());
                String type = mType.getText().toString().trim();
                String account = mAccount.getText().toString();
                String time = mTime.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();

                SqlOperate operate = new SqlOperate(this);

                if ("0.0".equals(money) || "请输入记账类别".equals(type)
                        || "请选择时间".equals(time)) {
                    Toast.makeText(OneIncomeActivity.this, "请输入完整的信息！！", Toast.LENGTH_SHORT)
                            .show();

                } else {
                    operate.insertPayIncomeDate(0, money, time, account, type, remark);
                    Toast.makeText(OneIncomeActivity.this, "记账一笔成功！", Toast.LENGTH_LONG)
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
        Intent intent = new Intent(OneIncomeActivity.this, AccountListActivity.class);
        intent.putExtra("WhatActivityLaunch", "OneIncomeActivity");
        startActivityForResult(intent, 0);

    }

    private void loadCategoryActivity() {
        Intent intent = new Intent(OneIncomeActivity.this,
                IncomeCategoryListActivity.class);
        intent.putExtra("WhatActivityLaunch", "OneIncomeActivity");
        startActivityForResult(intent, 0);

    }

    private void creatDateDialog() {
        dialog = new DatePickerDialog(OneIncomeActivity.this, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                int monthTemp = month + 1;
                String monthString = null;
                String dayString = null;
                if (month < 10) {
                    monthString = "0" + monthTemp;
                }else{
                    monthString = month+"";
                }
                if (day < 10) {
                    dayString = "0" + day;
                } else {
                    dayString = day + "";
                }
                mTime.setText(year + "-" + monthString + "-" + dayString);
            }
        }, initYear, initMonth, initDay);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String name = data.getStringExtra("result");
        if (resultCode == 0) {// 收入分类列表返回的信息
            mType.setText(name);
        } else if (resultCode == 1) {// 账户列表返回的信息
            mAccount.setText(name);
        }

    }

}
