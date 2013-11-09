package cn.com.unifront.financing;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.linko.R;

public class AccountBalanceInfo extends Activity implements OnLongClickListener, OnClickListener {
	private ListView lv_account_balance_list;
	private LayoutInflater inflater;
	private List<cn.com.unifront.domain.AccountBalanceInfo> infoes;
	private Button bt_back;
	private SqlOperate sqloperate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_account_balance);
		sqloperate = new SqlOperate(AccountBalanceInfo.this);
		sqloperate.updateAccountBalance();//更新账户金额信息
		SqlOperate oprate = new SqlOperate(AccountBalanceInfo.this);//查询金额
		infoes = oprate.getAccountBalanceInfo();
		
		findViews();		
		setClickListener();
	}

	private void setClickListener() {
		lv_account_balance_list.setOnLongClickListener(this);
		bt_back.setOnClickListener(this);
	}

	private void findViews() {
		lv_account_balance_list = (ListView) this
				.findViewById(R.id.lv_account_balance_list);
		lv_account_balance_list.setAdapter(new MyAdapter());
		bt_back = (Button) findViewById(R.id.bt_back);

	}

	@Override
	public boolean onLongClick(View v) {

		return true;
	}
	
	/**
	 * 适配器
	 *
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return infoes.size();
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
			View view = inflater.inflate(R.layout.account_balance_info_item, null);
			TextView tv_account_name = (TextView) view.findViewById(R.id.tv_account_name);
			TextView tv_account_balance = (TextView)view.findViewById(R.id.tv_account_balance);
			if(infoes.get(position).getBalance()==null){
				tv_account_balance.setText("0元");
			}else{
				tv_account_name.setText(infoes.get(position).getName());
				tv_account_balance.setText(infoes.get(position).getBalance()+"元");
			}
			
			return view;
		}
	}

	@Override
	public void onClick(View arg0) {
		finish();
		overridePendingTransition(0, R.anim.translate_left_out);
		
	}

}
