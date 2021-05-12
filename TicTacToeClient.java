import javax.swing.SwingUtilities;

public class TicTacToeClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//create each frame and add controllers
				View view = new View();
				Controller controller = new Controller(view);
				controller.start();
			}
		});
	}
}
