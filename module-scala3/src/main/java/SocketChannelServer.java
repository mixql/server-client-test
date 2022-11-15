import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class SocketChannelServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = null;
        SocketChannel client = null;
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));
        client = serverSocket.accept();
        System.out.println("Connection Set:  " + client.getRemoteAddress());
        Path path = Paths.get("/home/mihan1233/sources/server-client-test/temp1.txt");
        FileChannel fileChannel = FileChannel.open(path,
                EnumSet.of(StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING,
                        StandardOpenOption.WRITE)
        );
        try {
//            Thread.sleep(9000);
            ByteBuffer buffer = ByteBuffer.allocate(24);
            int res = client.read(buffer);
            do {
                buffer.clear();
                System.out.println(res);
            } while (res < 0);

            do {
                buffer.flip();
                fileChannel.write(buffer);
                buffer.clear();
                res = client.read(buffer);
                System.out.println(res);
            }
            while (res > 0);

            fileChannel.close();
            System.out.println("File Received: ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            client.close();
        }
    }
}