package lab7;

public class FindMax {
	public static int findMax(int[] arr, int start, int end) {
	    if (start == end) {
	        return arr[start];
	    }
	    int mid = (start + end) / 2;
	    int maxLeft = findMax(arr, start, mid);
	    int maxRight = findMax(arr, mid + 1, end);
	    return Math.max(maxLeft, maxRight);
	}
	public static void main(String[] args) {
		int array[] = {0,9,1,4,6};
		int max = findMax(array, 0, array.length-1);
		System.out.println(max);
		int array2[] = {3,9,1,16,6,8,0,2};
		max = findMax(array2, 0, array2.length-1);
		System.out.println(max);
		int array3[] = {20,20,15,2,1};
		max = findMax(array3, 0, array3.length-1);
		System.out.println(max);
		int array4[] = {0,0,1,0};
		max = findMax(array4, 0, array4.length-1);
		System.out.println(max);
	}
}
