package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.com.unifront.linko.LinkoApplication;
import cn.com.unifront.linko.R;

public class SetupActivity extends Activity implements OnClickListener {
	private LinearLayout ll_set_category;
	private LinearLayout ll_setup_payoutcategory;
	private LinearLayout ll_about_author;
	private LinearLayout ll_psw_manage;
	private LinearLayout ll_setup_account;
	private LinearLayout ll_send_email;

	private SharedPreferences sp;
	private ImageView iv_unlock;
	private ImageView iv_lock;
	private boolean state;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 重新更新显示锁的图标
			state = sp.getBoolean("PROTECT_STATE", false);
			viewOfLock();
		}

	};
	private String tag = "返回值：";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkoApplication app = (LinkoApplication) getApplication();
        sp = app.getDefaultSP();
		setContentView(R.layout.activity_setup);
		findView();
		setItemonClickListener();

	}

	private void setItemonClickListener() {
		ll_set_category.setOnClickListener(this);
		ll_about_author.setOnClickListener(this);
		ll_setup_account.setOnClickListener(this);
		ll_setup_payoutcategory.setOnClickListener(this);
		ll_send_email.setOnClickListener(this);
		iv_unlock.setOnClickListener(this);
		iv_lock.setOnClickListener(this);
	}

	private void findView() {
		ll_set_category = (LinearLayout) this
				.findViewById(R.id.ll_set_category);
		ll_setup_payoutcategory = (LinearLayout) this
				.findViewById(R.id.ll_setup_payoutcategory);
		ll_about_author = (LinearLayout) this
				.findViewById(R.id.ll_about_author);
		ll_send_email = (LinearLayout) this
		.findViewById(R.id.ll_send_email);
		iv_unlock = (ImageView) this.findViewById(R.id.iv_unlock);
		iv_lock = (ImageView) this.findViewById(R.id.iv_lock);
		ll_setup_account = (LinearLayout) this
				.findViewById(R.id.ll_setup_account);

		// 获取配置文件中"PROTECT_STATE"的值
		state = sp.getBoolean("PROTECT_STATE", false);
		// 显示锁的图标
		viewOfLock();
	}

	/**
	 * 显示锁的图标,依据配置文件中"PROTECT_STATE"的值来显示对应的图标
	 */
	private void viewOfLock() {
		if (state) {
			iv_lock.setVisibility(View.VISIBLE);
			iv_unlock.setVisibility(View.GONE);
		} else {
			iv_unlock.setVisibility(View.VISIBLE);
			iv_lock.setVisibility(View.GONE);
		}

	}

	/**
	 * item点击的事件处理函数
	 */
	@Override
	public void onClick(View v) {
		if (v == ll_set_category) {// 分类
			loadIncomeCategoryListActivity();
		} else if (v == ll_about_author) {// 关于作者
			loadAboutZhongshanActivity();
		} else if (v == ll_setup_account) {// 账户管理
			loadAccountListActivity();
		}else if (v==ll_setup_payoutcategory){
			loadPayOutCategoryActivity();
		}else if (v==ll_send_email){
			loadSendEmailActivity();
		}else if (v==iv_lock){
		    sp.edit().putBoolean("PROTECT_STATE", false).commit();
			handler.sendEmptyMessage(0);
		}else if (v==iv_unlock){
		    sp.edit().putBoolean("PROTECT_STATE", true).commit();
			handler.sendEmptyMessage(0);
		}
	}

	private void loadSendEmailActivity() {
		Intent intent = new Intent(SetupActivity.this,
				SendEmail.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}

	private void loadPayOutCategoryActivity() {
		Intent intent = new Intent(SetupActivity.this,
				PayCategoryListActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
		
	}

	/**
	 * 进入关于作者
	 */
	private void loadAboutZhongshanActivity() {
		Intent intent = new Intent(SetupActivity.this,
				AboutZhongshan.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);

	}

	/**
	 * 进入分类列表
	 */
	private void loadIncomeCategoryListActivity() {
		Intent intent = new Intent(SetupActivity.this,
				IncomeCategoryListActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
	}

	/**
	 * 进入账户列表
	 */
	private void loadAccountListActivity() {
		Intent intent = new Intent(SetupActivity.this,
				AccountListActivity.class);
		intent.putExtra("requestcode", 2);
		startActivity(intent);
		overridePendingTransition(R.anim.translate_right, R.anim.translate_left);
	}

}
