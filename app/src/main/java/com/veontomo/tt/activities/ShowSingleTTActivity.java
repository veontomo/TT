package com.veontomo.tt.activities;

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

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_tt);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Config config = new Config();
            this.mId = b.getInt(config.TT_ID_KEY, -1);
            this.mText = b.getString(config.TT_TEXT_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mId != -1) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
            TextView tv = (TextView) ll.findViewById(R.id.tt_text);
            tv.setText(this.mText);
        }
        this.mPlay = (ImageButton) findViewById(R.id.imgPlay);
        this.mStop = (ImageButton) findViewById(R.id.imgStop);
        this.mRecord = (ImageButton) findViewById(R.id.imgRecord);

        mAudioRecorder = new MediaRecorder();
        mAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        mAudioRecorder.setOutputFile(mOutputFile);

        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mAudioRecorder.prepare();
                    mAudioRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    mRecord.setEnabled(false);
                    mStop.setEnabled(true);
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

                m.start();
                Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();
            }
        });
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
}
