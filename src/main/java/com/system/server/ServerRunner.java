package com.system.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Luffy
 * @description 接收数据服务器
 * @createTime 2021年05月31日 20:41:00
 */
@Component
public class ServerRunner implements CommandLineRunner {

    @Autowired
    private SocketProperties properties;

    @Override
    public void run(String... args) throws Exception {
        ServerSocket server = null;
        server = new ServerSocket(properties.getPort());
        System.out.println("server is starting , the port is :" + properties.getPort());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                properties.getPoolCore(),
                properties.getPoolMax(),
                properties.getPoolKeep(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(properties.getPoolQueueInit()),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );
        while (true) {
            Socket socket = server.accept();
            pool.execute(new ServerConfig(socket));
        }
    }
}
