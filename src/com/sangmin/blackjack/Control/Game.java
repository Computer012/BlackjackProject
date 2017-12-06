package com.sangmin.blackjack.Control;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.sangmin.blackjack.Construction.Card;
import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Gamer;
import com.sangmin.blackjack.Construction.Player;
import com.sangmin.blackjack.Construction.Rule;

public class Game {
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
	private static final String STOP_RECEIVE_CARD = "0";
	
	public void play() {
		System.out.println("===== Blackjack =====");
		Scanner sc = new Scanner(System.in);
		Rule rule = new Rule();
		CardDeck cardDeck = new CardDeck();
		
		List<Player> players = Arrays.asList(new Gamer("User1"), new Dealer());
		List<Player> initAfterPlayers = initPhase(cardDeck, players);
		List<Player> playingAfterPlayers = playingPhase(sc, cardDeck, initAfterPlayers);
		
		Player winner = rule.getWinner(playingAfterPlayers);
		System.out.println("승자는 " + winner.getName());
	}
	
	// 처음 2장 카드 배분
	private List<Player> initPhase(CardDeck cardDeck, List<Player> players) {
		System.out.println("처음 2장의 카드를 각자 뽑겠습니다.\n");
		
		for(int i=0; i<INIT_RECEIVE_CARD_COUNT; i++) {
			for(Player player : players) {
				System.out.println(player.getName() + "님 차례입니다.");
				Card card = cardDeck.draw ();
				player.receiveCard(card);
			}
		}
		
		return players;
	}
	
	// 게임 진행 (카드 받기)
	private List<Player> playingPhase(Scanner sc, CardDeck cardDeck, List<Player> players) {
		List<Player> cardReceivedPlayers;

		while(true) {
			cardReceivedPlayers = receiveCardAllPlayers(sc, cardDeck, players);
			
			if(isAllPlayerTurnOff(cardReceivedPlayers)) {
				break;
			}
		}
		return cardReceivedPlayers;
	}
	
	// 의사에 따라 카드 배분
	private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) {
		for(Player player : players) {
			System.out.println(player.getName() + "님 차례입니다.");
			
			if(isReceiveCard(sc)) {
				Card card = cardDeck.draw();
				player.receiveCard(card);
				player.turnOn();
			}else {
				player.turnOff();
			}
		}
		
		return players;
	}
	
	// 모든 플레이어 턴 종료여부 판단
	private boolean isAllPlayerTurnOff(List<Player> players) {
		for(Player player : players) {
			if(player.isTurn()) {
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
