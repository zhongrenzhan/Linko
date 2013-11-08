
package cn.com.unifront.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.com.unifront.linko.MainActivity;
import cn.com.unifront.linko.R;

public class EditTitleDialog extends DialogFragment implements OnClickListener {
    private int mStyle = 4;
    private int mTheme = 2;

    public static EditTitleDialog getInstance(String title) {
        EditTitleDialog dialog = new EditTitleDialog();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mStyle = DialogFragment.STYLE_NORMAL;
        mTheme = android.R.style.Theme_Holo_Light_Dialog;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                ((MainActivity) getActivity()).doPositiveClick();
                dismiss();
                break;

            case R.id.bt_cancle:
                ((MainActivity) getActivity()).doNegativeClick();
                dismiss();
                break;

            default:
                break;
        }

    }

}
