
package cn.com.unifront.financing;

import java.util.List;

import cn.com.unifront.adapter.FinancingSettingsHeaderAdapter;
import cn.com.unifront.linko.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends PreferenceActivity {
    private List<Header> myHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(R.style.perference_set_activity);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBuildHeaders(List<Header> target) {
        // super.setListAdapter(new HeaderAdapter(this, 0, target));
        myHeader = target;
        loadHeadersFromResource(R.layout.financing_settings, target);

    }

    @Override
    public void setListAdapter(ListAdapter adapter) {
        // TODO Auto-generated method stub
        if (adapter == null) {
            super.setListAdapter(null);
        } else {
            super.setListAdapter(new FinancingSettingsHeaderAdapter(SettingsActivity.this, 0,
                    myHeader));
        }

    }

}
