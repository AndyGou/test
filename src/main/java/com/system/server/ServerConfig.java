package com.system.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.constant.RequestKey;
import com.system.entity.SCMData;
import com.system.service.SCMService;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;


public class ServerConfig extends Thread {

    private final Socket socket;

    public ServerConfig(Socket socket) {
        this.socket = socket;
    }
	// 获取spring容器管理的类，可以获取到sevrice的类
    private final SCMService service = SpringUtil.getBean(SCMService.class);

    private String handle(InputStream inputStream) throws IOException, DataFormException {
        byte[] bytes = new byte[1024 * 10];
        int len = inputStream.read(bytes);
        if (len != -1) {
            StringBuffer request = new StringBuffer();
            request.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
            System.out.println("接受的数据: " + request);
            System.out.println("from client ... " + request + "当前线程" + Thread.currentThread().getName());
            JSONObject jsonObject;
            try {
                jsonObject = JSON.parseObject(request.toString());
            } catch (Exception e) {
                throw new DataFormException("数据处理异常");
            }
            SCMData data = new SCMData();
            data.setTemperature(jsonObject.getBigDecimal(RequestKey.TEMP));
            data.setConcentration(jsonObject.getBigDecimal(RequestKey.AIR));
            data.setIntensity(jsonObject.getBigDecimal(RequestKey.ILLUMINATION));
            int i = service.saveSCMData(data);
            if (i >= 1) {
                return "ok";
            } else {
                throw new DataFormException("数据处理异常");
            }
        } else {
            throw new DataFormException("数据处理异常");
        }
    }

    @Override
    public void run() {
        BufferedWriter writer = null;
        try {
            // 设置连接超时9秒
            socket.setSoTimeout(9000);
            System.out.println("客户 - " + socket.getRemoteSocketAddress() + " -> 机连接成功");
            InputStream inputStream = socket.getInputStream();
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String result = null;
            try {
                result = handle(inputStream);
                writer.write(result);
                writer.newLine();
                writer.flush();
            } catch (IOException | DataFormException | IllegalArgumentException e) {
                writer.write("error");
                writer.newLine();
                writer.flush();
                System.out.println("发生异常");
                try {
                    System.out.println("再次接受!");
                    result = handle(inputStream);
                    writer.write(result);
                    writer.newLine();
                    writer.flush();
                } catch (DataFormException | SocketTimeoutException ex) {
                    System.out.println("再次接受, 发生异常,连接关闭");
                }
            }
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            try {
                assert writer != null;
                writer.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
