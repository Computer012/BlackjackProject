package com.sangmin.blackjack.GUI;

import java.awt.*;
import javax.swing.*;

public class GameWaitingView {

	public static void main(String[] args) {
		new GameWaitingView();
	}

	public GameWaitingView() {
		JFrame frame = new JFrame("Waiting Room");
		frame.setLocation(100, 30);
		frame.setPreferredSize(new Dimension(1218, 847));
		
		Container contentPane = frame.getContentPane();
		
		DrawPanel panel = new DrawPanel();
		contentPane.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

class DrawPanel extends JPanel {
	Image image = null;
	Toolkit toolkit = getToolkit();

	public DrawPanel() {
		image = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Card\\Play_Table.png");
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if (image != null)
			g.drawImage(image, 0, 0, 1200, 800, this);

		System.out.println("Paint method »£√‚");
	}
}