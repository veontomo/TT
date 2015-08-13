package com.veontomo.tt.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.veontomo.tt.Config;
import com.veontomo.tt.models.Storage;

/**
 * Saves tongue twister in a separate thread.
 */
public class SaveTongueTwisterTask extends AsyncTask<String, Void, Void> {
    private Context mContext;

    public SaveTongueTwisterTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }
        Storage storage = new Storage(this.mContext);
        String text = params[0];

        long id = storage.saveTT(text);
        Log.i((new Config()).TAG, "id = " + id);
        return null;
    }

    @Override
    public void onPostExecute(Void arg) {
        this.mContext = null;
    }
}
