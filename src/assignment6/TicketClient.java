
package assignment6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.math.*;

class ThreadedTicketClient implements Runnable {
	String hostname = "127.0.0.1";
	String threadname = "X";
	TicketClient sc;

	public ThreadedTicketClient(TicketClient sc, String hostname, String threadname) {
		this.sc = sc;
		this.hostname = hostname;
		this.threadname = threadname;
	}

	public void run() {
		System.out.flush();
		try {
			Socket echoSocket = new Socket(hostname, TicketServer.PORT);
			//Try this
			//TicketServer.PORT++;
			sc.sleep(Math.random()*100);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String temp = in.readLine();
			System.out.println(temp);
			in.close();
			out.close();
			stdIn.close();
			echoSocket.close();
			if(temp.equals("No seats left")){
				sc.done = 1;
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

public class TicketClient {
	ThreadedTicketClient tc;
	String result = "dummy";
	String hostName = "";
	String threadName = "";
	int done = 0;

	TicketClient(String hostname, String threadname) {
		tc = new ThreadedTicketClient(this, hostname, threadname);
		hostName = hostname;
		threadName = threadname;
	}

	TicketClient(String name) {
		this("localhost", name);
	}

	TicketClient() {
		this("localhost", "unnamed client");
	}
	
	int requestTicket() {
		// TODO thread.run()
		tc.run();
		if(this.done == 1){
			return this.done;
		}
	//	System.out.println(hostName + "," + threadName + " got one ticket");
		return 0;
	}

	void sleep(double d) {
		try {
			Thread.sleep((long) d);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
