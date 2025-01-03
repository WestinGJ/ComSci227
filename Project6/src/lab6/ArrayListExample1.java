package lab6;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListExample1
{
  public static void main(String[] args)
  {
    String s = "3 5 7 9 12";
    ArrayList<Integer> result = readNumbers(s);
    System.out.println(result);
  }
  
  public static ArrayList<Integer> readNumbers(String text)
  {
    Scanner scanner = new Scanner(text);

    ArrayList<Integer> nums = new ArrayList<Integer>();
    
    while (scanner.hasNextInt())
    { 
      nums.add(scanner.nextInt());
    }
    scanner.close();
    return nums;  
  }
}