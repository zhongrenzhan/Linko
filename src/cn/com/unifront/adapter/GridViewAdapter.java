
package cn.com.unifront.adapter;

import java.util.ArrayList;

import cn.com.unifront.linko.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mNameList;

    public GridViewAdapter(Context mContext, ArrayList<String> list) {
        super(mContext, 0, list);
        this.mContext = mContext;
        this.mNameList = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return mNameList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.gridview_item, null);
        ImageView gridViewImage = (ImageView) convertView.findViewById(R.id.gridview_img);
        TextView gridViewText = (TextView) convertView.findViewById(R.id.gridview_name);
        gridViewText.setText(mNameList.get(position));
        return convertView;
    }

}
