package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages;

/**
 * Created by piekaa on 2017-04-08.
 */

public interface IMessageUrlProvider
{
    String getMessageByIdUrl(Long id) ;
    String getSendMessageUrl();
    String getUnreadMessagesByReciverIDUrl(Long reciverId);
    String getMarkMessageAsReadUrl(Long id);
}
