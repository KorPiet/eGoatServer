package eGoatServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {

	public static void main(String[] args) throws Exception {
		Database base = new Database();
		//DatabaseAdd.main(null);


		class AddFile implements Runnable{

			public void run(){
				DatagramSocket datagramSocket = null;
				try {
					datagramSocket = new DatagramSocket(ConfigurationOfPorts.PORT_FILE_AND_CONTROL_SUM );
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] byteResponse = null;
				try {
					byteResponse = "OK".getBytes("utf8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true){

					DatagramPacket reclievedPacket
					= new DatagramPacket( new byte[ConfigurationOfPorts.BUFFER_SIZE], ConfigurationOfPorts.BUFFER_SIZE);

					try {
						datagramSocket.receive(reclievedPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int length = reclievedPacket.getLength();
					String message = null;
					try {
						message = new String(reclievedPacket.getData(), 0, length, "utf8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Port i host który wysłał nam zapytanie
					InetAddress address = reclievedPacket.getAddress();
					int port = reclievedPacket.getPort();
					base.addLine(message + "\t" + address.toString());

					DatagramPacket response
					= new DatagramPacket(
							byteResponse, byteResponse.length, address, port);

					try {
						datagramSocket.send(response);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					for(DatabaseLine line : base.files)
						System.out.println(line.toString());

				}
			}
		};
		

		class SearchFile implements Runnable {

			public void run() {
				DatagramSocket datagramSocket = null;
				try {
					datagramSocket = new DatagramSocket(ConfigurationOfPorts.PORT_SEARCH_FILE);
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				byte[] byteResponse = null;
				try {
					byteResponse = "OK".getBytes("utf8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true){

					DatagramPacket reclievedPacket
					= new DatagramPacket( new byte[ConfigurationOfPorts.BUFFER_SIZE], ConfigurationOfPorts.BUFFER_SIZE);

					try {
						datagramSocket.receive(reclievedPacket);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					int length = reclievedPacket.getLength();
					String message = null;
					try {
						message = new String(reclievedPacket.getData(), 0, length, "utf8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Port i host który wysłał nam zapytanie
					InetAddress address = reclievedPacket.getAddress();
					int port = reclievedPacket.getPort();
					List<DatabaseLine> send = new ArrayList<DatabaseLine>();
					for(DatabaseLine line : base.files) {
						if(line.isFilename(message))
							send.add(line);
					}
					System.out.println("pliki:");
					String test = "";
					for(DatabaseLine line : send) {
						System.out.println(line.toString());
						test += line.toString() + "\n";
					}
					try {
						byteResponse = test.getBytes("utf8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DatagramPacket response
					= new DatagramPacket(
							byteResponse, byteResponse.length, address, port);

					try {
						datagramSocket.send(response);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		};
		Thread addFileThread = new Thread(new AddFile());
		
		Thread searchFileThread = new Thread(new SearchFile());
		searchFileThread.start();
		addFileThread.start();
	}
}
