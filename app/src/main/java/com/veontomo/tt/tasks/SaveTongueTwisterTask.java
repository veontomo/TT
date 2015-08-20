package com.veontomo.tt.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.veontomo.tt.Config;
import com.veontomo.tt.models.Storage;

/**
 * Saves tongue twister in a separate thread.
 */
public class SaveTongueTwisterTask extends AsyncTask<Void, Void, Void> {
    private final int mId;
    private final String mText;
    private Context mContext;

    public SaveTongueTwisterTask(Context context, int id, String text) {
        this.mContext = context;
        this.mId = id;
        this.mText = text;
        Log.i(Config.TAG, "saving proverb: id = " + id + ", text = " + text);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Storage storage = new Storage(this.mContext);
        if (mId != -1) {
            storage.updateTT(mId, mText);
        } else {
            long id = storage.saveTT(mText);
            Log.i(Config.TAG, "id = " + id);
        }
        return null;
    }

    @Override
    public void onPostExecute(Void arg) {
        this.mContext = null;
    }
}
