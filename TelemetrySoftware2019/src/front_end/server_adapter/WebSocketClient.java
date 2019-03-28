package front_end.server_adapter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class WebSocketClient {

    protected WebSocketContainer container;
    protected Session userSession = null;

    public WebSocketClient() {
        container = ContainerProvider.getWebSocketContainer();
    }

    public void connect(String sServer) {
      try {
          userSession = container.connectToServer(this, new URI(sServer));
        } catch (DeploymentException | URISyntaxException | IOException e) {}
    }

    public void sendMessage(String sMsg) throws IOException {
    	if(userSession.isOpen()){
    		userSession.getBasicRemote().sendText(sMsg);
    	}
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to server");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        userSession.close();
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        //Ricevo il numero di utenti connessi
    }

    public void disconnect() throws IOException {
        userSession.close();
    }
}
