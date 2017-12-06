package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Dealer implements Player{
	private List<Card> cards; 
    private boolean turn;

    private static final int CAN_RECEIVE_POINT = 16;
	private static final String NAME = "����";

	// ������ method
	public Dealer() {
		cards = new ArrayList<>();
	}
	
	// Card �޴� method
	@Override
	public void receiveCard(Card card) {
		if(this.isReceiveCard()) {
			this.cards.add(card);
			this.showCards();
		}else {
			System.out.println("ī���� �� ���� 17 �̻��Դϴ�."
					+ "�� �̻� ī�带 ���� �� �����ϴ�.");
		}
	}
	
	// Card�� ���� �� �ִ��� ���� �Ǵ��ϴ� method
	private boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	// Dealer�� ���� ���� �� ������ ��ȯ�ϴ� method
	private int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}

	// Gamer�� ���� ������ �ִ� ī�� ����� �����ִ� method
	@Override
	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("���� ���� ī�� ��� \n");
		
		for(Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// Gamer�� ���� ������ �ִ� ī�� ����� ��ȯ�ϴ� method
	@Override
	public List<Card> openCards() {
		return this.cards;
	}

	// turn�� off�� ��û�ϴ� method
	@Override
	public void turnOff() {
		this.setTurn(false);
	}

	// turn�� on���� ��û�ϴ� method
	@Override
	public void turnOn() {
		this.setTurn(true);
	}

	// turn�� ���� ��ȯ�ϴ� method
	@Override
	public boolean isTurn() {
		return this.turn;
	}

	// turn�� setting�ϴ� method
	private void setTurn(boolean turn) {
		this.turn = turn;
	}

	// name�� ��ȯ�ϴ� method
	@Override
	public String getName() {
		return NAME;
	}
	
}