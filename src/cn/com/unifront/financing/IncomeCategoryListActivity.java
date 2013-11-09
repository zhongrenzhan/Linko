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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.CategoryName;
import cn.com.unifront.linko.R;

public class IncomeCategoryListActivity extends Activity implements OnClickListener, OnItemClickListener {
	private ListView lv_categorylist;
	public List<CategoryName> names;
	private LinearLayout ll_category_foot;
	private LayoutInflater inflater;
	private String tag = "是否获取？";
	private String launchactivity;
	private Button bt_back;
	private Button bt_categorylist_add;
	private CheckBox cb_category_deletecategory;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			SqlOperate operate = new SqlOperate(IncomeCategoryListActivity.this);
			names = operate.getCategry("incomecategory");
			lv_categorylist.setAdapter(new MyCategoryListAdapter());

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		launchactivity = intent.getStringExtra("WhatActivityLaunch");
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_categorylist);
		ll_category_foot = (LinearLayout) this.findViewById(R.id.ll_category_foot);
		
		lv_categorylist = (ListView) this.findViewById(R.id.lv_categorylist);
		SqlOperate operate = new SqlOperate(IncomeCategoryListActivity.this);
		names = operate.getCategry("incomecategory");
		lv_categorylist.setAdapter(new MyCategoryListAdapter());
		bt_back = (Button) this.findViewById(R.id.bt_back);
		bt_categorylist_add = (Button) this
				.findViewById(R.id.bt_categorylist_add);
		bt_back.setOnClickListener(this);
		bt_categorylist_add.setOnClickListener(this);
		lv_categorylist.setOnItemClickListener(this);

	}

	/**
	 * 分类列表适配器
	 */
	private class MyCategoryListAdapter extends BaseAdapter {

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
			cb_category_deletecategory = (CheckBox) view.findViewById(R.id.cb_category_deletecategory);			
			tv.setText(names.get(position).getName());
			
			return view;
		}


	}

	@Override
	public void onClick(View v) {
		if (v == bt_back) {
			finish();
		}

		if (v == bt_categorylist_add) {
			loadAddCategoryActivity();
		}

	}

	/**
	 * 跳转到新增分类的函数
	 */
	private void loadAddCategoryActivity() {
		Intent intent = new Intent(IncomeCategoryListActivity.this,
				AddIncomeCategoryActivity.class);
		startActivityForResult(intent, 2);
	}

	/**
	 * 发消息更新分类列表
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 2) {
			Log.i(tag, resultCode + "");
			handler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		if("OneIncomeActivity".equals(launchactivity)){
			ll_category_foot.setVisibility(View.INVISIBLE);
			TextView tv = (TextView) v.findViewById(R.id.tv_categorylist);
			String text = (String) tv.getText();
			Intent intent = getIntent();
			intent.putExtra("result", text);
			setResult(0, intent);
			finish();
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			Intent intent = getIntent();
			intent.putExtra("result", "请输入记账类别");
			setResult(0, intent);
			finish();
			break;
		}
		return true;
	}
	
	
}
