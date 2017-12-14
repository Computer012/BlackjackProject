package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.*;

import com.sangmin.blackjack.Control.ClientBackground;

public class GameView {

//	public static void main(String[] args) {
//		new GameView();
//	}
	
	private Stack<String> dealerCards = new Stack<String>();
	private Stack<String> cards = new Stack<String>();
	private DrawPanel panel = null;
	
	public GameView(ClientBackground client) {
		JFrame frame = new JFrame("Waiting Room");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		
		JButton hitButton = new JButton("HIT"); // ī�� �� �ޱ�
		hitButton.setFont(new Font("���� ���", 1, 30));
		hitButton.setBounds(250, 480, 150, 100);
		hitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// client������ turn�� true�� �ؼ� server���ٰ� �������� ��û�ϴ� �ڵ带 �ۼ��ؾ�
				if (client.getPointSum() >= 21) {
					client.turnOff();
					System.out.println("21�� �Ѿ����Ƿ� �Ұ�!");
				}
				client.cardRequest();
			}
		});
		JButton stayButton = new JButton("STAY"); // ī�� �׸� �ޱ�
		stayButton.setFont(new Font("���� ���", 1, 30));
		stayButton.setBounds(250, 620, 150, 100);
		stayButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// client������ turn�� false�� �ؼ� server���ٰ� �������� ��û�ϴ� �ڵ带 �ۼ��ؾ�
				client.turnOff();
				client.cardRequest();
				showDealerCard();
			}
		});
		
		panel = new DrawPanel();
		panel.setLayout(null);
		panel.add(hitButton);
		panel.add(stayButton);
		contentPane.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void addCard(String card) {
		cards.add(card);
		panel.repaint();
	}
	
	public void addDealerCard(String card) {
		dealerCards.add(card);
		panel.repaint();
	}
	
	private void showDealerCard() {
		
	}
	
	// �׷��ִ� Panel
	@SuppressWarnings("serial")
	class DrawPanel extends JPanel {
		Image backGroundImg = null;
		Image cardImg = null;
		Image backCardImg = null;
		Image dealerCardImg = null;
		Toolkit toolkit = getToolkit();
		boolean gameEnd = false;

		public DrawPanel() {
			backGroundImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Play_Table.png");
			backCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Card_Back.png");
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);
			if (backCardImg != null)
				g.drawImage(backCardImg, 550, 50, 210, 300, this);
			if (!dealerCards.isEmpty()) {
				dealerCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + dealerCards.get(0) + ".png");
				if (dealerCardImg != null)
					g.drawImage(dealerCardImg, 460, 50, 210, 300, this);
				
			}
			
			if (!cards.isEmpty()) {
				int i = 0;
				for (String card : cards) {
					cardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + card + ".png");
					if (cardImg != null)
						g.drawImage(cardImg, 450+(90)*i, 450, 210, 300, this);
					i++;
				}
			}
			System.out.println("Paint method ȣ��");
		}
	}
}
