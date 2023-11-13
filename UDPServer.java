package server;

import utils.Database;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UDPServer extends AbstractServer implements Runnable {
    public UDPServer(Database database, int udpPort) {
        super(database, udpPort);
    }

    @Override
    public void run() {
        try {
            //create UDP server socket.
            DatagramSocket server = new DatagramSocket(port);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                server.receive(request);

                // process the request from client and handle the key-value operation
                String keyValueStoreOperation = new String(Arrays.copyOfRange(request.getData(),
                        0, request.getLength()), StandardCharsets.UTF_8);
                logger.debugLog("from: "  + request.getAddress().getCanonicalHostName() + ":" + request.getPort() + keyValueStoreOperation);
                String serverResponse = parseInput(keyValueStoreOperation);
                logger.debugLog("response: " + serverResponse);

                // get client's address to send response back
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                byte[] serverResponseBytes = serverResponse.getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(serverResponseBytes, serverResponseBytes.length,
                        clientAddress, clientPort);

                server.send(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.close();
        }
    }
}
