package lab7;

import java.io.File;

public class FileCount {
	public static int countFiles(File file) {
	    int count = 0;
	    if (file.isDirectory()) {
	        File[] files = file.listFiles();
	        if (files != null) {
	            for (File f : files) {
	                count += countFiles(f);
	            }
	        }
	    } else {
	        count = 1;
	    }
	    return count;
	}
	
	public static void main(String[] args) {
		File dir = new File("/C:/ComSci227/Project7/src/lab7");
		int fileCount = countFiles(dir);
		System.out.println("Total number of files in the directory tree: " + fileCount);
		File dir2 = new File("/C:/ComSci227/Project7/src/lab7/FileCount");
		int fileCount2 = countFiles(dir2);
		System.out.println("Total number of files in the directory tree: " + fileCount2);
	}
}
