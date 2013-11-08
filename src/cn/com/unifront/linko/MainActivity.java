
package cn.com.unifront.linko;

import java.util.ArrayList;

import cn.com.unifront.adapter.GridViewAdapter;
import cn.com.unifront.dialog.LinkoFragmentDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemLongClickListener, OnItemClickListener {
    private DragGridView mDragGridView;
    private GridViewAdapter mAdapter;
    private SharedPreferences sp;
    private Context mContext;
    private static String IS_FIST_LAUNCH = "is_fist_launch";
    private static String NAME_ITEMS = "name_items_";
    private ArrayList<String> mNameList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();   
        mDragGridView = (DragGridView) findViewById(R.id.drag_gridview);
        mDragGridView.setAdapter(mAdapter);
        mDragGridView.setOnItemLongClickListener(this);
        mDragGridView.setOnItemClickListener(this);
    }

    private void initData() {
        mContext = MainActivity.this;
        String[] names = getResources().getStringArray(R.array.gridview_names);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        
        if(!sp.getBoolean(IS_FIST_LAUNCH, false)){ //first launch
            sp.edit().putBoolean(IS_FIST_LAUNCH, true).commit();
            for (int i = 0; i < names.length; i++) {
                mNameList.add(names[i]);
                sp.edit().putString(NAME_ITEMS+i, names[i]).commit();//add item names to default .xml
            }
        }else{
            for (int i = 0; i < names.length; i++) {
                mNameList.add(sp.getString(NAME_ITEMS+i, "UNKNOW"));
            }
        }
        
        mAdapter = new GridViewAdapter(this, mNameList);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
        String pleaseEnterTitleName = mContext.getResources().getString(R.string.enter_item_name);
        LinkoFragmentDialog dialog = LinkoFragmentDialog.getInstance(pleaseEnterTitleName);
        dialog.show(MainActivity.this.getFragmentManager(), "ENTER_ITEM_NAME_DIALOG");
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        Toast.makeText(MainActivity.this, "click:"+position, 0).show();
    }

    public void doPositiveClick() {
        
    }

    public void doNegativeClick() {
        
    }

}
