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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.dialog.FinancingDialog;
import cn.com.unifront.domain.CategoryName;
import cn.com.unifront.linko.R;

public class PayCategoryListActivity extends Activity implements
		OnClickListener, OnItemClickListener, OnItemLongClickListener {
	private ListView lv_categorylist;
	public List<CategoryName> names;

	private LayoutInflater inflater;
	private String tag = "是否获取？";
	private Button bt_back;
	private Button bt_categorylist_add;
	private String launchactivity;
	private Button buttoncacle;
	private FinancingDialog dialog;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			SqlOperate operate = new SqlOperate(PayCategoryListActivity.this);
			names = operate.getCategry("payoutcategory");
			lv_categorylist.setAdapter(new MyCategoryListAdapter());

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_paycategorylist);

		lv_categorylist = (ListView) this.findViewById(R.id.lv_categorylist);
		SqlOperate operate = new SqlOperate(PayCategoryListActivity.this);
		names = operate.getCategry("payoutcategory");
		lv_categorylist.setAdapter(new MyCategoryListAdapter());
		bt_back = (Button) this.findViewById(R.id.bt_back);
		bt_categorylist_add = (Button) this
				.findViewById(R.id.bt_categorylist_add);
		
		bt_back.setOnClickListener(this);
		bt_categorylist_add.setOnClickListener(this);

		lv_categorylist.setOnItemClickListener(this);
		lv_categorylist.setOnItemLongClickListener(this);
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
			
			tv.setText(names.get(position).getName());

			return view;
		}

	}

	@Override
	public void onClick(View v) {
		if (v == bt_back) {
			Intent intent = getIntent();
			intent.putExtra("result", "请输入记账类别");
			setResult(0, intent);
			finish();
		}

		if (v == bt_categorylist_add) {
			loadAddPayCategoryActivity();
		}
		
		if(v ==buttoncacle){
			dialog.dismissPopWindow();			
		}
	}

	/**
	 * 跳转到新增分类的函数
	 */
	private void loadAddPayCategoryActivity() {
		Intent intent = new Intent(PayCategoryListActivity.this,
				AddPayCategoryActivity.class);
		startActivityForResult(intent, 2);
	}

	/**
	 * 发消息更新分类列表
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 3) {
			Log.i(tag, resultCode + "");
			handler.sendEmptyMessage(0);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		TextView tv = (TextView) v.findViewById(R.id.tv_categorylist);
		String text = (String) tv.getText();
		Intent intent = getIntent();
		intent.putExtra("result", text);
		launchactivity = intent.getStringExtra("WhatActivityLaunch");		
		if("OnePayActivity".equals(launchactivity)){
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
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int arg2,
			long arg3) {
		 dialog = new FinancingDialog(PayCategoryListActivity.this);
		View v =  dialog.openPopupWindowDialog("我的爱", "各位朋友大家好，我是钟人站", "确定", "确定");
		Button buttonSure = (Button) v.findViewById(R.id.id_bt_dialog_sure);
		 buttoncacle = (Button) v.findViewById(R.id.id_bt_dialog_cancle);
		buttonSure.setOnClickListener(this);
		buttoncacle.setOnClickListener(this);
		
		CheckBox box = (CheckBox) view.findViewById(R.id.cb_category_deletecategory);
		box.setVisibility(view.VISIBLE);
		return true;
	}

}
