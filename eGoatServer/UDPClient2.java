package eGoatServer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPClient2 {

    public static void main(String[] args) throws IOException {

        String message = "umlaut" + "\t" + "d5d59516be45fb70fd90e6c3ae3af20f5cd1c1314e3b4db602272cd9ed3e372a";
        InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
        System.out.println(serverAddress);

                DatagramSocket socket = new DatagramSocket(); //Otwarcie gniazda
        byte[] stringContents = message.getBytes("utf8"); //Pobranie strumienia bajtów z wiadomosci

        DatagramPacket sentPacket = new DatagramPacket(stringContents, stringContents.length);
        sentPacket.setAddress(serverAddress);
        sentPacket.setPort(ConfigurationOfPorts.PORT_FILE_AND_CONTROL_SUM);
        socket.send(sentPacket);

        DatagramPacket reclievePacket = new DatagramPacket( new byte[ConfigurationOfPorts.BUFFER_SIZE], ConfigurationOfPorts.BUFFER_SIZE);
        socket.setSoTimeout(1010);

        try{
            socket.receive(reclievePacket);
            System.out.println("Serwer otrzymał wiadomość");
        }catch (SocketTimeoutException ste){
            System.out.println("Serwer nie odpowiedział, więc albo dostał wiadomość albo nie...");
        }

    }
}