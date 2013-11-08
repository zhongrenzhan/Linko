
package cn.com.unifront.dialog;

import cn.com.unifront.linko.MainActivity;
import cn.com.unifront.linko.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LinkoFragmentDialog extends DialogFragment implements OnClickListener {
    private int mStyle = 4;
    private int mTheme = 2;

    public static LinkoFragmentDialog getInstance(String title) {
        return getInstance(title, null);
    }

    public static LinkoFragmentDialog getInstance(String title, String message) {
        LinkoFragmentDialog dialog = new LinkoFragmentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        if (message != null) {
            bundle.putString("MESSAGE", message);
        }
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        switch (mStyle) {
            case 1:
                mStyle = DialogFragment.STYLE_NO_TITLE;
                break;
            case 2:
                mStyle = DialogFragment.STYLE_NO_FRAME;
                break;
            case 3:
                mStyle = DialogFragment.STYLE_NO_INPUT;
                break;
            case 4:
                mStyle = DialogFragment.STYLE_NORMAL;
                break;
        }
        switch (mTheme) {
            case 1:
                mTheme = android.R.style.Theme_Holo;
                break;
            case 2:
                mTheme = android.R.style.Theme_Holo_Light_Dialog;
                break;
            case 3:
                mTheme = android.R.style.Theme_Holo_Light;
                break;
            case 4:
                mTheme = android.R.style.Theme_Holo_Light_Panel;
                break;
            case 5:
                mTheme = android.R.style.Theme_Holo_Light;
                break;
        }

        setStyle(mStyle, mTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.linko_dialog_style, null);
        EditText et = (EditText) view.findViewById(R.id.et_title_enter);
        Button btSure = (Button) view.findViewById(R.id.bt_sure);
        Button btCancle = (Button) view.findViewById(R.id.bt_cancle);
        getDialog().setTitle(getArguments().getString("TITLE"));
        btSure.setOnClickListener(this);
        btCancle.setOnClickListener(this);
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                ((MainActivity)getActivity()).doPositiveClick();
                break;

            case R.id.bt_cancle:
                ((MainActivity)getActivity()).doNegativeClick();
                break;

            default:
                break;
        }

    }

}
