package cn.com.unifront.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.com.unifront.linko.R;

public class FinancingDialog {
	private Context context;
	private PopupWindow window;

	public FinancingDialog(Context context) {
		this.context = context;
	}

	public View openPopupWindowDialog(String title, String content,
			String buttonText1, String buttonText2) {
		View view = View.inflate(context, R.layout.my_dialog, null);
		Button button1 = (Button) view.findViewById(R.id.id_bt_dialog_sure);
		Button button2 = (Button) view.findViewById(R.id.id_bt_dialog_cancle);
		TextView titleText = (TextView) view
				.findViewById(R.id.id_text_dialog_title);
		TextView contentText = (TextView) view
				.findViewById(R.id.id_text_warming);
		titleText.setText(title);
		contentText.setText(content);
		button1.setText(buttonText1);
		button2.setText(buttonText2);

		 window = new PopupWindow(view, 300, 200, true);
		window.showAtLocation(view, Gravity.CENTER, 0, 0);
		return view;
	}

	public void dismissPopWindow() {
		if (window != null && window.isShowing()) {
			window.dismiss();
			window = null;
		}
	}

}
