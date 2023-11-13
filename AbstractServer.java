package server;

import utils.Database;
import utils.Logger;

import java.util.Arrays;
import java.util.HashMap;

abstract public class AbstractServer {
    // As we have shared database for both TCPServer and UDPServer.
    // Only created one global logger, so TCP server and UDP server could write to the same log file.
    protected static Logger logger = ServerApp.logger;

    // Shared database which will be passed through ServerApp, this is only the reference of the database.
    protected Database database;
    protected int port;

    private static final String INVALID_INPUT = "Invalid input";
    private static final String SUCCESS = "Success";

    public AbstractServer(Database database, int port) {
        this.database = database;
        this.port = port;
    }

    public  String parseInput(String input) {
        /*
         *  PUT    <KEY> <VALUE>
         *  GET    <KEY>
         *  DELETE <KEY>
         */

        //split string with spaces. use regex.
        String[] arr = input.split("\s\s*");

        if (arr.length < 2) {
            System.out.println("Invalid input");
            return "false, invalid input";
        }

        // start to parse the operation and key
        String operation = arr[0];
        String key = arr[1];

        if (operation.equals("GET")) {
            if (arr.length != 2) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            return database.get(key);
        } else if (operation.equals("DELETE")) {
            if (arr.length != 2) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            return database.delete(key);
        } else if (operation.equals("PUT")) {
            if (arr.length != 3) {
                System.out.println("Invalid input");

                return "false, invalid input";
            }

            String value = arr[2];
            return database.put(key, value);
        }

        return "unknown operation";
    }
}

