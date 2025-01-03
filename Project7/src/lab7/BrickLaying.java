package lab7;

public class BrickLaying {
	public static int countPatterns(int n) {
	    if (n < 0) {
	        return 0;
	    } else if (n == 0) {
	        return 1;
	    } else {
	        return countPatterns(n-1) + countPatterns(n-3);
	    }
	}
	public static void main(String[] args) {
		int feet = 5;
		int patterns = countPatterns(feet);
		System.out.println("Number of different ways to lay out bricks for a " + feet + " foot walkway: " + patterns);
		feet = 4;
		patterns = countPatterns(feet);
		System.out.println("Number of different ways to lay out bricks for a " + feet + " foot walkway: " + patterns);
		feet = 3;
		patterns = countPatterns(feet);
		System.out.println("Number of different ways to lay out bricks for a " + feet + " foot walkway: " + patterns);
		
	}
}
