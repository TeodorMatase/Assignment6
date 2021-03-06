/*
 * Contains tests for our ticket office
 * 
 * @author Jiwhan Son, Teodor Matase
 */
package assignment6;

//import static org.junit.Assert.fail;

//import org.junit.Test;

public class TestTicketOffice {
	public static int score = 0;
	
	public static void main(String[] args) {
		TestTicketOffice ourOffice = new TestTicketOffice();
		//ourOffice.basicServerTest();
		//ourOffice.testServerCachedHardInstance();
		//ourOffice.twoNonConcurrentServerTest();
		//ourOffice.twoServerTest();
		//ourOffice.twoConcurrentServerTest();
		//ourOffice.twoServerTwoThreadTest();
	}

//	@Test
	public void basicServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16789,theat, "A");
		} catch (Exception e) {
			//fail();
		}
		TicketClient client = new TicketClient();
		client.requestTicket();
	}

//	@Test
	public void testServerCachedHardInstance() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16790, theat, "A");
		} catch (Exception e) {
			//fail();
		}
		TicketClient client1 = new TicketClient("localhost", "c1");
		TicketClient client2 = new TicketClient("localhost", "c2");
		client1.requestTicket();
		client2.requestTicket();
		/*
		for(int i = 0; i < 1000; i++){
			int temp = client1.requestTicket();
			if(temp == 1){
				return;
			}
		}
		*/
	}

//	@Test
	public void twoNonConcurrentServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16791, theat, "A");
			//TicketServer.start(15000, theat, "B");
		} catch (Exception e) {
			//fail();
		}
		TicketClient c1 = new TicketClient("nonconc1");
		TicketClient c2 = new TicketClient("nonconc2");
		TicketClient c3 = new TicketClient("nonconc3");
		c1.requestTicket();
		c2.requestTicket();
		c3.requestTicket();
		/*
		for(int i = 0; i < 1000; i++){
			c1.requestTicket();
			c2.requestTicket();
			c3.requestTicket();
		}
		*/
	}
	
	//test method
	//@Test
	public void twoServerTest() {
		TicketClient c1 = new TicketClient();
		TicketClient c2 = new TicketClient();
		try {
			Theater theat = new Theater();
			TicketServer.start(16792, theat, "A");
			c1 = new TicketClient("conc1");
			TicketServer.start(16645, theat, "B");
			c2 = new TicketClient("conc2");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		c1.requestTicket();
		c2.requestTicket();
	}
	

//	@Test
	public void twoConcurrentServerTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16792,theat,"A");
			//TicketServer.start(16645,theat,"B");
			
		} catch (Exception e) {
			//fail();
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
	
	//unfinished
	public void twoServerTwoThreadTest() {
		try {
			Theater theat = new Theater();
			TicketServer.start(16792,theat,"A");
			final TicketClient c1 = new TicketClient("conc1");
			TicketServer.start(16645,theat,"B");
			final TicketClient c2 = new TicketClient("conc2");
			
			for(int i = 0; i < 10; i++) {
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
				t1.start();
				t2.start();
			}
			
		} catch (Exception e) {
			//fail();
		}
		
		/*try {
			while(true){
				t1.join();
				t2.join();
				t3.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

	}
}
