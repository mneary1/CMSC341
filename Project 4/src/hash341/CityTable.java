
package hash341;

import java.io.*;


/**
 * CityTable class
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 4
 * @section 01
 * @version Nov 24, 2013
 */
public class CityTable implements Serializable {

	private SecondaryTable[] primaryTable;
	private Hash24 h1;
	private String fname;
	private int numCities;
	private int[] collisions;
	private SecondaryTable maxTable;
	private int[] hashesTried;
	private int totalTables;
	private double avgTries;
	
	/**
	 * 
	 * @param fname
	 * @param tsize
	 */
	public CityTable(String fname, int tsize){
		this.fname = fname;
		
		primaryTable = new SecondaryTable[tsize];
		
		h1 = new Hash24();
		
		h1.dump();
		
		readFile();
		
		makeSecondaryTables();
		
		dump();
		
	}
	
	private void readFile(){
		
		File file = new File(fname);
		
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line;
			
			while((line = reader.readLine()) != null){
				
				String name = line;
				
				line = reader.readLine();
				
				String[] tokens = line.split(" ");
				
				String lat = tokens[0];
				String lon = tokens[1];

				
				float la = Float.parseFloat(lat);
				float lo = Float.parseFloat(lon);
				
				City newCity = new City(name, la, lo);
				numCities ++;
				
				int index = h1.hash(newCity.name) % primaryTable.length;
				
				if(primaryTable[index] != null){
					primaryTable[index].accept(newCity);				
				}
				else{
					primaryTable[index] = new SecondaryTable();
					primaryTable[index].accept(newCity);	
				}
			}
		}
		catch(IOException e){
			
			e.printStackTrace();
		}
		
		
		
	}
	
	private void makeSecondaryTables(){
		
		for(int i = 0; i < primaryTable.length; i++){
			
			if(primaryTable[i] != null){
				
				primaryTable[i].makeTable();
			}
		}
	}
	
	/**
	 * 
	 */
	public void dump(){
		System.out.println("Primary hash table statistics:");
		System.out.println("\tNumber of cities: " + numCities);
		System.out.println("\tTable size: " + primaryTable.length);
		
		doStatistics();
		
		System.out.println("\tMax collisions: " + maxTable.getSize());
		
		
		for(int i = 0; i < 25; i++){
			System.out.println("\t # of primary slots with " + i + " cities = " + collisions[i]);
		}
		
		System.out.println("\n*** Cities in the slot with the most collisions ***");
		System.out.println(maxTable);
		
		for(int i = 1; i < 21; i++){
			System.out.println("\t # of secondary hash tables trying " + i + " hash functions = " + hashesTried[i]);
		}
		
		System.out.println("\nNumber of secondary hash tables with more than 1 item = " + totalTables);
		System.out.println("Average # of hash functions tried = " + avgTries);
	}
	
	/**
	 * 
	 */
	public void doStatistics(){
		
		hashesTried = new int[21];
		collisions = new int[25];
		int max = Integer.MIN_VALUE;
		
		for(int i = 0; i < primaryTable.length; i++){
	
			if(primaryTable[i] != null){
				
				collisions[primaryTable[i].getSize()] ++;
				
				if(primaryTable[i].getSize() >= 2){
					hashesTried[primaryTable[i].getNumHashesTried()] ++;
				}
				
				if(primaryTable[i].getSize() > max){
					max = primaryTable[i].getSize();
					maxTable =  primaryTable[i];
				}
			}
			else
				collisions[0] ++;
		}
		
		//calculate # of secondary hash tables with more than one item
		
		totalTables = 0;
		int totalHashes = 0;
		
		for(int i = 0; i < hashesTried.length; i ++){
			totalTables += hashesTried[i];
			totalHashes += hashesTried[i] * i;
		}
		
		avgTries = (double)totalHashes / totalTables;
		
		
	}
	
	public City find(String cName){
		
		int pIndex = h1.hash(cName) % primaryTable.length;
		
		if(primaryTable[pIndex] != null){
			return primaryTable[pIndex].find(cName);
		}
		else{
			//System.out.println("*** Not Found!!!");
			return null;
		}
	}
	
	public void writeToFile(String fName){
		
		ObjectOutputStream os = null;
		
		try{
			
			FileOutputStream fs = new FileOutputStream(fName);
			
			os = new ObjectOutputStream(fs);
			
			os.writeObject(this);
			
			os.flush();
			
			os.close();
			
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(SecurityException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
       
	
	public static CityTable readFromFile(String fName){
		
		FileInputStream fs = null;
		ObjectInputStream os = null;
		
		try{
			fs = new FileInputStream(fName);
			
			os= new ObjectInputStream(fs);
			
			CityTable ct = (CityTable) os.readObject();
			
			os.close();
			
			fs.close();
			
			return ct;
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		catch(SecurityException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
