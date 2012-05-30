public class ElectionFraudDiv2 {
	public String IsFraudulent(int[] percentages) {
		int lowResult = 0;
		int highResult = 0;
		int numVotes = 10000;
		
		for (int i = 0; i < percentages.length; i++) {
			if (percentages[i] > 0) {
				lowResult = lowResult + (int)(numVotes * ((float)percentages[i] - 0.5) / 100);
			}
			
			if (percentages[i] < 100) {
				highResult = highResult + (int)(numVotes * ((float)percentages[i] + 0.4999) / 100);
			} else {
				highResult = highResult + (numVotes * 100);
			}
		}
		if (lowResult > numVotes || highResult < numVotes) {
			return "YES";
		}
		return "NO";
	}
}