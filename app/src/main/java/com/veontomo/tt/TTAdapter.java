package com.veontomo.tt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.tt.models.TongueTwister;

import java.util.ArrayList;
import java.util.List;

/**
 * Tongue-twister adapter
 * TODO: implement this class methods
 */
public class TTAdapter extends BaseAdapter {

    /**
     * items that this adapter should display
     */
    private List<TongueTwister> mItems;

    private final Context mContext;

    public TTAdapter(Context context, List<TongueTwister> items) {
        this.mContext = context;
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return this.mItems == null ? 0 : this.mItems.size();
    }

    @Override
    public TongueTwister getItem(int position) {
        return this.mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<TongueTwister> items){
        this.mItems = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.tt, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) row.findViewById(R.id.tt_text);
            holder.idView = (TextView) row.findViewById(R.id.tt_id);
            row.setTag(holder);
        }
        TongueTwister tt = this.getItem(position);
        ViewHolder holder = (ViewHolder) row.getTag();
        holder.textView.setText(tt.text);
        holder.idView.setText(String.valueOf(tt.id));

        return row;
    }

    private static final class ViewHolder {
        public TextView textView;
        public TextView idView;
    }
}
