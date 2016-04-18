package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TicketServer {
	static int PORT = 2222;

	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;

	public static void start(int portNumber) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer();
		Thread t = new Thread(serverThread);
		t.start();
	}
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname = "X";
	String testcase;
	TicketClient sc;
	Theater theat = new Theater();

	public void run() {
		// TODO 422C
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			Socket clientSocket = serverSocket.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			int temp = in.read();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String seat = theat.bestAvailableSeat();
			if(seat.equals("-1")){
				out.println("No seats left");
			}
			else{
				int r = Character.getNumericValue(seat.charAt(0));
				int s = Character.getNumericValue(seat.charAt(2));
				theat.markAvailableSeatTaken(r, s/*,boxoffice#*/);
				out.println(theat.printTicketSeat(r, s));
			}
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}