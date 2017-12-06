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
//				System.out.println("Client connected");
				PerClient client = new PerClient(this, socket);
				Thread thread = new Thread(client);
				thread.start();

				System.out.println(clientList.size());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// client를 추가하는 method
	public void addClient(String id,PerClient client) {
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
	private static final String STOP_RECEIVE_CARD = "0";

	public void play() {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		Rule rule = new Rule();
		CardDeck cardDeck = new CardDeck();

		initPhase(cardDeck);
//		playingPhase(sc, cardDeck, initAfterPlayers);
//
//		Player winner = rule.getWinner(playingAfterPlayers);
//		System.out.println("승자는 " + winner.getName());
	}

	// 처음 2장 카드 배분
	private void initPhase(CardDeck cardDeck) {
		System.out.println("처음 2장의 카드를 각자 뽑겠습니다.\n");
		
		Set<String> keySet = clientList.keySet();
		for (int i = 0; i < INIT_RECEIVE_CARD_COUNT; i++) {

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
			}
		}
	}

	// 게임 진행 (카드 받기)
	private List<Player> playingPhase(Scanner sc, CardDeck cardDeck, List<Player> players) {
		List<Player> cardReceivedPlayers;

		while (true) {
			cardReceivedPlayers = receiveCardAllPlayers(sc, cardDeck, players);

			if (isAllPlayerTurnOff(cardReceivedPlayers)) {
				break;
			}
		}
		return cardReceivedPlayers;
	}

	// 의사에 따라 카드 배분
	private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) {
		for (Player player : players) {
			System.out.println(player.getName() + "님 차례입니다.");

			if (isReceiveCard(sc)) {
				Card card = cardDeck.draw();
				player.receiveCard(card);
				player.turnOn();
			} else {
				player.turnOff();
			}
		}

		return players;
	}

	// 모든 플레이어 턴 종료여부 판단
	private boolean isAllPlayerTurnOff(List<Player> players) {
		for (Player player : players) {
			if (player.isTurn()) {
				return false;
			}
		}
		return true;
	}

	// 턴 종료 여부 판단
	private boolean isReceiveCard(Scanner sc) {
		System.out.println("카드를 뽑겠습니까? (종료:0)");
		return !STOP_RECEIVE_CARD.equals(sc.nextLine());
	}
}

class PerClient implements Runnable {

	private ServerBackground server = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private String id = null;
	private volatile boolean connected = false;

	public PerClient(ServerBackground server, Socket socket) {
		this.server = server;

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
			server.addClient(id, this);
			System.out.println(id + " connected!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			connected = false;
		} catch (IOException e) {
			System.out.println("Client disconnected");
			connected = false;
		}
		
		if (server.getClientSize() == 2) {
			System.out.println("Play!!");
			server.play();
		}

		while (connected) {
			
		}
		server.removeClient(id);
	}

	public void sendCard(Card card) {
		try {
			oos.writeObject(card);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}