package pl.noip.piekaa.bondaruktuiwaniuk2.model;

/**
 * Created by piekaa on 2017-04-08.
 */

public class Message
{
    private Long messageId;
    private String textContent;
    private Long senderId;
    private Long reciverId;
    private Boolean markedAsRead;
    private Long timestamp;


    public Long getTimestamp()
    {
        return timestamp;
    }

    public Boolean getMarkedAsRead() {
        return markedAsRead;
    }

    public void setMarkedAsRead(Boolean markedAsRead) {
        this.markedAsRead = markedAsRead;
    }

    public Long getSenderId()
    {
        return senderId;
    }

    public Long getReciverId()
    {
        return reciverId;
    }

    public Long getMessageId()
    {
        return messageId;
    }

    public String getTextContent()
    {
        return textContent;
    }


    public Message(  String textContent, Long senderId, Long reciverId, Long timestamp)
    {
        markedAsRead = false;
        this.textContent = textContent;
        this.senderId = senderId;
        this.reciverId = reciverId;
        this.timestamp = timestamp;
    }

    public Message(  String textContent, Long senderId, Long reciverId)
    {
        markedAsRead = false;
        this.textContent = textContent;
        this.senderId = senderId;
        this.reciverId = reciverId;
        this.timestamp = System.currentTimeMillis();
    }

    public Message()
    {
        super();
    }


    @Override
    public String toString()
    {
        return "Message{" +
                "messageId=" + messageId +
                ", textContent='" + textContent + '\'' +
                ", senderId=" + senderId +
                ", reciverId=" + reciverId +
                ", markedAsRead=" + markedAsRead +
                ", timestamp=" + timestamp +
                '}';
    }


    public Message createCopy(String newTextContent)
    {
        Message m = new Message();
        m.timestamp = timestamp;
        m.messageId = messageId;
        m.senderId = senderId;
        m.reciverId = reciverId;
        m.markedAsRead = markedAsRead;
        m.textContent = newTextContent;
        return m;
    }

}