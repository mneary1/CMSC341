/**
 * Implements a concurrent heap as described in the paper and project definitions. 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 5
 * @section 01
 * @version Dec 10, 2013
 */

package concheap;

import java.util.concurrent.locks.ReentrantLock; 

public class ConcHeap {

	public static final long EMPTY = -1 ;
	public static final long AVAILABLE = -2 ;
	
	private HeapItem[] H;
	private ReentrantLock heapLock;
	private int maxSize;
	private BRCounter count;
	
	public ConcHeap(int maximumSize){
		heapLock = new ReentrantLock(true);
		
		H = new HeapItem[maximumSize + 1];
		maxSize = maximumSize;
		count = new BRCounter();
		
		for(int i = 1; i <= maximumSize; i++){
			
			H[i] = new HeapItem();
			
		}
	}
	
	public ConcHeap(int[] A, int maximumSize){
		
		this(maximumSize);
		
		for(int i = 1; i < A.length; i++){

			H[i].priority = A[i] ;
			H[i].tag = AVAILABLE ;
			count.increment() ;   // update bit-reversed counter
			
		}
		
	}
	
	/**
	 * wrapper method for inserting a number into the heap, forks off a thread to insert the number
	 * @param x - int to insert
	 */
	public void insert(int x){
		
		InsertObject iO = new InsertObject(x);
		Thread tO = new Thread(iO);
		tO.start();
		
	}
	
	/**
	 * private class InsertObject to handle different thread inserting into the same heap
	 */
	private class InsertObject implements Runnable{
		
		int priority;
		
		public InsertObject(int x) {
			priority = x;
		}

		/**
		 * the insert is performed in run() so different threads can insert
		 */
		public void run(){
			
			int i;
			
			//Insert new item at the bottom of the heap.
			
			heapLock.lock();
			
			System.out.println("Thread " + Thread.currentThread().getId() + " got heap lock");
			i = count.increment();
			
			//lock the HeapItem at i
			H[i].itemLock.lock();
			
			//unlock the heap
			heapLock.unlock();
			
			//set priority of HeapItem at i to the number being inserted
			H[i].priority = priority;
			
			//set tag of HeapItem at i to the current thread id
			H[i].tag = Thread.currentThread().getId();
			
			//finally, unlock the HeapItem at i
			H[i].itemLock.unlock();
			
			//Move item towards top of heap while it has a higher priority
			//    than its parents
			while(i > 1){
				
				int parent, old_i;
				
				System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " getting itemLocks");
				
				parent = i / 2;
				H[parent].itemLock.lock();
				H[i].itemLock.lock();
				old_i = i;
				
				
				//add call to Thread.sleep() here, if needed
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
				
				
				if(H[parent].tag == AVAILABLE && H[i].tag == Thread.currentThread().getId()){
					
					System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " parent available");
					
					if(H[i].priority > H[parent].priority){
						
						//swap priority and tag of i and parent
						
						int tempPriority = H[i].priority;
						H[i].priority = H[parent].priority;
						H[parent].priority = tempPriority;
						
						long tempTag = H[i].tag;
						H[i].tag = H[parent].tag;
						H[parent].tag = tempTag;
						
						i = parent;
					}
					else{
						H[i].tag = AVAILABLE;
						i = 0;
					}
					
				}
				else if(H[parent].tag == EMPTY){
					
					System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " parent empty");
					
					i = 0;
				}
				else if( H[i].tag != Thread.currentThread().getId() ){
					
					System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " not my ID");
					
