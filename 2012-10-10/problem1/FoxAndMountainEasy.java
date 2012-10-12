public class FoxAndMountainEasy {
	
	String[] allPaths;
	int totalPaths;
	int pathSize;
	
	int h0;
	int hn;
	
	public void getAllPaths(String pathSoFar, int h) {
		if (h < 0) {
			return;
		}
		if (pathSoFar.length() == pathSize) {
			if (h == hn) {
				allPaths[totalPaths] = pathSoFar;
				totalPaths++;
			}
			return;
		}
		
		getAllPaths(pathSoFar + "U", h+1);
		getAllPaths(pathSoFar + "D", h-1);
	}
	
	public String possible(int n, int h0, int hn, String history) {
		allPaths = new String[(int)Math.pow(2, n+1)];
		totalPaths = 0;
		this.hn = hn;
		this.pathSize = n;
		
		getAllPaths("", h0);
		
		for (int i = 0; i < totalPaths; i++) {
			//System.out.println(allPaths[i]);
			if (allPaths[i].contains(history)) {
				return "YES";
			}
		}
		return "NO";
	}
}