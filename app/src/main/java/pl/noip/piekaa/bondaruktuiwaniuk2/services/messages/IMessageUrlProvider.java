package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

/**
 * Created by piekaa on 2017-04-08.
 */

public interface IMessageUrlProvider
{
    String getMessageByIdUrl(Long id) ;
    String getSendMessageUrl();
    String getMarkMessageAsReadUrl(Long id);
    String getMoreMessagesUrl(Long timestamp, int howMany, Long id1, Long id2);

    String getUnreadMessagesUrl(Long myId);
}
