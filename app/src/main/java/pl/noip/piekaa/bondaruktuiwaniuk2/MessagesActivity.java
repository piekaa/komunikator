package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IAsyncMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.MessageRecyclerAdapterHandler;


public class MessagesActivity extends AppCompatActivity {


    RecyclerView messageRecyclerView;

    EditText messageContentEditText;

    IClientSendingMessageService messageSender;

    IMessageCreator messageCreator;
    MessageRecyclerAdapterHandler messageRecyclerAdapter;
    IAsyncMessageService asyncMessageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Core core = Core.getInstance();

        core.startQueueService(this);

        setContentView(R.layout.activity_messages);

        asyncMessageService = core.getAsyncMessageService();
        messageRecyclerView = (RecyclerView) findViewById(R.id.rv_messages);
        messageContentEditText = (EditText) findViewById(R.id.et_messageContent);


        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerAdapter = new MessageRecyclerAdapterHandler();
        messageRecyclerAdapter.setRecyclerView(messageRecyclerView);
        messageRecyclerView.setAdapter(messageRecyclerAdapter);




        messageSender = core.getClientSendingMessageService();
        messageSender.setMessagesView(messageRecyclerAdapter);
        messageCreator = core.getMessageCreator();


        core.getNetworkMessageProvider().setMessagesView(messageRecyclerAdapter);


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
            System.out.println("TRYING TO LOAD OLDER MESSAGES");
            asyncMessageService.tryToGetMoreMessages(Vars.oldestTimestamp, Consts.howManyOldMessages, Vars.myId, Vars.reciverId, messageRecyclerAdapter,
                    ()->{
                        System.out.println("Getting older messages failed :((((");
                    }
                    );
        }

        return true;
    }
}
