/*
 * Model for our theater. Tracks seat availability
 * 
 * @author Jiwhan Son, Teodor Matase
 */

package assignment6;

public class Theater {
/*	int row; //A(0)...Z(25)
	int number; //number of seat, 0-27
	0-6 = house left
	7-20 = house middle
	21-27 = house right
*/
	boolean seats[][] = new boolean[26][28]; //728 seats in total
	//int BO[][] = new int[27][28]; //The boxoffice that sold that specific seat
	
	/*
	 * Constructs an empty theater
	 */
	public Theater(){
		for(int i = 0; i < 26; i++){
			for(int j = 0; j < 28; j++){
				seats[i][j] = false;
				//BO[i][j] = 0;
			}
		}
	}
	
	/*
	 * @return	row and seat number of best available seat
	 */
	public String bestAvailableSeat(){
		//Check first available row middle seats
		//If all are taken, then check that same rows side seats
		//If all side seats are taken go to the next row and start over
		//i = row number, s = seat number
		for(int i = 0; i < 26; i++){
			int s = checkseat(i);
			if(s != -1){
				return i+","+s;
			}
		}
		return "-1";
	}
	
	/*
	 * @param row	row number to check
	 * @return 	seat number of best available seat in row.
	 */
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
	
	/*
	 * Marks a seat taken
	 * 
	 * @param r	row number to check
	 * @param s seat number to check
	 */
	public void markAvailableSeatTaken(int r, int s){
		seats[r][s] = true;
		//BO[r][s] = boxoffice;
	}

	/*
	 * @param r	row number of seat
	 * @param s seat number
	 * @param tn	box office that sold seat
	 * @return seat ticket message
	 */
	public String printTicketSeat(int r, int s, String tn){
		String section = "";
		String ans = "";
		String boxoffice = tn;
		char row = (char)(r + 65);
		if(s <= 6){
			s++;
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
		ans = "This ticket was purchased from Box Office "+boxoffice+".It is located at Row:"+row+",Section:"+section+",Seat#:"+s+". Enjoy the Show!";
	
		return ans;
	}
}


