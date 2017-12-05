package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.Border;

public class LoginView {

	public static void main(String[] args) {
		new LoginView();
	}

	public LoginView() {

		JFrame frame = new JFrame();
		frame.setLocation(1400, 150);
		frame.setPreferredSize(new Dimension(450, 750));

		Container contentPane = frame.getContentPane();

		LogoPanel logoPanel = new LogoPanel();
		logoPanel.setPreferredSize(new Dimension(440, 380));
		logoPanel.setBackground(new Color(0, 120, 215));

		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(440, 100));
		textPanel.setBackground(new Color(0, 120, 215));
		textPanel.setLayout(new FlowLayout());

		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
		Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);
		JTextArea idArea = new JTextArea("ID");
		idArea.setPreferredSize(new Dimension(300, 40));
		idArea.setFont(new Font("맑은 고딕", 0, 20));
		idArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JPasswordField passArea = new JPasswordField("Password");
		passArea.setPreferredSize(new Dimension(300, 40));
		passArea.setFont(new Font("맑은 고딕", 0, 20));
		passArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		textPanel.add(idArea);
		textPanel.add(passArea);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(440, 210));
		buttonPanel.setBackground(new Color(0, 120, 215));
		JLabel label = new JLabel("");
		label.setFont(new Font("맑은 고딕", 0, 15));
		label.setForeground(Color.RED);
		JButton button1 = new JButton("로그인");
		button1.setPreferredSize(new Dimension(100, 40));
		button1.addActionListener(new LoginActionListener(idArea, passArea, label, frame));
		JButton button2 = new JButton("회원가입");
		button2.setPreferredSize(new Dimension(100, 40));
		button2.addActionListener(new SignUpActionListener());

		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(label);

		contentPane.add(logoPanel, BorderLayout.NORTH);
		contentPane.add(textPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.setBackground(Color.BLACK);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}

/********** 로고 패널 **********/
@SuppressWarnings("serial")
class LogoPanel extends JPanel {
	Image image = null;
	Toolkit toolkit = getToolkit();

	public LogoPanel() {
		image = toolkit.getImage("C:\\Users\\User\\Desktop\\java project\\image\\Layout\\Logo.png");
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (image != null)
			g.drawImage(image, 90, 80, 250, 250, this);
	}
}

/********** 로그인 버튼 Action Listener **********/
class LoginActionListener implements ActionListener {
	JTextArea idArea;
	JPasswordField passArea;
	JLabel label;
	Connection conn = null;
	Frame frame = null;

	public LoginActionListener(JTextArea idArea, JPasswordField passArea, JLabel label, Frame frame) {
		this.idArea = idArea;
		this.passArea = passArea;
		this.label = label;
		this.frame = frame;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3305/info_db?useSSL=false", "root", "345211");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String baseQuery = "SELECT id, pass from custinfo ";
		Statement stmt = null;
		System.out.println(String.valueOf(passArea.getPassword()));
		try {
			stmt = conn.createStatement();
			String whereClause = "";

			if (idArea.getText().equals("") | passArea.getPassword().equals("")) {
				label.setText("ID 또는 Password를 입력해주세요.");
				passArea.setText("");
			} else {
				whereClause = "WHERE id='" + idArea.getText() + "' and pass='" + String.valueOf(passArea.getPassword()) + "';";

				String query = baseQuery + whereClause;
				System.out.println(query);
				ResultSet rs = stmt.executeQuery(query);

				if (rs.next()) {
					do {
						System.out.println(rs.getString("id"));
						System.out.println(rs.getString("pass"));
					} while ((rs.next()));
					new GameWaitingView();
					frame.dispose();
				} else {
					label.setText("아이디 또는 비밀번호를 잘못 입력하셨습니다.");
					passArea.setText("");
					System.out.println("잘못 입력했어");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

}

/********** 회원가입 버튼 Action Listener **********/
class SignUpActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		new SignUpView();
	}

}