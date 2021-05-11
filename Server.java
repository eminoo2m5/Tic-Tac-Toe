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
	private int trial;

	// The set of all the print writers for all the clients, used for broadcast.
	private Set<PrintWriter> writers = new HashSet<>();

	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.game = new Game();
	}

	public void start() {
		var pool = Executors.newFixedThreadPool(200);
		int clientCount = 1;
		while (clientCount<3) {
			try {
				Socket socket = serverSocket.accept();
				pool.execute(new Handler(socket,clientCount));
				System.out.println("Connected to client " + clientCount++);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;
		private int client;

		public Handler(Socket socket, int client) {
			this.socket = socket;
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
				
				//
				for (PrintWriter writer : writers) {
					writer.println(this.client);
				}

				while (input.hasNextLine()) {
					var command = input.nextLine();
					int selected = -1;
					System.out.println("Server Received: " + command);


					try{
						selected = Integer.parseInt(command.trim());
						trial++;
						if(selected/10 == 1){
							game.oneSelected(selected%10);
							if(game.checkWin()){
								for (PrintWriter writer : writers) {
									writer.println("WIN 1");
								}
								System.out.println("Server Broadcasted: WIN 1");
							}
						}
						else if (selected/10 == 2){
							game.twoSelected(selected%10);
							if(game.checkWin()){
								for (PrintWriter writer : writers) {
									writer.println("WIN 2");
								}
								System.out.println("Server Broadcasted: WIN 2");
							}
						}

						if(trial == 9 && !game.checkWin()){
							for (PrintWriter writer : writers) {
									writer.println("DRAW");
								}
						}
					} catch(NumberFormatException e){
						names++;
						//receive names of two players, now let them begin
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
					}
					

					System.out.println("Server Broadcasted: " + selected);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				// client disconnected
				if (output != null) {
					writers.remove(output);
				}
			}
		}
	}
}
