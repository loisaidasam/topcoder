/*
Problem Statement
    
Alice and Bob decide to play a game with a deck of cards. Each card has a single positive integer written on it. Multiple cards may have the same number. Cards with the same number are indistinguishable. Alice and Bob begin by splitting the deck into two piles. They each take one pile. Then each of them writes down the sequence of numbers on the cards in his/her pile, in order from bottom to top.
Then comes the first phase of the game. In this phase Alice and Bob build a third pile of cards, designated the "community" pile. Initially, the community pile is empty. The phase consists of multiple turns. In each turn, either Alice or Bob takes the top card from his/her pile and places it on top of the community pile. This continues until all the cards are in the community pile. (Note that Alice and Bob are not required to alternate. They may take the turns in any order they like.)
Afterwards there is the second phase of the game. In this phase Alice and Bob take the cards from the community pile to their personal piles. Again, this happens in turns. In each turn, either Alice or Bob takes the top card from the community pile and places it on top of his/her personal pile. This continues until there are no more cards in the community pile. (Again, Alice and Bob are not required to alternate. They may take the turns in any order they like. They are each allowed to take any cards to their pile, not just those they had at the beginning. They are also each allowed to take any number of cards, regardless of how many they had at the beginning.) Finally, they again write down the sequence of numbers on the cards in each pile, from bottom to top.
Later, Alice and Bob decide to double-check what they wrote down. Given the starting and ending lists of cards, they wonder how many ways the game could have progressed. You will be given 4 int[]s: AliceStart, BobStart, AliceEnd, and BobEnd. AliceStart and BobStart are the card stacks belonging to Alice and Bob at the start, and AliceEnd and BobEnd are the card stacks belonging to Alice and Bob at the end. Return the number of different possible ways the game could have progressed, modulo 1,000,000,007 (10^9+7).
Definition
    
Class:
AliceBobShuffle
Method:
countWays
Parameters:
int[], int[], int[], int[]
Returns:
int
Method signature:
int countWays(int[] AliceStart, int[] BobStart, int[] AliceEnd, int[] BobEnd)
(be sure your method is public)
    

Notes
-
Each card movement can be described by the source pile, the destination pile, and the number on the card.
-
Two games are considered different if their sequences of card movements differ.
Constraints
-
AliceStart, BobStart, AliceEnd, and BobEnd will each contain between 1 and 50 elements, inclusive. 
-
Each element of AliceStart, BobStart, AliceEnd, and BobEnd will be between 1 and 100, inclusive.
Examples
0)

    
{1, 3}
{2, 4}
{2, 3}
{1, 4}
Returns: 4
There are 4 possible orders for the community pile at the end of the first phase: {4, 3, 2, 1}, {3, 4, 2, 1}, {4, 3, 1, 2}, {3, 4, 1, 2}. (All of these are listed from bottom to top.) Each of these uniquely determines one possible game.
1)

    
{1, 2, 1}
{1, 2}
{1, 2, 1}
{2, 1}
Returns: 4
The only possibility for the order of the community pile at the end of the first phase is {1, 2, 1, 2, 1}. Still, there are 4 ways this game could have been played. (For instance, the bottommost 1 must have come from Alice's deck, but the 2 above it could have been placed by either player.)
2)

    
{1}
{2}
{3}
{4}
Returns: 0
This is impossible.
3)

    
{1, 2}
{3}
{3}
{2, 1}
Returns: 0
Also impossible.
4)

    
{3, 3, 2, 4, 1, 3, 1, 2, 1, 1, 5, 5, 1, 2, 3, 1, 2, 1, 2, 1, 1, 1, 2}
{4, 1, 4, 3, 4, 4, 4, 3, 2, 1, 6, 2, 2, 3, 2, 2, 1, 3, 2, 3}
{4, 1, 4, 3, 4, 4, 4, 1, 3, 1, 2, 2, 2, 3, 2, 1, 2, 1, 2, 2, 2}
{3, 3, 2, 3, 4, 2, 1, 2, 1, 1, 5, 6, 5, 1, 3, 1, 2, 3, 1, 1, 1, 3}
Returns: 314159265

This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
*/

import java.util.*;
import java.util.Arrays;

public class AliceBobShuffle {
	Pile aStart;
	Pile bStart;
	Pile aEnd;
	Pile bEnd;
	
