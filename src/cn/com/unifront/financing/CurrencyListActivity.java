package cn.com.unifront.financing;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.CurrenceName;
import cn.com.unifront.linko.R;

public class CurrencyListActivity extends Activity implements
		OnItemClickListener {
	private ListView lv_currecylist;
	public List<CurrenceName> names;
	private LayoutInflater inflater;

	private String tag = "是否获取？";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_currencelist);
		findViews();
	}

	private void findViews() {

		lv_currecylist = (ListView) this.findViewById(R.id.lv_currencylist);

		// 查询全部账户
		SqlOperate operate = new SqlOperate(CurrencyListActivity.this);
		names = operate.getAllCurrence();
		lv_currecylist.setAdapter(new MyCurrenceListAdapter());
		setClickListener();

	}

	private void setClickListener() {
		lv_currecylist.setOnItemClickListener(this);
	}

	private class MyCurrenceListAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return names.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
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

		String name = tv.getText().toString();
		Toast.makeText(CurrencyListActivity.this, "" + name, Toast.LENGTH_SHORT)
				.show();
		Intent intent = getIntent();
		intent.putExtra("result", name);
		setResult(3, intent);
		finish();

	}

}
