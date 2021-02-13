package com.company;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        System.out.println("listening to 8080");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("client connected %s:%d\n",
                    clientSocket.getInetAddress().getAddress(),
                    clientSocket.getPort());

            Thread t1 = new Thread(new ClientHandler(clientSocket));
            t1.start();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(clientSocket.getInputStream());
            while (sc.hasNextLine()) {
                System.out.println("Has next line");
                String command = sc.nextLine();
                System.out.printf("GOT %s\n", command);
                if (command.equalsIgnoreCase("UPDATE")) {
                    String data = String.format("Time:%d\n", System.currentTimeMillis());
                    clientSocket.getOutputStream().write(data.getBytes());
                    clientSocket.getOutputStream().flush();
                }
            }
        } catch (Exception e) {
            //do nothing
        } finally {
            try {
                clientSocket.close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }
}