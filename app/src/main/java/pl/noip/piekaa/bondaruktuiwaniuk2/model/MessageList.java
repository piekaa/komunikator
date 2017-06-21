package pl.noip.piekaa.bondaruktuiwaniuk2.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by piekaa on 2017-04-10.
 */

public class MessageList
{
    private List<Message> messages;

    public MessageList(List<Message> messages)
    {
        this.messages = messages;
    }

    public List<Message> getMessages()
    {
        return messages;
    }


    {
        messages = new LinkedList<>();
    }

    @Override
    public String toString()
    {
        return "MessageList{" +
                "messages=" + messages +
                '}';
    }
}
