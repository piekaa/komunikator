package pl.noip.piekaa.bondaruktuiwaniuk2.model;

/**
 * Created by piekaa on 2017-05-12.
 */

public class MessageInfo
{
    private Message message;

    private boolean toMe;
    private boolean sent;
    private int position;

    public int getPosition()
    {
        return position;
    }

    public MessageInfo(Message message, boolean toMe, boolean sent, int position)
    {
        this.message = message;
        this.toMe = toMe;
        this.sent = sent;
        this.position = position;
    }

    public void setSent(boolean sent)
    {
        this.sent = sent;
    }

    public Message getMessage()
    {
        return message;
    }

    public boolean isToMe()
    {
        return toMe;
    }

    public boolean isSent()
    {
        return sent;
    }
}
