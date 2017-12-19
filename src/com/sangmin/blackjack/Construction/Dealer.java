package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	private List<Card> cards; // ���� ī��
    private static final int CAN_RECEIVE_POINT = 16;

	// ������ method
	public Dealer() {
		cards = new ArrayList<>();
	}
	
	// Card �޴� method
	public void receiveCard(Card card) {
		this.cards.add(card);
	}
	
	// Card�� ���� �� �ִ��� ���� �Ǵ��ϴ� method
	public boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	// Dealer�� ���� ���� �� ������ ��ȯ�ϴ� method
	public int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}
}