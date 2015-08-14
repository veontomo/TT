package com.veontomo.tt.tasks;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.veontomo.tt.TTAdapter;
import com.veontomo.tt.models.TongueTwister;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario Rossi on 15/08/2015 at 00:04.
 *
 * @author veontomo@gmail.com
 * @since 0
 */
public class LoadAllTongueTwisterTask extends AsyncTask<Void, Void, Void> {
    private TTAdapter mAdapter;

    public LoadAllTongueTwisterTask(final TTAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (this.mAdapter != null) {
            List<TongueTwister> list = new ArrayList<>();
            list.add(new TongueTwister("aaaa"));
            list.add(new TongueTwister("bbb"));
            this.mAdapter.setItems(list);
        }
        return null;
    }

    @Override
    public void onPostExecute(Void arg) {
        this.mAdapter = null;
    }
}
