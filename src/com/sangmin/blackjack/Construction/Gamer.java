package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Gamer implements Player{
	private List<Card> cards;
	private boolean turn;
	
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

	@Override
	public void turnOff() {
		this.setTurn(false);
	}

	@Override
	public void turnOn() {
		this.setTurn(true);
	}

	@Override
	public boolean isTurn() {
		return this.turn;
	}
	
	private void setTurn(boolean turn) {
		this.turn = turn;
	}
}