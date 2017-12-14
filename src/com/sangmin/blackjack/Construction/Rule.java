package com.sangmin.blackjack.Construction;

public class Rule {
	
	// Á¡¼ö¸¦ ºñ±³ÇØ ½ÂÀÚ°¡ ´©±ºÁö ¹ÝÈ¯ÇÏ´Â method
	public String getWinner(int playerScore, int dealerScore, String id) {
		String winner = "";
		
		if ((playerScore > 21 && dealerScore > 21) || playerScore == dealerScore)
			winner = "¹«½ÂºÎ";
		else if (playerScore > 21 || playerScore < dealerScore)
			winner = "Dealer ½Â";
		else
			winner = "Player ½Â";
		
		return winner;
	}
}
