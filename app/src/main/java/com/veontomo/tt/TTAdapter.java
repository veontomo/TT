package com.veontomo.tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.tt.models.TongueTwister;

import java.util.List;

/**
 * Tongue-twister adapter
 * TODO: implement this class methods
 */
public class TTAdapter extends BaseAdapter {

    /**
     * items that this adapter should display
     */
    private final List<TongueTwister> mItems;

    private final Context mContext;

    public TTAdapter(Context context, List<TongueTwister> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public TongueTwister getItem(int position) {
        return this.mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.tt, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.tt_text);
            row.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) row.getTag();
        holder.textView.setText(this.getItem(position).text);
        return row;
    }

    private static final class ViewHolder {
        public TextView textView;
    }
}
