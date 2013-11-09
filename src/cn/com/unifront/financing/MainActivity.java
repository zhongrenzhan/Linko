package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ViewFlipper;
import cn.com.unifront.adapter.FinancingGridViewAdapter;
import cn.com.unifront.dialog.FinancingDialog;
import cn.com.unifront.linko.R;

public class MainActivity extends Activity implements OnItemClickListener, android.view.View.OnClickListener {
	private GridView gv;
	private ViewFlipper vf;
	private FinancingDialog dialog;
	private Button buttonSure;
	private Button buttonCancle;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		
		findView();

	}


	private void findView() {
		vf = (ViewFlipper) this.findViewById(R.id.flipper);		
		
		gv = (GridView) this.findViewById(R.id.gv_01);
		gv.setOnItemClickListener(this);
		gv.setAdapter(new FinancingGridViewAdapter(this));
		
		vf.startFlipping();		
		vf.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.hyperspace_in));
		vf.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.hyperspace_out));
		vf.setInAnimation(AnimationUtils
				.loadAnimation(this, R.anim.push_up_in));
		vf.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_up_out));
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		switch (arg2) {
		case 0:
			loadOneIncomeActivity();
			break;
		case 1:
			loadOnePayActivity();
			break;
		case 2:
			loadOneOtherActivity();

			break;
		case 3:
			loadAccountBalanceInfoActivity();
			break;
		case 4:
			loadPieChartActivity();
			break;
		case 5:
			loadSetupActivity();
			break;
		case 6:
			loadPswManageActivity();

			break;
		case 7:
			loadBackUpActivity();
			break;
		case 8:
			loadSearchActivity();
			break;

		default:
			break;
		}
	}
	
	private void loadPieChartActivity() {
		Intent intent = new Intent(MainActivity.this, ChartDisplay.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}


	/**
	 * 账目查询
	 */
	private void loadSearchActivity() {
		Intent intent = new Intent(MainActivity.this, AccountSeach.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}


	private void loadBackUpActivity() {
		Intent intent = new Intent(MainActivity.this, BackUpActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}

	private void loadAccountBalanceInfoActivity() {
		Intent intent = new Intent(MainActivity.this, AccountBalanceInfo.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}

	private void loadSetupActivity() {
		Intent intent = new Intent(MainActivity.this, SetupActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}

	private void loadOneOtherActivity() {
		Intent intent = new Intent(MainActivity.this, OneOtherActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}

	private void loadOnePayActivity() {
		Intent intent = new Intent(MainActivity.this, OnePayActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}

	private void loadOneIncomeActivity() {
		Intent intent = new Intent(MainActivity.this, OneIncome.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}

	private void loadPswManageActivity() {
		Intent intent = new Intent(MainActivity.this, PswManageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}
	
	/**
	 * 按后退键按钮的触发事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {

		case KeyEvent.KEYCODE_BACK:
			showMyDialog();
			break;
		}
		return true;
	}
	
	/**
	 * 显示自定义的dialog
	 */
	private void showMyDialog() {
		dialog = new FinancingDialog(MainActivity.this);
		View view  = dialog.openPopupWindowDialog("提示！", "是否要退出Linko理财？", "是", "否");
		buttonSure = (Button) view.findViewById(R.id.id_bt_dialog_sure);
		buttonCancle = (Button) view.findViewById(R.id.id_bt_dialog_cancle);
		buttonSure.setOnClickListener(this);
		buttonCancle.setOnClickListener(this);
	}

	/**
	 * PopWindow上的两个按钮的触发事件
	 */
	@Override
	public void onClick(View v) {
		if(v==buttonSure){
			dialog.dismissPopWindow();
			finish();
		}
		if(v==buttonCancle){
			dialog.dismissPopWindow();
		}
	}

}
