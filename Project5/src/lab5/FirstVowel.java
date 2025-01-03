package lab5;

public class FirstVowel {
	public static int indexOfFirstVowel(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if ("aeiouAEIOU".indexOf(ch) >= 0) {
                return i;
            }
        }
        return -1;
    }
	public static void main(String args[]) {
		System.out.println("Expected -1, Actual " + indexOfFirstVowel("Why"));
		System.out.println("Expected 0, Actual " + indexOfFirstVowel("Acorn"));
		System.out.println("Expected 1, Actual " + indexOfFirstVowel("Rocket"));
		System.out.println("Expected 2, Actual " + indexOfFirstVowel("Tree"));
		System.out.println("Expected 3, Actual " + indexOfFirstVowel("String"));
	}
}
