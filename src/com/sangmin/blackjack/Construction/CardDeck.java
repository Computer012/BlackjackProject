package com.sangmin.blackjack.Construction;

import java.util.*;

public class CardDeck {
	private Stack<Card> cards;
	
	// 생성자 method
	public CardDeck() {
		cards = this.generateCards();
		Collections.shuffle(this.cards);
	}
	
	// 52개의 Card를 생성하는 method
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

	// CardDeck에서 Card를 한 장 뽑아 반환하는 method
	public Card draw() {
		return this.cards.pop();
	}
}
