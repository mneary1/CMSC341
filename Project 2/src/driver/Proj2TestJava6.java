package driver ;
import lazybst.* ;
import java.util.* ;

public class Proj2TestJava6 {
   public static void main (String [] args) {

      LazyBST<Integer> t ;
      t = new LazyBST<Integer>() ;

      System.out.println("Constructing BST in Figure 1.") ;
      
      // Try example from project description
      //
      t.insert(41) ;
      t.insert(22) ; t.insert(64) ; 
      t.insert(14) ; t.insert(30) ; t.insert(60) ; t.insert(79) ; 
      t.insert( 7) ; t.insert(20) ; t.insert(26) ; t.insert(37) ; 
      t.insert(54) ; t.insert(71) ;
      t.insert(50) ; t.insert(59) ; t.insert(75) ; 
      t.insert(3) ; t.insert(11) ; t.insert(15) ; t.insert(24) ; 
      t.insert( 9) ; t.insert(17) ;
      
      t.dump() ;
      System.out.println() ;

      Integer result ;
      if ( (result = t.find(24)) != null ) {
         System.out.println("Yes, t contains 24") ;
      } else {
         System.out.println("No, t does not contain 24") ;
      }

      if ( (result = t.find(19)) != null ) {
         System.out.println("Yes, t contains 19") ;
      } else {
         System.out.println("No, t does not contain 19") ;
      }

      int count ;
      System.out.println("\nTesting span() method:") ;
      count = t.span(22,59) ;
      if (count == 9) {
         System.out.println("Yes, there are " + count + " items between 22 and 59 (inclusive).") ;
      } else {
         System.out.print("Your span() method is buggy. It claims there are ") ;
         System.out.println(count + " items between 22 and 59 (inclusive).") ;
      }

      count = t.span(17,36) ;
      if (count == 6) {
         System.out.println("Yes, there are " + count + " items between 17 and 36 (inclusive).") ;
      } else {
         System.out.print("Your span() method is buggy. It claims there are ") ;
         System.out.println(count + " items between 17 and 36 (inclusive).") ;
      }

      count = t.span(13,25) ;
      if (count == 6) {
         System.out.println("Yes, there are " + count + " items between 13 and 25 (inclusive).") ;
      } else {
         System.out.print("Your span() method is buggy. It claims there are ") ;
         System.out.println(count + " items between 13 and 25 (inclusive).") ;
      }

      count = t.span(22,35) ;
      if (count == 4) {
         System.out.println("Yes, there are " + count + " items between 22 and 35 (inclusive).") ;
      } else {
         System.out.print("Your span() method is buggy. It claims there are ") ;
         System.out.println(count + " items between 22 and 35 (inclusive).") ;
      }

      count = t.span(5,22) ;
      if (count == 8) {
         System.out.println("Yes, there are " + count + " items between 5 and 22 (inclusive).") ;
      } else {
         System.out.print("Your span() method is buggy. It claims there are ") ;
         System.out.println(count + " items between 5 and 22 (inclusive).") ;
      }



      // Adding 12 should trigger a rebalance
      //
      System.out.println("\nAdding 12 should trigger a rebalance...") ;
      t.insert(12) ;

      System.out.println("\nOutput looks like Figure 4 *PLUS* node 12: ") ;
      t.dump() ;
      System.out.println() ;

      // Removing 41 and 50 should trigger a rebalance
      //
      System.out.println("\nRemoving 41... ") ;
      result = t.remove(41) ;   // hard case for remove, 50 should become root
      if (result == 41) {
         System.out.println("Yes, 41 was removed") ;
      } else {
         System.out.println("Your remove is buggy") ;
      }

      System.out.println("\nBST after removing 41 (50 should be new root): ") ;
      t.dump() ;
      System.out.println() ;

      System.out.println("\nRemoving 50 should trigger a rebalance ") ;
      result = t.remove(50) ;  // this will be done *after* rebalance
      if (result == 50) {
         System.out.println("Yes, 50 was removed") ;
      } else {
         System.out.println("Your remove is buggy") ;
      }

      t.dump() ;
      System.out.println() ;

   }
}
