package cn.com.unifront.financing;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.EmailSender;

public class SendEmail extends Activity implements OnClickListener {
	private EditText email_content;
	private Button sendButton;
	private Button cancleButton;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		email_content = (EditText) this.findViewById(R.id.id_et_email_content);
		sendButton = (Button) this.findViewById(R.id.id_bt_send);
		cancleButton = (Button) this.findViewById(R.id.bt_back);
		
		sendButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v==sendButton){
			content = email_content.getText().toString().trim();
			Log.e("content:", content);
			if("".equals(content)!=true){
				new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							EmailSender sender = new EmailSender();
							//设置服务器地址和端口，网上搜的到
							sender.setProperties("smtp.163.com", "25");
							//分别设置发件人，邮件标题和文本内容
							sender.setMessage("15295953557@163.com", "EmailSender", content);
							//设置收件人
							sender.setReceiver(new String[]{"472279438@qq.com"});
							//添加附件，我这里注释掉，因为有人跟我说这行报错...
							//这个附件的路径是我手机里的啊，要发你得换成你手机里正确的路径
							//sender.addAttachment("/default.prop");
							//发送邮件
							sender.sendEmail("smtp.163.com", "15295953557@163.com", "linko002");
						} catch (AddressException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				Toast.makeText(SendEmail.this, "感谢您的建议！", Toast.LENGTH_LONG).show();
				finish();
			}else{
				Toast.makeText(SendEmail.this, "您还没有写下建议呢！", Toast.LENGTH_LONG).show();
			}

		}	
		if(v==cancleButton){
			finish();
		}
	}

}
