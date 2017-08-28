package com.sangmin.blackjack.Control;

import java.util.Scanner;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Gamer;
import com.sangmin.blackjack.Construction.Rule;

public class Game {
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
	
	public void play() {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		 
		Dealer dealer = new Dealer();
		Gamer gamer = new Gamer();
		Rule rule = new Rule();
		CardDeck cardDeck = new CardDeck();
		
		initPhase(cardDeck, gamer);
		playingPhase(sc, cardDeck, gamer);
	}

	private void playingPhase(Scanner sc, CardDeck cardDeck, Gamer gamer) {
		String gamerInput;
		while(true) {
			System.out.println("ī�带 �̰ڽ��ϱ�? (����:0)");
			gamerInput = sc.nextLine();
			
			if("0".equals(gamerInput)) {
				break;
			}
			
			Card card = cardDeck.draw();
			gamer.receiveCard(card);
		}
	}
	
	private void initPhase(CardDeck cardDeck, Gamer gamer) {
		System.out.println("ó�� 2���� ī�带 ���� �̰ڽ��ϴ�.");
		
		for(int i=0; i<INIT_RECEIVE_CARD_COUNT; i++) {
			Card card = cardDeck.draw();
			gamer.receiveCard(card);
		}
	}
}
