package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	private List<Card> cards; // 받은 카드
    private static final int CAN_RECEIVE_POINT = 16;

	// 생성자 method
	public Dealer() {
		cards = new ArrayList<>();
	}
	
	// Card 받는 method
	public void receiveCard(Card card) {
		this.cards.add(card);
	}
	
	// Card를 받을 수 있는지 여부 판단하는 method
	public boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	// Dealer가 현재 가진 총 점수를 반환하는 method
	public int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}
}