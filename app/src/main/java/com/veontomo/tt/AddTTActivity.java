package com.veontomo.tt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTTActivity extends AppCompatActivity {

    private final Config config = new Config();

    /**
     * token name with which {@link #mText mText} is to be saved in the Bundle
     */
    private final String textToken = "text";

    /**
     * token name with which {@link #mPos mPos} is to be saved in the Bundle
     */
    private final String positionToken = "position";

    /**
     * Content of the edit text field.
     *
     * This variable receives string content of {@link #mText mText} when the activity
     * gets paused.
     */
    private String mText;

    private int mPos;
    private EditText mEditText;
    /**
     * Button to save the inserted text
     */
    private Button mBtnSave;
    /**
     * Button to finish the current activity
     */
    private Button mBtnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tt);
        Log.i(config.TAG, "on create");
    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(config.TAG, "on restore");
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        this.mText = savedInstanceState.getString(textToken);
        this.mPos = savedInstanceState.getInt(positionToken);
    }

    public void onResume() {
        super.onResume();
        Log.i(config.TAG, "on resume");
        this.mEditText = (EditText) findViewById(R.id.add_tt_edit);
        // restoring string content of the edit text
        if (this.mText != null) {
            this.mEditText.setText(this.mText);
            this.mText = null;
        }
        // placing the cursor at the correct position
        if (this.mPos > 0){
            this.mEditText.setSelection(this.mPos);
        }
        this.mBtnCancel = (Button) findViewById(R.id.btnCancel);
        this.mBtnSave = (Button) findViewById(R.id.btnSave);
        this.mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(config.TAG, "click save");
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(config.TAG, "click cancel");
            }
        });
    }

    public void onPause() {
        Log.i(config.TAG, "on pause");
        this.mBtnCancel.setOnClickListener(null);
        this.mBtnSave.setOnClickListener(null);
        this.mText = this.mEditText.getEditableText().toString();
        this.mPos = this.mEditText.getSelectionStart();
        this.mEditText = null;
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(config.TAG, "on save instance");
        savedInstanceState.putString(textToken, this.mText);
        savedInstanceState.putInt(positionToken, this.mPos);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onStop() {
        Log.i(config.TAG, "on stop");
        this.mText = null;
        super.onStop();
    }

    public void onDestroy() {
        Log.i(config.TAG, "on destroy. Do not add anything here, because it should be deleted.");
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_tt, menu);
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
