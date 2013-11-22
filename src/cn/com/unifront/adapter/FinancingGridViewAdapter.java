package cn.com.unifront.adapter;


import cn.com.unifront.linko.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FinancingGridViewAdapter extends BaseAdapter {
	private Context context;
	private ImageView iv_gridview_img;
	private TextView tv_gridview_text;
	private LayoutInflater inflater;
	private String[] names;
	private static int icon[] = { R.drawable.ic_grid_income_one, R.drawable.ic_grid_payout_one,
			R.drawable.ic_grid_add_catergory, R.drawable.ic_grid_account_info,
			R.drawable.ic_grid_chart_show, R.drawable.ic_grid_set_tool,
			R.drawable.ic_grid_pwm, R.drawable.ic_grid_backup,
			R.drawable.ic_grid_search };

	public FinancingGridViewAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		names = context.getResources().getStringArray(R.array.financing_gridview_names);
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.gridview_item1, null);
		iv_gridview_img = (ImageView) view.findViewById(R.id.iv_gridview_img);
		tv_gridview_text = (TextView) view.findViewById(R.id.tv_gridview_text);
		iv_gridview_img.setImageResource(icon[position]);
		tv_gridview_text.setText(names[position]);
		return view;
	}

}
