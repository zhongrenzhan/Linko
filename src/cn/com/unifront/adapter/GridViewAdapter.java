
package cn.com.unifront.adapter;


import cn.com.unifront.linko.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private static String NAME_ITEMS = "name_items_";
    private static String IMAGE_ITEMS = "image_items_";
    private SharedPreferences sp;
    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            ImageView gridViewImage = (ImageView) convertView.findViewById(R.id.gridview_img);
            TextView gridViewText = (TextView) convertView.findViewById(R.id.gridview_name);
            gridViewImage.setImageDrawable(mContext.getResources().getDrawable(Integer.parseInt(sp.getString(IMAGE_ITEMS+position, R.drawable.ic_launcher+""))));
            gridViewText.setText(sp.getString(NAME_ITEMS+position, "UNKNOW"));
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
