package lab5;
import java.util.Scanner;

public class Initials {
    public static String getInitials(String fullName) {
    	String initials = "";
    	initials += fullName.charAt(0);
    	for (int i = 0; i < fullName.length(); i++) {
            char ch = fullName.charAt(i);
            if (ch == ' ') {
                initials += fullName.charAt(i+1);
            }
        }
        return initials;
    }
    public static void main(String args[]) {
        System.out.println(Initials.getInitials("Cher"));
        System.out.println(Initials.getInitials("Edna del Humboldt von der Schooch"));
        System.out.println(Initials.getInitials("John McEnroe"));
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a name: ");
        String fullName = userInput.nextLine();
        System.out.println(Initials.getInitials(fullName));
        userInput.close();
    }
}

