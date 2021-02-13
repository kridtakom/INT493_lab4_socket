package com.company;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket clientSocket = new Socket();
        System.out.println("Connecting");
        clientSocket.connect(new InetSocketAddress("127.0.0.1", 8080));
        System.out.printf
                ("Connected from port:%d\n", clientSocket.getLocalPort());
        Scanner userInput = new Scanner(System.in);
        Scanner scanner = new Scanner(clientSocket.getInputStream());

        for (int i = 0; i < 10; i++) {
            String cmd = userInput.nextLine();
            clientSocket.getOutputStream().write((cmd+"\n").getBytes());
            clientSocket.getOutputStream().flush();

            String time = scanner.nextLine();
            System.out.println(time);

            Thread.sleep(1000);
        }
        clientSocket.close();
    }
}
