package com.system.server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * @author Luffy
 * @description
 * @createTime 2021年06月02日 12:21:00
 */
public class Client {
    public static final String IP_ADDR = "localhost";//服务器地址
    public static final int PORT = 7891;//服务器端口号

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        while (true) {
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR, PORT);
                //向服务器端发送数据
                OutputStream out =socket.getOutputStream();
                while (true) {
                    System.out.print("请输入: \t");
                    String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    out.write(str.getBytes(StandardCharsets.UTF_8));
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
            }
        }
    }
}
