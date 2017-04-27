package pl.noip.piekaa.bondaruktuiwaniuk2.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.noip.piekaa.bondaruktuiwaniuk2.R;

/**
 * Created by piekaa on 2017-04-26.
 */

public class MessageRecyclerAdapter extends  RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>
{


    int itemCount;

    public MessageRecyclerAdapter(int itemCount)
    {
        this.itemCount = itemCount;
    }


    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        Context context = parent.getContext();
        int singleMessageLayoutId = R.layout.message_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttachToParentImmediatley = false;

        View view = inflater.inflate(singleMessageLayoutId, parent, shouldAttachToParentImmediatley);
        MessageViewHolder viewHolder = new MessageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return itemCount;
    }

    class MessageViewHolder extends RecyclerView.ViewHolder
    {

        TextView timeAndDaateTextView;
        TextView messageTextView;

        public MessageViewHolder(View itemView)
        {
            super(itemView);
            timeAndDaateTextView = (TextView) itemView.findViewById(R.id.tv_rv_date_and_time);
            messageTextView = (TextView) itemView.findViewById(R.id.tv_rv_message_content);

        }
    }

}
