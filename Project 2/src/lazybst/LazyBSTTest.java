package lazybst;
import java.util.Random;
public class LazyBSTTest{
	
	public static void main(String[] args){
	
		LazyBST<Integer> t = new LazyBST<Integer>();
	/*	Random rand = new Random();
		for(int i = 1; i <= 10; i++){
			int x = rand.nextInt(50);
			System.out.println("Inserting "+ x + "....");
			bst.insert(x);
		}*/

		t.insert(41) ;
	      t.insert(22) ; t.insert(64) ; 
	      t.insert(14) ; t.insert(30) ; t.insert(60) ; t.insert(79) ; 
	      t.insert( 7) ; t.insert(20) ; t.insert(26) ; t.insert(37) ; 
	      t.insert(54) ; t.insert(71) ;
	      t.insert(50) ; t.insert(59) ; t.insert(75) ; 
	      t.insert(3) ; t.insert(11) ; t.insert(15) ; t.insert(24) ; 
	      t.insert( 9) ; t.insert(17) ;
	      System.out.println() ;
	      
		
		
		
		
		t.dump();
		System.out.println();
		t.insert(51);
		t.insert(52);
		t.dump();
		System.out.println();
		t.remove(41);
		//t.remove(7);
		
	   
	    System.out.println();
	    t.dump();
	    //int x = bst.span(4,7);
	    //System.out.println(x);
	    /*
	    Integer result = bst.find(2);
	    System.out.println(result);
	    if(result != null){
	    	System.out.println("Found it!");
	    }
	    else{
	    	System.out.println("Not found :(");
	    }
	
	    bst.dump(); */
	}
}
