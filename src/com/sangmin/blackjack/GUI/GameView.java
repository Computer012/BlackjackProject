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
		
		JFrame frame = new JFrame("Black jack");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		
		JButton hitButton = new JButton(); // Ä«µå ´õ ¹Þ±â
		hitButton.setFont(new Font("¸¼Àº °íµñ", 1, 30));
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
				if (client.getPlayerPointSum() >= 21) {
					client.turnOff();
					System.out.println("21ÀÌ ³Ñ¾úÀ¸¹Ç·Î ºÒ°¡!");
				}
				client.cardRequest();
			}
		});
		JButton stayButton = new JButton(); // Ä«µå ±×¸¸ ¹Þ±â
		stayButton.setFont(new Font("¸¼Àº °íµñ", 1, 30));
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
				client.turnOff();
				client.cardRequest();
			}
		});
		
		JButton exitButton = new JButton(); // Á¾·á
		exitButton.setBounds(20, 20, 50, 30);
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		exitButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setExitButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setExitButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setExitButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setExitButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		JButton menuButton = new JButton("Menu"); // ¸Þ´º·Î
		menuButton.setFont(new Font("¸¼Àº °íµñ", 1, 30));
		menuButton.setBounds(20, 58, 70, 30);
		menuButton.setContentAreaFilled(false);
		menuButton.setBorderPainted(false);
		menuButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setMenuButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setMenuButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setMenuButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setMenuButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new MenuView(client.getName());
			}
		});
		
		JButton replayButton = new JButton("Again"); // ´Ù½Ã ÇÏ±â
		replayButton.setFont(new Font("¸¼Àº °íµñ", 1, 30));
		replayButton.setBounds(20, 95, 90, 30);
		replayButton.setContentAreaFilled(false);
		replayButton.setBorderPainted(false);
		replayButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setReplayButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setReplayButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setReplayButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setReplayButtonEnterImg();
				panel.repaint();
			}
			
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
		panel.add(replayButton);
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
	
	// ±×·ÁÁÖ´Â Panel
	@SuppressWarnings("serial")
	class DrawPanel extends JPanel {
		Toolkit toolkit = null;

		Image backGroundImg = null;
		Image hitButtonImg = null;
		Image stayButtonImg = null;
		Image cardImg = null;
		Image backCardImg = null;
		Image dealerCardImg = null;
		Image exitButtonImg = null;
		Image menuButtonImg = null;
		Image replayButtonImg = null;
		
		
		boolean gameEnd = false;
		boolean background = false;
		int playerScore = 0;
		int dealerScore = 0;
		String winner = "";
		
		public DrawPanel() {
			toolkit = getToolkit();
			backGroundImg = toolkit.getImage(".\\img\\layout\\Play_Table.png");
			backCardImg = toolkit.getImage(".\\img\\cards\\Card_Back.png");
			hitButtonImg = toolkit.getImage(".\\img\\buttons\\HitBase.png");
			stayButtonImg = toolkit.getImage(".\\img\\buttons\\StayBase.png");
			exitButtonImg = toolkit.getImage(".\\img\\buttons\\ExitBase.png");
			menuButtonImg = toolkit.getImage(".\\img\\buttons\\MenuBase.png");
			replayButtonImg = toolkit.getImage(".\\img\\buttons\\ReplayBase.png");
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
			hitButtonImg = toolkit.getImage(".\\img\\buttons\\HitEnter.png");
		}
		
		public void setHitButtonBaseImg() {
			hitButtonImg = toolkit.getImage(".\\img\\buttons\\HitBase.png");
		}

		public void setHitButtonPressImg() {
			hitButtonImg = toolkit.getImage(".\\img\\buttons\\HitPress.png");
		}

		public void setStayButtonEnterImg() {
			stayButtonImg = toolkit.getImage(".\\img\\buttons\\StayEnter.png");
		}
		
		public void setStayButtonBaseImg() {
			stayButtonImg = toolkit.getImage(".\\img\\buttons\\StayBase.png");
		}
		
		public void setStayButtonPressImg() {
			stayButtonImg = toolkit.getImage(".\\img\\buttons\\StayPress.png");
		}
		
		public void setExitButtonEnterImg() {
			exitButtonImg = toolkit.getImage(".\\img\\buttons\\ExitEnter.png");
		}
		
		public void setExitButtonBaseImg() {
			exitButtonImg = toolkit.getImage(".\\img\\buttons\\ExitBase.png");
		}
		
		public void setExitButtonPressImg() {
			exitButtonImg = toolkit.getImage(".\\img\\buttons\\ExitPress.png");
		}
		
		public void setReplayButtonEnterImg() {
			replayButtonImg = toolkit.getImage(".\\img\\buttons\\ReplayEnter.png");
		}
		
		public void setReplayButtonBaseImg() {
			replayButtonImg = toolkit.getImage(".\\img\\buttons\\ReplayBase.png");
		}

		public void setReplayButtonPressImg() {
			replayButtonImg = toolkit.getImage(".\\img\\buttons\\ReplayPress.png");
		}

		public void setMenuButtonEnterImg() {
			menuButtonImg = toolkit.getImage(".\\img\\buttons\\MenuEnter.png");
		}
		
		public void setMenuButtonBaseImg() {
			menuButtonImg = toolkit.getImage(".\\img\\buttons\\MenuBase.png");
		}
		
		public void setMenuButtonPressImg() {
			menuButtonImg = toolkit.getImage(".\\img\\buttons\\MenuPress.png");
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

			if (exitButtonImg != null)
				g.drawImage(exitButtonImg, 5, 10, 100, 60, this);
			if (menuButtonImg != null)
				g.drawImage(menuButtonImg, 5, 45, 100, 60, this);
			if (replayButtonImg != null)
				g.drawImage(replayButtonImg, 5, 80, 110, 60, this);
			
			if (backCardImg != null)
				g.drawImage(backCardImg, 500, 50, 210, 300, this);
			if (!dealerCards.isEmpty()) {
				int i = 0;
				for(String dealerCard : dealerCards) {
					dealerCardImg = toolkit.getImage(".\\img\\cards\\" + dealerCard + ".png");
					if (dealerCardImg != null)
						g.drawImage(dealerCardImg, 410+(90*i), 50, 210, 300, this);
					i++;
				}
			}
			
			if (!cards.isEmpty()) {
				int i = 0;
				for (String card : cards) {
					cardImg = toolkit.getImage(".\\img\\cards\\" + card + ".png");
					if (cardImg != null)
						g.drawImage(cardImg, 400+(90)*i, 450, 210, 300, this);
					i++;
				}
			}
			
			g.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(dealerScore), 255, 165);
			
			g.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 30));
			g.drawString("Score : " + String.valueOf(playerScore), 255, 745);
			
			
			if (!winner.equals("")) {
				g.setColor(Color.black);
				g.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 100));				
				if (winner.equals("Dealer"))
					g.drawString("YOU LOSE!", 400, 435);
				else if (winner.equals("¹«½ÂºÎ"))
					g.drawString(" " + winner + "!!", 400, 435);
				else
					g.drawString(winner + ", WIN!", 400, 435);
			}
//			System.out.println("Paint method È£Ãâ");
		}
	}
}
