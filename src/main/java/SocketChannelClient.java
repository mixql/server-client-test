import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.System.*;
import java.text.MessageFormat;

public class SocketChannelClient {
    public static void main(String[] args) throws IOException {

        try {
            SocketChannel server = tryToConnect("localhost", 9000, 5, 5000);
            System.out.println("Connection established. Trying to write file");
            Path path = Paths.get("/home/mihan1233/sources/server-client-test/temp.txt");
            FileChannel fileChannel = FileChannel.open(path);
            ByteBuffer buffer = ByteBuffer.allocate(20);
            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                int res = 0;
                do{
                    res = server.write(buffer);
                    System.out.println(res);
                }while(res != 0);


                buffer.clear();
            }
            fileChannel.close();
            System.out.println("File Sent");
            server.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    static SocketChannel tryToConnect(String hostname, Integer port,
                                      Integer numberOfTries, long timeout) throws Exception {
        if (numberOfTries == 0) throw new Exception(
                "Number of tries are out. Stopped to connect to remote server's socket");
        SocketChannel server = null;
        try {
            System.out.println("Trying to connect to remote server's socket");
            server = SocketChannel.open();
            SocketAddress socketAddr = new InetSocketAddress(hostname, port);
            server.connect(socketAddr);
            return server;

        } catch (Exception ex) {
            if (server != null) server.close();

            System.out.println(
                    MessageFormat.format("Couls not connect to remote server socket. Sleep {0} millis", timeout));
            Thread.sleep(timeout);
            return tryToConnect(hostname, port, numberOfTries - 1, timeout);
        }
    }
}