package pl.noip.piekaa.bondaruktuiwaniuk2.model;

import java.io.Serializable;

/**
 * Created by piekaa on 2017-06-22.
 */

public class Settings implements Serializable
{
    private Long SenderId;
    private Long ReciverId;
    private String key;

    public Long getSenderId()
    {
        return SenderId;
    }

    public void setSenderId(Long senderId)
    {
        SenderId = senderId;
    }

    public Long getReciverId()
    {
        return ReciverId;
    }

    public void setReciverId(Long reciverId)
    {
        ReciverId = reciverId;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
