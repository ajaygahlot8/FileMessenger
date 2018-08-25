package client;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	Socket socket;

	public ClientSocket(String ipAddress, int port, String fileName) throws UnknownHostException, IOException {
		socket = new Socket(ipAddress, port);
		sendFile(fileName);
	}

	private void sendFile(String fileName) throws IOException {

		try (FileInputStream fileInputStream = new FileInputStream(fileName);
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
			byte[] buffer = new byte[4096];
			int read = 0;
			while ((read = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
				System.out.println("sending data " + read);
				dataOutputStream.write(buffer, 0, read);
			}
		}

	}

	public static void main(String args[]) throws UnknownHostException, IOException {

		String ipAddress = args[0];
		int port = Integer.parseInt(args[1]);
		String fileName = args[2];

		new ClientSocket(ipAddress, port, fileName);

	}

}
