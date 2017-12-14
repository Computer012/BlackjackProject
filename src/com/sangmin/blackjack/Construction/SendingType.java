package com.sangmin.blackjack.Construction;

import java.io.Serializable;

public class SendingType implements Serializable {
	private String id;
	private boolean turn;
	private int totalScore;
	
	public SendingType(String id, boolean turn, int totalScore) {
		this.id = id;
		this.turn = turn;
		this.totalScore = totalScore;
	}

	public String getId() {
		return id;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public boolean isTurn() {
		return turn;
	}
}
