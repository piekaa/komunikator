package pl.noip.piekaa.bondaruktuiwaniuk2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import pl.noip.piekaa.bondaruktuiwaniuk2.core.Core;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IClientSendingMessageService;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageCreator;
import pl.noip.piekaa.bondaruktuiwaniuk2.ui.MessageRecyclerAdapter;


public class MessagesActivity extends AppCompatActivity {


    RecyclerView messageRecyclerView;

    EditText messageContentEditText;

    IClientSendingMessageService messageSender;

    IMessageCreator messageCreator;
    MessageRecyclerAdapter messageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Core core = Core.getInstance();

        core.startQueueService(this);

        setContentView(R.layout.activity_messages);

        messageRecyclerView = (RecyclerView) findViewById(R.id.rv_messages);
        messageContentEditText = (EditText) findViewById(R.id.et_messageContent);


        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        if( messageRecyclerAdapter == null )
            messageRecyclerAdapter = new MessageRecyclerAdapter(0);
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
}
