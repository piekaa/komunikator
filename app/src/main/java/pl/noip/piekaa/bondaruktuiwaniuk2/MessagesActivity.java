package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.MessageRecyclerAdapter;


public class MessagesActivity extends AppCompatActivity {


    RecyclerView messageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Core.Initialize();
        setContentView(R.layout.activity_messages);

        messageRecyclerView = (RecyclerView) findViewById(R.id.rv_messages);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        MessageRecyclerAdapter adapter = new MessageRecyclerAdapter(100);
        messageRecyclerView.setAdapter(adapter);



    }
}
