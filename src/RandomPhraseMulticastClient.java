import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RandomPhraseMulticastClient {
    private static final String MULTICAST_IP = "224.0.11.111";
    private static final int PORT = 5557;

    private MulticastSocket socket;
    private InetAddress multicastIP;
    private int port;

    public void init() throws IOException {
        socket = new MulticastSocket(PORT);
        multicastIP = InetAddress.getByName(MULTICAST_IP);
        port = PORT;
        socket.joinGroup(multicastIP);
    }

    public void runClient() throws IOException {
        DatagramPacket packet;
        byte[] receivedData = new byte[1024];

        while (true) {
            packet = new DatagramPacket(receivedData, 1024);
            socket.receive(packet);
            String phrase = new String(packet.getData(), 0, packet.getLength());
            if (phrase.split("\\s+").length > 8) {
                System.out.println(phrase);
            }
        }
    }

    public void close() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.leaveGroup(multicastIP);
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        RandomPhraseMulticastClient client = new RandomPhraseMulticastClient();
        client.init();
        client.runClient();
        client.close();
    }
}
