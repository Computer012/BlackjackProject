package com.sangmin.blackjack.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import java.sql.*;

public class SignUpView {

	public static void main(String[] args) {
		new SignUpView();
	}
	
	public SignUpView() {
		JFrame frame = new JFrame();
		frame.setLocation(400, 150);
		frame.setPreferredSize(new Dimension(450, 750));

		Container contentPane = frame.getContentPane();
		
		JPanel infoPanel = new JPanel();
		infoPanel.setPreferredSize(new Dimension(440, 150));
		infoPanel.setBackground(new Color(0, 120, 215));
		infoPanel.setLayout(null);
		JLabel infoText = new JLabel("È¸¿ø °¡ÀÔ");
		infoText.setFont(new Font("¸¼Àº °íµñ", 1, 35));
		infoText.setForeground(Color.black);
		infoText.setBounds(140, 50, 200, 80);
		infoPanel.add(infoText);
		
		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize(new Dimension(440, 400));
		textPanel.setBackground(new Color(0, 120, 215));
		textPanel.setLayout(new FlowLayout());
		Border lineBorder = BorderFactory.createLineBorder(Color.black, 1);
		Border emptyBorder = BorderFactory.createEmptyBorder(7, 7, 7, 7);

		JTextArea nameArea = new JTextArea("Name");
		nameArea.setPreferredSize(new Dimension(350, 40));
		nameArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		nameArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JTextArea birthArea = new JTextArea("Birth");
		birthArea.setPreferredSize(new Dimension(350, 40));
		birthArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		birthArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JTextArea idArea = new JTextArea("ID");
		idArea.setPreferredSize(new Dimension(350, 40));
		idArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		idArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JTextArea passArea = new JTextArea("Password");
		passArea.setPreferredSize(new Dimension(350, 40));
		passArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		passArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JTextArea phoneArea = new JTextArea("Phone number");
		phoneArea.setPreferredSize(new Dimension(350, 40));
		phoneArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		phoneArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		JTextArea emailArea = new JTextArea("e-mail");
		emailArea.setPreferredSize(new Dimension(350, 40));
		emailArea.setFont(new Font("¸¼Àº °íµñ", 0, 20));
		emailArea.setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
		
		textPanel.add(nameArea);
		textPanel.add(birthArea);
		textPanel.add(idArea);
		textPanel.add(passArea);
		textPanel.add(phoneArea);
		textPanel.add(emailArea);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(440, 260));
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(new Color(0, 120, 215));
		JLabel label = new JLabel("");
		label.setFont(new Font("¸¼Àº °íµñ", 0, 15));
		label.setForeground(Color.RED);
		JButton button = new JButton("È¸¿ø °¡ÀÔ");
		button.setPreferredSize(new Dimension(150, 50));
		button.addActionListener(new InsertInfoActionListener(nameArea, birthArea, idArea, passArea, phoneArea, emailArea, label, frame));
		buttonPanel.add(button);
		buttonPanel.add(label);

		contentPane.add(infoPanel, BorderLayout.NORTH);
		contentPane.add(textPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.setBackground(Color.BLACK);

		frame.pack();
		frame.setVisible(true);
	}
}

class InsertInfoActionListener implements ActionListener {
	JTextArea nameArea;
	JTextArea birthArea;
	JTextArea idArea;
	JTextArea passArea;
	JTextArea phoneArea;
	JTextArea emailArea;
	JLabel label;
	
	Connection conn = null;
	JFrame frame;
	
	public InsertInfoActionListener(JTextArea nameArea, JTextArea birthArea, JTextArea idArea, JTextArea passArea,
			JTextArea phoneArea, JTextArea emailArea, JLabel label, JFrame frame) {
		this.nameArea = nameArea;
		this.birthArea = birthArea;
		this.idArea = idArea;
		this.passArea = passArea;
		this.phoneArea = phoneArea;
		this.emailArea = emailArea;
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
	public void actionPerformed(ActionEvent arg0) {
		String baseQuery = "INSERT into custinfo (name, birth, id, pass, phone, email) values (";
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String whereClause = "";
			if (nameArea.getText().equals("") || birthArea.getText().equals("") || idArea.getText().equals("") || passArea.getText().equals("")) {
				label.setText("Á¤º¸¸¦ ºüÁü ¾øÀÌ ÀÔ·ÂÇØ ÁÖ¼¼¿ä. (Name, Birth, Id, Password)");
			} else {
				whereClause = "'" + nameArea.getText() + "', '" + birthArea.getText() + "', '" + idArea.getText() + "', '"
						+ passArea.getText() + "', '" + phoneArea.getText() + "', '"  + emailArea.getText() + "');";
				String query = baseQuery + whereClause;
				System.out.println(query);
				
				int n = stmt.executeUpdate(query);
				System.out.println(n + "row affected");
				label.setText("");
				new SingUpSuccessedView(frame);
			}
		} catch (SQLIntegrityConstraintViolationException e1) {
			System.out.println("Integer¸¦ ³Ö¾î¾ß ÇØ¿ä!!! e1");
		} catch (SQLInvalidAuthorizationSpecException e2) {
			System.out.println("Invalid ¾îÂ¼±¸ ÀÍ¼Á¼Ç!!! e2");
		} catch (SQLClientInfoException e3) {
			System.out.println("ClientInfo ÀÍ¼Á¼Ç!!!");
		} catch (SQLNonTransientConnectionException e4) {
			System.out.println("NonTransientConnection ÀÍ¼Á¼Ç!!!");
		} catch (SQLFeatureNotSupportedException e5) {
			System.out.println("FeatureNotSupported ÀÍ¼Á¼Ç!!!");
		} catch (SQLWarning e6) {
			System.out.println("Warning ÀÍ¼Á¼Ç!!!");
		} catch (SQLTransientException e7) {
			System.out.println("Transient ÀÍ¼Á¼Ç!!!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("SQL ID Áßº¹ Exception");
			idArea.setText("");
			label.setText("ÀÌ¹Ì Á¸ÀçÇÏ´Â IDÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
		}
	}
}
