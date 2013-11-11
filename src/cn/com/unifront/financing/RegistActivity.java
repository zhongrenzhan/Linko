
package cn.com.unifront.financing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.com.unifront.linko.LinkoApplication;
import cn.com.unifront.linko.R;
import cn.com.unifront.util.DESPlus;

public class RegistActivity extends Activity implements OnClickListener {
    private static final String TAG = "RegestActivity";
    private EditText mUserPsw;
    private EditText mUserPswEnterCheck;
    private Button mButtonSure;
    private Button mButtonCancle;
    private EditText mQuestion;
    private EditText mAnswer;
    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        LinkoApplication app = (LinkoApplication) getApplication();
        sp = app.getDefaultSP();
        findView();
        registerListener();
    }

    public void findView() {

        mUserPsw = (EditText) this.findViewById(R.id.et_regist_pwd);
        mUserPswEnterCheck = (EditText) this.findViewById(R.id.et_regist_conf);
        mButtonSure = (Button) this.findViewById(R.id.bt_regest_sure);
        mButtonCancle = (Button) this.findViewById(R.id.bt_regest_cancle);
        mQuestion = (EditText) this
                .findViewById(R.id.et_regest_question);
        mAnswer = (EditText) this.findViewById(R.id.et_regest_answer);

    }

    public void registerListener() {
        mButtonSure.setOnClickListener(this);
        mButtonCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String psw = mUserPsw.getText().toString().trim();
        String pswCheck = mUserPswEnterCheck.getText().toString().trim();
        String qustion = mQuestion.getText().toString().trim();
        String answer = mAnswer.getText().toString().trim();

        switch (v.getId()) {
            case R.id.bt_regest_sure: {
                if ("".equals(psw) || "".equals(pswCheck) || "".equals(qustion)
                        || "".equals(answer)) {
                    String warmUserString = RegistActivity.this.getResources().getString(
                            R.string.warm_enter_right_psw);
                    Toast.makeText(RegistActivity.this, warmUserString,
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (psw.equals(pswCheck)) {
                        // String pwdencoded = MD5Encode.md5Encoding(psw1);
                        DESPlus plus;
                        try {
                            plus = new DESPlus();
                            String pwdEncoded = plus.encrypt(psw);

                            Editor editor = sp.edit();
                            editor.putString("USER_PSW", pwdEncoded);
                            editor.putString("qustion", qustion);
                            editor.putString("answer", answer);
                            editor.putBoolean("REGEST_STATE", true);
                            editor.commit();
                            String warmSuccess = RegistActivity.this.getResources().getString(
                                    R.string.warm_set_psw_success);
                            Toast.makeText(RegistActivity.this, warmSuccess,
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        finish();
                        loadMainUi();
                    } else {
                        String disaccord = RegistActivity.this.getResources().getString(
                                R.string.warm_psw_disaccord);

                        Toast.makeText(RegistActivity.this, disaccord,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
                break;

            case R.id.bt_regest_cancle:
                finish();
                break;
        }
    }

    private void loadMainUi() {
        Intent intent = new Intent(RegistActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.alpha_ctivity_in,
                R.anim.alpha_ctivity_out);
    }

}
