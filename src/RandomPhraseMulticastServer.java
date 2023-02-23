import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Random;

public class RandomPhraseMulticastServer {
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    private ArrayList<String> phrases = new ArrayList<>();

    public RandomPhraseMulticastServer(int portValue, String strIp) throws IOException {
        socket = new MulticastSocket(portValue);
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        phrases.add("Hello, world! Oh my God! What a disgrace! It's not working.");
        phrases.add("Today is the day we die, such a fine day.");
        phrases.add("Java is hell.");
        phrases.add("English humor is like shit but hey we still laugh.");
        phrases.add("I want to go home and sleep like for 24h straight.");
    }

    public void runServer() throws IOException{
        DatagramPacket packet;
        byte [] sendingData;
        Random random = new Random();

        while(continueRunning){
            String phrase = phrases.get(random.nextInt(phrases.size()));
            sendingData = phrase.getBytes();
            packet = new DatagramPacket(sendingData, sendingData.length,multicastIP, port);
            socket.send(packet);

            try {
                Thread.sleep(900);
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        RandomPhraseMulticastServer srvVel = new RandomPhraseMulticastServer(5557, "224.0.11.111");
        srvVel.runServer();
        System.out.println("Stopped!");
    }
}

