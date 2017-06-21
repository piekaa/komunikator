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
    public void canGetMessageWithId40()
    {
        PiekaHttpResponse<Message> response = execute(getMessageWithId40Url);
        System.out.println(getMessageWithId40Url);
        assertNotEquals(-1, response.getResponseCode());
        assertNotNull(response.getResponseObject());
        assertEquals(new Long(40L), response.getResponseObject().getMessageId());

        System.out.println(response.getResponseObject());

    }

    @Test
    public void canGetMessageWithId41()
    {
        PiekaHttpResponse<Message> response = execute(getMessageWithId41Url);
        System.out.println(getMessageWithId41Url);
        assertNotEquals(-1, response.getResponseCode());
        assertNotNull(response.getResponseObject());
        assertEquals(new Long(41L), response.getResponseObject().getMessageId());

        System.out.println(response.getResponseObject());

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
    public void mark42AsReaded()
    {
        PiekaHttpResponse<Object> response = restClient.sendRequest(mark42AsReadUrl, "PUT", Object.class, new Object());

        assertEquals(200, response.getResponseCode());

    }


    @Test
    public void doesntReturnMarkedAsReadedMessageWithId42()
    {
        PiekaHttpResponse<MessageList> response = restClient.sendRequest(getUnreadMessagesUrl,  MessageList.class);

        List<Message> messages = restClient.<MessageList>sendRequest(getUnreadMessagesUrl, MessageList.class).getResponseObject().getMessages();

        assertFalse( messages.stream().anyMatch((m) -> m.getMessageId() == 42L ) );

    }


    String getLast10MessagesUrl = host + "messages/0/10/2";
    @Test
    public void getLast10Messages()
    {
        PiekaHttpResponse<MessageList> response = restClient.sendRequest(getLast10MessagesUrl, MessageList.class);

        assertEquals(200, response.getResponseCode());
        assertNotNull(response.getResponseObject());

        MessageList messageList = response.getResponseObject();

        List<Message> messages = messageList.getMessages();

        assertEquals(10, messages.size() );

        System.out.println(messages);


        messages.stream().forEach( (m) -> assertNotNull(m) );

    }

    String get6Skip0Url =  host + "messages/0/6/2";
    String get5Skip5Url =  host + "messages/5/5/2";
    @Test
    public void get5Skip5()
    {
        List<Message> get6Skip0List = getMessags(get6Skip0Url);
        List<Message> get5Skip5List = getMessags(get5Skip5Url);


        assertEquals( get6Skip0List.get(get6Skip0List.size()-1).getMessageId(), get5Skip5List.get(0).getMessageId() );

        assertEquals(5 , get5Skip5List.size() );

    }

    String skip1000000Url = host + "messages/1000000/10/2";;
    @Test
    public void skip100000ShouldBeEmpty()
    {
        List<Message> messages = getMessags(skip1000000Url);
        assertEquals(0, messages.size());
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
