package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.util.Stack;

import javax.swing.*;

public class GameWaitingView {

	public static void main(String[] args) {
		new GameWaitingView();
	}

	private Stack<String> cards = new Stack<String>();
	private DrawPanel panel = null;
	public GameWaitingView() {
		JFrame frame = new JFrame("Waiting Room");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		
		panel = new DrawPanel();
		contentPane.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addCard(String card) {
		cards.add(card);
		panel.repaint();
	}
	
	// 그려주는 Panel
	@SuppressWarnings("serial")
	class DrawPanel extends JPanel {
		Image backGroundImg = null, cardImg = null;
		Toolkit toolkit = getToolkit();

		public DrawPanel() {
			backGroundImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Play_Table.png");
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);

			if (!cards.isEmpty()) {
				int i = 0;
				for (String card : cards) {
					cardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + card + ".png");
					g.drawImage(cardImg, 500 + (200)*i++, 500, 180, 250, this);
				}
			}
			System.out.println("Paint method 호출");
		}
	}
}
