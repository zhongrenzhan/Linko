
package cn.com.unifront.linko;

import java.util.ArrayList;

import cn.com.unifront.adapter.GridViewAdapter;
import cn.com.unifront.dialog.EditTitleDialog;
import cn.com.unifront.dialog.QuitAlertDialog;
import cn.com.unifront.financing.RegistActivity;
import cn.com.unifront.financing.SplashActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.KeyEvent;
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
    private static String IS_FIRST_LAUNCH = "is_first_launch";
    private static String NAME_ITEMS = "name_items_";
    private static String IMAGE_ITEMS = "image_items_";
    private ArrayList<String> mNameList = new ArrayList<String>();
    private int[] mImageArray;

    private static final int ALERTDIALOG_NORMAL = 1;
    private static final int ALERTDIALOG_EDIT = 2;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mDragGridView = (DragGridView) findViewById(R.id.drag_gridview);
        mDragGridView.setAdapter(mAdapter);
        mDragGridView.setOnItemLongClickListener(this);
        mDragGridView.setOnItemClickListener(this);
        createShortCut();
    }

    private void createShortCut() {
        if(!isShortCutExist()){
            //add shortcut name and shortcut icon
            Intent intent = new Intent();
            intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getResources().getString(R.string.shortcut_name));

            //what activity this shorcut can launch
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName component = new ComponentName(this, MainActivity.class);
            i.setComponent(component);
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, i);
            sendBroadcast(intent);
        }

    }

    private boolean isShortCutExist(){
        boolean isExist = false;
        int version = android.os.Build.VERSION.SDK_INT;;
        Uri uri = null;
        Log.i(TAG, ""+version);

        uri = Uri.parse("content://com.android.launcher2.settings/favorites");
        String selection = " title = ?";
        String[] selectionArgs = new String[]{getResources().getString(R.string.shortcut_name)};
        Cursor c = getContentResolver().query(uri, null, selection, selectionArgs, null);
        if(c != null){
            if(c.getCount() > 0){
                isExist = true;
            }
        }
        c.close();
        return isExist;
    }

    private void initData() {
        mContext = MainActivity.this;
        mImageArray = new int[] {
                R.drawable.ic_gridview_financing,
                R.drawable.ic_gridview_safe,
                R.drawable.ic_gridview_read,
                R.drawable.ic_gridview_cloud,
                R.drawable.ic_gridview_note,
                R.drawable.ic_gridview_douban,
                R.drawable.ic_gridview_news,
                R.drawable.ic_gridview_lottery,
                R.drawable.ic_gridview_music
        };
        String[] names = getResources().getStringArray(R.array.gridview_names);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (!sp.getBoolean(IS_FIRST_LAUNCH, false)) { // first launch
            sp.edit().putBoolean(IS_FIRST_LAUNCH, true).commit();
            for (int i = 0; i < names.length; i++) {
                mNameList.add(names[i]);
                sp.edit().putString(NAME_ITEMS + i, names[i]).commit();
                sp.edit().putString(IMAGE_ITEMS + i, mImageArray[i] + "").commit();
            }
        }

        mAdapter = new GridViewAdapter(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
        String titleName = mContext.getResources().getString(R.string.enter_item_name);
        showDialog(titleName, null, "CHAGE_ITEM_NAME_DIALOG", ALERTDIALOG_EDIT);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        switch (position) {
            case 0:
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public void doPositiveClick() {

    }

    public void doNegativeClick() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        String quitString = getResources().getString(R.string.confirm_quit_title);
        String warmMessage = getResources().getString(R.string.confirm_quit_message);
        showDialog(quitString, warmMessage, "QUIT_DIALOG", ALERTDIALOG_NORMAL);
        return true;
    }

    /**
     * @param title
     * @param message
     * @param tag
     */
    void showDialog(String title, String message, String tag, int dialogtype) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction. We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(tag);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        switch (dialogtype) {
            case ALERTDIALOG_NORMAL: {
                QuitAlertDialog newFragment = QuitAlertDialog.getInstance(title, message);
                newFragment.show(ft, tag);
            }
                break;
            case ALERTDIALOG_EDIT: {
                EditTitleDialog newFragment = EditTitleDialog.getInstance(title);
                newFragment.show(ft, tag);
            }
                break;

            default:
                break;
        }

    }

}