	class Move {
		char source;
		char dest;
		int num;
	}
	
	class Pile {
		public int[] cards;
		
		public Pile() {
			this.cards = new int[0];
		}
		
		public Pile(int[] cards) {
			this.cards = new int[cards.length];
			for (int i = 0; i < cards.length; i++) {
				this.cards[i] = cards[i];
			}
		}
		
		public void addCard(int card) {
			int[] newCards = new int[cards.length + 1];
			for (int i = 0; i < cards.length; i++) {
				newCards[i] = cards[i];
			}
			newCards[cards.length] = card;
			cards = newCards;
		}
		
		public int pullCard() {
			if (cards.length == 0){
				return -1;
			}
			
			int returnCard = cards[cards.length-1];
			
			int[] newCards = new int[cards.length - 1];
			for (int i = 0; i < cards.length - 1; i++) {
				newCards[i] = cards[i];
			}
			cards = newCards;
			
			return returnCard;
		}
		
		public boolean equalTo(Pile inputPile) {
			if (inputPile.cards.length != cards.length) {
				return false;
			}
			
			for (int i = 0; i < cards.length; i++) {
				if (cards[i] != inputPile.cards[i]) {
					return false;
				}
			}
			
			return true;
		}
		
		public String toString() {
			String result = "{";
			
			for (int i = 0; i < cards.length; i++) {
				if (i > 0) {
					result += ", ";
				}
				result += cards[i];
			}
			
			result += "}";
			return result;
		}
	}
	
	class CommunityPossibility {
		public Pile pile;
		public ArrayList<String> moves;
		
		public CommunityPossibility(Pile pile) {
			this.pile = pile;
			moves = new ArrayList<String>();
		}
		
		public void addMove(String move) {
			this.moves.add(move);
		}
		
		public String toString() {
			String result = pile.toString();
			for (int i = 0; i < moves.size(); i++) {
				result = result + "\n\t" + moves.get(i);
			}
			return result;
		}
	}
	
	private void recursiveFinder(ArrayList<CommunityPossibility> possibilities, Pile a, Pile b, Pile community, String moves) {
		if (a.cards.length == 0 && b.cards.length == 0) {
			for (int i = 0; i < possibilities.size(); i++) {
				if (possibilities.get(i).pile.equalTo(community)) {
					possibilities.get(i).addMove(moves);
					return;
				}
			}
			
			CommunityPossibility possibility = new CommunityPossibility(community);
			possibility.addMove(moves);
			possibilities.add(possibility);
			return;
		}
		
		if (a.cards.length > 0) {
			int[] newCommunity = new int[community.cards.length+1];
			for (int i = 0; i < community.cards.length; i++) {
				newCommunity[i] = community.cards[i];
			}
			newCommunity[community.cards.length] = a.cards[a.cards.length-1];
			int[] newDeck = new int[a.cards.length - 1];
			for (int i = 0; i < a.cards.length - 1; i++) {
				newDeck[i] = a.cards[i];
			}
			String newMoves = "";
			if (moves.length() > 0) {
				newMoves = moves + ", ";
			}
			newMoves = newMoves + "AC" + newCommunity[newCommunity.length-1];
			
			recursiveFinder(possibilities, new Pile(newDeck), b, new Pile(newCommunity), newMoves);
		}
		
		if (b.cards.length > 0) {
			int[] newCommunity = new int[community.cards.length+1];
			for (int i = 0; i < community.cards.length; i++) {
				newCommunity[i] = community.cards[i];
			}
			newCommunity[community.cards.length] = b.cards[b.cards.length-1];
			int[] newDeck = new int[b.cards.length - 1];
			for (int i = 0; i < b.cards.length - 1; i++) {
				newDeck[i] = b.cards[i];
			}
			String newMoves = "";
			if (moves.length() > 0) {
				newMoves = moves + ", ";
			}
			newMoves = newMoves + "BC" + newCommunity[newCommunity.length-1];
			
			recursiveFinder(possibilities, a, new Pile(newDeck), new Pile(newCommunity), newMoves);
		}
	}
	
	private ArrayList<CommunityPossibility> getCommunityPossibilities(Pile a, Pile b) {
		ArrayList<CommunityPossibility> possibilities = new ArrayList<CommunityPossibility>();
		recursiveFinder(possibilities, a, b, new Pile(), "");
		return possibilities;
	}
	
