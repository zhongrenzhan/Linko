
package cn.com.unifront.financing;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.ListAdapter;
import cn.com.unifront.adapter.FinancingSettingsHeaderAdapter;
import cn.com.unifront.linko.R;

public class SettingsActivity extends PreferenceActivity {
    private List<Header> myHeader;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(R.style.perference_set_activity);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBuildHeaders(List<Header> target) {
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
