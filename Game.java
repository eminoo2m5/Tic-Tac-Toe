/**
 * This is the Game Field Object for the Tic Tac Toe Game
 * @author eminoo
 *
 */
public class Game {
	//field
	/*
		0 | 1 | 2
		---------
		3 | 4 | 5
		---------
		6 | 7 | 8
	*/
	private int[] n;

	//default constructor
	public Game(){
		this.n = new int[9];
		for(int i=0; i<n.length; i++) n[i] = 0;
	}
	/** 
	 *increase if it's selected by player 1
	 *
	 * @param int location
	 */
	public synchronized void oneSelected(int i){
		n[i]++;
	}

	/** 
	 * decrease if it's selected by player 2
	 * so if it is selected by player1 it is 1, selected by player 2 will be -1, 
	 * selected by none of them will be 0
	 *
	 * @param int location
	 */
	public synchronized void twoSelected(int i){
		n[i]--;
	}

	/** 
	 * check whether game is over
	 * checks whether there is a line of same elements and they are not 0
	 *
	 * @return boolean true (if someone has won)
	 */
	public synchronized boolean checkWin(){
		if(n[0]!= 0 && n[0]==n[1] && n[1]==n[2]) return true;
		if(n[3]!= 0 && n[3]==n[4] && n[4]==n[5]) return true;
		if(n[6]!= 0 && n[6]==n[7] && n[7]==n[8]) return true;
		if(n[0]!= 0 && n[0]==n[3] && n[3]==n[6]) return true;
		if(n[1]!= 0 && n[1]==n[4] && n[4]==n[7]) return true;
		if(n[2]!= 0 && n[2]==n[5] && n[5]==n[8]) return true;
		if(n[0]!= 0 && n[0]==n[4] && n[4]==n[8]) return true;
		if(n[2]!= 0 && n[2]==n[4] && n[4]==n[6]) return true;
		return false;
	}

	/** 
	 * check whether there is no more empty field
	 * indicator for a draw game
	 *
	 * @return boolean true (if every field has been selected by either player1 or 2)
	 */
	public synchronized boolean checkFull(){
		for(int i=0; i<n.length; i++){
			if (n[i] == 0) return false;
		}
		return true;
	}
}