package com.sangmin.blackjack.Construction;

public class Rule {
	
	// ������ ���� ���ڰ� ������ ��ȯ�ϴ� method
	public String getWinner(int playerScore, int dealerScore, String id) {
		String winner = "";
		
		if ((playerScore > 21 && dealerScore > 21) || playerScore == dealerScore)
			winner = "���º�";
		else if (playerScore > 21 || playerScore < dealerScore)
			winner = "Dealer ��";
		else
			winner = "Player ��";
		
		return winner;
	}
}
