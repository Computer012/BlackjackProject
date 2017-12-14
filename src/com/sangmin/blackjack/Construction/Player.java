package com.sangmin.blackjack.Construction;

public interface Player {

	void receiveCard(Card card);
	
	void turnOff();
	
	void turnOn();
	
	boolean isTurn();
	
	String getName();
}