import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class TicTacToeServer {

	public static void main(String[] args) throws IOException {
		System.out.println("Tic Tace Toe Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Server Stopped.");
			}
		}));

		try (var listener = new ServerSocket(58901)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
