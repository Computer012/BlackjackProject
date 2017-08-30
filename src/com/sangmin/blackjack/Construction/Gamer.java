package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Gamer implements Player{
	private List<Card> cards;
	
	public Gamer() {
		cards = new ArrayList<>();
	}
	
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card); 
		this.showCards();
	}
	
	@Override
	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("현재 보유 카드 목록 \n");

		for(Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	@Override
	public List<Card> openCards() {
		return this.cards;
	}
}