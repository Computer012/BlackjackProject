package com.sangmin.blackjack.Construction;

public class Rule {
	
	// 점수를 비교해 승자가 누군지 반환하는 method
	public String getWinner(int playerScore, int dealerScore, String id) {
		String winner = "";
		
		if ( (playerScore > 21 && dealerScore > 21) || playerScore == dealerScore)
			winner = "무승부";
		else if (playerScore > 21 || (playerScore <= 21 && dealerScore <= 21 && playerScore < dealerScore) )
			winner = "Dealer";
		else
			winner = id;
		
		return winner;
	}
}
