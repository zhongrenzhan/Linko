
package cn.com.unifront.financing;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import android.app.Activity;
import android.content.Context;
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
    private EditText mContent;
    private Button sendButton;
    private Button cancleButton;
    private EditText mEmailAddress;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = SendEmail.this;
        setContentView(R.layout.activity_feedback);
        mContent = (EditText) this.findViewById(R.id.id_et_email_content);
        sendButton = (Button) this.findViewById(R.id.id_bt_send);
        cancleButton = (Button) this.findViewById(R.id.bt_back);
        mEmailAddress = (EditText) this.findViewById(R.id.email_address);
        sendButton.setOnClickListener(this);
        cancleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == sendButton) {
            final String content = mContent.getText().toString().trim();
            final String address = mEmailAddress.getText().toString().trim();
            final String host = SendEmail.this.getString(R.string.email_host);
            final String receiver = SendEmail.this.getString(R.string.receiver_email_address);
            Log.e("content:", content);
            if ("".equals(content) != true) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            EmailSender sender = new EmailSender();
                            sender.setProperties(host, "25");
                            sender.setMessage(address, "LinkoFeedBack", content);
                            sender.setReceiver(new String[] {receiver});
                            // sender.addAttachment("/default.prop");
                            sender.sendEmail(host, address, "linko002");
                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(SendEmail.this, SendEmail.this.getString(R.string.text_email_thanks), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(SendEmail.this, SendEmail.this.getString(R.string.text_email_no_content), Toast.LENGTH_LONG).show();
            }

        }
        if (v == cancleButton) {
            finish();
        }
    }

}
