package com.veontomo.tt.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.veontomo.tt.Config;
import com.veontomo.tt.R;

public class ShowSingleTTActivity extends AppCompatActivity {

    /**
     * tongue-twister id
     */
    private int mId;

    /**
     * tongue-twister text
     */
    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_tt);
        Bundle b = getIntent().getExtras();
        if (b != null){
            Config config = new Config();
            this.mId = b.getInt(config.TT_ID_KEY, -1);
            this.mText = b.getString(config.TT_TEXT_KEY);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (this.mId != -1){
            LinearLayout ll = (LinearLayout) findViewById(R.id.tt_layout);
            TextView tv = (TextView) ll.findViewById(R.id.tt_text);
            tv.setText(this.mText);
        }
    }

    @Override
    public void onPause(){
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
