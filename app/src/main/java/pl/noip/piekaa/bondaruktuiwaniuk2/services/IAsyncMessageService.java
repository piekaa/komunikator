package pl.noip.piekaa.bondaruktuiwaniuk2.services;

/**
 * Created by piekaa on 2017-04-09.
 */

public interface IAsyncMessageService
{
    void tryToGetMessage(Long id, IOnGetMessageSucceed succeedHandler, IOnGetMessageFailed failedHandler);
}
