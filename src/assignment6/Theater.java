package assignment6;



public class Theater {
/*	int row; //A(0)...Z(25)
	int number; //number of seat, 0-27
	0-6 = house left
	7-20 = house middle
	21-27 = house right
*/
	boolean seats[][]; //728 seats in total
	
	public Theater(){
		for(int i = 0; i < 26; i++){
			for(int j = 0; j < 28; j++){
				seats[i][j] = false;
			}
		}
	}
	public int bestAvailableSeat(){
		//Check first available row middle seats
		//If all are taken, then check that same rows side seats
		//If all side seats are taken go to the next row and start over
		for(int i = 0; i < 26; i++){
			int s = checkseat(i);
			if(s != -1){
				return s;
			}
		}
		return -1;
	}
	
	public int checkseat(int row){
		for(int i = 7; i < 21; i++){ //Checks middle of row for empty seat
			if(seats[row][i] == false){
				return i;
			}
		}
		for(int i = 0; i < 7; i++){
			if(seats[row][i] == false){ //Checks left of row for empty seat
				return i;
			}
		}
		for(int i = 21; i < 28; i++){ //Checks right of row for empty seat
			if(seats[row][i] == false){
				return i;
			}
		}
		return -1; //no empty seats in row
	}
	

	public void markAvailableSeatTaken(int r, int s){
		seats[r][s] = true;
	}

	public void printTicketSeat(int r, int s){
		String section = "";
		char row = (char)(r + 65);
		if(s <= 6){
			section = "House Left";
		}
		else if(s >= 21){
			section = "House Right";
			s = s-20;
		}
		else{
			s = s-6;
			section = "House Middle";
		}
		System.out.println("This ticket is good for the seat at the following location");
		System.out.println("Row		: "+row);
		System.out.println("Section	: "+section);
		System.out.println("Seat#	: "+s);
		System.out.println("Enjoy the Show!");
		

	}
}


