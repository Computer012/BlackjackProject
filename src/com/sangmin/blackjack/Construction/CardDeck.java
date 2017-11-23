package com.sangmin.blackjack.Construction;

import java.util.*;

public class CardDeck {
	private Stack<Card> cards;
	//¢¼¢¾¡ß¢À
	
	public CardDeck() {
		cards = this.generateCards();
		Collections.shuffle(this.cards);
	}
	
	private Stack<Card> generateCards() {
		Stack<Card> cards = new Stack<>();
		
		for(Card.Pattern pattern : Card.Pattern.values()) {
			for(Card.Denomination denomination : Card.Denomination.values()) {
				Card card = new Card(pattern, denomination);
				cards.push(card);
			}
		}
		return cards;
	}
	
	public Stack<Card> getCards() {
		return cards;
	}

	public Card draw() {
		return this.cards.pop();
	}
}
