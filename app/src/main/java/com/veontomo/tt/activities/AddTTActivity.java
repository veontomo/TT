package com.veontomo.tt.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.veontomo.tt.Config;
import com.veontomo.tt.R;
import com.veontomo.tt.tasks.SaveTongueTwisterTask;

public class AddTTActivity extends AppCompatActivity {

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
     * <p/>
     * This variable receives string content of {@link #mEditText mEditText} when the activity
     * gets paused.
     */
    private String mText;

    /**
     * Cursor position in the edit text field.
     * <p/>
     * The value gets updated when the activity gets paused.
     */
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
    /**
     * id of the tongue twister in case it has already been saved into db, or -1 otherwise
     */
    private int mId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tt);
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
        this.mPos = savedInstanceState.getInt(positionToken);
    }

    public void onResume() {
        super.onResume();
        this.mEditText = (EditText) findViewById(R.id.add_tt_edit);
        // restoring string content of the edit text
        if (this.mText != null) {
            this.mEditText.setText(this.mText);
            this.mText = null;
        }
        // placing the cursor at the correct position
        if (this.mPos > 0) {
            this.mEditText.setSelection(this.mPos);
        }
        this.mBtnCancel = (Button) findViewById(R.id.btnCancel);
        this.mBtnSave = (Button) findViewById(R.id.btnSave);
        if (this.mId != -1){
            this.mBtnSave.setText(getResources().getString(R.string.update));
            setTitle(getResources().getString(R.string.editTT));
        }
        this.mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditText.getEditableText().toString();
                if (input != null && !input.isEmpty()) {
                    SaveTongueTwisterTask task = new SaveTongueTwisterTask(getApplicationContext(), mId, input);
                    task.execute();
                    Intent intent = new Intent();
                    intent.putExtra(Config.TT_TEXT_KEY, input);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(Config.TAG, "click cancel");
                finish();
            }
        });
    }

    public void onPause() {
        this.mBtnCancel.setOnClickListener(null);
        this.mBtnSave.setOnClickListener(null);
        this.mText = this.mEditText.getEditableText().toString();
        this.mPos = this.mEditText.getSelectionStart();
        this.mEditText = null;
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(textToken, this.mText);
        savedInstanceState.putInt(positionToken, this.mPos);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onStop() {
        this.mText = null;
        super.onStop();
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
        if (id == R.id.settings) {
            return true;
        }
        if (id == R.id.show_all) {
            Intent intent = new Intent(getApplicationContext(), ShowAllTTActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
