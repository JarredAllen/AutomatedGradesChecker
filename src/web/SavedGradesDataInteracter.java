package web;

import java.util.Scanner;

public class SavedGradesDataInteracter {
	public static final String lastDataFile="res/txt/SavedData.txt";
	
	public static void parseAeriesHTML(String html) {
		Scanner input=new Scanner(html);
		for(int i=0;i<435;i++) {
			input.nextLine();
		}
		String line=input.nextLine();
		while(line!=null) {
			//TODO Pull needed data out of the HTML and move to the next line
			line=input.nextLine();
		}
		input.close();
	}
}
