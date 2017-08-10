package com.sangmin.blackjack.Control;

import com.sangmin.blackjack.Construction.CardDeck;
import com.sangmin.blackjack.Construction.Dealer;
import com.sangmin.blackjack.Construction.Gamer;
import com.sangmin.blackjack.Construction.Rule;

public class Game {
	
	public void play() {
		System.out.println("===== Blackjack =====");
		Dealer dealer = new Dealer();
		Gamer gamer = new Gamer();
		Rule rule = new Rule();
		CardDeck cardDeck = new CardDeck();
	}
}
