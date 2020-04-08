package edu.cmu.andrew.sguglani;

// MyWhiteboard.java

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/whiteboardendpoint", encoders = {FigureEncoder.class}, decoders = {FigureDecoder.class})
public class MyWhiteboard {

    // create a synchronized (thread safe) set of sessions in a hashset
    // names this collection peers
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());


    // when the web socket is created, save the associated session
    @OnOpen
    public void onOpen (Session peer) {
        peers.add(peer);
        System.out.println("Added a peer");
    }

    // when a message arrives over the socket, call the peers
    @OnMessage
    public void broadcastFigure(Figure figure, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + figure);

        // for each peer in the peers set of sessions
        for (Session peer : peers) {
            System.out.println("In broadcast loop");
            if (!peer.equals(session)) {
                // this is not being sent back to the caller
                System.out.println("Sending object to peer");
                // getBasicRemote returns a reference to a RemoteEndpoint object representing the peer of
                // this conversation that is able to send messages synchronously to the peer.
                // Here, the encoder is called on figure
                peer.getBasicRemote().sendObject(figure);
            }
        }
    }
    @OnClose
    public void onClose (Session peer) {
        System.out.println("Closing with @OnClose()");
        peers.remove(peer);
    }
}
