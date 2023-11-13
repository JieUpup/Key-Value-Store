package server;

import utils.Database;
import utils.Logger;
import utils.Utils;

import java.util.HashMap;

//use multi-thread to start both TCP and UDP server in the same application

public class ServerApp {
    public static Logger logger = new Logger("server.log");
    private static Database database = new Database(logger);

    public static void main(String[] args) {
        // validate the input
        if (args.length != 2) {
            logger.errLog("Usage: ServerApp <tcpPort> <udpPort>");
            System.exit(-1);
        }

        // start both TCP and UDP server at the same time
        // They're sharing the same database but should not have same port number
        int tcpPort = Utils.parsePort(args[0], logger);
        int udpPort = Utils.parsePort(args[1], logger);

        //create new thread to run the tcp server
        TCPServer tcpServer = new TCPServer(database, tcpPort);
        new Thread(tcpServer).start();

        //create another new thread to run the udp server
        UDPServer udpServer = new UDPServer(database, udpPort);
        new Thread(udpServer).start();
    }
}
