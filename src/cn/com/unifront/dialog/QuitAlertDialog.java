
package cn.com.unifront.dialog;

import cn.com.unifront.linko.MainActivity;
import cn.com.unifront.linko.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class QuitAlertDialog extends DialogFragment implements OnClickListener {

    public static QuitAlertDialog getInstance(String title, String message) {
        QuitAlertDialog dialog = new QuitAlertDialog();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("MESSAGE", message);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("TITLE");
        String message = getArguments().getString("MESSAGE");
        LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.linko_dialog_style, null);
        EditText et = (EditText) view.findViewById(R.id.et_title_enter);
        et.setVisibility(View.GONE);
        Button btSure = (Button) view.findViewById(R.id.bt_sure);
        Button btCancle = (Button) view.findViewById(R.id.bt_cancle);
        btSure.setOnClickListener(this);
        btCancle.setOnClickListener(this);
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.ic_launcher)
                .setTitle(title)
                .setMessage(message)
                .setView(view)
                .create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                getActivity().finish();
                dismiss();
                break;

            case R.id.bt_cancle:
                dismiss();
                break;

            default:
                break;
        }

    }

}
