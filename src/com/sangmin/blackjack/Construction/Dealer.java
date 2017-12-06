package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements Player{
	private List<Card> cards; 
    private boolean turn;

    private static final int CAN_RECEIVE_POINT = 16;
	private static final String NAME = "딜러";

	// 생성자 method
	public Dealer() {
		cards = new ArrayList<>();
	}
	
	// Card 받는 method
	@Override
	public void receiveCard(Card card) {
		if(this.isReceiveCard()) {
			this.cards.add(card);
			this.showCards();
		}else {
			System.out.println("카드의 총 합이 17 이상입니다."
					+ "더 이상 카드를 받을 수 없습니다.");
		}
	}
	
	// Card를 받을 수 있는지 여부 판단하는 method
	private boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	// Dealer가 현재 가진 총 점수를 반환하는 method
	private int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
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
		return NAME;
	}
	
}