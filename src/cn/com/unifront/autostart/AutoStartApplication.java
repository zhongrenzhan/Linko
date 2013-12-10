
package cn.com.unifront.autostart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.unifront.autostart.AutoStartApplication.TableInfo;
import cn.com.unifront.linko.R;

interface AutoAppClickListener {
    void onItemClick(TableInfo tab, AdapterView<?> parent,
            View view, int position, long id);
}

public class AutoStartApplication extends Fragment implements AutoAppClickListener {
    private static final String AUTO_START_APP_LIST = "auto_start_app_list";
    private static final String BOOT_START_PERMISSION = "android.permission.RECEIVE_BOOT_COMPLETED";
    private static final String TAG = "AutoStartApplication";
    private static final int SYSTEM_AUTO_APP = 0;
    private View mRootView;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private SharedPreferences sp;
    private PackageManager mPkm;
    private Context mContext;
    private ArrayList<TableInfo> mTabs = new ArrayList<TableInfo>();
    private List<Map<String, Object>> apps;
    
    private int mCurrentPos = 0;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        sp = getActivity().getSharedPreferences(AUTO_START_APP_LIST,
                Context.MODE_PRIVATE);
        mPkm = getActivity().getPackageManager();

        TableInfo tab1 = new TableInfo(getString(R.string.apps_system), this);
        mTabs.add(tab1);
        TableInfo tab2 = new TableInfo(getString(R.string.apps_user), this);
        mTabs.add(tab2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        mRootView = inflater.inflate(R.layout.manage_applications_content, null);
        ViewPager pager = (ViewPager) mRootView.findViewById(R.id.pager);
        MyPagerAdapter adapter = new MyPagerAdapter(); 
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(adapter);
        PagerTabStrip tabs = (PagerTabStrip) mRootView.findViewById(R.id.tabs);
        tabs.setTextColor(getResources().getColor(R.color.white_color));
        tabs.setTabIndicatorColorResource(android.R.color.holo_blue_light);
        return mRootView;
    }

    private class MyPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
        
        @Override
        public int getCount() {
            if (mTabs != null) {
                return mTabs.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).lable;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View root = mTabs.get(position).buildView(mInflater);
            container.addView(root);
            return root;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                updateCurrentTab(mCurrentPos);
            }
        }

        

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPos = position;
        }
    }
    
    private void updateCurrentTab(int position) {
        Log.i("zhongrenzhan", "updateCurrentTab");
        TableInfo info =  mTabs.get(position);
        info.buildView(mInflater);
    }

    public class TableInfo implements OnItemClickListener {
        private String lable;
        private LayoutInflater mInflater;
        private View mRootView;
        public AutoAppClickListener mClickListener;
        private MyAutoAppsListAdapter adapter;

        public TableInfo(String lable, AutoAppClickListener clickListener) {
            this.lable = lable;
            mClickListener = clickListener;
        }

        public View buildView(LayoutInflater flater) {
            mInflater = flater;
            mRootView = mInflater.inflate(R.layout.auto_start_apps, null);
            ListView appList = (ListView) mRootView.findViewById(R.id.list);
            apps = getAutoStartApps(mCurrentPos);
            adapter = new MyAutoAppsListAdapter(this);
            appList.setAdapter(adapter);
            appList.setOnItemClickListener(this);
            return mRootView;
        }

        private class MyAutoAppsListAdapter extends BaseAdapter{
            private TableInfo mTab;
            
            public MyAutoAppsListAdapter(TableInfo tab) {
                this.mTab = tab;
            }

            @Override
            public int getCount() {
                if (apps != null) {
                    return apps.size();
                } else {
                    return 0;
                }
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                AppViewHolder holder = AppViewHolder.createOrRecycle(mTab.mInflater, convertView);
                convertView = holder.rootView;

                String packageName = apps.get(position).get("name").toString();
                holder.appName.setText(packageName);
                holder.appName.setTextColor(mContext.getResources().getColorStateList(
                        android.R.color.primary_text_dark));
                holder.appIcon.setImageDrawable((Drawable) apps.get(position)
                        .get("icon"));
                holder.checkBox.setVisibility(View.VISIBLE);
                boolean isEnable = getAutoStartAppState(packageName);
                holder.checkBox.setChecked(isEnable);
                holder.disabled.setVisibility(View.VISIBLE);
                holder.disabled.setText(apps.get(position)
                        .get("flags").toString());
                return convertView;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView tv = (TextView) view.findViewById(R.id.app_name);
            CheckBox cb = (CheckBox) view.findViewById(R.id.app_state);
            updateAutoStartAppState(tv.getText().toString(),!cb.isChecked());
            adapter.notifyDataSetChanged();
            mClickListener.onItemClick(this, parent, view, position, id);
            
        }
    }

    // View Holder used when displaying views
    static class AppViewHolder {
        public View rootView;
        public TextView appName;
        public ImageView appIcon;
        public TextView disabled;
        public CheckBox checkBox;

        static public AppViewHolder createOrRecycle(LayoutInflater inflater, View convertView) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.manage_applications_item, null);

                // Creates a ViewHolder and store references to the two children
                // views
                // we want to bind data to.
                AppViewHolder holder = new AppViewHolder();
                holder.rootView = convertView;
                holder.appName = (TextView) convertView.findViewById(R.id.app_name);
                holder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
                holder.disabled = (TextView) convertView.findViewById(R.id.app_disabled);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.app_state);
                convertView.setTag(holder);
                return holder;
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                return (AppViewHolder) convertView.getTag();
            }
        }
    }

    public List<Map<String, Object>> getAutoStartApps(int category) {
        List<ApplicationInfo> packages = mPkm.getInstalledApplications(0);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(packages.size());
        Iterator<ApplicationInfo> appInfoIterator = packages.iterator();

        int listLength = ProtectedWhiteList.APP_WHITE_LIST.length;
        HashSet<String> whiteSet = new HashSet<String>(listLength);
        for (int i = 0; i < listLength; i++) {
            whiteSet.add(ProtectedWhiteList.APP_WHITE_LIST[i]);
        }
        while (appInfoIterator.hasNext()) {
            ApplicationInfo app = (ApplicationInfo) appInfoIterator.next();
            boolean queryFlag = false;
            if (category == SYSTEM_AUTO_APP) {
                queryFlag = app.flags < 10000000;
            }else{
                queryFlag = app.flags > 10000000;
            }
            if (PackageManager.PERMISSION_GRANTED == mPkm.checkPermission(
                    BOOT_START_PERMISSION, app.packageName) && queryFlag) {
                String label = mPkm.getApplicationLabel(app)
                        .toString();
                Drawable appIcon = mPkm.getApplicationIcon(app);

                Map<String, Object> map = new HashMap<String, Object>();
                if (!whiteSet.contains(app.packageName)) {
                    boolean isEnable = getAutoStartAppState(app.packageName);
                    updateAutoStartAppState(app.packageName, isEnable);
                    map.put("desc", app.packageName);
                    map.put("icon", appIcon);
                    map.put("name", label);
                    map.put("flags", app.flags);
                    map.put("class", app.getClass());
                    list.add(map);
                }
            }
        }
        return list;
    }

    private void updateAutoStartAppState(String packageName, boolean state) {

        sp.edit().putBoolean(packageName, state).commit();
    }

    private boolean getAutoStartAppState(String packageName) {
        return sp.getBoolean(packageName, false);
    }

    @Override
    public void onItemClick(TableInfo tab, AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity().getBaseContext(), "tab:" + tab + "position:" + position, 0)
                .show();
    }

}
