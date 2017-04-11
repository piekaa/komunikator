package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;


public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Core.Initialize();

        setContentView(R.layout.activity_messages);
    }
}
