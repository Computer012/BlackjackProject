package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
	private List<Card> cards; 
	
	private static final int CAN_RECEIVE_POINT = 16;
	
	public Dealer() {
		cards = new ArrayList<>();
	}
	
	public void receiveCard(Card card) {
		if(this.isReceiveCard()) {
			this.cards.add(card);
			this.showCards();
		}else {
			System.out.println("ī���� �� ���� 17 �̻��Դϴ�."
					+ "�� �̻� ī�带 ���� �� �����ϴ�.");
		}
	}
	
	private boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	private int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}

	private void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("���� ���� ī�� ��� \n");
		
		for(Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	public List<Card> openCards() {
		return null;
	}
	
	
}