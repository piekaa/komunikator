package pl.noip.piekaa.bondaruktuiwaniuk2.messageService;

/**
 * Created by piekaa on 2017-04-08.
 */

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import pl.noip.piekaa.bondaruktuiwaniuk2.Consts;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.Message;
import pl.noip.piekaa.bondaruktuiwaniuk2.model.MessageList;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.IPiekaRestClient;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaHttpResponse;
import pl.noip.piekaa.bondaruktuiwaniuk2.networking.PiekaJsonRestClient;

import static org.junit.Assert.*;




public class PiekaRestClientTest
{

    private String host = Consts.kobaHost;

    private IPiekaRestClient restClient;

    private String getMessageWithId40Url = host + "messages/40";
    private String getMessageWithId41Url = host + "messages/41";
    private String sendMessageUrl = host + "messages";


    private String getUnreadMessagesUrl = host + "messages/unread/2";

    private String mark42AsReadUrl = host + "messages/42";


    @Before
    public void Init()
    {
        restClient = new PiekaJsonRestClient(1000);
    }






    @Test
    public void sendMessageTest()
    {
        Message message = new Message("bla bla bla", 00L, 02L );
        PiekaHttpResponse<Message> response = restClient.sendRequest(sendMessageUrl, "POST", Message.class, message );

        assertEquals(200, response.getResponseCode());
    }


    @Test
    public void getUnreadMessages()
    {
        PiekaHttpResponse<MessageList> response = restClient.sendRequest(getUnreadMessagesUrl,  MessageList.class);

        assertEquals(200,  response.getResponseCode());

        assertNotNull(response.getResponseObject());

        MessageList messageList = response.getResponseObject();


        assertNotEquals(0,  messageList.getMessages().size());

        assertNotNull(messageList.getMessages().get(0));

        System.out.println(messageList.getMessages());


    }


    @Test
    public void doesntReturnMarkedAsReadedMessageWithId42()
    {
        PiekaHttpResponse<MessageList> response = restClient.sendRequest(getUnreadMessagesUrl,  MessageList.class);

        List<Message> messages = restClient.<MessageList>sendRequest(getUnreadMessagesUrl, MessageList.class).getResponseObject().getMessages();

        assertFalse( messages.stream().anyMatch((m) -> m.getMessageId() == 42L ) );

    }







    private List<Message> getMessags(String url)
    {
        return restClient.<MessageList>sendRequest(url, MessageList.class).getResponseObject().getMessages();
    }

    private PiekaHttpResponse<Message> execute(String url)
    {
        return restClient.sendRequest( url, Message.class );
    }


}
