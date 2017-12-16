package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SingUpSuccessedView {

	JFrame preFrame = null;
	public static void main(String[] args) {
		new SingUpSuccessedView(new JFrame());		
	}

	public SingUpSuccessedView(JFrame preFrame) {
		this.preFrame = preFrame;
		
		JFrame frame = new JFrame();
		frame.setLocation(430, 300);
		frame.setPreferredSize(new Dimension(400, 300));

		Container contentPane = frame.getContentPane();
		contentPane.setBackground(new Color(0, 120, 215));
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("회원가입이 완료되었습니다.");
		label.setFont(new Font("맑은 고딕", 1, 25));
		label.setForeground(Color.black);
		label.setBounds(30, 50, 350, 100);
		JButton button = new JButton("로그인 창으로");
		button.setBounds(130, 150, 120, 50);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				preFrame.dispose();
			}
		});
		
		contentPane.add(label);
		contentPane.add(button);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}
