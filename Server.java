import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket serverSocket;
	private Game game;

	private int names;
	boolean start = false;
	private int clientCount = 0;

	// The set of all the print writers for all the clients, used for broadcast.
	private Set<PrintWriter> writers = new HashSet<>();

	//create a game field when server starts
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.game = new Game();
	}

	public void start() {
		var pool = Executors.newFixedThreadPool(200);
		while (this.clientCount<3) {
			try {
				Socket socket = serverSocket.accept();
				//assign client number to check there are maximum two players
				clientCount++;
				pool.execute(new Handler(socket,clientCount));
				System.out.println("Connected to client " + clientCount);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;

		//for counting client numbers
		private int client;

		public Handler(Socket socket, int client) {
			this.socket = socket;
			//assign client number
			this.client = client;
		}

		@Override
		public void run() {
			System.out.println("Connected: " + socket);
			try {
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);
				
				// add this client to the broadcast list
				writers.add(output);
				
				//broadcast how many client has joined (will be used to indictae player#)
				for (PrintWriter writer : writers) {
					writer.println(this.client);
				}

				while (input.hasNextLine()) {
					var command = input.nextLine();
					System.out.println("Server Received: " + command);
					//field selected by client (default = -1)
					int selected = -1;

					//If client sends number then it is a selected field
					//If cllient sends a string then it is a client name
					try{
						selected = Integer.parseInt(command.trim());
						//update according to player#
						if(selected/10 == 1){
							game.oneSelected(selected%10);
						}
						else if (selected/10 == 2){
							game.twoSelected(selected%10);
						}
					} catch(NumberFormatException e){
						names++;
						//if received names of two players, now let them begin
						if(names == 2){
							for (PrintWriter writer : writers) {
								writer.println("START");
								start = true;
							}
							System.out.println("Server Broadcasted: START");
						}
					}

					if(start && selected!=-1){
						// broadcast updated number to all clients
						for (PrintWriter writer : writers) {
							writer.println(selected);
						}
						System.out.println("Server Broadcasted: " + selected);

						//check whether the game is over
						if(game.checkWin()){
							if(selected/10 == 1){
								for (PrintWriter writer : writers) {
									writer.println("WIN 1");
								}
								System.out.println("Server Broadcasted: WIN 2");
							}
							else{
								for (PrintWriter writer : writers) {
									writer.println("WIN 2");
								}
								System.out.println("Server Broadcasted: WIN 2");
							}
						}
						//check whether it is a draw game
						if(game.checkFull()&&!game.checkWin()){
							for (PrintWriter writer : writers) {
									writer.println("DRAW");
								}
							System.out.println("Server Broadcasted: DRAW");
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				// client disconnected
				if (output != null) {
					writers.remove(output);
					clientCount--;
					//if one client is disconnected, end the game
					for (PrintWriter writer : writers) {
						writer.println("LEFT");
					}
					System.out.println("Server Broadcasted: LEFT");
				}
			}
		}
	}
}
