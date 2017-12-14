package com.sangmin.blackjack.Control;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.Player;
import com.sangmin.blackjack.Construction.SendingType;
import com.sangmin.blackjack.GUI.GameView;

public class ClientBackground implements Player {

	public static void main(String[] args) {
		new ClientBackground("computer");
	}

	/**************** Client ****************/
	// ��� ���� Data
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private Receiver receiver = null;
	private Thread thread = null;
	private GameView waitingView = null;

	// Client ������ method
	public ClientBackground(String id) {
		setName(id);
		waitingView = new GameView(this);

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
	private List<Card> dealerCards = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();
	private boolean turn;
	private String name;

	// Dealer�� Card�� �޴� method
	public void dealerCardReceive(Card card) {
		this.dealerCards.add(card);
		waitingView.addDealerCard(card.toString());
	}
	
	// Card �޴� method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		waitingView.addCard(card.toString());
	}

	// Server���� ī�带 �� ������ ���θ� ����
	public void cardRequest() {
		try {
			oos.writeObject(new SendingType(name, turn, getPlayerPointSum()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// turn�� off�� ��û�ϴ� method
	@Override
	public void turnOff() {
		this.setTurn(false);
		System.out.println("Turn off" + turn);
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
	public void setTurn(boolean turn) {
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

	public int getPlayerPointSum() {
		int sum = 0;
		
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}
	
	public int getDealerPointSum() {
		int sum = 0;
		
		for(Card card : dealerCards) {
			sum += card.getPoint();
		}
		return sum;
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
				
				Card card = (Card) ois.readObject();
				System.out.println("����ī�� ���� !");
				dealerCardReceive(card);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// ������ ���� �� ���� �ݺ�
			System.out.println("���� ���涧 ���� �ݺ� ����");
			turnOn();
			while (isConnected()) {
				try {
					Card card = (Card) ois.readObject();
					
					if (isTurn()) {
						System.out.println("�÷��̾� ī�� ���� !");
						receiveCard(card);
					} else {
						System.out.println("����ī�� ���� !");
						dealerCardReceive(card);
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					setConnectToDisconnect();
					break;
				} catch (IOException e) {
					e.printStackTrace();
					setConnectToDisconnect();
					break;
				}
			}
			
			System.out.println("Connection closed...");
		}
	}
}
