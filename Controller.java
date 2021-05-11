import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller {

	private View view;
	private ActionListener submitListener;
	private String clientName;

	private Socket socket;
	private Scanner in;
	private PrintWriter out;

	public Controller(View view) {
		this.view = view;
	}

	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 58901);
			this.in = new Scanner(socket.getInputStream());
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		submitListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				clientName = view.getName().getText();
				view.getFrame().setTitle("Tic Tac Toe-Player: " + clientName);
				view.getInfo().setText("WELCOME " + clientName);
				out.println(clientName);
				System.out.println("Client Sent: " + clientName);
			}
		};
		view.getSubmit().addActionListener(submitListener);

		// Creates a new Thread for reading server messages
		Thread handler = new ClinetHandler(socket);
		handler.start();
	}

	class ClinetHandler extends Thread {
		private Socket socket;

		public ClinetHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {
					var command = in.nextLine();
					System.out.println("Client Received: " + command);
					out.flush();
					//view.getResultLabel().setText(command.trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}

}
