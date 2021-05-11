public class Game {
	private int[] n;
	//private String[] player;
	public Game(){
		this.n = new int[9];
		for(int i=0; i<n.length; i++) n[i] = 0;
	}
	public synchronized void oneSelected(int i){
		n[i]++;
	}
	public synchronized void twoSelected(int i){
		n[i]--;
	}
	public int[] getField(){
		return n;
	}
	public boolean checkWin(){
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
}