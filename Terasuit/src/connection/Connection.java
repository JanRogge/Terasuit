package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Connection implements Runnable {

	private Socket socket;

	private BufferedReader reader;
	private Analyser analyser;

	private PrintStream writer;
	private ConcurrentLinkedQueue<String> queue;

	private int id;

	public Connection(Socket socket, Analyser analyser, int id) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.analyser = analyser;
		queue = new ConcurrentLinkedQueue<String>();
		this.id = id;
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	public void addMessage(String message) {
		queue.add(message);
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					System.out.println("Testerino");
					String in = reader.readLine();
					analyser.analyse(in);
				}
				if (!queue.isEmpty()) {
					writer.println(queue.remove());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// public void run() {
	// String in;
	// try {
	// System.out.println("nachricht");
	// while ((in = reader.readLine()) != null) {
	// analyser.analyse(in);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
