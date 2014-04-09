package hash341;

import java.util.Scanner;

/**
 * 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 4
 * @section 01
 * @version Nov 26, 2013
 */

public class SecondProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Reading CityTable from file...");
		
		CityTable t = CityTable.readFromFile("US_Cities_LL.ser");
		
		Scanner scan = new Scanner(System.in);
		
		String input = "";
		
		while(!input.equalsIgnoreCase("q")){
			System.out.println("Enter City, State (or 'q' to quit): ");
			input = scan.nextLine();
			
			City c= t.find(input);
			
			if(c != null){
				System.out.println("Found " + c );
				System.out.println("http://www.google.com/maps?z=10&q="+c.getLat()+","+c.getLon());
			}
			else if(!input.equalsIgnoreCase("q")){
				System.out.println(input + " *** not found!!!");
			}
			
		}
		
		System.out.println("Goodbye!");
	}

}
