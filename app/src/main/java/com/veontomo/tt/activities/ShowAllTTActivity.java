package com.veontomo.tt.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.veontomo.tt.R;
import com.veontomo.tt.TTAdapter;
import com.veontomo.tt.models.TongueTwister;
import com.veontomo.tt.tasks.LoadAllTongueTwisterTask;

import java.util.ArrayList;
import java.util.List;

public class ShowAllTTActivity extends AppCompatActivity {
    /**
     * list view that contains tongue-twisters
     */
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_tt);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.mListView = (ListView) findViewById(R.id.listview);
        TTAdapter adapter = new TTAdapter(getApplicationContext(), null);
        this.mListView.setAdapter(adapter);
        LoadAllTongueTwisterTask task = new LoadAllTongueTwisterTask(adapter);
        task.execute();
    }

    @Override
    public void onPause(){
        this.mListView.setAdapter(null);
        this.mListView = null;
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_all_tt, menu);
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
