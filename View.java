import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class View {
	//frame
	private JFrame frame;
	//panels
	private JPanel[] panels;
	//button and textfield for submitting player name
	private JButton btn_name;
	private JTextField name;
	//info panel on top
	private JLabel info;
	//game area in the middle, each area is a button
	private JPanel board;
	private JButton[] area;

	public View() {
		setFrame();
		setInfoPanel();
		setGamePanel();
		setSubmitPanel();
	}

	//setting up frame
	private void setFrame() {
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 350);
		frame.setVisible(true);

		Container cp = frame.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		//create three panels (infor, game, and submit)
		panels = new JPanel[3];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			cp.add(panels[i]);
		}

		//menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Control");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		menu.add(exit);
		JMenu menu2 = new JMenu("Help");
		JMenuItem instruction = new JMenuItem("Instruction");
		instruction.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "Some information about this game:\nCriteria for a valid move:\n-The move is not occupied by any mark.\n-The move is made in player's turn.\n-The move is made within the 3x3 board.\nThe game would continue and switch among the opposite player until it reaches either one of the following conditions:\n-Player 1 wins.\n-Player 2 wins.\n-Draw.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu2.add(instruction);
		menuBar.add(menu);
		menuBar.add(menu2);
		frame.setJMenuBar(menuBar);
	}
	
	//setting up information panel where it tells validity of move and turn
	private void setInfoPanel(){
		info = new JLabel("Enter your Player name...");
		//info.setHorizontalAlignment(SwingConstants.LEFT);
		panels[0].add(info);
	}

	//seting up actual game panel
	private void setGamePanel() {
		board = new JPanel();
		//create nine buttons - nine areas on tic tac toe
		area = new JButton[9];

		JButton button1 = new JButton("?");
		area[0] = button1;
		JButton button2 = new JButton("?");
		area[1] = button2;
		JButton button3 = new JButton("?");
		area[2] = button3;
		JButton button4 = new JButton("?");
		area[3] = button4;
		JButton button5 = new JButton("?");
		area[4] = button5;
		JButton button6 = new JButton("?");
		area[5] = button6;
		JButton button7 = new JButton("?");
		area[6] = button7;
		JButton button8 = new JButton("?");
		area[7] = button8;
		JButton button9 = new JButton("?");
		area[8] = button9;

		//styling buttons
		for (int i = 0; i < area.length; i++) {
			area[i].setContentAreaFilled(true);
			area[i].setFocusPainted(false);
			area[i].setBorder(new LineBorder(Color.BLACK));
			area[i].setBackground(Color.WHITE);
			area[i].setOpaque(true);
			area[i].setForeground(Color.WHITE);
			area[i].setFont(new Font("Arial", Font.BOLD, 46));
		}

		//using gridbaglayout to make it 3x3
		board.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 70;
		c.ipady = 20;
		c.fill = GridBagConstraints.HORIZONTAL;

		//fill in the gridbag one by one
		int k = 0;
		for(int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				c.gridx = j;
				c.gridy = i;
				board.add(area[k],c);
				k++;
			}
		}
		panels[1].add(board);
	}

	//setting up the name submitting panel
	private void setSubmitPanel() {
		btn_name = new JButton("Submit");
		name = new JTextField(15);
		
		panels[2].add(name);
		panels[2].add(btn_name);
	}

	/*
	 * This function removes all the listeners(listener on each button) on the game panel
	 */
	public void removeAllListeners(){
		for(int i =0; i < area.length; i++){
			ActionListener[] list = area[i].getActionListeners();
			if(list.length > 0)
				area[i].removeActionListener(list[0]);
		}
	}

	// this function displays draw message
	public void drawMessage() {
		JOptionPane.showMessageDialog(this.frame, "Draw.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	// this function displays message when one of the player leaves
	public void leftMessage() {
		JOptionPane.showMessageDialog(this.frame, "Game Ends. One of the players left.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	// this function displays win message
	public void winMessage() {
		JOptionPane.showMessageDialog(this.frame, "Congratulations. You Win.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	// this function displays lose message
	public void loseMessage() {
		JOptionPane.showMessageDialog(this.frame, "You lose.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	//get methods for name, button and other labels
	public JTextField getName(){
		return name;
	}

	public JButton getSubmit(){
		return btn_name;
	}

	public JFrame getFrame(){
		return frame;
	}

	public JLabel getInfo(){
		return info;
	}

	//get method for the buttons (field)
	public JButton[] getArea(){
		return area;
	}

}
