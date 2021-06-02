package com.system.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.constant.RequestKey;
import com.system.entity.SCMData;
import com.system.service.SCMService;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ServerConfig extends Thread {

    private final Socket socket;

    public ServerConfig(Socket socket) {
        this.socket = socket;
    }

    private final SCMService service = SpringUtil.getBean(SCMService.class);

    private void handle(InputStream inputStream) throws IOException {

        byte[] bytes = new byte[1024 * 10];
        int len = inputStream.read(bytes);
        if (len != -1) {
            String receive = new String(bytes, 0, len, StandardCharsets.UTF_8);
            System.out.println("receive data: " + receive + " , current thread: " + Thread.currentThread().getName());
            JSONObject jsonObject;
            try {
                jsonObject = JSON.parseObject(receive);
            } catch (Exception e) {
                jsonObject = null;
            }
            if (jsonObject != null) {
                SCMData data = new SCMData();
                data.setTemperature(jsonObject.getBigDecimal(RequestKey.TEMP));
                data.setConcentration(jsonObject.getBigDecimal(RequestKey.AIR));
                data.setIntensity(jsonObject.getBigDecimal(RequestKey.ILLUMINATION));
                service.saveSCMData(data);
            }
        }

    }

    @Override
    public void run() {
        try {
            System.out.println("client - " + socket.getRemoteSocketAddress() + " -> connect success");
            InputStream inputStream = socket.getInputStream();
            while (true) {
                handle(inputStream);
            }
        } catch (IOException socketException) {
            socketException.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
