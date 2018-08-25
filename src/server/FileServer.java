package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends Thread {

	public static final int PORT = 9999;
	ServerSocket serverSocket;

	public FileServer() {

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			Socket clientSocket;
			try {
				System.out.println("waiting for connection" + serverSocket.getLocalPort());
				clientSocket = serverSocket.accept();
				System.out.println("connected to " + clientSocket);
				saveFile(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile(Socket clientSocket) throws IOException {
		InputStream inputStream = clientSocket.getInputStream();
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		FileOutputStream fileOutputStream = new FileOutputStream("temp.zip");

		byte[] buffer = new byte[4096];
		int read = 0;
		int totalRead = 0;

		while ((read = dataInputStream.read(buffer, 0, buffer.length)) > 0) {

			totalRead += read;
			System.out.println("read " + totalRead + " bytes.");
			fileOutputStream.write(buffer, 0, read);

		}

		fileOutputStream.close();
		dataInputStream.close();
	}

	public static void main(String args[]) throws IOException {

		FileServer fileServer = new FileServer();
		fileServer.run();
	}
}
