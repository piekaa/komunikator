package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import pl.noip.piekaa.bondaruktuiwaniuk2.services.files.settings.SettingsUtils;

public class SettingsActivity extends AppCompatActivity
{

    EditText senderIdEditText;
    EditText reciverIdEditText;
    EditText keyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        senderIdEditText = (EditText) findViewById(R.id.et_sender_id);
        reciverIdEditText = (EditText) findViewById(R.id.et_reciver_id);
        keyEditText = (EditText) findViewById( R.id.et_key );

        senderIdEditText.setText(Vars.myId+"");
        reciverIdEditText.setText(Vars.reciverId+"");
        keyEditText.setText(Vars.key);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Vars.myId = Long.valueOf( senderIdEditText.getText().toString() );
        Vars.reciverId = Long.valueOf( reciverIdEditText.getText().toString() );
        Vars.key = keyEditText.getText().toString();
        SettingsUtils.saveSettings(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
    }
}
