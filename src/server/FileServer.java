package server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class FileServer extends Thread {

	public static final int PORT = 9999;
	ServerSocket serverSocket;

	public FileServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
	}

	public void run() {
		Socket clientSocket;
		while (true) {
			try {
				System.out.println("waiting for connection on port " + serverSocket.getLocalPort());
				clientSocket = serverSocket.accept();
				System.out.println("connected to " + clientSocket.getInetAddress());
				saveFile(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile(Socket clientSocket) throws IOException {
		InputStream inputStream = clientSocket.getInputStream();

		try (DataInputStream dataInputStream = new DataInputStream(inputStream);
				FileOutputStream fileOutputStream = new FileOutputStream("temp.7z")) {
			byte[] buffer = new byte[32768];
			int read = 0;
			int totalRead = 0;
			int count = 0;
			while ((read = dataInputStream.read(buffer, 0, buffer.length)) > 0) {
				totalRead += read;
				Date d = new Date();
				System.out.println(d.getMinutes() +"::"+ d.getSeconds()+ ": read " + totalRead + " bytes.");
				fileOutputStream.write(buffer, 0, read);
				count++;
			}
			System.out.println("Transfer complete!!");
			System.out.println("count = "+count);
		}
	}

	public static void main(String args[]) throws IOException {
		FileServer fileServer = new FileServer();
		fileServer.run();
	}
}
