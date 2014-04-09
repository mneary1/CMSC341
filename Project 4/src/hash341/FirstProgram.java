/**
 * 
 */
package hash341;

/**
 * First program reads in 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 4
 * @section 01
 * @version Nov 25, 2013
 */
public class FirstProgram {

	public static void main(String[] args){
		
		/*
		 * read file, hash, print stats, write hash table to file
		 * reading, performing hash, and printing stats done at construction
		 * of CityTable object
		 * 
		 * then writes 
		 */
		
		CityTable t = new CityTable("US_Cities_LL.txt", 16000);
		
		t.writeToFile("US_Cities_LL.ser");
		
	}
}
