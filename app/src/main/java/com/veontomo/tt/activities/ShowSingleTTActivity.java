package com.veontomo.tt.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareButton;
import com.veontomo.tt.Config;
import com.veontomo.tt.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ShowSingleTTActivity extends AppCompatActivity {

    /**
     * token name with which {@link #mText mText} is to be saved in the Bundle
     */
    private final String textToken = "text";
    /**
     * token name with which id of the tongue-twister is to be saved in the Bundle
     */
    private final String idToken = "id";

    /**
     * tongue-twister id
     */
    private int mId;
    /**
     * tongue-twister text
     */
    private String mText;
    private ImageButton mRecord, mStop, mPlay;
    private MediaRecorder mAudioRecorder;
    private String mOutputFile;
    /**
     * Name of the directory in which tongue-twister audio records are to be stored
     */
    private String mDirName;
    /**
     * Text view with tongue-twister text.
     */
    private TextView mTTText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_tt);
        Log.i(Config.TAG, "on Create: savedInstanceState is null?" + (savedInstanceState == null));
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.mId = b.getInt(Config.TT_ID_KEY, -1);
            this.mText = b.getString(Config.TT_TEXT_KEY);
        }
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        this.mText = savedInstanceState.getString(textToken);
        this.mId = savedInstanceState.getInt(idToken);
        Log.i(Config.TAG, "on restore " + this.mText);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(Config.TAG, "on Resume");
        if (this.mId == -1) {
            return;
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
        this.mTTText = (TextView) ll.findViewById(R.id.tt_text);
        fillInTTText(this.mText);

        initializeShareButton();

        this.mTTText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTTActivity.class);
                intent.putExtra(Config.TT_ID_KEY, mId);
                intent.putExtra(Config.TT_TEXT_KEY, mText);
                startActivityForResult(intent, Config.TT_UPDATE_REQUEST);
            }
        });

        this.mDirName = createDir(Config.DIR_NAME + "/" + String.valueOf(this.mId));
        if (this.mDirName == null) {
            return;
        }

        this.mPlay = (ImageButton) findViewById(R.id.imgPlay);
        mPlay.setEnabled(false);
        this.mStop = (ImageButton) findViewById(R.id.imgStop);
        mStop.setEnabled(false);
        this.mRecord = (ImageButton) findViewById(R.id.imgRecord);

        mAudioRecorder = new MediaRecorder();
        mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorder = new MediaRecorder();
                mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                mOutputFile = mDirName + "/" + (new Date()).getTime() + ".3gp";
                mAudioRecorder.setOutputFile(mOutputFile);

                try {
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    mRecord.setEnabled(false);
                    mStop.setEnabled(true);
                    mPlay.setEnabled(false);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorder.stop();
                mAudioRecorder.release();
                mAudioRecorder = null;

                mStop.setEnabled(false);
                mPlay.setEnabled(true);
                mRecord.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
            }
        });

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException {
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(mOutputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mStop.setEnabled(false);
                mPlay.setEnabled(false);
                mRecord.setEnabled(false);
                m.start();
                mPlay.setEnabled(true);
                mRecord.setEnabled(true);
            }
        });
    }

    /**
     * Initializes the button to share the info on facebook
     */
    private void initializeShareButton() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(getResources().getString(R.string.fb_post))
                .setContentUrl(Uri.parse(Config.GOOGLE_PLAY_STORE))
                .setContentDescription(this.mText)
                .build();
        ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
    }

    private void initializeShareButton2() {
        // Create an object
        ShareOpenGraphObject object = new ShareOpenGraphObject.Builder()
                .putString("og:type", "article")
                .putString("og:title", "Tongue-twister: " + this.mText)
                .putString("og:url", Config.GOOGLE_PLAY_STORE)
                .build();
        // Create an action
        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .putObject("article", object)
                .build();

        ShareOpenGraphContent content = new ShareOpenGraphContent.Builder()
                .setPreviewPropertyName("article")
                .setAction(action)
                .build();

        ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);
    }

    /**
     * Creates (if it does not exist) a directory with given name in the external storage and
     * returns absolute path to it.
     *
     * @param dir
     * @return absolute path to the directory
     */
    private String createDir(String dir) {
        File f = new File(Environment.getExternalStorageDirectory(),
                dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

    @Override
    public void onPause() {
        Log.i(Config.TAG, "on Pause " + this.mText);
        mRecord.setOnClickListener(null);
        mStop.setOnClickListener(null);
        mPlay.setOnClickListener(null);
        this.mAudioRecorder = null;
        this.mPlay = null;
        this.mStop = null;
        this.mRecord = null;
        this.mTTText.setOnClickListener(null);
        this.mTTText = null;
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(textToken, this.mText);
        savedInstanceState.putInt(idToken, this.mId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy(){
        this.mText = null;
        this.mId = -1;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_single_tt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.TT_UPDATE_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                String text = data.getStringExtra(Config.TT_TEXT_KEY);
                if (text != null) {
                    this.mText = text;
                    fillInTTText(text);
                }
            }
        }
    }

    /**
     * Fill in the text view with tongue-twister.
     *
     * @param text
     */
    private void fillInTTText(final String text) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
        TextView tv = (TextView) ll.findViewById(R.id.tt_text);
        tv.setText(text);
    }

}
