
package hash341;

import java.io.Serializable;

/**
 * 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 4
 * @section 01
 * @version Nov 24, 2013
 */
public class City implements Comparable<City>, Serializable{

	public String name;
	public float latitude;
	public float longitude;
	
	public City(String n, float la, float lo){
		
		name = n;
		latitude = la;
		longitude = lo;
		
	}
	
	public String toString(){
		return name + " (" + Float.toString(latitude) + ", " + Float.toString(longitude) + ")";
	}
	
	public String getName(){
		return name;
	}
	
	public float getLat(){
		return latitude;
	}
	
	public float getLon(){
		return longitude;
	}
	
	public int compareTo(City c){
		
		if(this.name.compareTo(c.name) < 0){
			return -1;
		}
		else if(this.name.compareTo(c.name) == 0){
			return 0;
		}
		else{
			return 1;
		}
	}
}
