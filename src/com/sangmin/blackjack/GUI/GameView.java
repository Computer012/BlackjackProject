package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;

import javax.swing.*;

import com.sangmin.blackjack.Construction.Rule;
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
		
		JButton hitButton = new JButton(); // ī�� �� �ޱ�
		hitButton.setFont(new Font("���� ���", 1, 30));
		hitButton.setBounds(190, 355, 165, 110);
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
				// client������ turn�� true�� �ؼ� server���ٰ� �������� ��û�ϴ� �ڵ带 �ۼ��ؾ�
				if (client.getPlayerPointSum() >= 21) {
					client.turnOff();
					System.out.println("21�� �Ѿ����Ƿ� �Ұ�!");
				}
				client.cardRequest();
			}
		});
		JButton stayButton = new JButton(); // ī�� �׸� �ޱ�
		stayButton.setFont(new Font("���� ���", 1, 30));
		stayButton.setBounds(190, 508, 165, 110);
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
				// client������ turn�� false�� �ؼ� server���ٰ� �������� ��û�ϴ� �ڵ带 �ۼ��ؾ�
				client.turnOff();
				client.cardRequest();
			}
		});
		
		JButton exitButton = new JButton("Exit"); // �޴���
		exitButton.setFont(new Font("���� ���", 1, 30));
		exitButton.setBounds(30, 28, 120, 50);
//		exitButton.setContentAreaFilled(false);
//		exitButton.setBorderPainted(false);
		exitButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		JButton menuButton = new JButton("Menu"); // �޴���
		menuButton.setFont(new Font("���� ���", 1, 30));
		menuButton.setBounds(30, 88, 120, 50);
//		menuButton.setContentAreaFilled(false);
//		menuButton.setBorderPainted(false);
		menuButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				//new MenuView();
			}
		});
		
		JButton againButton = new JButton("Again"); // �ٽ� �ϱ�
		againButton.setFont(new Font("���� ���", 1, 30));
		againButton.setBounds(1000, 55, 165, 110);
//		againButton.setContentAreaFilled(false);
//		againButton.setBorderPainted(false);
		againButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) { }
			
			@Override
			public void mousePressed(MouseEvent e) { }
			
			@Override
			public void mouseExited(MouseEvent e) { }
			
			@Override
			public void mouseEntered(MouseEvent e) { }
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new ClientBackground(client.getName());
			}
		});
		
		panel = new DrawPanel();
		panel.setLayout(null);
		
		panel.add(hitButton);
		panel.add(stayButton);
		panel.add(exitButton);
		panel.add(menuButton);
		panel.add(againButton);
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
	
	public void setWinner(String id) {
		panel.setWinnerName(id);
	}
	
	// �׷��ִ� Panel
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
		String winner = "";
		
		public DrawPanel() {
			toolkit = getToolkit();
			backGroundImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\Play_Table.png");
			backCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Card_Back.png");
			hitButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\HitBase.png");
			stayButtonImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\layout\\StayBase.png");
		}
		
		public void setWinnerName(String winner) {
			this.winner = winner;
			repaint();
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
				g.drawImage(hitButtonImg, 140, 340, 220, 150, this);
			if (stayButtonImg != null)
				g.drawImage(stayButtonImg, 130, 470, 240, 165, this);
			
			if (backCardImg != null)
				g.drawImage(backCardImg, 500, 50, 210, 300, this);
			if (!dealerCards.isEmpty()) {
				int i = 0;
				for(String dealerCard : dealerCards) {
					dealerCardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + dealerCard + ".png");
					if (dealerCardImg != null)
						g.drawImage(dealerCardImg, 410+(90*i), 50, 210, 300, this);
					i++;
				}
			}
			
			if (!cards.isEmpty()) {
				int i = 0;
				for (String card : cards) {
					cardImg = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\" + card + ".png");
					if (cardImg != null)
						g.drawImage(cardImg, 400+(90)*i, 450, 210, 300, this);
					i++;
				}
			}
			
			g.setFont(new Font("���� ���", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(dealerScore), 255, 165);
			
			g.setFont(new Font("���� ���", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(playerScore), 255, 745);
			
			
			if (!winner.equals("")) {
				g.setColor(Color.black);
				g.setFont(new Font("���� ���", Font.BOLD, 100));				
				if (winner.equals("Dealer"))
					g.drawString("YOU LOSE!", 400, 435);
				else if (winner.equals("���º�"))
					g.drawString(" " + winner + "!!", 400, 435);
				else
					g.drawString(winner + ", WIN!", 400, 435);
			}

//			System.out.println("Paint method ȣ��");
		}
	}
}
