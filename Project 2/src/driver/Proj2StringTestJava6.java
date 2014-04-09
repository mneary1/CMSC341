package driver ;
import lazybst.* ;
import java.util.* ;


public class Proj2StringTestJava6 {
   public static void main (String [] args) {

      LazyBST<String> groceryList = new LazyBST<String>() ;

      System.out.println("Constructing grocery list.") ;
      
      // Try example from project description
      //
      groceryList.insert("Apple") ;
      groceryList.insert("Banana") ;
      groceryList.insert("Coca Cola") ;
      groceryList.insert("Donuts") ;
      groceryList.insert("Eggs") ;
      groceryList.insert("Fettucini") ;
      groceryList.insert("Green Tea") ;
      groceryList.insert("Ham") ;
      groceryList.insert("Ice Cream") ;
      groceryList.insert("Jelly") ;
      groceryList.insert("Kit Kat") ;
      groceryList.insert("Lemons") ;
      groceryList.insert("Marinara Sauce") ;
      groceryList.insert("Nutella") ;
      groceryList.insert("Oreos") ;
      groceryList.insert("Pretzels") ;
      groceryList.insert("Quaker Oats") ;
      groceryList.insert("Rice") ;
      groceryList.insert("Spaghetti") ;
      groceryList.insert("Tuna Fish") ;
      groceryList.insert("Unsalted Butter") ;
      groceryList.insert("Vinegar") ;
      groceryList.insert("Worcestershire Sauce") ;
      groceryList.insert("Xanax") ;
      groceryList.insert("Yams") ;
      groceryList.insert("Ziti") ;
      
      groceryList.dump() ;
      System.out.println() ;

      String result ;
      result = groceryList.find("Kit Kat") ;

      if (result != null) {
         System.out.println("We found " +  result) ;
      }


   }
}
