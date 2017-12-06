package com.sangmin.blackjack.Construction;

import java.util.ArrayList;
import java.util.List;

public class Gamer implements Player{
	private List<Card> cards;
	private boolean turn;
	private String name;
	
	// ������ method
	public Gamer(String name) {
		this.cards = new ArrayList<>();
		this.name = name;
	}
	
	// Card �޴� method
	@Override
	public void receiveCard(Card card) {
		this.cards.add(card);
		this.showCards();
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
		return this.name;
	}
}