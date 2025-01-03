package lab7;

public class PyramidCount {
	public static int getPyramidCount(int n) {
	    if (n == 1) {
	        return 1;
	    } else {
	        int countLowerLevels = getPyramidCount(n - 1);
	        int countThisLevel = n * n;
	        return countThisLevel + countLowerLevels;
	    }
	}
	public static void main(String[] args) {
		int value = getPyramidCount(1);
		System.out.println(value);
		value = getPyramidCount(2);
		System.out.println(value);
		value = getPyramidCount(3);
		System.out.println(value);
		value = getPyramidCount(4);
		System.out.println(value);
	}
}
