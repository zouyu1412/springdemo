package com.ssp.higher.base.utils.thread.tcpipbio;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2018/8/7
 * Time: 9:49 PM
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9999);
        //创建读取服务器端返回流的BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //创建服务器写入流
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag){
            String line = sysin.readLine();
            if (line == null || "quit".equalsIgnoreCase(line.trim())){
                flag = false;
                System.out.println("Client quit！");
                out.println("quit");
                out.close();
                in.close();
                socket.close();
                continue;
            }
            out.println(line);
            String res = in.readLine();
            System.out.println(res);
        }
    }
}
