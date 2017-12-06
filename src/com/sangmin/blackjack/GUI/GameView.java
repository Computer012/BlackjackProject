package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.util.Stack;

import javax.swing.*;

public class GameView {

	public static void main(String[] args) {
		new GameView();
	}

	private Stack<String> cards = new Stack<String>();
	private DrawPanel panel = null;
	public GameView() {
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
		Image backGroundImg = null, cardImg = null, backCardImg = null;
		Toolkit toolkit = getToolkit();

		public DrawPanel() {
			backGroundImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Play_Table.png");
			backCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Card_Back.png");
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);
			
			if (!cards.isEmpty()) {
				int i = 0;
				for (String card : cards) {
					if (backCardImg != null)
						g.drawImage(backCardImg, 395+(210)*i, 50, 200, 300, this);
					cardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + card + ".png");
					if (cardImg != null)
						g.drawImage(cardImg, 395+(210)*i, 450, 200, 300, this);
					i++;
				}
			}
			System.out.println("Paint method 호출");
		}
	}
}
