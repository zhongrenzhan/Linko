package cn.com.unifront.financing;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.PayIncomeList;
import cn.com.unifront.linko.R;

public class PayIncomeInfoList extends Activity {
	private LayoutInflater inflater;
	private ListView list;
	private List<PayIncomeList> listInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.search_pay_income_list);
		findViews();
		
		
		SqlOperate operate = new SqlOperate(PayIncomeInfoList.this);		
	}

	
	private void findViews() {
		list = (ListView) this.findViewById(R.id.lv_pay_income_info);
		SqlOperate opreate = new SqlOperate(PayIncomeInfoList.this);
		Intent intent = getIntent();
		String[] condictions = intent.getStringArrayExtra("SEARCH_CONDICTION");
		listInfo = opreate.getPayIncomeList(condictions);
		list.setAdapter(new MyAdapter());
	}
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listInfo.size();
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
			View view = inflater.inflate(R.layout.search_pay_income_list_item, null);
			TextView account = (TextView) view.findViewById(R.id.tv_list_account);
			TextView amount = (TextView)view.findViewById(R.id.tv_list_amount);
			TextView date = (TextView)view.findViewById(R.id.tv_list_date);
			TextView type = (TextView)view.findViewById(R.id.tv_list_type);
			account.setText(listInfo.get(position).getAccount());
			amount.setText(listInfo.get(position).getAmount());
			date.setText(listInfo.get(position).getDate());
			return view;
		}}
	
	

}
