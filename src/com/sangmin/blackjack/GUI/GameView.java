package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.*;

import com.sangmin.blackjack.Control.ClientBackground;

public class GameView {

	private Stack<String> dealerCards = new Stack<String>();
	private Stack<String> cards = new Stack<String>();
	private DrawPanel panel = null;
	private ClientBackground client = null;
	
	public GameView(ClientBackground client) {
		this.client = client;
		
		JFrame frame = new JFrame("Waiting Room");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		
		JButton hitButton = new JButton(); // 카드 더 받기
		hitButton.setFont(new Font("맑은 고딕", 1, 30));
		hitButton.setBounds(240, 355, 165, 110);
		hitButton.setContentAreaFilled(false);
		hitButton.setBorderPainted(false);
		hitButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setHitButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setHitButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setHitButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setHitButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// client측에서 turn을 true로 해서 server에다가 보내도록 요청하는 코드를 작성해야
				if (client.getPlayerPointSum() >= 21) {
					client.turnOff();
					System.out.println("21이 넘었으므로 불가!");
				}
				client.cardRequest();
			}
		});
		JButton stayButton = new JButton(); // 카드 그만 받기
		stayButton.setFont(new Font("맑은 고딕", 1, 30));
		stayButton.setBounds(240, 508, 165, 110);
		stayButton.setContentAreaFilled(false);
		stayButton.setBorderPainted(false);
		stayButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setStayButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setStayButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setStayButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setStayButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// client측에서 turn을 false로 해서 server에다가 보내도록 요청하는 코드를 작성해야
				client.turnOff();
				client.cardRequest();
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
		setPlayerPoint(client.getPlayerPointSum());
		panel.repaint();
	}
	
	public void addDealerCard(String card) {
		dealerCards.add(card);
		setDealerPoint(client.getDealerPointSum());
		panel.repaint();
	}
	
	public void setPlayerPoint(int score) {
		panel.setPlayerScore(score);
	}
	
	public void setDealerPoint(int score) {
		panel.setDealerScore(score);
	}
	
	// 그려주는 Panel
	@SuppressWarnings("serial")
	class DrawPanel extends JPanel {
		Image hitButtonImg = null;
		Image stayButtonImg = null;
		Image backGroundImg = null;
		Image cardImg = null;
		Image backCardImg = null;
		Image dealerCardImg = null;
		Toolkit toolkit = null;
		
		boolean gameEnd = false;
		boolean background = false;
		int playerScore = 0;
		int dealerScore = 0;
		
		public DrawPanel() {
			toolkit = getToolkit();
			backGroundImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\Play_Table.png");
			backCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Card_Back.png");
			hitButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\HitBase.png");
			stayButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\StayBase.png");
		}
		
		public void setPlayerScore(int playerScore) {
			this.playerScore = playerScore;
		}
		
		public void setDealerScore(int dealerScore) {
			this.dealerScore = dealerScore;
		}
		
		public void setHitButtonEnterImg() {
			hitButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\HitEnter.png");
		}
		
		public void setHitButtonBaseImg() {
			hitButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\HitBase.png");
		}

		public void setHitButtonPressImg() {
			hitButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\HitPress.png");
		}

		public void setStayButtonEnterImg() {
			stayButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\StayEnter.png");
		}
		
		public void setStayButtonBaseImg() {
			stayButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\StayBase.png");
		}
		
		public void setStayButtonPressImg() {
			stayButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\StayPress.png");
		}
		
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null && !background)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);
			if (hitButtonImg != null)
				g.drawImage(hitButtonImg, 190, 340, 220, 150, this);
			if (stayButtonImg != null)
				g.drawImage(stayButtonImg, 180, 470, 240, 165, this);
			
			if (backCardImg != null)
				g.drawImage(backCardImg, 550, 50, 210, 300, this);
			if (!dealerCards.isEmpty()) {
				int i = 0;
				for(String dealerCard : dealerCards) {
					dealerCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + dealerCard + ".png");
					if (dealerCardImg != null)
						g.drawImage(dealerCardImg, 460+(90*i), 50, 210, 300, this);
					i++;
				}
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
			
			g.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(dealerScore), 295, 175);
		
			g.setFont(new Font("맑은 고딕", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(playerScore), 295, 745);

			System.out.println("Paint method 호출");
		}
	}
}
