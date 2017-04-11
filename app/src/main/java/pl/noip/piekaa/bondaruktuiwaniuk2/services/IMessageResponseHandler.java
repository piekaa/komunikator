package pl.noip.piekaa.bondaruktuiwaniuk2.services;

import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IMessageResponseHandler
{
    void handleMassage(Message message);
}
