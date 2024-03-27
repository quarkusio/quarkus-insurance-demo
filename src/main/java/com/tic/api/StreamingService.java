package com.tic.api;

import java.io.IOException;

import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import org.eclipse.microprofile.context.ManagedExecutor;

/**
 * Streaming service. This allows streaming of data via web sockets
 * @author Phillip Kruger(phillip.kruger@gmail.com)
 */
@ServerEndpoint("/ws")
public class StreamingService {

    @Inject
    ManagedExecutor managedExecutor;
    
    @OnOpen
    public void onOpen(Session session) {
        
        managedExecutor.execute(() -> {
            try {
                session.getBasicRemote().sendText("Connection opened");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnClose
    void onClose(Session session) {
        try {
            // TODO:
        }catch (Throwable t){}
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        managedExecutor.execute(() -> {
            // For now we just reply like a parrot.
            // Here you would typically call to some backend and reply with some response
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
