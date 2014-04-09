package driver;

import hash341.CityTable;

public class myTest {

	public static void main(String[] args){
		
		CityTable t = new CityTable("US_Cities_LL.txt", 16000);
		
		//System.out.println(t.find("San Francisco, CA"));
		t.writeToFile("US_Cities_LL.ser");
	}
}
