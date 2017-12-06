package com.sangmin.blackjack.Construction;

import java.util.List;

public class Rule {
	
	// 점수를 비교해 승자가 누군지 반환하는 method
	public Player getWinner(List<Player> players) {
		Player highestPlayer = null;
		int highestPoint = 0;
		
		for(Player player : players) {
			int playerPointSum = getPointSum(player.openCards());
			if(playerPointSum > highestPoint) {
				highestPlayer = player;
				highestPoint = playerPointSum;
			}
		}
		return highestPlayer;
	}
	
	// Card의 총 점수를 계산해 반환하는 method
	private int getPointSum(List<Card> cards) {
		int sum = 0;
		
		for(Card card : cards) {
			sum += card.getPoint();
		}
		
		return sum;
	}
}
