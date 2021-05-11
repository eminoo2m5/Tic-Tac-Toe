/*
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class View {

	private JFrame frame;
	private JPanel[] panels;

	private JButton btn_name;
	private JTextField name;

	private JLabel info;
	private JPanel board;
	private JButton[] area;

	//private JLabel board;
	//private JLabel circle;
	//private JLabel x;


	public View() {
		setFrame();
		setInfoPanel();
		setDisplayPanel();
		setControlPanel();
	}

	private void setFrame() {
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 350);
		frame.setVisible(true);

		Container cp = frame.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));

		panels = new JPanel[3];
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new JPanel();
			cp.add(panels[i]);
		}

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
				JOptionPane.showMessageDialog(null, "blah blah blah", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu2.add(instruction);
		menuBar.add(menu);
		menuBar.add(menu2);
		frame.setJMenuBar(menuBar);
	}
	
	private void setInfoPanel(){
		info = new JLabel("Enter your Player name...");
		//info.setHorizontalAlignment(SwingConstraints.LEFT);
		panels[0].add(info);
	}

	private void setDisplayPanel() {
		board = new JPanel();
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

		for (int i = 0; i < area.length; i++) {
			area[i].setContentAreaFilled(true);
			area[i].setFocusPainted(false);
			area[i].setBorder(new LineBorder(Color.BLACK));
			area[i].setBackground(Color.WHITE);
			area[i].setOpaque(true);
			area[i].setForeground(Color.WHITE);
			area[i].setFont(new Font("Arial", Font.BOLD, 46));
		}

		board.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 70;
		c.ipady = 20;
		c.fill = GridBagConstraints.HORIZONTAL;

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

	private void setControlPanel() {

		btn_name = new JButton("Submit");
		name = new JTextField(15);
		
		panels[2].add(name);
		panels[2].add(btn_name);
	}

	public void drawMessage() {
		JOptionPane.showMessageDialog(null, "Draw.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	public void winMessage() {
		JOptionPane.showMessageDialog(null, "Congratulations. You Win.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	public void loseMessage() {
		JOptionPane.showMessageDialog(null, "You lose.", "Message", JOptionPane.INFORMATION_MESSAGE);
	}

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

	public JButton[] getArea(){
		return area;
	}

}
