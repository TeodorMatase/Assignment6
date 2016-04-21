package assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class TicketServer {
	static int PORT = 2222;

	// EE422C: no matter how many concurrent requests you get,
	// do not have more than three servers running concurrently
	final static int MAXPARALLELTHREADS = 3;
	static Thread s;

	public static void start(int portNumber, Theater theater, String boxoffice) throws IOException {
		PORT = portNumber;
		Runnable serverThread = new ThreadedTicketServer(theater, boxoffice);
		Thread t = new Thread(serverThread);
		s = t;
		s.start();
		//s.run();
	}
}

class ThreadedTicketServer implements Runnable {

	String hostname = "127.0.0.1";
	String threadname;
	String testcase;
	TicketClient sc;
	Theater theat;
	
	public ThreadedTicketServer(Theater t, String bo){
		hostname = "127.0.0.1";
		threadname = bo;
		this.theat = t;
	}

	public void run() {
		// TODO 422C
		System.out.flush();
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(TicketServer.PORT);
			while(true){
			/*	try {
					Thread.sleep((long) (Math.random()*100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				*/
				Socket clientSocket = serverSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				String seat = theat.bestAvailableSeat();
				if(seat.equals("-1")){
					out.println("No seats left");
					serverSocket.close();
					in.close();
					out.close();
					return;
				}
				else{
					StringTokenizer temp = new StringTokenizer(seat, ",");
					int count = temp.countTokens();
					int x[] = new int[count];
					x[0] = Integer.parseInt(temp.nextToken());
					x[1] = Integer.parseInt(temp.nextToken());
					theat.markAvailableSeatTaken(x[0], x[1]);
					out.println(theat.printTicketSeat(x[0], x[1], threadname));
				}
				in.close();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}