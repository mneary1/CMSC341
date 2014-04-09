/*
   Implements bit-reversed counter from paper.
*/


package concheap ;

/**
 * 
 * This code was supplied for us in the project description. 
 * 
 * @author Michael Neary <mneary1@umbc.edu>
 * @project CMSC 341 - Fall 2013 - Project 5
 * @section 01
 * @version Dec 10, 2013
 */
public class BRCounter {

   int counter ;
   int reversed ;
   int high_bit ;

   public BRCounter () {
      counter = 0 ;
      reversed = 0 ;
      high_bit = -1 ;
   }

   public int value() {
      return counter ;
   }

   public int revValue() {
      return reversed ;
   }


   // a line-by-line translation of the pseudocode in the paper.
   //
   public int increment() {
      int bit, mask ;

      counter ++ ;

      for (bit = high_bit - 1 ; bit >= 0 ; bit--) {
         mask = 1 << bit ;
         reversed ^= mask ;   // xor to flip
         if ( (reversed & mask) != 0 ) break ;
      }

      if ( bit < 0 ) {
         reversed = counter ;
         high_bit++ ;
      }

      return reversed ;
      
   }


   // decrement is used in deleteMax
   // There's a bug in the paper. The paper version does pre-decrement: --x
   // We really want post-decrement x--, because we want to replace
   // the root of the heap with the current "bottom" and then decrement.
   // With pre-decrement, the next to last item replaces the root.
   //
   // Otherwise this is pretty much a line by line translation from the
   // pseudocode in the paper.

   public int decrement() {
      int bit, mask ;
      int old_reversed ;

      old_reversed = reversed ;  // bug fix
      counter-- ;

      for (bit = high_bit - 1 ; bit >= 0 ; bit--) {
         mask = 1 << bit ;
         reversed ^= mask ;   // xor to flip
         if ( (reversed & mask) == 0 ) break ;
      }

      if ( bit < 0 ) {
         reversed = counter ;
         high_bit-- ;
      }

      return old_reversed ;  // bug fix
   }

}