package com.ssp.higher.base.utils.thread.tcpipbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2018/8/8
 * Time: 9:57 AM
 * 单客户端
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(9999);
        Socket socket = ss.accept();
        System.out.println("正在监听9999端口");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        while (true){
            String line = in.readLine();
            if (line == null){
                Thread.sleep(1000);
                continue;
            }
            if ("quit".equalsIgnoreCase(line)){
                in.close();
                out.close();
                ss.close();
                System.out.println("关闭服务器....");
                System.exit(0);
            }else {
                System.out.println("客户端信息:" + line);
                out.println("服务器回应：" + line);
                Thread.sleep(100);
            }
        }

    }
}
