package pl.noip.piekaa.bondaruktuiwaniuk2.services.asyncTaskRelated;

import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;

/**
 * Created by piekaa on 2017-06-07.
 */

public interface IExecutableObject extends IExecutable
{
    Object execute() throws MessageException;
}
