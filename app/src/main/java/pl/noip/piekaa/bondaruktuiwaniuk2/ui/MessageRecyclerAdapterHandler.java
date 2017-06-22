package pl.noip.piekaa.bondaruktuiwaniuk2.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.noip.piekaa.bondaruktuiwaniuk2.R;
import pl.noip.piekaa.bondaruktuiwaniuk2.Vars;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.MessageInfo;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageListResponseHandler;

/**
 * Created by piekaa on 2017-04-26.
 */

public class MessageRecyclerAdapterHandler extends  RecyclerView.Adapter<RecyclerView.ViewHolder> implements IMessagesView, IMessageListResponseHandler
{


    private static int TO_ME = 1;
    private static int FROM_ME_NOT_SENT = 2;
    private static int FROM_ME_SENT = 3;

    private RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
    }

    private static List<MessageInfo> messages = new ArrayList<>();
    private static Map<Message, MessageInfo> messagesMap = new HashMap<>();


    private Set<Long> messagesTimestamps = new HashSet<>();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        System.out.println("onCreateViewHolder viewType = " + viewType);


        Context context = parent.getContext();
        int singleMessageLayoutId = 0;
        if( viewType == FROM_ME_SENT )
           singleMessageLayoutId = R.layout.from_me_sent_message_item;
        if( viewType == FROM_ME_NOT_SENT )
            singleMessageLayoutId = R.layout.from_me_not_sent_message_item;
        if( viewType == TO_ME )
            singleMessageLayoutId = R.layout.to_me_message_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediatley = false;

        View view = inflater.inflate(singleMessageLayoutId, parent, shouldAttachToParentImmediatley);
        RecyclerView.ViewHolder viewHolder = null;


        if( viewType == FROM_ME_SENT )
            viewHolder = new FromMeSentMessageViewHolder(view);
        if( viewType == FROM_ME_NOT_SENT )
            viewHolder = new FromMeNotSentMessageViewHolder(view);
        if( viewType == TO_ME )
            viewHolder = new ToMeMessageViewHolder(view);


    //    System.out.println("OnCreateViewHolder");

        return viewHolder;
    }




    @Override
    public int getItemViewType(int position)
    {
        if( messages.get(position).isSent()) return FROM_ME_SENT;
        if( messages.get(position).isToMe() == false &&messages.get(position).isSent() == false ) return FROM_ME_NOT_SENT;
        return TO_ME;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        MyViewHolder mvh = (MyViewHolder) holder;
        mvh.bind(messages.get(position));
    }

    @Override
    public int getItemCount()
    {
        return messages.size();
    }

    @Override
    public void addToMeMessage(Message message)
    {
        if( messagesTimestamps.contains(message.getTimestamp() ))
            return;

        messagesTimestamps.add(message.getTimestamp());

        MessageInfo messageInfo = new MessageInfo(message, true, false, messages.size());
        messages.add(messageInfo);
        messagesMap.put(message, messageInfo);
        this.notifyItemInserted(messages.size());
        scrollToBottom();


        checkIfItsOldets(message);
    }

    @Override
    public void addFromMeMessageSending(Message message)
    {
        MessageInfo messageInfo =  new MessageInfo(message, false, false, messages.size());
        messages.add( messageInfo );
        messagesMap.put(message, messageInfo );
        this.notifyItemInserted(messages.size());
        scrollToBottom();

        checkIfItsOldets(message);

    }

    @Override
    public void setFromMeMessageAsSent(Message message)
    {
        MessageInfo messageInfo = messagesMap.get(message);
        messageInfo.setSent(true);
        this.notifyItemChanged(messageInfo.getPosition());
        scrollToBottom();


        checkIfItsOldets(message);
    }

    @Override
    public void addOldToMeMessage(Message message)
    {
        if( messagesTimestamps.contains(message.getTimestamp() ))
            return;

        messagesTimestamps.add(message.getTimestamp());

        MessageInfo messageInfo = new MessageInfo(message, true, false, -1);
        messages.add(0,messageInfo);
        for(MessageInfo m  : messages )
        {
            m.setPosition(m.getPosition()+1);
        }
        messagesMap.put(message, messageInfo);
        this.notifyItemInserted(0);

        scrollToTop();

        checkIfItsOldets(message);

    }

    @Override
    public void addOldFromMe(Message message)
    {
        if( messagesTimestamps.contains(message.getTimestamp() ))
            return;

        messagesTimestamps.add(message.getTimestamp());

        MessageInfo messageInfo = new MessageInfo(message, false, true, -1);
        messages.add(0,messageInfo);

        for( MessageInfo m : messages )
        {
            m.setPosition(m.getPosition()+1);
        }

        messagesMap.put(message, messageInfo);
        this.notifyItemInserted(0);

        scrollToTop();

        checkIfItsOldets(message);
    }


    private void checkIfItsOldets(Message message)
    {
        if( message.getTimestamp() < Vars.oldestTimestamp )
        {
            Vars.oldestTimestamp = message.getTimestamp();
        }
    }


    private void scrollToBottom()
    {
        recyclerView.smoothScrollToPosition( messages.size() );
    }

    private void scrollToTop()
    {
        recyclerView.smoothScrollToPosition( 0 );
    }

    @Override
    public void handle(List<Message> messages)
    {
        Collections.reverse(messages);
        System.out.println("Handling old messages: " + messages.size());
        for (Message message : messages)
        {
            if( message.getReciverId() == Vars.myId )
            {
                addOldFromMe(message);
            }
            else
            {
                addOldToMeMessage(message);
            }
        }
    }


    class FromMeSentMessageViewHolder extends RecyclerView.ViewHolder implements MyViewHolder
    {
        TextView timeAndDaateTextView;
        TextView messageTextView;
        public FromMeSentMessageViewHolder(View itemView)
        {
            super(itemView);
            timeAndDaateTextView = (TextView) itemView.findViewById(R.id.tv_rv_tms_date_and_time);
            messageTextView = (TextView) itemView.findViewById(R.id.tv_rv_tms_message_content);

        }
        public void bind(MessageInfo messageInfo)
        {
            messageTextView.setText(messageInfo.getMessage().getTextContent());
            timeAndDaateTextView.setText(new SimpleDateFormat("dd/MM HH:mm:ss").format(new Date(messageInfo.getMessage().getTimestamp())));
        }
    }

    class FromMeNotSentMessageViewHolder extends RecyclerView.ViewHolder implements MyViewHolder
    {
        TextView timeAndDaateTextView;
        TextView messageTextView;
        public FromMeNotSentMessageViewHolder(View itemView)
        {
            super(itemView);
            timeAndDaateTextView = (TextView) itemView.findViewById(R.id.tv_rv_tmns_date_and_time);
            messageTextView = (TextView) itemView.findViewById(R.id.tv_rv_tmns_message_content);

        }
        public void bind(MessageInfo messageInfo)
        {
            messageTextView.setText(messageInfo.getMessage().getTextContent());
            timeAndDaateTextView.setText(new SimpleDateFormat("dd/MM HH:mm:ss").format(new Date(messageInfo.getMessage().getTimestamp())));
        }
    }

    class ToMeMessageViewHolder extends RecyclerView.ViewHolder implements MyViewHolder
    {
        TextView timeAndDaateTextView;
        TextView messageTextView;
        public ToMeMessageViewHolder(View itemView)
        {
            super(itemView);
            timeAndDaateTextView = (TextView) itemView.findViewById(R.id.tv_rv_fm_date_and_time);
            messageTextView = (TextView) itemView.findViewById(R.id.tv_rv_fm_message_content);

        }
        public void bind(MessageInfo messageInfo)
        {
            messageTextView.setText(messageInfo.getMessage().getTextContent());
            timeAndDaateTextView.setText(new SimpleDateFormat("dd/MM HH:mm:ss").format(new Date(messageInfo.getMessage().getTimestamp())));
        }
    }


    interface MyViewHolder
    {
        void bind(MessageInfo messageInfo);
    }


}
