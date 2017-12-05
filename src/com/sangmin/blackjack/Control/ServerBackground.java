package com.sangmin.blackjack.Control;

import java.io.*;
import java.net.*;
import java.util.*;

import com.sangmin.blackjack.Construction.Card;

public class ServerBackground {
	
	private ArrayList<PerClient> clientList = new ArrayList<PerClient>();
	private ServerSocket server = null;
	private Socket socket = null;
	
	public ServerBackground() {
		try {
			server = new ServerSocket(9000);
			System.out.println("Listening..");
			
			while (true) {
				socket = server.accept();
				System.out.println("Client connected");
				PerClient client = new PerClient(this, socket);
				addClient(client);
				Thread thread = new Thread(client);
				thread.start();

				if (clientList.size() == 3)
					new Game();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void addClient(PerClient client) {
		clientList.add(client);
	}
	 
	public void removeClient(PerClient client) {
		clientList.remove(client);
	}
	
	public void sendCardToAll(Card card) {
		for (PerClient client : clientList)
			client.sendCard(card);
	}

	public static void main(String[] args) {
		new ServerBackground();
	}
	
//	class timeThread implements Runnable {
//		int time = 60;
//		
//		@Override
//		public void run() {
//			try {
//				while (time != 0) {
//					sendTimeToAll(String.valueOf(time));
//					Thread.sleep(1000);
//					time--;
//				}
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}

class PerClient implements Runnable {
	
	private ServerBackground server = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private String time = null;
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
		while (connected) {
			try {
				
//				time = (String) ois.readObject();
//				server.sendTimeToAll(time);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				connected = false;
				break;
			} catch (IOException e) {
				System.out.println("Client disconnected");
				connected = false;
				break;
			}
		}
		server.removeClient(this);
	}
	
	public void sendCard(Card card) {
		try {
			oos.writeObject(card);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}