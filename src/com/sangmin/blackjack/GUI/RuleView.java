package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class RuleView {

	public static void main(String[] args) {
		new RuleView();
	}
	
	public RuleView() {
		JFrame frame = new JFrame("How to play game?");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		DrawPanel panel = new DrawPanel();
		panel.setLayout(null);
		
		JButton menuButton = new JButton(); // Á¾·á
		menuButton.setBounds(940, 630, 200, 70);
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
			}
		});
		
		panel.add(menuButton);
		contentPane.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	class DrawPanel extends JPanel {
		Toolkit toolkit = null;
		Image backGroundImg = null;
		Image logo = null;
		Image menuButton = null;
		Image ruleContent = null;
		
		public DrawPanel() {
			toolkit = getToolkit();
			backGroundImg = toolkit.getImage(".\\img\\layout\\Menu_Table.png");
			menuButton = toolkit.getImage(".\\img\\buttons\\MenuBase.png");
			ruleContent = toolkit.getImage(".\\img\\layout\\RuleContent.png");
		}
		
		public void setMenuButtonEnterImg() {
			menuButton = toolkit.getImage(".\\img\\buttons\\MenuEnter.png");
		}
		
		public void setMenuButtonBaseImg() {
			menuButton = toolkit.getImage(".\\img\\buttons\\MenuBase.png");
		}
		
		public void setMenuButtonPressImg() {
			menuButton = toolkit.getImage(".\\img\\buttons\\MenuPress.png");
		}


		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			if (backGroundImg != null)
				g.drawImage(backGroundImg, 0, 0, 1200, 800, this);
			if (menuButton != null)
				g.drawImage(menuButton, 900, 600, 280, 150, this);
			if (ruleContent != null)
				g.drawImage(ruleContent, 100, 40, 1020, 680, this);
		}
	}
}
