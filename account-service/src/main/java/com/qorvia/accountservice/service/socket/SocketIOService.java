//package com.qorvia.accountservice.service.socket;
//
//import com.corundumstudio.socketio.SocketIOServer;
//import jakarta.annotation.PreDestroy;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocketIOService {
//
//    private final SocketIOServer server;
//
//    @Autowired
//    public SocketIOService(SocketIOServer server) {
//        this.server = server;
//        this.server.start();
//    }
//
//    @PreDestroy
//    public void stopServer() {
//        this.server.stop();
//    }
//}
