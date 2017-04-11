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


    public Message(  String textContent, Long senderId, Long reciverId)
    {
        markedAsRead = false;
        this.textContent = textContent;
        this.senderId = senderId;
        this.reciverId = reciverId;
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
                ", read=" + markedAsRead +
                '}';
    }
}