package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.impl;

import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.IMessageUrlProvider;

/**
 * Created by piekaa on 2017-04-08.
 */

public class MessageUrlProvider implements IMessageUrlProvider
{

    private String host;

    public MessageUrlProvider(String host) {
        this.host = host;
    }

    @Override
    public String getMessageByIdUrl(Long id) {
        StringBuilder builder = new StringBuilder();

        builder.append(host);
        builder.append("messages/");
        builder.append(id);

        return builder.toString();
    }

    @Override
    public String getSendMessageUrl()
    {
        return host + "messages";
    }


    @Override
    public String getMarkMessageAsReadUrl(Long id)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(host);
        builder.append("messages/");
        builder.append(id);

        return builder.toString();
    }

    @Override
    public String getMoreMessagesUrl(Long timestamp, int howMany, Long id1, Long id2)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(host);
        builder
                .append("messages/")
                .append("old/")
                .append(timestamp).append("/")
                .append(howMany).append("/")
                .append(id1).append("/")
                .append(id2);

        return builder.toString();
    }


    @Override
    public String getUnreadMessagesUrl(Long myId)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(host);
        builder
                .append("messages/")
                .append("unread/")
                .append(myId);

        return builder.toString();
    }


}
