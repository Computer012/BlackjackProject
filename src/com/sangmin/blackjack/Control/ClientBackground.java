package com.sangmin.blackjack.Control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.Player;
import com.sangmin.blackjack.GUI.GameWaitingView;

public class ClientBackground implements Player {

	public static void main(String[] args) {
		new ClientBackground("computer012");
	}

	/**************** Client ****************/
	// ��� ���� Data
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private Receiver receiver = null;
	private Thread thread = null;
	private GameWaitingView waitingView = null;

	// Client ������ method
	public ClientBackground(String id) {
		setName(id);
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

	/**************** Game ****************/
	// Game ���� Data
	private List<Card> cards = new ArrayList<>();
	private boolean turn;
	private String name;

	// Card �޴� method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		this.showCards();
		waitingView.addCard(card.toString());
	}

	// Gamer�� ���� ������ �ִ� ī�� ����� �����ִ� method
	@Override
	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("���� ���� ī�� ��� \n");

		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	// Gamer�� ���� ������ �ִ� ī�� ����� ��ȯ�ϴ� method
	@Override
	public List<Card> openCards() {
		return this.cards;
	}

	// turn�� off�� ��û�ϴ� method
	@Override
	public void turnOff() {
		this.setTurn(false);
	}

	// turn�� on���� ��û�ϴ� method
	@Override
	public void turnOn() {
		
		this.setTurn(true);
	}

	// turn�� ���� ��ȯ�ϴ� method
	@Override
	public boolean isTurn() {
		return this.turn;
	}

	// turn�� setting�ϴ� method
	private void setTurn(boolean turn) {
		this.turn = turn;
	}

	// name�� setting�ϴ� method
	public void setName(String name) {
		this.name = name;
	}
	
	// name�� ��ȯ�ϴ� method
	@Override
	public String getName() {
		return this.name;
	}

	
	// ������ ����ϴ� Client�� Thread
	class Receiver implements Runnable {
		private ObjectInputStream ois = null;
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

			try {
				System.out.println("Receiver ����, " + name + " ����");
				oos.writeObject(name); // id �����ϱ�
				setName(name);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// ������ ���� �� ���� �ݺ�
			System.out.println("���� ���涧 ���� �ݺ� ����");
			while (connect) {
				try {
					Card card = (Card) ois.readObject();
					System.out.println("ī�� ���� ����!");
					receiveCard(card);
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
