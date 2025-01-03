package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LineNumberer {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("C:/ComSci227/Project5/src/lab5/story.txt");
		Scanner scanner = new Scanner(file);
		int lineCount = 1;
		
		//File Info
		System.out.println(file.exists()); // true if the file exists
		System.out.println(file.getName()); // name of the file
		System.out.println(file.getAbsolutePath()); // absolute path to the file
		System.out.println(file.length()); // size of the file
		
		//Loop that reads the file line by line
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.print(lineCount + " ");
			int count = 0;
			char letter[] = new char[line.length()];
			//Loop that counts the number of words
			for (int i = 0; i < line.length(); i++) {
				letter[i] = line.charAt(i);
				if (((i > 0) && (letter[i] != ' ') && (letter[i - 1] == ' ')) || ((letter[0] != ' ') && (i == 0)))
					count++;
			}
			//Output
			System.out.println(line + "       Words: " + count);
			lineCount += 1;
		}
		scanner.close();
	}
}
