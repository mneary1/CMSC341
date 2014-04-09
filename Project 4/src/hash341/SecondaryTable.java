

/**
 * 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 4
 * @section 01
 * @version Nov 25, 2013
 */

package hash341;

import java.io.Serializable;
import java.util.ArrayList;

public class SecondaryTable implements Serializable {

	/**
	 * 
	 */

	private ArrayList<City> cities;
	
	private City[] cityTable;
	
	private Hash24 h2;
	
	private int numHashesTried;
	
	public SecondaryTable(){
		
		this.cities = new ArrayList<City>();
		
	}
	
	public void accept(City c){
		cities.add(c);
		
	}
	
	public int getSize(){
		return cities.size();
	}
	
	public void makeTable(){
		
		int numCities = cities.size();
		
		boolean isCollision;
		
		numHashesTried = 0;
		//while loop
		while(true){
			
			isCollision = false;
			
			
			// choose hash function
			h2 = new Hash24();
			
			// make array of N^2 items
			cityTable = new City[(int) Math.pow(numCities, 2)];
		
			for(int i = 0; i < numCities; i++){
				
				// find the hash value of the item in the ArrayList at i
				int index = h2.hash(cities.get(i).getName()) % cityTable.length;
				
				//check for a collision at the hash value
				if(cityTable[index] != null){
					
					isCollision = true;
					break;
				}
				else{
					cityTable[index] = cities.get(i);
				}
				
			}
			numHashesTried ++;
			if(!isCollision) break;
			
			
		}
	} 
	
	public int getNumHashesTried(){
		return numHashesTried;
	}
	
	public City[] getTable(){
		return cityTable;
	}
	
	public String toString(){
		String s = "";
		
		for(int i = 0; i < cityTable.length; i++){
			if(cityTable[i] != null){
				String a = cityTable[i].toString();
				s += "\t" + a + "\n";
			}
		}
		
		return s;
	}
	
	public City find(String cName){
		
		int sIndex = h2.hash(cName) % cityTable.length;
		
		if(cityTable[sIndex] != null){
			
			/*
			 * if you try to find an invalid name, there is a small chance that 
			 * there is something that hashed to the same spot that the invalid name
			 * would have hashed to, so do a quick comparison to make sure 
			 * you're returning the right city
			 * 
			 */
			if( cityTable[sIndex].getName().compareTo(cName) == 0){
				return cityTable[sIndex];
			}
		}
		
		//System.out.println("*** Not Found!!!");
		return null;
		
		
		
	}
}
