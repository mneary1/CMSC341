/**
 * Test1 performs a test of the increment and decrement methods in BRCounter.
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 5
 * @section 01
 * @version Dec 10, 2013
 */

package driver;
import concheap.*;

public class Test1 {

	public static void main(String[] args) {
		
		 BRCounter brc = new BRCounter();
		   int inc, dec;
		   for(int i = 1; i <= 31; i++){
			   inc = brc.increment();
			   System.out.println("Printing call #" + i +" of increment(): "+ inc);
		   }
		   
		   System.out.println();
		   
		   for(int i = 1; i <= 31; i++){
			   dec = brc.decrement();
			   System.out.println("Printing call #" + i +" of decrement(): "+ dec);
		   
		   }
		   
	} 
	  

}
