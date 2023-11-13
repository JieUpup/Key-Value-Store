package server;

import utils.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/*
 * implement runnable interface. override run function
 * use read buffer to receive message from client, set up read 1024 byte once time.
 * construct the string thought the byte."off" means offset inside the buffer.
 *  encoding: standard
 */

public class TCPServer extends AbstractServer implements Runnable {
    public TCPServer(Database database, int tcpPort) {
        super(database, tcpPort);
    }

    @Override
    public void run() {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(port);
            socket = server.accept();

            while (true) {
                byte[] readBuffer = new byte[1024];
                int off = socket.getInputStream().read(readBuffer);
                String inputString = new String(Arrays.copyOfRange(readBuffer, 0, off), StandardCharsets.UTF_8);
                logger.debugLog("from: "  + socket.getRemoteSocketAddress().toString() + " " + inputString);

                String outputString = parseInput(inputString);
                logger.debugLog("response: " + outputString);
                //write response to client through OutputStream(convert output string with UTF_8 encoding)
                socket.getOutputStream().write(outputString.getBytes(StandardCharsets.UTF_8));
                //flush to make sure the output has been sent back to the client.
                socket.getOutputStream().flush();
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                if (socket != null) socket.close();
                if (server != null) server.close();
            } catch (IOException e) {
                // swallow the exceptions
                e.printStackTrace();
            }
            logger.close();
        }
    }
}
