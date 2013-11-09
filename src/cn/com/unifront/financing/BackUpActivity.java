package cn.com.unifront.financing;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.FileUtils;

public class BackUpActivity extends Activity implements OnClickListener {
	private LinearLayout ll_backup_to_sd;
	private LinearLayout ll_inport_to_phone;
	private LinearLayout ll_export_to_excel;
	private String DATABASE_PATH;
	private String COPY_DATABASE_PATH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
		findViews();
		setClickListener();
	}

	private void setClickListener() {
		ll_backup_to_sd.setOnClickListener(this);
		ll_inport_to_phone.setOnClickListener(this);
		ll_export_to_excel.setOnClickListener(this);
	}

	private void findViews() {
		ll_backup_to_sd = (LinearLayout) this
				.findViewById(R.id.ll_backup_to_sd);
		ll_inport_to_phone = (LinearLayout) this
				.findViewById(R.id.ll_inport_to_phone);
		ll_export_to_excel = (LinearLayout) this
				.findViewById(R.id.ll_export_to_excel);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_backup_to_sd:
			backupDatabase();
			break;
		case R.id.ll_inport_to_phone:
			importDatabase();
			break;

		default:
			break;
		}
	}

	private void importDatabase() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String sdpath = Environment.getExternalStorageDirectory().getPath();
			COPY_DATABASE_PATH = sdpath + "/licai/backup";
		} else {

			Toast.makeText(BackUpActivity.this, "没有检测到SD卡，可能无法备份成功",
					Toast.LENGTH_SHORT).show();
		}

		DATABASE_PATH = "/data/data/com.example.licai/databases";
		File file = new File(DATABASE_PATH);
		File filecopyto = new File(COPY_DATABASE_PATH);
		try {
			FileUtils.copyDirectory(filecopyto, file);
			Toast.makeText(BackUpActivity.this, "还原成功！",
					Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void backupDatabase() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			String sdpath = Environment.getExternalStorageDirectory().getPath();
			COPY_DATABASE_PATH = sdpath + "/licai/backup";
		} else {

			Toast.makeText(BackUpActivity.this, "没有检测到SD卡，可能无法备份成功",
					Toast.LENGTH_SHORT).show();
		}

		DATABASE_PATH = "/data/data/com.example.licai/databases";
		File file = new File(DATABASE_PATH);
		File filecopyto = new File(COPY_DATABASE_PATH);
		try {
			FileUtils.copyDirectory(file, filecopyto);
			Toast.makeText(BackUpActivity.this, "备份成功！",
					Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
