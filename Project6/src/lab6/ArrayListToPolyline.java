package lab6;

import java.awt.Point;
import plotter.Plotter;
import plotter.Polyline;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListToPolyline {
	private static Polyline parseOneLine(String line) {
		Polyline shape;
		Scanner lineScanner = new Scanner(line);
		String firstValue = lineScanner.next();
		if(firstValue.contains("a") || firstValue.contains("e") || firstValue.contains("i") || firstValue.contains("o") || firstValue.contains("u") ){
			String color = firstValue;
			shape = new Polyline(color);
		}
		else {
			int width = Integer.parseInt(firstValue);
			String color = lineScanner.next();
			shape = new Polyline(color, width);
		}
		while(lineScanner.hasNextInt()) {
			int firstNum = lineScanner.nextInt();
			int secondNum = lineScanner.nextInt();
			shape.addPoint(new Point(firstNum, secondNum));
		}
		lineScanner.close();
		return shape;
	}

	/*public static void main(String[] args) {
		Plotter plotter = new Plotter();
		Polyline p = parseOneLine("red 100 100 200 100 200 200 100 200 100 100");
		plotter.plot(p);

		p = parseOneLine("2 blue 250 100 400 350 100 350 250 100");
		plotter.plot(p);
	}*/

	private static ArrayList<Polyline> readFile(String filename) throws FileNotFoundException {
		ArrayList<Polyline> list = new ArrayList<Polyline>();
		File file = new File(filename);
		Scanner fileScanner = new Scanner(file);
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			if ((line.contains("#")) || (line.isEmpty())) {
				continue;
			}
			else {
				Polyline shape = parseOneLine(line);
				list.add(shape);
			}
		}
		fileScanner.close();
		return list;
	}
	
	public static void main(String[] args) throws FileNotFoundException
	  {
	    ArrayList<Polyline> list = readFile("hello.txt");
	    Plotter plotter = new Plotter();

	    for (Polyline p : list)
	    {
	      plotter.plot(p);
	    }
	  }
}
