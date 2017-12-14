package com.sangmin.blackjack.Control;

import java.io.*;
import java.net.*;
import java.util.*;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Rule;
import com.sangmin.blackjack.Construction.SendingType;

public class ServerBackground {

	public static void main(String[] args) {
		new ServerBackground();
	}

	/**************** Server ****************/
	// 통신 관련 Data
	private HashMap<String, PerClient> clientList = new HashMap<String, PerClient>();
	private ServerSocket server = null;
	private Socket socket = null;

	// server 생성자 method
	public ServerBackground() {
		try {
			server = new ServerSocket(9000);
			System.out.println("Listening..");

			while (true) {
				socket = server.accept();
				PerClient client = new PerClient(socket, new CardDeck(), new Dealer(), new Rule());
				Thread thread = new Thread(client);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// client를 추가하는 method
	public void addClient(String id, PerClient client) {
		clientList.put(id, client);
	}

	// client를 삭제하는 method
	public void removeClient(String id) {
		clientList.remove(id);
	}

	public int getClientSize() {
		return clientList.size();
	}

	/**************** Game ****************/
	// Game 관련 Data
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
//	private static final String STOP_RECEIVE_CARD = "0";

	public void waiting() {
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void play(CardDeck _deck, Dealer _deal, Rule _rule) {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		Rule rule = _rule;
		CardDeck cardDeck = _deck;
		Dealer dealer = _deal;

		initDealerAndSendToAll(cardDeck, dealer);
		initPhase(cardDeck);
	}

	// Dealer의 Card를 모든 Player에게 전송하는 method
	private void initDealerAndSendToAll(CardDeck cardDeck, Dealer dealer) {
		waiting();
		
		Set<String> keySet = clientList.keySet();
		Card card = cardDeck.draw();
		dealer.receiveCard(card);

		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String id = it.next();
			clientList.get(id).sendCard(card);
		}
	}

	// 처음 2장 카드 배분
	private void initPhase(CardDeck cardDeck) {

		Set<String> keySet = clientList.keySet();
		for (int i = 0; i < INIT_RECEIVE_CARD_COUNT; i++) {
			waiting();

			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String id = it.next();
				Card card = cardDeck.draw();
				clientList.get(id).sendCard(card);
				System.out.println("한장 보냅니당");
			}
		}
	}

	// 게임 진행 (카드 받기)
	private void playingPhase(String id, CardDeck cardDeck) {
		Card card = cardDeck.draw();
		clientList.get(id).sendCard(card);
		System.out.println("한장 보냅니당");
	}


	class PerClient implements Runnable {

		private ObjectInputStream ois = null;
		private ObjectOutputStream oos = null;
		private String id = null;
		private volatile boolean connected = false;
		
		private CardDeck cardDeck = null;
		private Dealer dealer = null;
		private Rule rule = null;
		
		public PerClient(Socket socket, CardDeck cardDeck, Dealer dealer, Rule rule) {
			this.cardDeck = cardDeck;
			this.dealer = dealer;
			this.rule = rule;
			
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				connected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				id = (String) ois.readObject();
				addClient(id, this);
				System.out.println(id + " connected!");
			} catch (ClassNotFoundException e) {
				System.out.println(id + " disconnected");
				setDisconnect();
			} catch (IOException e) {
				System.out.println(id + " disconnected");
				setDisconnect();
			}

			if (getClientSize() == 1) {
				System.out.println("Play!!");
				play(cardDeck, dealer, rule);
			}

			while (connected) {
				try {
					SendingType type = (SendingType) ois.readObject();
					System.out.println(type.isTurn() + "받았음~!");
					if (type.isTurn())
						playingPhase(type.getId(), cardDeck);
					else {
						while (dealer.isReceiveCard()) {
							initDealerAndSendToAll(cardDeck, dealer);
							System.out.println(dealer.getPointSum() + "딜러 총점!!");
						}
						waiting();
						String winner = rule.getWinner(type.getTotalScore(), dealer.getPointSum(), id);
						System.out.println(winner + " 입니다!!");
						setDisconnect();
					}
				} catch (IOException e) {
					System.out.println(id + " disconnected");
					setDisconnect();
				} catch (ClassNotFoundException e) {
					System.out.println(id + " disconnected");
					setDisconnect();
				}
			}
			removeClient(id);
		}

		public void sendCard(Card card) {
			try {
				oos.writeObject(card);
			} catch (IOException e) {
				System.out.println("Error!");
			}
		}
		
		private void setDisconnect() {
			connected = false;
		}
	}
}
