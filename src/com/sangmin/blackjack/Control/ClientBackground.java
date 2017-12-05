package com.sangmin.blackjack.Control;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.GUI.GameWaitingView;

public class ClientBackground {

	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private Receiver receiver = null;
	private Thread thread = null;
	private String id = null;
	
	private GameWaitingView waitingView = null;
	private LinkedList<Card> cardList = null;

	public ClientBackground(String id) {
		
		this.id = id;
		waitingView = new GameWaitingView();
		
		try {
			// Server�� IP, Port number�� �޾ƿ� ���� ����
			socket = new Socket("127.0.0.1", 9000);
			oos = new ObjectOutputStream(socket.getOutputStream());

			receiver = new Receiver(socket);
			thread = new Thread(receiver);
			thread.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void addCard(Card card) {
		cardList.add(card);
	}

	// content ��ü�� ������ �����ϴ� method
//	public void send(String string) {
//		try {
//			oos.writeObject();
//			oos.flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		new ClientBackground("idTest");
	}

	// ������ ����ϴ� Client�� Thread
	class Receiver implements Runnable {
		private ObjectInputStream ois = null;
		private String time = null;
		volatile boolean connect = false;

		public Receiver(Socket socket) {
			
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				connect = true;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean isConnected() {
			return connect;
		}
		
		public void setConnectToDisconnect() {
			connect = false;
		}

		@Override
		public void run() {
			
			// ������ ���� �� ���� �ݺ�
			while (connect) {
				try {
					time = (String) ois.readObject();
					waitingView.sets(time);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
			connect = false;
			System.out.println("Connection closed...");
		}
	}
}

