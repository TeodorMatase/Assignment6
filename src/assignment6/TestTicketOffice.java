package assignment6;

import static org.junit.Assert.fail;

import org.junit.Test;

public class TestTicketOffice {
	
	public static int score = 0;

	// @Test
	public void basicServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16789,theat);
		} catch (Exception e) {
			fail();
		}
		TicketClient client = new TicketClient();
		client.requestTicket();
		
		//client.requestTicket();
	}

	@Test
	public void testServerCachedHardInstance() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16790, theat);
		} catch (Exception e) {
			fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		//TicketClient client2 = new TicketClient("localhost", "c2");
		//client1.requestTicket();
		//client2.requestTicket();
		for(int i = 0; i < 1000; i++){
			int temp = client1.requestTicket();
			if(temp == 1){
				return;
			}
		}
		
	}

	//@Test
	public void twoNonConcurrentServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16791, theat);
			TicketServer.start(16666, theat);
		} catch (Exception e) {
			fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
	}

	//@Test
	public void twoConcurrentServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16792,theat);
			
		} catch (Exception e) {
			fail();
		}
		final TicketClient c1 = new TicketClient("conc1");
		final TicketClient c2 = new TicketClient("conc2");
		final TicketClient c3 = new TicketClient("conc3");
		Thread t1 = new Thread() {
			public void run() {
				int temp = c1.requestTicket();
				if(temp == 1){
					return;
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				int temp = c2.requestTicket();
				if(temp == 1){
					return;
				}
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				int temp = c3.requestTicket();
				if(temp == 1){
					return;
				}
			}
		};
		
		t1.start();
		t2.start();
		t3.start();
		try {
			while(true){
				t1.join();
				t2.join();
				t3.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
