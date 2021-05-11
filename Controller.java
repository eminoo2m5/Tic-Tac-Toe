import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Controller {

	private View view;
	private ActionListener submitListener;
	private ActionListener one;
	private ActionListener two;
	private ActionListener three;
	private ActionListener four;
	private ActionListener five;
	private ActionListener six;
	private ActionListener seven;
	private ActionListener eight;
	private ActionListener nine;

	private String clientName;
	private int player = -1;
	private int lastplayer = 2;
	//private boolean player1 = false;
	//private boolean myturn = false;

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
				view.getName().setEnabled(false);
				view.getSubmit().setEnabled(false);
			}
		};
		view.getSubmit().addActionListener(submitListener);

		one = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 0;
					out.println(send);
				}
			}
		};
		two = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 1;
					out.println(send);
				}
			}
		};
		three = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 2;
					out.println(send);
				}
			}
		};
		four = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 3;
					out.println(send);
				}
			}
		};
		five = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 4;
					out.println(send);
				}
			}
		};
		six = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 5;
					out.println(send);
				}
			}
		};
		seven = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 6;
					out.println(send);
				}
			}
		};
		eight = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 7;
					out.println(send);
				}
			}
		};
		nine = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				if(lastplayer != player){
					int send = player*10 + 8;
					out.println(send);
				}
			}
		};

		// Creates a new Thread for reading server messages
		Thread handler = new ClinetHandler(socket);
		handler.start();
	}
	public void addListeners(){
		view.getArea()[0].addActionListener(one);
		view.getArea()[1].addActionListener(two);
		view.getArea()[2].addActionListener(three);
		view.getArea()[3].addActionListener(four);
		view.getArea()[4].addActionListener(five);
		view.getArea()[5].addActionListener(six);
		view.getArea()[6].addActionListener(seven);
		view.getArea()[7].addActionListener(eight);
		view.getArea()[8].addActionListener(nine);
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

					try{
						int input = Integer.parseInt(command.trim());
						if(input == 1){
							player = 1;
						}
						else if (input == 2 && player == -1){
							player = 2;
						}
						else if (input/10 > 0) {
							int update = Integer.parseInt(command.trim())%10;
							lastplayer = Integer.parseInt(command.trim())/10;

							if(lastplayer == 1) {
								view.getArea()[update].setText("X");
								view.getArea()[update].setForeground(Color.GREEN);
							}
							else {
								view.getArea()[update].setText("O");
								view.getArea()[update].setForeground(Color.RED);
							}
							ActionListener[] list = view.getArea()[update].getActionListeners();
							view.getArea()[update].removeActionListener(list[0]);
							if(player != lastplayer) view.getInfo().setText("Your opponent has moved, now is your turn");
							else view.getInfo().setText("Valid move, wait for your opponent.");
						}
					} catch(NumberFormatException e){
						if(command.equals("START")){
							addListeners();
						} else if(command.equals("DRAW")){
							view.drawMessage();
						} else {
							String[] part = command.split(" ");
							int winner = Integer.parseInt(part[1]);
							if(winner == player) view.winMessage();
							else view.loseMessage();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}

}
