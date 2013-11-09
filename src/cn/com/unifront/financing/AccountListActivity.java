package cn.com.unifront.financing;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.AccountName;
import cn.com.unifront.linko.R;

public class AccountListActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	private ListView lv_accountlist;
	public List<AccountName> names;
	private LayoutInflater inflater;
	private LinearLayout ll_accountlist_foot;
	private Button bt_back;
	private Button bt_accountlist_add;
	private String tag = "是否获取？";
	private Button bt_ok;
	private String launchactivity;
	private int LAUNCH_CODE;// 启动这个页面的来自哪个界面
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			SqlOperate operate = new SqlOperate(AccountListActivity.this);
			names = operate.getAllAccount();
			lv_accountlist.setAdapter(new MyAccountListAdapter());

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_accountlist);

		findViews();
	}

	private void findViews() {

		bt_back = (Button) this.findViewById(R.id.bt_back);
		
		bt_accountlist_add = (Button) this
				.findViewById(R.id.bt_accountlist_add);
		lv_accountlist = (ListView) this.findViewById(R.id.lv_accountlist);
		ll_accountlist_foot = (LinearLayout) this
				.findViewById(R.id.ll_accountlist_foot);

		// 判断是哪个页面启动这个页面来确定底部现不现实按钮
		Intent intent = getIntent();
		LAUNCH_CODE = intent.getIntExtra("requestcode", 0);
		launchactivity = intent.getStringExtra("WhatActivityLaunch");
		
		// 查询全部账户
		SqlOperate operate = new SqlOperate(AccountListActivity.this);
		names = operate.getAllAccount();
		lv_accountlist.setAdapter(new MyAccountListAdapter());
		setClickListener();

	}

	private void setClickListener() {
		lv_accountlist.setOnItemClickListener(this);
		bt_back.setOnClickListener(this);
		bt_accountlist_add.setOnClickListener(this);

	}

	private class MyAccountListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.categorylist_item, null);
			TextView tv = (TextView) view.findViewById(R.id.tv_categorylist);
			tv.setText(names.get(position).getName());
			return view;
		}

	}

	/**
	 * 获取列表里的账户信息
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		TextView tv = (TextView) v.findViewById(R.id.tv_categorylist);
		if (LAUNCH_CODE == 1) {
			String name = tv.getText().toString();
			Toast.makeText(AccountListActivity.this, "" + name,
					Toast.LENGTH_SHORT).show();
			Intent intent = getIntent();
			intent.putExtra("result", name);
			setResult(2, intent);
			finish();
		} else if (LAUNCH_CODE == 2) {
			String name = tv.getText().toString();
			Toast.makeText(AccountListActivity.this, "" + name,
					Toast.LENGTH_SHORT).show();
		}else if("OneIncomeActivity".equals(launchactivity)||"OnePayActivity".equals(launchactivity)){
			String name = tv.getText().toString();
			Toast.makeText(AccountListActivity.this, "" + name,
					Toast.LENGTH_SHORT).show();
			Intent intent = getIntent();
			intent.putExtra("result", name);
			setResult(1, intent);
			finish();
		}

	}

	@Override
	public void onClick(View v) {
		if (v == bt_back) {
			finish();
		}  else if (v == bt_accountlist_add) {
			Intent intent = new Intent(AccountListActivity.this,
					AddAccountActivity.class);
			startActivityForResult(intent, 0);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 3) {
			Log.i(tag, resultCode + "");
			handler.sendEmptyMessage(0);
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			Intent intent = getIntent();
			intent.putExtra("result", "请选择账户");
			setResult(1, intent);
			finish();
			break;
		}
		return true;
	}

}
