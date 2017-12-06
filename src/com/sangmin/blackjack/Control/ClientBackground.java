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
	// 통신 관련 Data
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private Receiver receiver = null;
	private Thread thread = null;
	private GameWaitingView waitingView = null;

	// Client 생성자 method
	public ClientBackground(String id) {
		setName(id);
		waitingView = new GameWaitingView();

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
	private List<Card> cards = new ArrayList<>();
	private boolean turn;
	private String name;

	// Card 받는 method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		this.showCards();
		waitingView.addCard(card.toString());
	}

	// Gamer가 현재 가지고 있는 카드 목록을 보여주는 method
	@Override
	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("현재 보유 카드 목록 \n");

		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

	// Gamer가 현재 가지고 있는 카드 목록을 반환하는 method
	@Override
	public List<Card> openCards() {
		return this.cards;
	}

	// turn을 off로 요청하는 method
	@Override
	public void turnOff() {
		this.setTurn(false);
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
	private void setTurn(boolean turn) {
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
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// 연결이 끊길 때 까지 반복
			System.out.println("연결 끊길때 까지 반복 예정");
			while (connect) {
				try {
					Card card = (Card) ois.readObject();
					System.out.println("카드 한장 받음!");
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
