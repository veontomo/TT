package com.veontomo.tt.activities;

import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.veontomo.tt.Config;
import com.veontomo.tt.R;
import com.veontomo.tt.TTAdapter;
import com.veontomo.tt.tasks.LoadAllTongueTwisterTask;

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
    public void onResume() {
        super.onResume();
        this.mListView = (ListView) findViewById(R.id.listview);
        TTAdapter adapter = new TTAdapter(getApplicationContext(), null);
        this.mListView.setAdapter(adapter);
        LoadAllTongueTwisterTask task = new LoadAllTongueTwisterTask(adapter, getApplicationContext());
        task.execute();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //here you can use the position to determine what checkbox to check
                //this assumes that you have an array of your checkboxes as well. called checkbox
                TextView tv = (TextView) view.findViewById(R.id.tt_text);
                if (tv == null) {
                    Log.i((new Config()).TAG, "tv is null");
                } else {
                    Intent intent = new Intent(getApplicationContext(), ShowSingleTTActivity.class);
                    Config config = new Config();
                    intent.putExtra(config.TT_TEXT_KEY, tv.getText().toString());
                    intent.putExtra(config.TT_ID_KEY, position);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onPause() {
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
        if (id == R.id.settings) {
            return true;
        }

        if (id == R.id.add_tt) {
            Intent intent = new Intent(getApplicationContext(), AddTTActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