	private void recursiveEndStateFinder(ArrayList<String> allMoves, Pile community, Pile a, Pile b, String moves) {
		if (community.cards.length == 0) {
			if (a.equalTo(aEnd) && b.equalTo(bEnd)) {
				allMoves.add(moves);
			}
			return;
		}
		
		Pile newCommunityA = new Pile(community.cards);
		int newCardA = newCommunityA.pullCard();
		Pile newA = new Pile(a.cards);
		newA.addCard(newCardA);
		String newMovesA = "";
		if (moves.length() > 0) {
			newMovesA = moves + ", ";
		}
		newMovesA = newMovesA + "CA" + newCardA;
		recursiveEndStateFinder(allMoves, newCommunityA, newA, b, newMovesA);
		
		Pile newCommunityB = new Pile(community.cards);
		int newCardB = newCommunityB.pullCard();
		Pile newB = new Pile(b.cards);
		newB.addCard(newCardB);
		String newMovesB = "";
		if (moves.length() > 0) {
			newMovesB = moves + ", ";
		}
		newMovesB = newMovesB + "CB" + newCardB;
		recursiveEndStateFinder(allMoves, newCommunityB, a, newB, newMovesB);
	}
	
	private ArrayList<String> getEndStateMoves(Pile community) {
		ArrayList<String> moves = new ArrayList<String>();
		recursiveEndStateFinder(moves, community, new Pile(), new Pile(), "");
		return moves;
	}
	
	public int countWays(int[] AliceStart, int[] BobStart, int[] AliceEnd, int[] BobEnd) {
		// Store our variables
		aStart = new Pile(AliceStart);
		bStart = new Pile(BobStart);
		aEnd = new Pile(AliceEnd);
		bEnd = new Pile(BobEnd);
		
		// Figure out all possible community piles
		ArrayList<CommunityPossibility> communityPossibilities = getCommunityPossibilities(aStart, bStart);
		
		int total = 0;
		
		// Figure out all possible end states from each community pile
		System.out.println("Community possibilities:");
		for (int i = 0; i < communityPossibilities.size(); i++) {
			System.out.println(communityPossibilities.get(i).toString());
			
			ArrayList<String> endStateMoves = getEndStateMoves(communityPossibilities.get(i).pile);
			for (int j = 0; j < endStateMoves.size(); j++) {
				System.out.println("\t\t" + endStateMoves.get(j));
			}
			
			System.out.println(communityPossibilities.get(i).moves.size() + " * " + endStateMoves.size());
			total += (communityPossibilities.get(i).moves.size() * endStateMoves.size());
		}

		
		// Find which ones match the actual end state
		
		// Multiply # of unique ways to get from start to community
		// by # of unique ways to get from community to end state
		
		return total;
	}
	
	public static void main(String[] args) {
		AliceBobShuffle a = new AliceBobShuffle();
		//int result = a.countWays(new int[]{1, 3}, new int[]{2, 4}, new int[]{2, 3}, new int[]{1, 4});
		//int result = a.countWays(new int[]{1, 2, 1}, new int[]{1, 2}, new int[]{1, 2, 1}, new int[]{2, 1});
		//int result = a.countWays(new int[]{1}, new int[]{2}, new int[]{3}, new int[]{4});
		//int result = a.countWays(new int[]{1, 2}, new int[]{3}, new int[]{3}, new int[]{2, 1});
		int result = a.countWays(new int[]{3, 3, 2, 4, 1, 3, 1, 2, 1, 1, 5, 5, 1, 2, 3, 1, 2, 1, 2, 1, 1, 1, 2}, new int[]{4, 1, 4, 3, 4, 4, 4, 3, 2, 1, 6, 2, 2, 3, 2, 2, 1, 3, 2, 3}, new int[]{4, 1, 4, 3, 4, 4, 4, 1, 3, 1, 2, 2, 2, 3, 2, 1, 2, 1, 2, 2, 2}, new int[]{3, 3, 2, 3, 4, 2, 1, 2, 1, 1, 5, 6, 5, 1, 3, 1, 2, 3, 1, 1, 1, 3});
		System.out.println("result: " + result);
	}
}