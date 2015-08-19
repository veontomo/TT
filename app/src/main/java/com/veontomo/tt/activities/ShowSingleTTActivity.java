package com.veontomo.tt.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.veontomo.tt.Config;
import com.veontomo.tt.R;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ShowSingleTTActivity extends AppCompatActivity {

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
        Bundle b = getIntent().getExtras();
        if (b != null) {
            this.mId = b.getInt(Config.TT_ID_KEY, -1);
            this.mText = b.getString(Config.TT_TEXT_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mId == -1) {
            return;
        }
        LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
        this.mTTText = (TextView) ll.findViewById(R.id.tt_text);
        fillInTTText(this.mText);

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
     * @param text
     */
    private void fillInTTText(final String text){
        LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
        TextView tv = (TextView) ll.findViewById(R.id.tt_text);
        tv.setText(text);
    }

}
