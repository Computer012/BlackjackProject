package com.sangmin.blackjack.Construction;

import java.io.Serializable;

public class SendingType implements Serializable {
	private String id;
	private boolean turn;
	
	public SendingType(String id, boolean turn) {
		this.id = id;
		this.turn = turn;
	}

	public String getId() {
		return id;
	}

	public boolean isTurn() {
		return turn;
	}
	
}
