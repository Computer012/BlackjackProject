package com.sangmin.blackjack.Control;

import java.io.*;
import java.net.*;
import java.util.*;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Gamer;
import com.sangmin.blackjack.Construction.Player;
import com.sangmin.blackjack.Construction.Rule;
import com.sangmin.blackjack.Construction.SendingType;

public class ServerBackground {

	public static void main(String[] args) {
		new ServerBackground();
	}

	/**************** Server ****************/
	// ��� ���� Data
	private HashMap<String, PerClient> clientList = new HashMap<String, PerClient>();
	private ServerSocket server = null;
	private Socket socket = null;

	// server ������ method
	public ServerBackground() {
		try {
			server = new ServerSocket(9000);
			System.out.println("Listening..");

			while (true) {
				socket = server.accept();
				PerClient client = new PerClient(socket, new CardDeck());
				Thread thread = new Thread(client);
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// client�� �߰��ϴ� method
	public void addClient(String id, PerClient client) {
		clientList.put(id, client);
	}

	// client�� �����ϴ� method
	public void removeClient(String id) {
		clientList.remove(id);
	}

	public int getClientSize() {
		return clientList.size();
	}

	/**************** Game ****************/
	// Game ���� Data
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
//	private static final String STOP_RECEIVE_CARD = "0";

	public void play(CardDeck Deck) {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		Rule rule = new Rule();
		CardDeck cardDeck = Deck;
		Dealer dealer = new Dealer();

		initDealerAndSendToAll(cardDeck, dealer);
		initPhase(cardDeck);
		// Player winner = rule.getWinner(playingAfterPlayers);
		// System.out.println("���ڴ� " + winner.getName());
	}

	// Dealer�� Card�� ��� Player���� �����ϴ� method
	private void initDealerAndSendToAll(CardDeck cardDeck, Dealer dealer) {
		Card card = null;
		
		Set<String> keySet = clientList.keySet();
		for (int i = 0; i < INIT_RECEIVE_CARD_COUNT; i++) {
			card = cardDeck.draw();
			dealer.receiveCard(card);

			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String id = it.next();
				clientList.get(id).sendCard(card);
			}
		}
	}

	// ó�� 2�� ī�� ���
	private void initPhase(CardDeck cardDeck) {

		Set<String> keySet = clientList.keySet();
		for (int i = 0; i < INIT_RECEIVE_CARD_COUNT; i++) {
			System.out.println("1.5�� ���..");
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String id = it.next();
				Card card = cardDeck.draw();
				clientList.get(id).sendCard(card);
				System.out.println("���� �����ϴ�");
			}
		}
	}

	// ���� ���� (ī�� �ޱ�)
	private void playingPhase(String id, CardDeck cardDeck) {
		Card card = cardDeck.draw();
		clientList.get(id).sendCard(card);
		System.out.println("���� �����ϴ�");
	}

//	// �ǻ翡 ���� ī�� ���
//	private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) {
//		for (Player player : players) {
//			System.out.println(player.getName() + "�� �����Դϴ�.");
//
//			if (isReceiveCard(sc)) {
//				Card card = cardDeck.draw();
//				player.receiveCard(card);
//				player.turnOn();
//			} else {
//				player.turnOff();
//			}
//		}
//
//		return players;
//	}
//
//	// ��� �÷��̾� �� ���Ῡ�� �Ǵ�
//	private boolean isAllPlayerTurnOff(List<Player> players) {
//		for (Player player : players) {
//			if (player.isTurn()) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	// �� ���� ���� �Ǵ�
//	private boolean isReceiveCard(Scanner sc) {
//		System.out.println("ī�带 �̰ڽ��ϱ�? (����:0)");
//		return !STOP_RECEIVE_CARD.equals(sc.nextLine());
//	}

	class PerClient implements Runnable {

		private ObjectInputStream ois = null;
		private ObjectOutputStream oos = null;
		private String id = null;
		private volatile boolean connected = false;
		
		private CardDeck cardDeck = null;
		
		public PerClient(Socket socket, CardDeck cardDeck) {
			this.cardDeck = cardDeck;
			
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
				play(cardDeck);
			}

			while (connected) {
				try {
					SendingType type = (SendingType) ois.readObject();
					System.out.println(type.isTurn() + "�޾���~!");
					if (type.isTurn())
						playingPhase(type.getId(), cardDeck);
					else {
						System.out.println("���� �������� �Ǵܰ� ���");
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
				e.printStackTrace();
			}
		}
		
		private void setDisconnect() {
			connected = false;
		}
	}
}
