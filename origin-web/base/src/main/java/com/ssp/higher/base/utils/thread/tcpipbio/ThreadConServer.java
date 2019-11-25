package com.ssp.higher.base.utils.thread.tcpipbio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IDEA
 * Creater: MOBIN
 * Date: 2018/8/8
 * Time: 2:36 PM
 * 每线程每连接，多客户端
 */
public class ThreadConServer {

    public void creatTask() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        ServerSocket ss = new ServerSocket(9999);
        while (!Thread.currentThread().isInterrupted()){
            Socket socket = ss.accept();
            executorService.submit(new Task(socket));
        }
    }

    public static void main(String[] args) throws IOException {
      ThreadConServer server = new ThreadConServer();
      server.creatTask();
    }

    class Task extends Thread {
        private Socket socket;
        public Task(Socket socket){
            this.socket = socket;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && !socket.isClosed()){
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    String line = in.readLine();
                    if ("quit".equalsIgnoreCase(line)){
                        in.close();
                        out.close();
                        socket.close();
                        System.out.println("关闭服务器");
                        System.exit(0);
                    }
                    if (line != null){
                        System.out.println(Thread.currentThread().getName() + "客户端信息：" + line);
                        out.println("服务端接收成功!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
