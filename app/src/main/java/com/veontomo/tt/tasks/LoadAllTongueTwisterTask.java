package com.veontomo.tt.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.veontomo.tt.TTAdapter;
import com.veontomo.tt.models.Storage;
import com.veontomo.tt.models.TongueTwister;

import java.util.List;

/**
 * Created by Mario Rossi on 15/08/2015 at 00:04.
 *
 * @author veontomo@gmail.com
 * @since 0
 */
public class LoadAllTongueTwisterTask extends AsyncTask<Void, Void, Void> {
    private Context mContext;
    private TTAdapter mAdapter;

    public LoadAllTongueTwisterTask(final TTAdapter adapter, final Context context) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // check whether the adapter has been nullified
        if (this.mAdapter == null) {
            return null;
        }
        Storage storage = new Storage(this.mContext);
        List<TongueTwister> list = storage.retrieveAllTT();
        this.mAdapter.setItems(list);
        return null;
    }

    @Override
    public void onPostExecute(Void arg) {
        this.mAdapter = null;
        this.mContext = null;
    }
}
