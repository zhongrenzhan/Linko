package cn.com.unifront.financing;

import java.util.List;

import com.zgy.piechartview.OnPieChartItemSelectedLinstener;
import com.zgy.piechartview.PieChartView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.com.unifront.db.SqlOperate;
import cn.com.unifront.domain.CategoryName;
import cn.com.unifront.linko.R;

public class ChartDisplay extends Activity implements OnPieChartItemSelectedLinstener, OnTabChangeListener {
	private TabHost tabhost;
	private PieChartView pieChart;
	private PieChartView pieChart1;
	private PieChartView pieChart2;
	private PieChartView pieChart3;
	private TextView text_item_info1;
	private TextView text_item_info2;
	private TextView text_item_info3;
	private TextView text_item_info;
	private LayoutInflater inflater;
	
	private List<CategoryName>  categoryName;
	private List<cn.com.unifront.domain.AccountBalanceInfo> info;
	
	public String[] colors ;
	public float[] items ;
	private int radius = 100;
	private int strokeWidth = 1;
	private String strokeColor = "#000000";
	private float animSpeed = (float) 8;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cartogram);
		inflater =  LayoutInflater.from(this);
		text_item_info1 = (TextView) findViewById(R.id.text_item_info1);
		pieChart1 = (PieChartView) findViewById(R.id.parbar_view1);
		text_item_info2 = (TextView) findViewById(R.id.text_item_info2);
		pieChart2 = (PieChartView) findViewById(R.id.parbar_view2);
		text_item_info3 = (TextView) findViewById(R.id.text_item_info3);
		pieChart3 = (PieChartView) findViewById(R.id.parbar_view3);
		
		addTabs();		
		getTabDataAndShow("incomecategory");		
		tabhost.setOnTabChangedListener(this);
		
	}
	
	/**
	 * 添加Tabs
	 */
	private void addTabs() {
		tabhost=(TabHost) findViewById(R.id.tabhost1);
		tabhost.setup();
						
		TabSpec tabs1 = tabhost.newTabSpec("income");
		tabs1.setIndicator(getIndicatorView("收入", R.id.iv_lock));
		tabs1.setContent(R.id.ll_incom_chart1);
		tabhost.addTab(tabs1);
				
		TabSpec tabs2 = tabhost.newTabSpec("out");
		tabs2.setIndicator(getIndicatorView("支出", R.id.iv_unlock));
		tabs2.setContent(R.id.ll_incom_chart2);		
		tabhost.addTab(tabs2);
		
		TabSpec tabs3 = tabhost.newTabSpec("all");
		tabs3.setIndicator(getIndicatorView("总的", R.id.iv_lock));
		tabs3.setContent(R.id.ll_incom_chart3);
		tabhost.addTab(tabs3);
		
		tabhost.setCurrentTab(0);
	}
	
	/**
	 * chart显示
	 */
	private void getTabDataAndShow(String tab) {
		String table = tab;
		SqlOperate oprate = new SqlOperate(ChartDisplay.this);		
		oprate.updateCategryAmount(table);//更新一下收入分类对应的金额
		
		categoryName = oprate.getCategry_1(table);
		int size = categoryName.size();
		colors=new String[]{};
		items = new float[size];
		for (int i =0;i <size;i++){	
		    	items[i]= Math.abs(Float.parseFloat(categoryName.get(i).getAmount()));
		}	
		for(float item: items){
			Log.e("items", item+"");
		}
		
		findViewsOfPieCahrt();
		
	}
	
	/**
	 * 设置饼图的显示方式
	 */
	private void findViewsOfPieCahrt() {
		int i = tabhost.getCurrentTab();
		if(i==0){
			pieChart = pieChart1;
		}else if(i==1){
			pieChart = pieChart2;
		}else if(i==2){
			pieChart = pieChart3;
		}
		pieChart.setAnimEnabled(true);//是否开启动画
		pieChart.setItemsSizes(items);// 设置各个块的值
		pieChart.setAnimSpeed(animSpeed);// 设置旋转速度
		pieChart.setRaduis(radius);// 设置饼状图半径，不包含边缘的圆环
		pieChart.setStrokeWidth(strokeWidth);// 设置边缘的圆环粗度
		pieChart.setStrokeColor(strokeColor);// 设置边缘的圆环颜色
		pieChart.setRotateWhere(PieChartView.TO_BOTTOM);//设置选中的item停靠的位置，默认在右侧
		pieChart.setSeparateDistence(4);// 设置旋转的item分离的距离
				
        pieChart.setOnItemSelectedListener(this);			
	}

	
	/**
	 * 饼图点击相应的块的callback
	 */
	@Override
	public void onPieChartItemSelected(PieChartView view, int position, String colorRgb, float size, float rate, boolean isFreePart, float rotateTime) {
		
		if (isFreePart) {
			text_item_info1.setText("多余的部分" + position + "\r\nitem size: " + size + "\r\nitem color: " + colorRgb + "\r\nitem rate: " + rate + "\r\nrotateTime : " + rotateTime);
		} else {
			float temp = rate*100;
			String result = String.format("%.2f", temp);
			String percent = result+"%";						
			int i = tabhost.getCurrentTab();
			
			if(i==0){
				String catogoryinfo = categoryName.get(position).getName();
				text_item_info = text_item_info1;				
				text_item_info.setText( "类型："+catogoryinfo+"\r\n金额："+size +"元"+"\r\n比例："+ percent+"\r\nitem：" +position);
			
			}else if(i==1){
				String catogoryinfo = categoryName.get(position).getName();
				text_item_info = text_item_info2;
				text_item_info.setText( "类型："+catogoryinfo+"\r\n金额："+size +"元"+"\r\n比例："+ percent+"\r\nitem：" +position);
			
			}else if(i==2){
				String accounName = info.get(position).getName();
				text_item_info = text_item_info3;
				text_item_info.setText( "类型："+accounName+"\r\n金额："+size +"元"+"\r\n比例："+ percent+"\r\nitem：" +position);
			}	
			
			text_item_info.setVisibility(View.VISIBLE);
			Animation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
			myAnimation_Alpha.setDuration((int) (3 * rotateTime));
			text_item_info.startAnimation(myAnimation_Alpha);
		}
		
		
	}

	/**
	 * Tab切换触发
	 */
	@Override
	public void onTabChanged(String tabId) {
		if(tabId.equals("income")){
			getTabDataAndShow("incomecategory");	
		}else if(tabId.equals("out")){
			getTabDataAndShow("payoutcategory");
		}else if(tabId.equals("all")){
			getTabDatAndShowOfAccount();
		}
		
	}

	private void getTabDatAndShowOfAccount() {
		SqlOperate oprate = new SqlOperate(ChartDisplay.this);		
		oprate.updateAccountBalance();
		info = oprate.getAccountBalanceInfo();
		int size = info.size();
		colors=new String[]{};
		items = new float[size];
		for (int i =0;i <size;i++){	
			if(Float.parseFloat(info.get(i).getBalance())>0){}
		    	items[i]= Float.parseFloat(info.get(i).getBalance());
		}	
		findViewsOfPieCahrt();
	}
	/**
	 * 获取条目显示的view对象 
	 */
	private View getIndicatorView(String name, int iconid){
		View view = inflater.inflate(R.layout.tab_nav, null);
	    ImageView ivicon =	(ImageView) view.findViewById(R.id.ivIcon);
	    TextView tvtitle =	(TextView) view.findViewById(R.id.tvTitle);
	    ivicon.setImageResource(iconid);
	    tvtitle.setText(name);
	    return view;
	}
	

}
