
package cn.com.unifront.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.preference.PreferenceActivity.Header;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import cn.com.unifront.linko.R;

public class FinancingSettingsHeaderAdapter extends ArrayAdapter<Header> {
    private LayoutInflater inflater;
    private static final int HEADER_TYPE_NORMAL = 0;
    private static final int HEADER_TYPE_SWITCH = 1;
    private static final int HEADER_TYPE_CATEGORY = 2;
    private HeaderViewHolder holder;
    private Context context;

    public FinancingSettingsHeaderAdapter(Context context, int textViewResourceId,
            List<Header> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class HeaderViewHolder {
        TextView title;
        TextView summary;
        ImageView imageView;
        Switch switch_;
    }

    @SuppressLint("NewApi")
    private int getHeaderType(Header header) {
        if (header.intent == null) {
            return HEADER_TYPE_CATEGORY;
        } else if (header.id == R.id.psw_protect_state) {
            return HEADER_TYPE_SWITCH;
        } else {
            return HEADER_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        Header header = getItem(position);
        return getHeaderType(header);
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != HEADER_TYPE_CATEGORY;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        Header header = getItem(position);
        int headerType = getHeaderType(header);
        if (convertView == null) {
            holder = new HeaderViewHolder();
            switch (headerType) {
                case HEADER_TYPE_CATEGORY:
                    view = inflater.inflate(R.layout.preference_header_category_item, null);
                    holder.title = (TextView) view.findViewById(R.id.title);
                    break;
                case HEADER_TYPE_SWITCH:
                    view = inflater.inflate(R.layout.preference_header_switch_item, null);
                    holder.imageView = (ImageView) view.findViewById(R.id.icon);
                    holder.title = (TextView) view.findViewById(R.id.title);
                    break;
                case HEADER_TYPE_NORMAL:
                    view = inflater.inflate(R.layout.preference_header_item, null);
                    holder.imageView = (ImageView) view.findViewById(R.id.icon);
                    holder.title = (TextView) view.findViewById(R.id.settings_title);
                    break;

                default:
                    break;
            }
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (HeaderViewHolder) view.getTag();
        }

        switch (headerType) {
            case HEADER_TYPE_NORMAL:
                holder.title.setText(header.getTitle(context.getResources()));
                holder.imageView.setImageDrawable(context.getResources().getDrawable(
                        R.drawable.setup_account));
                break;
            case HEADER_TYPE_SWITCH:
                holder.title.setText(header.getTitle(context.getResources()));
                break;
            case HEADER_TYPE_CATEGORY:
                holder.title.setText(header.getTitle(context.getResources()));
                break;

            default:
                break;
        }
        return view;
    }

}
