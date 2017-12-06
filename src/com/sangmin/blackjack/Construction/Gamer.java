package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Gamer implements Player{
	private List<Card> cards;
	private boolean turn;
	private String name;
	
	// 생성자 method
	public Gamer(String name) {
		this.cards = new ArrayList<>();
		this.name = name;
	}
	
	// Card 받는 method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		this.showCards();
	}
	
	// Gamer가 현재 가지고 있는 카드 목록을 보여주는 method
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

	// name을 반환하는 method
	@Override
	public String getName() {
		return this.name;
	}
}