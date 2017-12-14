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
	// 통신 관련 Data
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private Receiver receiver = null;
	private Thread thread = null;
	private GameView waitingView = null;

	// Client 생성자 method
	public ClientBackground(String id) {
		setName(id);
		waitingView = new GameView(this);

		try {
			// Server의 IP, Port number로 받아와 소켓 생성
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
	// Game 관련 Data
	private List<Card> dealerCards = new ArrayList<>();
	private List<Card> cards = new ArrayList<>();
	private boolean turn;
	private String name;

	// Dealer의 Card를 받는 method
	public void dealerCardReceive(Card card) {
		this.dealerCards.add(card);
		waitingView.addDealerCard(card.toString());
	}
	
	// Card 받는 method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		waitingView.addCard(card.toString());
	}

	// Server측에 카드를 더 받을지 여부를 전송
	public void cardRequest() {
		try {
			oos.writeObject(new SendingType(name, turn, getPlayerPointSum()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// turn을 off로 요청하는 method
	@Override
	public void turnOff() {
		this.setTurn(false);
		System.out.println("Turn off" + turn);
	}

	// turn을 on으로 요청하는 method
	@Override
	public void turnOn() {
		this.setTurn(true);
	}

	// turn의 값을 반환하는 method
 	@Override
	public boolean isTurn() {
		return this.turn;
	}

	// turn을 setting하는 method
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	// name을 setting하는 method
	public void setName(String name) {
		this.name = name;
	}
	
	// name을 반환하는 method
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
	
	// 수신을 담당하는 Client측 Thread
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
				System.out.println("Receiver 동작, " + name + " 전송");
				oos.writeObject(name); // id 전송하기
				setName(name);
				
				Card card = (Card) ois.readObject();
				System.out.println("딜러카드 한장 !");
				dealerCardReceive(card);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			// 연결이 끊길 때 까지 반복
			System.out.println("연결 끊길때 까지 반복 예정");
			turnOn();
			while (isConnected()) {
				try {
					Card card = (Card) ois.readObject();
					
					if (isTurn()) {
						System.out.println("플레이어 카드 한장 !");
						receiveCard(card);
					} else {
						System.out.println("딜러카드 한장 !");
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