					i = parent;
				}
				else{
					System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " parent not available");
					//do nothing otherwise
				}
				
				H[old_i].itemLock.unlock();
				H[parent].itemLock.unlock();
				
				System.out.println("Thread " + Thread.currentThread().getId() + " (insert " + priority + "): at index " + i + " released itemLocks");
			
			} //end while

			if(i == 1){
				
				H[i].itemLock.lock();
				
				if( H[i].tag == Thread.currentThread().getId() ){
					
					H[i].tag = AVAILABLE;
				}
				
				H[i].itemLock.unlock();
				
			}
		}
	}
	
	/**
	 * wrapper method deleteMax() to delete the maximum item in the heap, forks off a thread to do so
	 */
	public void deleteMax(){
		
		DeleteObject dO = new DeleteObject();
		Thread tO = new Thread(dO);
		tO.start();
	}
	
	/**
	 * private class DeleteObject to handle the deletion in multiple threads
	 * 
	 */
	private class DeleteObject implements Runnable{
		
		/**
		 * the deletion is performed in run() so different threads can perform the action on the same heap
		 */
		public void run(){
			
			int bottom, priority, i;
			
			//Grab an item from the bottom of the heap to replace the
			//   to-be-deleted top item.
			
			System.out.println("Thread " + Thread.currentThread().getId() + " (deleteMax): getting heap lock");
			
			heapLock.lock();
			
			bottom = count.decrement();
			
			H[bottom].itemLock.lock();
			
			heapLock.unlock();
			
			priority = H[bottom].priority;
			
			H[bottom].tag = EMPTY;
			
			H[bottom].itemLock.unlock();
			
			//Lock first item. Stop if it was the only item in the heap.
			
			H[1].itemLock.lock();
			if(H[1].tag == EMPTY){
				
				H[1].itemLock.unlock();
				//not returning a value in this version, but you still want to exit the method
				//if the only item in the heap was deleted
				return;
			}
			
			//Replace the top item with the item stored from the bottom.
			
			int tempP = H[1].priority;
			H[1].priority = priority;
			priority = tempP;
			
			H[1].tag = AVAILABLE;
			
			//adjust the heap starting at the top.
			//   we always hold a lock on the item being adjusted.
			
			i = 1;
			while(i < maxSize / 2){
				
				int left,  right, child;
				
				left = i * 2;
				right = i * 2 + 1;
				
				System.out.println("Thread " + Thread.currentThread().getId() + " (deleteMax): trickling down at index " + i +". getting item locks");
			
				H[left].itemLock.lock();
				H[right].itemLock.lock();
				
				//make call to Thread.sleep() here, if needed
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				if(H[left].tag == EMPTY){
					H[right].itemLock.unlock();
					H[left].itemLock.unlock();
					break;
				}
				else if(H[right].tag == EMPTY || H[left].priority > H[right].priority){
					H[right].itemLock.unlock();
					child = left;
				}
				else{
					H[left].itemLock.unlock();
					child = right;
				}
				
				//If the child has a high priority than the parent then 
				//   swap them. IF not, swap
				if(H[child].priority > H[i].priority){
					
					//swap priority and tag, not item lock
					int temp = H[child].priority;
					H[child].priority = H[i].priority;
					H[i].priority = temp;
					
					long tempT = H[child].tag;
					H[child].tag = H[i].tag;
					H[i].tag = tempT;
					
					H[i].itemLock.unlock();
					
					i = child;
				}
				else{
					H[child].itemLock.unlock();
					break;
				}
				
				
			}
			
			H[i].itemLock.unlock();
			return;
		}
	}
	
	/**
	 * 
	 * private class HeapItem to serve as the items in the heap, because the items in the heap also need to be lockable
	 * in order for the concurrent heap to work
	 */
	private class HeapItem {
		
		// Each item in the heap has these fields.
		// Use the names in the paper to avoid confusion.
		
		int priority ;    // heap is ordered on this key.
    	long tag ;        // Thread.getId() returns long
	    ReentrantLock itemLock ;

	    HeapItem() {
	    	priority = 0 ;
	        tag = EMPTY ;

	        // *** true = we need fairness or algorithm could
	        // *** livelock and starve.
	        itemLock = new ReentrantLock(true) ;
	        }
	    }
	
	// sanityCheck()
		//
		// This method makes sure that the HeapItems in H[] are heap ordered.
		// It also checks some other heap properties.
		// It does not check all heap properties (e.g., that items are filled
		// level by level).
		//
	public void sanityCheck() {
		boolean bad = false ;
		int left, right ;

		for (int i = 1 ; i <= maxSize ; i++) {
			left = 2 * i ; 
			right = 2 * i + 1 ;
			
			if ( H[i].tag == EMPTY ) {
				
				// an empty node should not have non-empty children
				if ( left <= maxSize && H[left].tag != EMPTY ) {
					bad = true ;
					System.out.println("*** Non-empty left child of empty node at index: " + i) ;
					}
				if ( right <= maxSize && H[right].tag != EMPTY ) {
					bad = true ;
					System.out.println("*** Non-empty right child of empty node at index: " + i) ;
				}
			} 
			else {

				// check that left child is smaller than parent
				if ( left <= maxSize && (H[left].tag != EMPTY) &&  (H[left].priority > H[i].priority) ) {
					bad = true ;
					System.out.println("*** Left child bigger at index: " + i) ;
				}
				// check that right child is smaller than parent
				if ( right <= maxSize && (H[right].tag != EMPTY) &&  (H[right].priority > H[i].priority) ) {
					bad = true ;
					System.out.println("*** Right child bigger at index: " + i) ;
				}
				// if there's no left child, there shouldn't be a right child
				if ( left <= maxSize &&  right <= maxSize && H[left].tag == EMPTY && H[right].tag != EMPTY ) {
					bad = true ;
					System.out.println("*** Left child empty but right child non-empty at index: " + i) ;
				}
			}
		}  // end of for loop
		
		if (!bad) {
			System.out.println("Heap passes sanity check!!!") ;
		}
	}
}
