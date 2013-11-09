package cn.com.unifront.financing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import cn.com.unifront.linko.R;

public class AboutZhongshan extends Activity implements OnClickListener  {
	private ImageButton button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutzhongshan);
		button = (ImageButton) this.findViewById(R.id.imbt_back);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		finish();
		
	}

}
