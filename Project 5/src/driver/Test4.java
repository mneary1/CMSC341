/**
 * Test4 performs a stress test on a concurrent heap.
 * it creates a random heap-ordered array of size 32738, filling it with 32737 items
 * that gives a heap with all levels filled, which it feeds into a concurrent heap
 * 
 * it then does a random series of 500 insertions and deleteMaxs (can be increased or decreased)
 * and does a sanity check of the heap after it completes that series of operations
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 5
 * @section 01
 * @version Dec 10, 2013
 */

package driver;
import java.util.Random;
import concheap.*;

public class Test4 {

	public static void main(String[] args) {
		
			int[] A = new int[32738];
			
			Random rand = new Random();
			
			int root = rand.nextInt(10000000);
			
			int decrement = rand.nextInt(1000);
			
					
			for(int i = 1; i < A.length; i++){
				
				A[i] = root;
				
				root -= decrement;
			}
			
			ConcHeap heap = new ConcHeap(A, 65536);

			for(int i = 0; i < 10; i ++){
				
				int ra = rand.nextInt(2);
				
				if(ra == 0){
					heap.insert(rand.nextInt(100000000));
				}
				else{
					heap.deleteMax();
				}
			}
			
			int tCount ;
			   do {
			      try {
					Thread.sleep(300) ;
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			      tCount = Thread.activeCount() ;
			   } while (tCount > 1) ;

			   heap.sanityCheck() ;
	}

}
