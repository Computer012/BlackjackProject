package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import com.sangmin.blackjack.Control.ClientBackground;

public class MenuView {
	
	public static void main(String [] args) {
		new MenuView("TestId");
	}

	public MenuView(String id) {
		String _id = id;
		
		JFrame frame = new JFrame("Main Menu");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		DrawPanel panel = new DrawPanel();
		panel.setLayout(null);
		
		JButton startButton = new JButton();
		startButton.setBounds(520, 435, 150, 70);
		startButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		startButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setPlayButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setPlayButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setPlayButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setPlayButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new ClientBackground(_id);
				frame.dispose();
			}
		});
		
		JButton ruleButton = new JButton();
		ruleButton.setBounds(520, 535, 150, 70);
		ruleButton.setContentAreaFilled(false);
		ruleButton.setBorderPainted(false);
		ruleButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				panel.setRuleButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				panel.setRuleButtonPressImg();
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel.setRuleButtonBaseImg();
				panel.repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel.setRuleButtonEnterImg();
				panel.repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new RuleView();
			}
		});
		
		JButton exitButton = new JButton();
		exitButton.setBounds(520, 635, 150, 70);
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
		
		panel.add(startButton);
		panel.add(ruleButton);
		panel.add(exitButton);
		
		contentPane.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	class DrawPanel extends JPanel {
		Toolkit toolkit = null;
		Image backGroundImg = null;
		Image logo = null;
		Image playButton = null;
		Image ruleButton = null;
		Image exitButton = null;
		
		public DrawPanel() {
			toolkit = getToolkit();
			backGroundImg = toolkit.getImage(".\\img\\layout\\Menu_Table.png");
			logo = toolkit.getImage(".\\img\\layout\\Logo.png");
			playButton = toolkit.getImage(".\\img\\buttons\\PlayBase.png");
			ruleButton = toolkit.getImage(".\\img\\buttons\\RuleBase.png");
			exitButton = toolkit.getImage(".\\img\\buttons\\ExitBase.png");
		}
		
		public void setPlayButtonEnterImg() {
			playButton = toolkit.getImage(".\\img\\buttons\\PlayEnter.png");
		}
		
		public void setPlayButtonBaseImg() {
			playButton = toolkit.getImage(".\\img\\buttons\\PlayBase.png");
		}

		public void setPlayButtonPressImg() {
			playButton = toolkit.getImage(".\\img\\buttons\\PlayPress.png");
		}

		public void setRuleButtonEnterImg() {
			ruleButton = toolkit.getImage(".\\img\\buttons\\RuleEnter.png");
		}
		
		public void setRuleButtonBaseImg() {
			ruleButton = toolkit.getImage(".\\img\\buttons\\RuleBase.png");
		}
		
		public void setRuleButtonPressImg() {
			ruleButton = toolkit.getImage(".\\img\\buttons\\RulePress.png");
		}
		
		public void setExitButtonEnterImg() {
			exitButton = toolkit.getImage(".\\img\\buttons\\ExitEnter.png");
		}
		
		public void setExitButtonBaseImg() {
			exitButton = toolkit.getImage(".\\img\\buttons\\ExitBase.png");
		}
		
		public void setExitButtonPressImg() {
			exitButton = toolkit.getImage(".\\img\\buttons\\ExitPress.png");
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);
			if (logo != null)
				g.drawImage(logo, 450, 50, 300, 300, this);
			
			if (playButton != null)
				g.drawImage(playButton, 480, 400, 300, 150, this);
			if (ruleButton != null)
				g.drawImage(ruleButton, 480, 500, 300, 150, this);
			if (exitButton != null)
				g.drawImage(exitButton, 480, 600, 280, 150, this);
		}
	}
}
