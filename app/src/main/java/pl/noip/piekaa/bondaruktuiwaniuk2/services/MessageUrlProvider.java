package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import java.util.IllegalFormatCodePointException;

/**
 * Created by piekaa on 2017-04-08.
 */

public class MessageUrlProvider implements  IMessageUrlProvider
{

    private String host;

    public MessageUrlProvider(String host) {
        this.host = host;
    }

    @Override
    public String getMessageUrlById( Long id) {
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
}
