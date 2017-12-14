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
		this.cards.add(card);
	}
	
	// Card�� ���� �� �ִ��� ���� �Ǵ��ϴ� method
	public boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	// Dealer�� ���� ���� �� ������ ��ȯ�ϴ� method
//	private
	public int getPointSum() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
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