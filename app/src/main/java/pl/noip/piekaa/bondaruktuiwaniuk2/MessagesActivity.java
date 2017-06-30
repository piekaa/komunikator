package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import pl.noip.piekaa.bondaruktuiwaniuk2.androidServices.ScheduledService;
import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.files.settings.SettingsUtils;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.notifications.MessageNotification;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.MessageRecyclerAdapterHandler;


public class MessagesActivity extends AppCompatActivity {


    RecyclerView messageRecyclerView;

    EditText messageContentEditText;

    IClientSendingMessageService messageSender;

    ProgressBar progressBar;

    IMessageCreator messageCreator;
    MessageRecyclerAdapterHandler messageRecyclerAdapter;
    IAsyncMessageService asyncMessageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        SettingsUtils.loadSettings(this);

        MessageNotification.setContext(this);

        Core core = Core.getInstance();

        core.startQueueService(this);

        setContentView(R.layout.activity_messages);

        asyncMessageService = core.getAsyncMessageService();
        messageRecyclerView = (RecyclerView) findViewById(R.id.rv_messages);
        messageContentEditText = (EditText) findViewById(R.id.et_messageContent);
        progressBar = (ProgressBar) findViewById(R.id.pb_old_messages);

        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerAdapter = new MessageRecyclerAdapterHandler();
        messageRecyclerAdapter.setRecyclerView(messageRecyclerView);
        messageRecyclerView.setAdapter(messageRecyclerAdapter);





        messageSender = core.getClientSendingMessageService();
        messageSender.setMessagesView(messageRecyclerAdapter);
        messageCreator = core.getMessageCreator();


        core.getNetworkMessageProvider().setMessagesView(messageRecyclerAdapter);




    }

    @Override
    public void onResume()
    {
        super.onResume();
        messageRecyclerAdapter.scrollToBottom();
    }


    public void sendMessage(View view)
    {
        Message newMessage = messageCreator.createMessage(messageContentEditText.getText().toString());
        messageSender.sendMessage(newMessage);
        messageContentEditText.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int selectedItemId = item.getItemId();


        if( selectedItemId == R.id.action_load_more )
        {
            showProgressBar();

            asyncMessageService.tryToGetMoreMessages(Vars.oldestTimestamp, Consts.howManyOldMessages, Vars.myId, Vars.reciverId,
                    messages ->
                    {
                        messageRecyclerAdapter.handle(messages);
                        hideProgressBar();
                    }
                    ,
                    ()->{
                        hideProgressBar();
                        Toast.makeText(this, "Nie udało się załadować starszych wiadomości", Toast.LENGTH_LONG ).show();
                    }
                    );
        }


        if( selectedItemId == R.id.action_settings )
        {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }


    private void showProgressBar()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar()
    {
        progressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Vars.isActive = true;

        MessageNotification.turnOff();

        while( Vars.activityQueue.size() > 0 )
        {
            Activity activity = Vars.activityQueue.poll();
            if( activity != null && activity != this)
                activity.finish();
        }
        Vars.activityQueue.add(this);

    }

    @Override
    protected void onStop()
    {
        Vars.isActive = false;

        super.onStop();

        MessageNotification.turnOn();

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        stopService(new Intent(this, ScheduledService.class));
    }
}
