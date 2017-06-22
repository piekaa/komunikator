package pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.impl;

import android.util.Base64;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.Vars;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageNotFoundException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.exceptions.MessageSendErrorException;
import pl.noip.piekaa.bondaruktuiwaniuk2.services.messages.networking.IMessageService;

/**
 * Created by piekaa on 2017-06-22.
 */

public class MessageServiceBase64AndCryptographicDecorator implements IMessageService
{

    private IMessageService messageService;

    public MessageServiceBase64AndCryptographicDecorator(IMessageService messageService)
    {
        this.messageService = messageService;
    }

    @Override
    public Message getMessageById(Long id) throws MessageNotFoundException
    {
        Message message = messageService.getMessageById(id);
        message = decodeMessage(message);
        return message;
    }

    @Override
    public void sendMessage(Message message) throws MessageSendErrorException
    {
        message = encodeMessage(message);
        messageService.sendMessage(message);
    }

    @Override
    public void markAsRead(Message message) throws MessageException
    {
        messageService.markAsRead(message);
    }

    @Override
    public List<Message> getOldMessages(Long olderThan, int howMany, Long id1, Long id2) throws MessageNotFoundException
    {
        List<Message> messages = messageService.getOldMessages(olderThan,howMany,id1,id2);
        List<Message> decodecList = new LinkedList<>();
        for(Message message : messages)
        {
            decodecList.add(decodeMessage(message));
        }
        return decodecList;
    }

    @Override
    public List<Message> getUnreadMessages(Long myId) throws MessageNotFoundException
    {
        List<Message> messages = messageService.getUnreadMessages(myId);
        List<Message> decodecList = new LinkedList<>();
        for(Message message : messages)
        {
            decodecList.add(decodeMessage(message));
        }
        return decodecList;
    }



    private Message encodeMessage(Message message)
    {
        String messageTextContent = message.getTextContent();

        byte[] messageBytes = messageTextContent.getBytes(Charset.forName("UTF-8"));

        xor(messageBytes);

        return message.createCopy( Base64.encodeToString(messageBytes, Base64.DEFAULT));
    }

    private void xor(byte[] message)
    {
        if(Vars.key == null || Vars.key.length() == 0 )
            return;

        byte[] keyBytes = Vars.key.getBytes();
        int keyIndex = 0;

        for(int i = 0 ; i < message.length ; i++)
        {
            message[i] = (byte) (message[i] ^ keyBytes[keyIndex++]);
            keyIndex %= keyBytes.length;
        }
    }



    private Message decodeMessage(Message message)
    {
        try
        {
            byte messageBytes[] = Base64.decode(message.getTextContent(), Base64.DEFAULT);
            xor(messageBytes);
            return message.createCopy(new String(messageBytes, Charset.forName("UTF-8")));
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        return message;
    }



}
